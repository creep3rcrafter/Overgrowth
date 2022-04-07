package com.creep3rcrafter.overgrowth.common.data;

import com.creep3rcrafter.overgrowth.common.register.ModRecipes;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class CustomShapelessRecipeBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Item result;
    private final Item craftingRemainder;
    private final Item replace;
    private final int count;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group;

    public CustomShapelessRecipeBuilder(IItemProvider itemProvider, int count, IItemProvider craftingRemainder, IItemProvider replace) {
        this.result = itemProvider.asItem();
        this.count = count;
        this.craftingRemainder = craftingRemainder.asItem();
        this.replace = replace.asItem();
    }

    public static CustomShapelessRecipeBuilder shapeless(IItemProvider result, IItemProvider craftingRemainder, IItemProvider replace) {
        return new CustomShapelessRecipeBuilder(result, 1, craftingRemainder, replace);
    }

    public static CustomShapelessRecipeBuilder shapeless(IItemProvider result, int count, IItemProvider craftingRemainder, IItemProvider replace) {
        return new CustomShapelessRecipeBuilder(result, count, craftingRemainder, replace);
    }
    public CustomShapelessRecipeBuilder requires(ITag<Item> pTag) {
        return this.requires(Ingredient.of(pTag));
    }

    public CustomShapelessRecipeBuilder requires(IItemProvider pItem) {
        return this.requires(pItem, 1);
    }

    public CustomShapelessRecipeBuilder requires(IItemProvider pItem, int pQuantity) {
        for (int i = 0; i < pQuantity; ++i) {
            this.requires(Ingredient.of(pItem));
        }

        return this;
    }
    public CustomShapelessRecipeBuilder requires(Ingredient pIngredient) {
        return this.requires(pIngredient, 1);
    }

    public CustomShapelessRecipeBuilder requires(Ingredient pIngredient, int pQuantity) {
        for (int i = 0; i < pQuantity; ++i) {
            this.ingredients.add(pIngredient);
        }

        return this;
    }

    public CustomShapelessRecipeBuilder unlockedBy(String p_200483_1_, ICriterionInstance p_200483_2_) {
        this.advancement.addCriterion(p_200483_1_, p_200483_2_);
        return this;
    }

    public CustomShapelessRecipeBuilder group(String p_200490_1_) {
        this.group = p_200490_1_;
        return this;
    }

    public void save(Consumer<IFinishedRecipe> p_200482_1_) {
        this.save(p_200482_1_, Registry.ITEM.getKey(this.result));
    }

    public void save(Consumer<IFinishedRecipe> p_200484_1_, String p_200484_2_) {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result);
        if ((new ResourceLocation(p_200484_2_)).equals(resourcelocation)) {
            throw new IllegalStateException("Custom Shapeless Recipe " + p_200484_2_ + " should remove its 'save' argument");
        } else {
            this.save(p_200484_1_, new ResourceLocation(p_200484_2_));
        }
    }

    public void save(Consumer<IFinishedRecipe> p_200485_1_, ResourceLocation p_200485_2_) {
        this.ensureValid(p_200485_2_);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_200485_2_)).rewards(AdvancementRewards.Builder.recipe(p_200485_2_)).requirements(IRequirementsStrategy.OR);
        p_200485_1_.accept(new CustomShapelessRecipeBuilder.Result(p_200485_2_, this.result, this.craftingRemainder, this.replace, this.count, this.group == null ? "" : this.group, this.ingredients, this.advancement, new ResourceLocation(p_200485_2_.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + p_200485_2_.getPath())));
    }

    /**
     * Makes sure that this recipe is valid and obtainable.
     */
    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Item craftingResult;
        private final Item replace;
        private final int count;
        private final String group;
        private final List<Ingredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, Item pCraftingResult, Item pReplace, int pCount, String pGroup, List<Ingredient> pIngredients, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.craftingResult = pCraftingResult;
            this.replace = pReplace;
            this.count = pCount;
            this.group = pGroup;
            this.ingredients = pIngredients;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            JsonArray jsonarray = new JsonArray();

            for (Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }

            pJson.add("ingredients", jsonarray);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            pJson.add("result", jsonobject);
            JsonObject jsonobject2 = new JsonObject();
            JsonObject jsonobject3 = new JsonObject();
            jsonobject2.addProperty("item", Registry.ITEM.getKey(this.craftingResult).toString());
            jsonobject3.addProperty("item", Registry.ITEM.getKey(this.replace).toString());
            pJson.add("crafting_remainder", jsonobject2);
            pJson.add("replace", jsonobject3);
        }

        public IRecipeSerializer<?> getType() {
            return ModRecipes.SOUL_SAND_AND_BOTTLE.get();
        }
        public ResourceLocation getId() {
            return this.id;
        }
        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }
        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
