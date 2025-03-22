package top.srcres258.tutorialmod.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.block.ModBlocks;
import top.srcres258.tutorialmod.recipe.GrowthChamberRecipe;

public class GrowthChamberRecipeCategory implements IRecipeCategory<GrowthChamberRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID,
            "growth_chamber");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID,
            "textures/gui/growth_chamber/growth_chamber_gui.png");

    public static final RecipeType<GrowthChamberRecipe> GROWTH_CHAMBER_RECIPE_TYPE =
            new RecipeType<>(UID, GrowthChamberRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public GrowthChamberRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.GROWTH_CHAMBER));
    }

    @Override
    public @NotNull RecipeType<GrowthChamberRecipe> getRecipeType() {
        return GROWTH_CHAMBER_RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.tutorialmod.growth_chamber");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(
            @NotNull IRecipeLayoutBuilder builder,
            @NotNull GrowthChamberRecipe recipe,
            @NotNull IFocusGroup focuses
    ) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34)
                .addIngredients(recipe.getIngredients().getFirst());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34)
                .addItemStack(recipe.getResultItem(null));
    }
}
