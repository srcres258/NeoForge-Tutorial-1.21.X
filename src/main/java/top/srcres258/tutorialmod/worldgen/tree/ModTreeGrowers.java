package top.srcres258.tutorialmod.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.worldgen.ModConfiguredFeatures;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower BLOODWOOD = new TreeGrower(TutorialMod.MOD_ID + ":bloodwood",
            Optional.empty(), Optional.of(ModConfiguredFeatures.BLOODWOOD_KEY), Optional.empty());

}
