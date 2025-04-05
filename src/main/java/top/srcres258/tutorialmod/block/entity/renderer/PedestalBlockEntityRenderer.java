package top.srcres258.tutorialmod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.CropBlock;
import org.jetbrains.annotations.NotNull;
import top.srcres258.tutorialmod.block.entity.PedestalBlockEntity;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    public PedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(
            @NotNull PedestalBlockEntity blockEntity,
            float partialTick,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
    ) {
        var itemRenderer = Minecraft.getInstance().getItemRenderer();
        var blockRenderer = Minecraft.getInstance().getBlockRenderer();
        var stack = blockEntity.inventory.getStackInSlot(0);

        var level = blockEntity.getLevel();
        if (level != null) {
            if (stack.getItem() instanceof BlockItem item && item.getBlock() instanceof CropBlock block) {
                var scalingFactor = Mth.abs(Mth.sin(blockEntity.getRenderingRotation() / 180.0F * (float) Math.PI)) * 0.75F;

                poseStack.pushPose();
                poseStack.translate(0.5F, 0.0F, 0.5F);
                poseStack.scale(scalingFactor, scalingFactor, scalingFactor);
                poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getRenderingRotation()));
                poseStack.translate(0.0F, 1.0F / scalingFactor, 0.0F);
                poseStack.translate(-0.5F, 0.0F, -0.5F);

                var maxAgeState = block.getStateForAge(block.getMaxAge());
                blockRenderer.renderBatched(maxAgeState, blockEntity.getBlockPos(), level, poseStack,
                        bufferSource.getBuffer(RenderType.CUTOUT), false, level.getRandom());

                poseStack.popPose();
            } else {
                poseStack.pushPose();
                poseStack.translate(0.5F, 1.15F, 0.5F);
                poseStack.scale(0.5F, 0.5F, 0.5F);
                poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getRenderingRotation()));

                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED,
                        getLightLevel(level, blockEntity.getBlockPos()),
                        OverlayTexture.NO_OVERLAY, poseStack, bufferSource, level, 1);

                poseStack.popPose();
            }
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
