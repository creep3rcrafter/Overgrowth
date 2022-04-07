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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class ShapelessReturnRecipeBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Item result;
    private final int count;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final List<Item> craftingRemainder = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group;

    public ShapelessReturnRecipeBuilder(IItemProvider itemProvider, int count) {
        this.result = itemProvider.asItem();
        this.count = count;
    }

    public static ShapelessReturnRecipeBuilder shapeless(IItemProvider result) {
        return new ShapelessReturnRecipeBuilder(result, 1);
    }
    public static ShapelessReturnRecipeBuilder shapeless(IItemProvider result, int count) {
        return new ShapelessReturnRecipeBuilder(result, count);
    }

    public ShapelessReturnRecipeBuilder requires(ITag<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }
    public ShapelessReturnRecipeBuilder requires(ITag<Item> tag, int quantity) {
        return this.requires(Ingredient.of(tag), quantity);
    }
    public ShapelessReturnRecipeBuilder requires(ITag<Item> tag, IItemProvider remainderItem) {
        return this.requires(Ingredient.of(tag), remainderItem);
    }
    public ShapelessReturnRecipeBuilder requires(ITag<Item> tag, IItemProvider remainderItem, int quantity) {
        return this.requires(Ingredient.of(tag), remainderItem, quantity);
    }

    public ShapelessReturnRecipeBuilder requires(IItemProvider item) {
        return this.requires(Ingredient.of(item));
    }
    public ShapelessReturnRecipeBuilder requires(IItemProvider item, int quantity) {
        return this.requires(Ingredient.of(item), quantity);
    }
    public ShapelessReturnRecipeBuilder requires(IItemProvider item, IItemProvider remainderItem) {
        return this.requires(Ingredient.of(item), remainderItem);
    }
    public ShapelessReturnRecipeBuilder requires(IItemProvider item, IItemProvider remainderItem, int quantity) {
        return this.requires(Ingredient.of(item), remainderItem, quantity);
    }

    public ShapelessReturnRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, Items.AIR, 1);
    }
    public ShapelessReturnRecipeBuilder requires(Ingredient ingredient, int quantity) {
        return this.requires(ingredient, Items.AIR, quantity);
    }
    public ShapelessReturnRecipeBuilder requires(Ingredient ingredient, IItemProvider remainder) {
        return this.requires(ingredient, remainder, 1);
    }
    public ShapelessReturnRecipeBuilder requires(Ingredient ingredient, IItemProvider remainder, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            this.ingredients.add(ingredient);
            this.craftingRemainder.add(remainder.asItem());
        }
        return this;
    }


    public ShapelessReturnRecipeBuilder unlockedBy(String p_200483_1_, ICriterionInstance p_200483_2_) {
        this.advancement.addCriterion(p_200483_1_, p_200483_2_);
        return this;
    }
    public ShapelessReturnRecipeBuilder group(String p_200490_1_) {
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
        p_200485_1_.accept(new ShapelessReturnRecipeBuilder.Result(p_200485_2_, this.result, this.count, this.group == null ? "" : this.group, this.ingredients, this.craftingRemainder, this.advancement, new ResourceLocation(p_200485_2_.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + p_200485_2_.getPath())));
    }
    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }




    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final String group;
        private final List<Ingredient> ingredients;
        private final List<Item> craftingRemainder;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item result, int count, String group, List<Ingredient> ingredients, List<Item> craftingRemainder, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.group = group;
            this.ingredients = ingredients;
            this.craftingRemainder = craftingRemainder;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            JsonArray jsonarrayIngredients = new JsonArray();
            JsonArray jsonarrayRemainders = new JsonArray();

            for (Ingredient ingredient : this.ingredients) {
                jsonarrayIngredients.add(ingredient.toJson());
            }

            for (int i = 0; i < ingredients.size(); i++){
                JsonObject remainderObject = new JsonObject();
                remainderObject.addProperty("item", Registry.ITEM.getKey(craftingRemainder.get(i)).toString());
                jsonarrayRemainders.add(remainderObject);
            }

            /*
            for (int i = 0; i < ingredients.size(); i++){
                JsonObject airObject = (JsonObject) ingredients.get(i).toJson();
                JsonObject remainderObject = (JsonObject) ingredients.get(i).toJson();
                airObject.addProperty("remainder", Registry.ITEM.getKey(Items.AIR).toString());
                remainderObject.addProperty("remainder", Registry.ITEM.getKey(craftingRemainder.get(i)).toString());

                if (this.craftingRemainder.get(i) == Items.AIR){//item and air
                    jsonArray.add(airObject);
                }else{//item and remander
                    jsonArray.add(remainderObject);
                }
            }
             */

            pJson.add("ingredients", jsonarrayIngredients);
            pJson.add("remainders", jsonarrayRemainders);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            pJson.add("result", jsonobject);
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
