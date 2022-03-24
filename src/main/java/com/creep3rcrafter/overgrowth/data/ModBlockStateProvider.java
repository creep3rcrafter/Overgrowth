package com.creep3rcrafter.overgrowth.data;

import com.creep3rcrafter.overgrowth.Overgrowth;
import com.creep3rcrafter.overgrowth.register.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Overgrowth.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        cropBuilder(ModBlocks.NETHER_CARROTS.get());
        cropBuilder(ModBlocks.NETHER_BEETROOTS.get());
        cropBuilder(ModBlocks.NETHER_POTATOS.get());

        caneBuilder(ModBlocks.SULFUR_CANE_BLOCK.get());

        farmlandBuilder(ModBlocks.NYLIUM_FARMLAND.get());

        blockAndItemBuilder(ModBlocks.LIVE_SOUL_SAND.get());
    }


    public static String name(Block block) {
        return block.getRegistryName().getPath();
    }

    public ResourceLocation itemTexture(Block block) {
        ResourceLocation name = block.getRegistryName();
        return new ResourceLocation(name.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + name.getPath());
    }
    public ResourceLocation blockTexture(Block block) {
        ResourceLocation name = block.getRegistryName();
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }

    private void blockItemBuilder(Block block) {
        itemModels().withExistingParent(name(block), blockTexture(block));
    }
    private void blockAndItemBuilder(Block block) {
        simpleBlock(block);
        itemModels().withExistingParent(name(block), blockTexture(block));
    }

    private void farmlandBuilder(String name, Block block) {
        ModelFile farmlandTemplate = models().getExistingFile(mcLoc("block/template_farmland"));
        getVariantBuilder(block).forAllStates(state -> {
            return ConfiguredModel.builder().modelFile(models().getBuilder(name).parent(farmlandTemplate)
                            .texture("dirt", modLoc("block/" + name + "_other"))
                            .texture("top", modLoc("block/" + name + "_top")))
                    .build();
        });
        blockItemBuilder(block);
    }

    private void farmlandBuilder(Block block) {
        ModelFile farmlandTemplate = models().getExistingFile(mcLoc("block/template_farmland"));
        getVariantBuilder(block).forAllStates(state -> {
            return ConfiguredModel.builder().modelFile(models().getBuilder(name(block)).parent(farmlandTemplate)
                            .texture("dirt", modLoc("block/" + name(block) + "_other"))
                            .texture("top", modLoc("block/" + name(block) + "_top")))
                    .build();
        });
        blockItemBuilder(block);
    }

    private void cropBuilder(String name, Block block) {
        CropsBlock cropsBlock = (CropsBlock) block;
        getVariantBuilder(block).forAllStates(state -> {
            int i = cropAgeToIndex(state.getValue(cropsBlock.AGE));
            return ConfiguredModel.builder()
                    .modelFile(models().crop(name + i, modLoc("block/" + name + i)))
                    .build();
        });
    }

    private void cropBuilder(Block block) {
        CropsBlock cropsBlock = (CropsBlock) block;
        getVariantBuilder(block).forAllStates(state -> {
            int i = cropAgeToIndex(state.getValue(cropsBlock.AGE));
            return ConfiguredModel.builder()
                    .modelFile(models().crop(name(block) + i, modLoc("block/" + name(block) + i)))
                    .build();
        });
    }

    private void caneBuilder(String name, Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            return ConfiguredModel.builder()
                    .modelFile(models().cross(name, modLoc("block/" + name)))
                    .build();
        });
    }

    private void caneBuilder(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            return ConfiguredModel.builder()
                    .modelFile(models().cross(name(block), modLoc("block/" + name(block))))
                    .build();
        });
    }

    private int cropAgeToIndex(int age) {
        if (age > 6) {
            return 3;
        } else if (age > 3) {
            return 2;
        } else if (age > 1) {
            return 1;
        } else {
            return 0;
        }
    }

}
