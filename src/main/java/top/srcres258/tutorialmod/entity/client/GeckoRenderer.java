package top.srcres258.tutorialmod.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.entity.GeckoVariant;
import top.srcres258.tutorialmod.entity.custom.GeckoEntity;

import java.util.Map;
import java.util.function.Function;

public class GeckoRenderer extends MobRenderer<GeckoEntity, GeckoModel<GeckoEntity>> {
    private static final Map<GeckoVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GeckoVariant.class), map -> {
                Function<String, ResourceLocation> genResLoc = color ->
                        ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID,
                                "textures/entity/gecko/gecko_" + color + ".png");

                map.put(GeckoVariant.BLUE, genResLoc.apply("blue"));
                map.put(GeckoVariant.GREEN, genResLoc.apply("green"));
                map.put(GeckoVariant.PINK, genResLoc.apply("pink"));
                map.put(GeckoVariant.BROWN, genResLoc.apply("brown"));
            });

    public GeckoRenderer(EntityRendererProvider.Context context) {
        super(context, new GeckoModel<>(context.bakeLayer(GeckoModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public ResourceLocation getTextureLocation(GeckoEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(GeckoEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.45F, 0.45F, 0.45F);
        } else {
            poseStack.scale(1F, 1F, 1F);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
