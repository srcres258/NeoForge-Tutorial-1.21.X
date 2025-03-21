package top.srcres258.tutorialmod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record GrowthChamberRecipe(Ingredient inputItem, ItemStack output) implements Recipe<GrowthChamberRecipeInput> {
    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(@NotNull GrowthChamberRecipeInput growthChamberRecipeInput, @NotNull Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return inputItem.test(growthChamberRecipeInput.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(
            @NotNull GrowthChamberRecipeInput growthChamberRecipeInput,
            HolderLookup.@NotNull Provider provider
    ) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return output;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.GROWTH_CHAMBER_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.GROWTH_CHAMBER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<GrowthChamberRecipe> {
        public static final MapCodec<GrowthChamberRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(GrowthChamberRecipe::inputItem),
                        ItemStack.CODEC.fieldOf("result").forGetter(GrowthChamberRecipe::output)
                ).apply(inst, GrowthChamberRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, GrowthChamberRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC,
                        GrowthChamberRecipe::inputItem,
                        ItemStack.STREAM_CODEC,
                        GrowthChamberRecipe::output,
                        GrowthChamberRecipe::new
                );

        @Override
        public @NotNull MapCodec<GrowthChamberRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, GrowthChamberRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
