package top.srcres258.tutorialmod.datagen;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.block.ModBlocks;
import top.srcres258.tutorialmod.item.ModItems;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = Util.make(
            new LinkedHashMap<>(), map -> {
                map.put(TrimMaterials.QUARTZ, 0.1F);
                map.put(TrimMaterials.IRON, 0.2F);
                map.put(TrimMaterials.NETHERITE, 0.3F);
                map.put(TrimMaterials.REDSTONE, 0.4F);
                map.put(TrimMaterials.COPPER, 0.5F);
                map.put(TrimMaterials.GOLD, 0.6F);
                map.put(TrimMaterials.EMERALD, 0.7F);
                map.put(TrimMaterials.DIAMOND, 0.8F);
                map.put(TrimMaterials.LAPIS, 0.9F);
                map.put(TrimMaterials.AMETHYST, 1.0F);
            });

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TutorialMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.BISMUTH.get());
        basicItem(ModItems.RAW_BISMUTH.get());

        basicItem(ModItems.CHISEL.get());
        basicItem(ModItems.RADISH.get());
        basicItem(ModItems.STARLIGHT_ASHES.get());
        basicItem(ModItems.FROSTFIRE_ICE.get());

        buttonItem(ModBlocks.BISMUTH_BUTTON, ModBlocks.BISMUTH_BLOCK);
        fenceItem(ModBlocks.BISMUTH_FENCE, ModBlocks.BISMUTH_BLOCK);
        wallItem(ModBlocks.BISMUTH_WALL, ModBlocks.BISMUTH_BLOCK);

        basicItem(ModBlocks.BISMUTH_DOOR.asItem());

        handheldItem(ModItems.BISMUTH_SWORD);
        handheldItem(ModItems.BISMUTH_PICKAXE);
        handheldItem(ModItems.BISMUTH_SHOVEL);
        handheldItem(ModItems.BISMUTH_AXE);
        handheldItem(ModItems.BISMUTH_HOE);

        handheldItem(ModItems.BISMUTH_HAMMER);

        trimmedArmorItem(ModItems.BISMUTH_HELMET);
        trimmedArmorItem(ModItems.BISMUTH_CHESTPLATE);
        trimmedArmorItem(ModItems.BISMUTH_LEGGINGS);
        trimmedArmorItem(ModItems.BISMUTH_BOOTS);
    }

    private void trimmedArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final var MOD_ID = TutorialMod.MOD_ID;

        if (itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                var trimValue = value;

                var armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                var armorItemPath = armorItem.toString();
                var trimPath = "trim/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                var currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                var armorItemResLoc = ResourceLocation.parse(armorItemPath);
                var trimResLoc = ResourceLocation.parse(trimPath);
                var trimNameResLoc = ResourceLocation.parse(currentTrimName);

                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                withExistingParent(itemDeferredItem.getId().getPath(), mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue)
                        .end()
                        .texture("layer0", ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
