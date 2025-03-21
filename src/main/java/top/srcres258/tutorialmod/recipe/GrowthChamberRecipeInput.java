package top.srcres258.tutorialmod.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public record GrowthChamberRecipeInput(ItemStack input) implements RecipeInput {
    @Override
    public @NotNull ItemStack getItem(int i) {
        return input;
    }

    @Override
    public int size() {
        return 1;
    }
}
