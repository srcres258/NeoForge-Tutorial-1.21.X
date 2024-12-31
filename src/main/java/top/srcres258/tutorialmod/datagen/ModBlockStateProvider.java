package top.srcres258.tutorialmod.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.block.ModBlocks;
import top.srcres258.tutorialmod.block.custom.BismuthLampBlock;
import top.srcres258.tutorialmod.block.custom.GojiBerryBushBlock;
import top.srcres258.tutorialmod.block.custom.RadishCropBlock;

import java.util.function.Function;
import java.util.stream.Stream;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TutorialMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.BISMUTH_BLOCK);

        blockWithItem(ModBlocks.BISMUTH_ORE);
        blockWithItem(ModBlocks.BISMUTH_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.BISMUTH_END_ORE);
        blockWithItem(ModBlocks.BISMUTH_NETHER_ORE);

        blockWithItem(ModBlocks.MAGIC_BLOCK);

        stairsBlock(ModBlocks.BISMUTH_STAIRS.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
        slabBlock(ModBlocks.BISMUTH_SLAB.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()),
                blockTexture(ModBlocks.BISMUTH_BLOCK.get()));

        buttonBlock(ModBlocks.BISMUTH_BUTTON.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
        pressurePlateBlock(ModBlocks.BISMUTH_PRESSURE_PLATE.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));

        fenceBlock(ModBlocks.BISMUTH_FENCE.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
        fenceGateBlock(ModBlocks.BISMUTH_FENCE_GATE.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));
        wallBlock(ModBlocks.BISMUTH_WALL.get(), blockTexture(ModBlocks.BISMUTH_BLOCK.get()));

        doorBlockWithRenderType(ModBlocks.BISMUTH_DOOR.get(),
                modLoc("block/bismuth_door_bottom"),
                modLoc("block/bismuth_door_top"),
                "cutout");
        trapdoorBlockWithRenderType(ModBlocks.BISMUTH_TRAPDOOR.get(), modLoc("block/bismuth_trapdoor"),
                true, "cutout");

        blockItem(ModBlocks.BISMUTH_STAIRS);
        blockItem(ModBlocks.BISMUTH_SLAB);
        blockItem(ModBlocks.BISMUTH_PRESSURE_PLATE);
        blockItem(ModBlocks.BISMUTH_FENCE_GATE);
        blockItem(ModBlocks.BISMUTH_TRAPDOOR, "_bottom");

        customLamp();

        makeCrop(((CropBlock) ModBlocks.RADISH_CROP.get()), "radish_crop_stage", "radish_crop_stage");

        makeBush(((GojiBerryBushBlock) ModBlocks.GOJI_BERRY_BUSH.get()), "goji_berry_bush_stage", "goji_berry_bush_stage");

        logBlock(((RotatedPillarBlock) ModBlocks.BLOODWOOD_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.BLOODWOOD_WOOD.get()),
                blockTexture(ModBlocks.BLOODWOOD_LOG.get()),
                blockTexture(ModBlocks.BLOODWOOD_LOG.get()));

        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_BLOODWOOD_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_BLOODWOOD_WOOD.get()),
                blockTexture(ModBlocks.STRIPPED_BLOODWOOD_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_BLOODWOOD_LOG.get()));

        blockItem(ModBlocks.BLOODWOOD_LOG);
        blockItem(ModBlocks.BLOODWOOD_WOOD);
        blockItem(ModBlocks.STRIPPED_BLOODWOOD_LOG);
        blockItem(ModBlocks.STRIPPED_BLOODWOOD_WOOD);

        blockWithItem(ModBlocks.BLOODWOOD_PLANKS);
        leavesBlock(ModBlocks.BLOODWOOD_LEAVES);

        saplingBlock(ModBlocks.BLOODWOOD_SAPLING);
    }

    private void saplingBlock(DeferredBlock<Block> blockRegObj) {
        simpleBlock(blockRegObj.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(blockRegObj.get()).getPath(),
                                blockTexture(blockRegObj.get()))
                        .renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> blockRegObj) {
        simpleBlockWithItem(blockRegObj.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(blockRegObj.get()).getPath(),
                                ResourceLocation.parse("minecraft:block/leaves"),
                                "all",
                                blockTexture(blockRegObj.get()))
                        .renderType("cutout"));
    }

    public void makeBush(SweetBerryBushBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, String modelName, String textureName) {
        var models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(GojiBerryBushBlock.AGE),
                ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "block/" + textureName + state.getValue(GojiBerryBushBlock.AGE)))
                .renderType("cutout"));

        return models;
    }

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        var models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((RadishCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "block/" + textureName + state.getValue(((RadishCropBlock) block).getAgeProperty())))
                .renderType("cutout"));

        return models;
    }

    private void customLamp() {
        var lampNameFunc = Function.<Boolean>identity()
                .andThen(clicked -> clicked ? "bismuth_lamp_on" : "bismuth_lamp_off");

        getVariantBuilder(ModBlocks.BISMUTH_LAMP.get()).forAllStates(state ->
                Stream.of(state.getValue(BismuthLampBlock.CLICKED))
                        .map(lampNameFunc)
                        .map(name -> new ConfiguredModel[] {
                                new ConfiguredModel(models().cubeAll(name, ResourceLocation.fromNamespaceAndPath(
                                        TutorialMod.MOD_ID, "block/" + name)))
                        })
                        .findAny()
                        .get());

        simpleBlockItem(ModBlocks.BISMUTH_LAMP.get(), models().cubeAll(lampNameFunc.apply(true),
                ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "block/" + lampNameFunc.apply(true))));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(
                TutorialMod.MOD_ID + ":block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(
                TutorialMod.MOD_ID + ":block/" + deferredBlock.getId().getPath() + appendix));
    }
}
