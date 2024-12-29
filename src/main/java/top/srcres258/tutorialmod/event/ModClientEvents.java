package top.srcres258.tutorialmod.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.item.ModItems;

@EventBusSubscriber(modid = TutorialMod.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onComputeFovModifier(ComputeFovModifierEvent event) {
        if (event.getPlayer().isUsingItem()
                && event.getPlayer().getUseItem().getItem() == ModItems.KAUPEN_BOW.get()) {
            var fovModifier = 1F;
            var ticksUsingItem = event.getPlayer().getTicksUsingItem();
            var deltaTicks = ((float) ticksUsingItem) / 20F;
            if (deltaTicks > 1F) {
                deltaTicks = 1F;
            } else {
                deltaTicks *= deltaTicks;
            }
            fovModifier *= 1F - deltaTicks * 0.15F;
            event.setNewFovModifier(fovModifier);
        }
    }
}
