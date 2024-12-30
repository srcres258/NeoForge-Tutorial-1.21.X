package top.srcres258.tutorialmod.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.enchantment.custom.LightningStrikerEnchantmentEffect;

import java.util.function.Supplier;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENCHANTMENT_ENTITY_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, TutorialMod.MOD_ID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> LIGHTNING_STRIKER =
            ENCHANTMENT_ENTITY_EFFECTS.register("lightning_striker",
                    () -> LightningStrikerEnchantmentEffect.CODEC);


    public static void register(IEventBus eventBus) {
        ENCHANTMENT_ENTITY_EFFECTS.register(eventBus);
    }
}
