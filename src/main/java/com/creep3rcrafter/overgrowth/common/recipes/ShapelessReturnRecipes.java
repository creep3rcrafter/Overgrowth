package com.creep3rcrafter.overgrowth.common.recipes;

import com.creep3rcrafter.overgrowth.common.register.ModRecipes;
import com.google.gson.*;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShapelessReturnRecipes extends SpecialRecipe {
    private final String group;
    public final ItemStack result;
    public final NonNullList<ItemStack> replace;
    public final NonNullList<ItemStack> remainder;
    private final NonNullList<Ingredient> ingredients;
    private final boolean isSimple;

    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;

    public ShapelessReturnRecipes(ResourceLocation id, String group, ItemStack result, NonNullList<ItemStack> replace, NonNullList<ItemStack> remainder, NonNullList<Ingredient> ingredients) {
        super(id);
        this.group = group;
        this.result = result;
        this.replace = replace;
        this.remainder = remainder;
        this.ingredients = ingredients;
        this.isSimple = this.ingredients.stream().allMatch(Ingredient::isSimple);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.SOUL_SAND_AND_BOTTLE.get();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public boolean matches(CraftingInventory pInv, World pLevel) {
        RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for (int j = 0; j < pInv.getContainerSize(); ++j) {
            ItemStack itemstack = pInv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                if (isSimple)
                    recipeitemhelper.accountStack(itemstack, 1);
                else inputs.add(itemstack);
            }
        }

        return i == this.ingredients.size() &&
                (isSimple ? recipeitemhelper.canCraft(this, (IntList) null) :
                        net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs, this.ingredients) != null);
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        return this.result.copy();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inventory) {
        NonNullList<ItemStack> returns = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < returns.size(); ++i) {
            ItemStack currentItemStack = inventory.getItem(i);
            for (int j = 0; j < replace.size(); ++j) {
                if (currentItemStack.sameItem(replace.get(j))){
                    returns.set(i, remainder.get(j).copy());
                }
            }
        }

            /*
            int remainderIndex = 0;
            ItemStack currentItemStack = inventory.getItem(i);
            if (currentItemStack.getCount() > 1) {
                if (currentItemStack.sameItem(replace.get(remainderIndex))) {
                    returns.add(remainder.get(remainderIndex).copy());
                    remainderIndex++;
                }
            } else {
                if (currentItemStack.sameItem(replace.get(remainderIndex))) {
                    returns.set(i, remainder.get(remainderIndex).copy());
                    remainderIndex++;
                }
            }
        }
            */
        return returns;
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= this.ingredients.size();
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapelessReturnRecipes> {
        private static final ResourceLocation NAME = new ResourceLocation("overgrowth", "shapeless_return_recipe");

        public ShapelessReturnRecipes fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            String s = JSONUtils.getAsString(pJson, "group", "");
            NonNullList<Ingredient> ingredients = ingredientsFromJson(JSONUtils.getAsJsonArray(pJson, "ingredients"));
            NonNullList<ItemStack> remainders = itemStackFromJson(JSONUtils.getAsJsonArray(pJson, "remainders"));
            NonNullList<ItemStack> replace = itemStackFromJson(JSONUtils.getAsJsonArray(pJson, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for custom shapeless recipe");
            } else if (ingredients.size() > MAX_WIDTH * MAX_HEIGHT) {
                throw new JsonParseException("Too many ingredients for custom shapeless recipe the max is " + (MAX_WIDTH * MAX_HEIGHT));
            } else {
                ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(pJson, "result"));
                return new ShapelessReturnRecipes(pRecipeId, s, result, replace, remainders, ingredients);
            }
        }

        public static NonNullList<Ingredient> ingredientsFromJson(JsonArray ingredientArray) {
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }
            return ingredients;
        }

        public static NonNullList<ItemStack> itemStackFromJson(JsonArray ingredientArray) {
            NonNullList<ItemStack> remainders = NonNullList.create();
            for (JsonElement jsonElement : ingredientArray) {
                remainders.add(ShapedRecipe.itemFromJson((JsonObject) jsonElement));
            }
            return remainders;
        }

        public ShapelessReturnRecipes fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            String s = buffer.readUtf(32767);
            int i = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(i, Ingredient.EMPTY);
            NonNullList<ItemStack> remainders = NonNullList.withSize(i, ItemStack.EMPTY);
            NonNullList<ItemStack> replace = NonNullList.withSize(i, ItemStack.EMPTY);

            for (int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.fromNetwork(buffer));
                remainders.set(j, buffer.readItem());
                replace.set(j, buffer.readItem());
            }

            ItemStack itemstack = buffer.readItem();
            return new ShapelessReturnRecipes(recipeId, s, itemstack, replace, remainders, ingredients);
        }

        public void toNetwork(PacketBuffer buffer, ShapelessReturnRecipes recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeVarInt(recipe.ingredients.size());

            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }
            for (ItemStack remainder : recipe.remainder) {
                buffer.writeItem(remainder);
            }
            for (ItemStack replace : recipe.replace) {
                buffer.writeItem(replace);
            }

            buffer.writeItem(recipe.result);
        }
    }
}
