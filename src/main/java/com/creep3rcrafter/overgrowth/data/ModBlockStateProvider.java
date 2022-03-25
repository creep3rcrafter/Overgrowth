package com.creep3rcrafter.overgrowth.data;

import com.creep3rcrafter.creep3rcore.util.data.CCBlockStateProvider;
import com.creep3rcrafter.overgrowth.Overgrowth;
import com.creep3rcrafter.overgrowth.common.register.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends CCBlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Overgrowth.MOD_ID, exFileHelper);
    }

    @Override
    public void registerStatesAndModels() {
        cropBuilder(ModBlocks.NETHER_CARROTS.get());
        cropBuilder(ModBlocks.NETHER_BEETROOTS.get());
        cropBuilder(ModBlocks.NETHER_POTATOS.get());

        caneBuilder(ModBlocks.SULFUR_CANE_BLOCK.get());

        farmlandBuilder(ModBlocks.NYLIUM_FARMLAND.get());

        blockAndItemBuilder(ModBlocks.LIVE_SOUL_SAND.get());
    }
}
