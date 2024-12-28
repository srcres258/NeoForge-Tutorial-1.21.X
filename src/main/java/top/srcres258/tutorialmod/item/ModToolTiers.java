package top.srcres258.tutorialmod.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import top.srcres258.tutorialmod.util.ModTags;

public class ModToolTiers {
    public static final Tier BISMUTH = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_BISMUTH_TOOL,
            1400, 4F, 3F, 28, () -> Ingredient.of(ModItems.BISMUTH));
}
