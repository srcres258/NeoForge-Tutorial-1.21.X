package top.srcres258.tutorialmod.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import top.srcres258.tutorialmod.TutorialMod;

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
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

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
