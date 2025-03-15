package top.srcres258.tutorialmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.srcres258.tutorialmod.item.ModItems;
import top.srcres258.tutorialmod.screen.custom.GrowthChamberMenu;

public class GrowthChamberBlockEntity extends BlockEntity implements MenuProvider {
    public static final ItemStack RECIPE_OUTPUT = new ItemStack(ModItems.BISMUTH.get(), 8);

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            if (level != null) {
                setChanged();
                if (!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int i) {
            return switch (i) {
                case 0 -> progress;
                case 1 -> maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int i, int value) {
            switch (i) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return itemHandler.getSlots();
        }
    };
    private int progress = 0;
    private int maxProgress = 72;

    public GrowthChamberBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.GROWTH_CHAMBER_BE.get(), pos, blockState);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.tutorialmod.growth_chamber");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new GrowthChamberMenu(i, inventory, this, data);
    }

    public void drops() {
        if (level != null) {
            var inventory = new SimpleContainer(itemHandler.getSlots());
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                inventory.setItem(i, itemHandler.getStackInSlot(i));
            }

            Containers.dropContents(level, worldPosition, inventory);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("growth_chamber.progress", progress);
        tag.putInt("growth_chamber.max_progress", maxProgress);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);

        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("growth_chamber.progress");
        maxProgress = tag.getInt("growth_chamber.max_progress");
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private boolean hasRecipe() {
        // Hardcode the recipe temporarily; will replace this with recipe data from
        // datapacks' implementation inside Minecraft in the future.

        var output = RECIPE_OUTPUT;

        return itemHandler.getStackInSlot(INPUT_SLOT).is(ModItems.RAW_BISMUTH) &&
                canInsertAmountIntoOutputSlot(output.getCount()) &&
                canInsertItemIntoOutputSlot(output);
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    /**
     * Consumes input materials of current recipe from the input slot, and inserts the output of
     * current recipe into the output slot.
     * <p>
     * Be **sure** to check whether the current recipe is legal before calling this method, e.g.
     * whether the amount in the input slot is affordable for this recipe and whether the output
     * slot is capable of the coming-out output of the recipe.
     */
    private void craftItem() {
        var output = RECIPE_OUTPUT;

        itemHandler.extractItem(INPUT_SLOT, 1, false);
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private void resetProgress() {
        progress = 0;
        maxProgress = 72; // Reset max progress as well in order to get rid of unexpected situations.
    }

    private boolean canInsertItemIntoOutputSlot(@NotNull ItemStack output) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int amount) {
        int maxAmount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 :
                itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentAmount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxAmount >= currentAmount + amount;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
