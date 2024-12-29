package top.srcres258.tutorialmod.util;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.component.ModDataComponents;
import top.srcres258.tutorialmod.item.ModItems;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.CHISEL.get(), ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "used"),
                (stack, level, entity, seed) -> stack.get(ModDataComponents.COORDINATES) != null ? 1F : 0F);
    }
}
