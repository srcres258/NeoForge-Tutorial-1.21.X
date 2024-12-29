package top.srcres258.tutorialmod.trim;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.neoforged.neoforge.registries.DeferredItem;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.item.ModItems;

public class ModTrimPatterns {
    public static final ResourceKey<TrimPattern> KAUPEN = ResourceKey.create(Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "kaupen"));

    public static void bootstrap(BootstrapContext<TrimPattern> context) {
        register(context, ModItems.KAUPEN_SMITHING_TEMPLATE, KAUPEN);
    }

    private static void register(
            BootstrapContext<TrimPattern> context,
            DeferredItem<Item> item,
            ResourceKey<TrimPattern> key
    ) {
        var trimPattern = new TrimPattern(key.location(), item.getDelegate(),
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())),
                false);
        context.register(key, trimPattern);
    }
}
