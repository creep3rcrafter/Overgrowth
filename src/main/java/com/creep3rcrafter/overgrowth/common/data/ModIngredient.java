package com.creep3rcrafter.overgrowth.common.data;

import net.minecraft.item.crafting.Ingredient;

import java.util.stream.Stream;

public class ModIngredient extends Ingredient {
    protected ModIngredient(Stream<? extends IItemList> stream) {
        super(stream);
    }
}
