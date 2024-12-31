package top.srcres258.tutorialmod.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.block.ModBlocks;

import java.util.List;

/*
* Concepts on how Minecraft and NeoForge do worldgen:
*
* Steps:
*     ConfiguredFeatures (CF) -> PlacedFeatures (PF) -> BiomeModifiers (BM)
*
* Take the generation of flowers as an example, steps being like:
*     1. ConfiguredFeatures: How to place the flowers, i.e. the method and approach to place down the flowers.
*     2. PlacedFeatures: Where do I place the flowers, and how many flowers should I place down.
*     3. BiomeModifiers: At what stage of generation am I going to place down the flowers that have been decided
*                        to do so according to the two steps above.
*/

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_BISMUTH_ORE_KEY =
            registerKey("bismuth_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_BISMUTH_ORE_KEY =
            registerKey("nether_bismuth_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_BISMUTH_ORE_KEY =
            registerKey("end_bismuth_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLOODWOOD_KEY =
            registerKey("bloodwood");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        var stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        var deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        var netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        var endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        var overworldBismuthOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.BISMUTH_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.BISMUTH_DEEPSLATE_ORE.get().defaultBlockState())
        );

        register(context, OVERWORLD_BISMUTH_ORE_KEY, Feature.ORE, new OreConfiguration(overworldBismuthOres, 9));
        register(context, NETHER_BISMUTH_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplaceables,
                ModBlocks.BISMUTH_NETHER_ORE.get().defaultBlockState(), 9));
        register(context, END_BISMUTH_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables,
                ModBlocks.BISMUTH_NETHER_ORE.get().defaultBlockState(), 9));

        register(context, BLOODWOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BLOODWOOD_LOG.get()),
                new FancyTrunkPlacer(4, 4, 3),
                BlockStateProvider.simple(ModBlocks.BLOODWOOD_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(3), 3),
                new TwoLayersFeatureSize(1, 0, 2)
        ).dirt(BlockStateProvider.simple(Blocks.NETHERRACK)).build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, name));
    }

    private static
    <FC extends FeatureConfiguration, F extends Feature<FC>>
    void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC configuration
    ) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
