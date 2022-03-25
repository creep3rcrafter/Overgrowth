package com.creep3rcrafter.overgrowth.common.register;

import com.creep3rcrafter.creep3rcore.util.register.Register;
import com.creep3rcrafter.overgrowth.Overgrowth;
import com.creep3rcrafter.overgrowth.block.*;
import com.creep3rcrafter.overgrowth.common.block.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Overgrowth.MOD_ID);

    public static final RegistryObject<NyliumFarmlandBlock> NYLIUM_FARMLAND = Register.registerBlock(ModItems.ITEMS, BLOCKS,"nylium_farmland",
            () -> new NyliumFarmlandBlock(AbstractBlock.Properties.of(Material.DIRT).randomTicks().strength(0.6F).sound(SoundType.GRAVEL)), ItemGroup.TAB_DECORATIONS);
    public static final RegistryObject<SoulSoilFarmlandBlock> SOUL_SOIL_FARMLAND = Register.registerBlock(ModItems.ITEMS, BLOCKS,"soul_soil_farmland",
            () -> new SoulSoilFarmlandBlock(AbstractBlock.Properties.of(Material.DIRT).randomTicks().strength(0.6F).sound(SoundType.GRAVEL)), ItemGroup.TAB_DECORATIONS);
    public static final RegistryObject<ActiveSoulSandBlock> LIVE_SOUL_SAND = Register.registerBlock(ModItems.ITEMS, BLOCKS,"live_soul_sand",
            () -> new ActiveSoulSandBlock(14406560, AbstractBlock.Properties.of(Material.SAND, MaterialColor.COLOR_BROWN)
                    .strength(0.5F).speedFactor(0.4F).sound(SoundType.SOUL_SAND)
                    .isValidSpawn(Register::always).isRedstoneConductor(Register::always).isViewBlocking(Register::always)
                    .lightLevel((val) -> {
                        return 1;
                    })
                    .isSuffocating(Register::always).hasPostProcess(Register::always).emissiveRendering(Register::always)), ItemGroup.TAB_DECORATIONS);

    public static final RegistryObject<NetherCropBlock> NETHER_CARROTS = Register.registerBlockWithoutItem(BLOCKS, "nether_carrots",
            () -> new NetherCropBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<NetherCropBlock> NETHER_BEETROOTS = Register.registerBlockWithoutItem(BLOCKS, "nether_beetroots",
            () -> new NetherCropBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<NetherCropBlock> NETHER_POTATOS = Register.registerBlockWithoutItem(BLOCKS, "nether_potatos",
            () -> new NetherCropBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));

    public static final RegistryObject<SulfurCaneBlock> SULFUR_CANE_BLOCK = Register.registerBlockWithoutItem(BLOCKS, "sulfur_cane_block",
            () -> new SulfurCaneBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));

    public static final RegistryObject<XPPlantBlock> XP_PLANT_BLOCK = Register.registerBlockWithoutItem(BLOCKS, "xp_plant",
            () -> new XPPlantBlock(AbstractBlock.Properties.of(Material.PLANT).randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<XPStemBlock> XP_STEM_BLOCK = Register.registerBlock(ModItems.ITEMS, BLOCKS, "stem_block",
            () -> new XPStemBlock(AbstractBlock.Properties.of(Material.PLANT).instabreak().sound(SoundType.CROP)), ItemGroup.TAB_FOOD);
    public static final RegistryObject<XPBaulbBlock> XP_BULB_BLOCK = Register.registerBlock(ModItems.ITEMS, BLOCKS,"xp_bulb_block",
            () -> new XPBaulbBlock(AbstractBlock.Properties.of(Material.PLANT).instabreak().sound(SoundType.CROP)), ItemGroup.TAB_FOOD);
}
