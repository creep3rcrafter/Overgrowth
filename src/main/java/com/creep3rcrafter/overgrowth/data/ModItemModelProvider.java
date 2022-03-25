package com.creep3rcrafter.overgrowth.data;

import com.creep3rcrafter.creep3rcore.util.data.CCItemModelProvider;
import com.creep3rcrafter.overgrowth.Overgrowth;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends CCItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Overgrowth.MOD_ID, existingFileHelper);
    }

    @Override
    public void registerModels() {
        itembuilder("nether_carrot");
        itembuilder("nether_beetroot_seeds");
        itembuilder("nether_beetroot");
        itembuilder("nether_potato");
        itembuilder("baked_nether_potato");
        itembuilder("nether_poisonous_potato");
        itembuilder("sulfur_cane");
        itembuilder("soul_paper");
        itembuilder("dark_paper");
        itembuilder("soul_book");
        itembuilder("sulfur");
    }
}
