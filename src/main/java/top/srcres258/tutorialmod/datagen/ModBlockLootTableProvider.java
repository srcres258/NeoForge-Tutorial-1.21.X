package top.srcres258.tutorialmod.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import top.srcres258.tutorialmod.block.ModBlocks;
import top.srcres258.tutorialmod.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.BISMUTH_BLOCK.get());

        add(ModBlocks.BISMUTH_ORE.get(), block -> createOreDrop(
                ModBlocks.BISMUTH_ORE.get(), ModItems.RAW_BISMUTH.get()));
        add(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(), block -> createMultipleOreDrops(
                ModBlocks.BISMUTH_DEEPSLATE_ORE.get(), ModItems.RAW_BISMUTH.get(),
                2F, 5F));

        dropSelf(ModBlocks.BISMUTH_STAIRS.get());
        add(ModBlocks.BISMUTH_SLAB.get(), block -> createSlabItemTable(ModBlocks.BISMUTH_SLAB.get()));

        dropSelf(ModBlocks.BISMUTH_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.BISMUTH_BUTTON.get());

        dropSelf(ModBlocks.BISMUTH_FENCE.get());
        dropSelf(ModBlocks.BISMUTH_FENCE_GATE.get());
        dropSelf(ModBlocks.BISMUTH_WALL.get());
        dropSelf(ModBlocks.BISMUTH_TRAPDOOR.get());

        add(ModBlocks.BISMUTH_DOOR.get(), block -> createDoorTable(ModBlocks.BISMUTH_DOOR.get()));
    }

    protected LootTable.Builder createMultipleOreDrops(
            Block pBlock,
            Item pItem,
            float pMinDrops,
            float pMaxDrops
    ) {
        var registryLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        return createSilkTouchDispatchTable(pBlock, applyExplosionDecay(pBlock, LootItem.lootTableItem(pItem)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(pMinDrops, pMaxDrops)))
                .apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
