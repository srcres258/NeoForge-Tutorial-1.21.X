package top.srcres258.tutorialmod.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.block.ModBlocks;
import top.srcres258.tutorialmod.recipe.ModRecipes;
import top.srcres258.tutorialmod.screen.custom.GrowthChamberScreen;

@JeiPlugin
public class JEITutorialModPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new GrowthChamberRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        var level = Minecraft.getInstance().level;
        if (level != null) {
            var recipeManager = level.getRecipeManager();

            var growthChamberRecipes = recipeManager.getAllRecipesFor(ModRecipes.GROWTH_CHAMBER_TYPE.get())
                    .stream().map(RecipeHolder::value).toList();
            registration.addRecipes(GrowthChamberRecipeCategory.GROWTH_CHAMBER_RECIPE_TYPE, growthChamberRecipes);
        }
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(GrowthChamberScreen.class, 74, 30, 22, 20,
                GrowthChamberRecipeCategory.GROWTH_CHAMBER_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.GROWTH_CHAMBER),
                GrowthChamberRecipeCategory.GROWTH_CHAMBER_RECIPE_TYPE);
    }
}
