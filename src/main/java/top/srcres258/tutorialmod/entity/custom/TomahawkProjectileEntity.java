package top.srcres258.tutorialmod.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import top.srcres258.tutorialmod.entity.ModEntities;
import top.srcres258.tutorialmod.item.ModItems;

public class TomahawkProjectileEntity extends AbstractArrow {
    private float rotation;
    public Vec2 groundedOffset;

    public TomahawkProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public TomahawkProjectileEntity(LivingEntity shooter, Level level) {
        super(ModEntities.TOMAHAWK.get(), shooter, level, new ItemStack(ModItems.TOMAHAWK.get()), null);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.TOMAHAWK.get());
    }

    public float getRenderingRotation() {
        rotation += 0.5F;
        if (rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public boolean isGrounded() {
        return inGround;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        var entity = result.getEntity();
        entity.hurt(damageSources().thrown(this, getOwner()), 4);

        if (!level().isClientSide()) {
            level().broadcastEntityEvent(this, ((byte) 3));
            discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        groundedOffset = switch (result.getDirection()) {
            case SOUTH -> new Vec2(215F, 180F);
            case NORTH -> new Vec2(215F, 0F);
            case EAST -> new Vec2(215F, -90F);
            case WEST -> new Vec2(215F, 90F);
            case DOWN -> new Vec2(115F, 180F);
            case UP -> new Vec2(285F, 180F);
        };
    }
}
