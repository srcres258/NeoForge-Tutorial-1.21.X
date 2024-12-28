package top.srcres258.tutorialmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends DiggerItem {
    public HammerItem(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    public static List<BlockPos> getBlockToBeDestroyed(int range, BlockPos initialBlockPos, ServerPlayer player) {
        var positions = new ArrayList<BlockPos>();

        var traceResult = player.level().clip(new ClipContext(player.getEyePosition(1F),
                player.getEyePosition(1F).add(player.getViewVector(1F).scale(6F)),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        if (traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if (traceResult.getDirection() == Direction.DOWN || traceResult.getDirection() == Direction.UP) {
            for (var x = -range; x <= range; x++) {
                for (var z = -range; z <= range; z++) {
                    positions.add(new BlockPos(initialBlockPos.getX() + x,
                            initialBlockPos.getY(),
                            initialBlockPos.getZ() + z));
                }
            }
        }

        if (traceResult.getDirection() == Direction.NORTH || traceResult.getDirection() == Direction.SOUTH) {
            for (var x = -range; x <= range; x++) {
                for (var y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initialBlockPos.getX() + x,
                            initialBlockPos.getY() + y,
                            initialBlockPos.getZ()));
                }
            }
        }

        if (traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST) {
            for (var y = -range; y <= range; y++) {
                for (var z = -range; z <= range; z++) {
                    positions.add(new BlockPos(initialBlockPos.getX(),
                            initialBlockPos.getY() + y,
                            initialBlockPos.getZ() + z));
                }
            }
        }

        return positions;
    }
}
