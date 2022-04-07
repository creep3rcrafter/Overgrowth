package com.creep3rcrafter.overgrowth.common.register;

import com.creep3rcrafter.overgrowth.Overgrowth;
import com.creep3rcrafter.overgrowth.common.recipes.ShapelessReturnRecipes;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModRecipes {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Overgrowth.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<?>> SOUL_SAND_AND_BOTTLE =
            registerRecipe(RECIPES, "shapeless_return_recipe", () -> new ShapelessReturnRecipes.Serializer());

    public static <T extends IRecipeSerializer<?>> RegistryObject<T> registerRecipe(DeferredRegister RECIPES, String name, Supplier<T> recipeSerializer) {
        RegistryObject<T> toReturn = RECIPES.register(name, recipeSerializer);
        return toReturn;
    }
}
