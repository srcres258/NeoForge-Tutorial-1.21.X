package top.srcres258.tutorialmod.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.block.ModBlocks;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> BISMUTH_ORE_PLACED_KEY =
            registerKey("bismuth_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_BISMUTH_ORE_PLACED_KEY =
            registerKey("nether_bismuth_ore_placed");
    public static final ResourceKey<PlacedFeature> END_BISMUTH_ORE_PLACED_KEY =
            registerKey("end_bismuth_ore_placed");

    public static final ResourceKey<PlacedFeature> BLOODWOOD_PLACED_KEY =
            registerKey("bloodwood_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, BISMUTH_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_BISMUTH_ORE_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64),
                                VerticalAnchor.absolute(80))));
        register(context, NETHER_BISMUTH_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_BISMUTH_ORE_KEY),
                ModOrePlacements.commonOrePlacement(9,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                                VerticalAnchor.absolute(80))));
        register(context, END_BISMUTH_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.END_BISMUTH_ORE_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                                VerticalAnchor.absolute(80))));

        // Some points that should have been noticed here:
        // 1. If sapling is not given here then the tree would have spawned everywhere!
        //    Consequently not recommended to remove the sapling here.
        // 2. The `chance` parameter within the countExtra method must meet the requirement that the result it divides
        //    1.0 **must** be finite decimal. Otherwise an exception will be thrown such that Minecraft will **not**
        //    accept this kind of value.
        register(context, BLOODWOOD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BLOODWOOD_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1F, 2),
                        ModBlocks.BLOODWOOD_SAPLING.get()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, name));
    }

    private static void register(
            BootstrapContext<PlacedFeature> context,
            ResourceKey<PlacedFeature> key,
            Holder<ConfiguredFeature<?, ?>> configuration,
            List<PlacementModifier> modifiers
    ) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
