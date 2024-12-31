package top.srcres258.tutorialmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.block.ModBlocks;
import top.srcres258.tutorialmod.item.ModItems;
import top.srcres258.tutorialmod.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagLookup<Block>> blockTags,
            @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, blockTags, TutorialMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.BISMUTH.get())
                .add(ModItems.RAW_BISMUTH.get())
                .add(Items.COAL)
                .add(Items.STICK)
                .add(Items.COMPASS);

        tag(ItemTags.SWORDS).add(ModItems.BISMUTH_SWORD.get());
        tag(ItemTags.PICKAXES).add(ModItems.BISMUTH_PICKAXE.get());
        tag(ItemTags.SHOVELS).add(ModItems.BISMUTH_SHOVEL.get());
        tag(ItemTags.AXES).add(ModItems.BISMUTH_AXE.get());
        tag(ItemTags.HOES).add(ModItems.BISMUTH_HOE.get());

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.BISMUTH_HELMET.get())
                .add(ModItems.BISMUTH_CHESTPLATE.get())
                .add(ModItems.BISMUTH_LEGGINGS.get())
                .add(ModItems.BISMUTH_BOOTS.get());

        tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.BISMUTH.get());

        tag(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.KAUPEN_SMITHING_TEMPLATE.get());

        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.BLOODWOOD_LEAVES.asItem())
                .add(ModBlocks.BLOODWOOD_WOOD.asItem())
                .add(ModBlocks.STRIPPED_BLOODWOOD_LOG.asItem())
                .add(ModBlocks.STRIPPED_BLOODWOOD_WOOD.asItem());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.BLOODWOOD_PLANKS.asItem());
    }
}
