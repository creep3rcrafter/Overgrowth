package com.creep3rcrafter.overgrowth.common.data;

import com.creep3rcrafter.creep3rcore.util.data.CCRecipeProvider;
import com.creep3rcrafter.overgrowth.common.register.ModBlocks;
import com.creep3rcrafter.overgrowth.common.register.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends CCRecipeProvider {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }
    @Override
    public void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(ModItems.DARK_PAPER.get(), 3)
                .define('#', ModItems.SULFUR_CANE_ITEM.get())
                .pattern("###")
                .unlockedBy("has_sulfur_reeds", has(ModItems.SULFUR_CANE_ITEM.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.SOUL_PAPER.get())
                .requires(ModItems.SOUL_BOTTLE.get())
                .requires(ModItems.DARK_PAPER.get())
                .unlockedBy("has_soul_bottle", has(ModItems.SOUL_BOTTLE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.SULFUR.get())
                .requires(ModItems.SULFUR_CANE_ITEM.get())
                .unlockedBy("has_sulfur_reeds", has(ModItems.SULFUR_CANE_ITEM.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.LIVE_SOUL_SAND.get())
                .requires(ModItems.SOUL_BOTTLE.get())
                .requires(Blocks.SOUL_SAND)
                .unlockedBy("has_soul_bottle", has(ModItems.SOUL_BOTTLE.get()))
                .save(consumer);

        ShapelessReturnRecipeBuilder.shapeless(Blocks.SOUL_SAND)
                .requires(Items.GLASS_BOTTLE, ModItems.SOUL_BOTTLE.get())
                .requires(ModBlocks.LIVE_SOUL_SAND.get())
                .unlockedBy("has_live_soul_sand", has(ModBlocks.LIVE_SOUL_SAND.get()))
                .save(consumer);
    }
}
