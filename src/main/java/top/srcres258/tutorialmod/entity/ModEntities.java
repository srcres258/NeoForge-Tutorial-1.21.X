package top.srcres258.tutorialmod.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.entity.custom.GeckoEntity;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, TutorialMod.MOD_ID);

    public static final Supplier<EntityType<GeckoEntity>> GECKO =
            ENTITY_TYPES.register("gecko", () -> EntityType.Builder.of(GeckoEntity::new, MobCategory.CREATURE)
                    .sized(0.75F, 0.35F)
                    .build("gecko"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
