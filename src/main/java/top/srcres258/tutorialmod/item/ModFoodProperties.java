package top.srcres258.tutorialmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties RADISH = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.25F)
            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 400), 0.35F)
            .build();

    public static final FoodProperties GOJI_BERRY = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.15F)
            .fast()
            .build();
}
