package top.srcres258.tutorialmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.block.ModBlocks;
import top.srcres258.tutorialmod.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> BISMUTH_SMELTABLES = List.of(
            ModItems.RAW_BISMUTH,
            ModBlocks.BISMUTH_ORE,
            ModBlocks.BISMUTH_DEEPSLATE_ORE
    );

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BISMUTH_BLOCK)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.BISMUTH)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BISMUTH, 9)
                .requires(ModBlocks.BISMUTH_BLOCK)
                .unlockedBy("has_bismuth_block", has(ModBlocks.BISMUTH_BLOCK))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BISMUTH, 18)
                .requires(ModBlocks.MAGIC_BLOCK)
                .unlockedBy("has_magic_block", has(ModBlocks.MAGIC_BLOCK))
                .save(recipeOutput, "tutorialmod:bismuth_from_magic_block");

        oreSmelting(recipeOutput, BISMUTH_SMELTABLES, RecipeCategory.MISC, ModItems.BISMUTH,
                0.25F, 200, "bismuth");
        oreBlasting(recipeOutput, BISMUTH_SMELTABLES, RecipeCategory.MISC, ModItems.BISMUTH,
                0.25F, 100, "bismuth");

        stairBuilder(ModBlocks.BISMUTH_STAIRS, Ingredient.of(ModItems.BISMUTH))
                .group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH))
                .save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_SLAB, ModItems.BISMUTH);

        buttonBuilder(ModBlocks.BISMUTH_BUTTON, Ingredient.of(ModItems.BISMUTH))
                .group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH))
                .save(recipeOutput);
        pressurePlate(recipeOutput, ModBlocks.BISMUTH_PRESSURE_PLATE, ModItems.BISMUTH);

        fenceBuilder(ModBlocks.BISMUTH_FENCE, Ingredient.of(ModItems.BISMUTH))
                .group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH))
                .save(recipeOutput);
        fenceGateBuilder(ModBlocks.BISMUTH_FENCE_GATE, Ingredient.of(ModItems.BISMUTH))
                .group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH))
                .save(recipeOutput);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_WALL, ModItems.BISMUTH);

        doorBuilder(ModBlocks.BISMUTH_DOOR, Ingredient.of(ModItems.BISMUTH))
                .group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH))
                .save(recipeOutput);
        trapdoorBuilder(ModBlocks.BISMUTH_TRAPDOOR, Ingredient.of(ModItems.BISMUTH))
                .group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH))
                .save(recipeOutput);
    }

    protected static void oreSmelting(
            RecipeOutput pRecipeOutput,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTime,
            String pGroup
    ) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory,
                pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(
            RecipeOutput pRecipeOutput,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTime,
            String pGroup
    ) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory,
                pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(
            RecipeOutput pRecipeOutput,
            RecipeSerializer<T> pCookingSerializer,
            AbstractCookingRecipe.Factory<T> pFactory,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTime,
            String pGroup,
            String pRecipeName
    ) {
        for (var itemLike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemLike), pCategory, pResult, pExperience, pCookingTime,
                            pCookingSerializer, pFactory)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemLike), has(itemLike))
                    .save(pRecipeOutput, TutorialMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName
                            + "_" + getItemName(itemLike));
        }
    }
}
