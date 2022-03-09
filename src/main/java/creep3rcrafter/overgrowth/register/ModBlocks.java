package creep3rcrafter.overgrowth.register;

import java.util.function.Supplier;

import creep3rcrafter.overgrowth.Overgrowth;
import creep3rcrafter.overgrowth.block.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    private Blocks blocks;
    private Items items;

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Overgrowth.MOD_ID);

    public static final RegistryObject<Block> NYLIUM_FARMLAND = registerBlock("nylium_farmland",
            () -> new NyliumFarmlandBlock(AbstractBlock.Properties.of(Material.DIRT).randomTicks().strength(0.6F).sound(SoundType.GRAVEL)), ItemGroup.TAB_DECORATIONS);

    public static final RegistryObject<NetherCropBlock> NETHER_CARROTS = registerBlockWithoutItem("nether_carrots",
            () -> new NetherCropBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<NetherCropBlock> NETHER_BEETROOTS = registerBlockWithoutItem("nether_beetroots",
            () -> new NetherCropBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<NetherCropBlock> NETHER_POTATOS = registerBlockWithoutItem("nether_potatos",
            () -> new NetherCropBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));

    public static final RegistryObject<ActiveSoulSandBlock> LIVE_SOUL_SAND = registerBlock("live_soul_sand",
            () -> new ActiveSoulSandBlock(14406560, AbstractBlock.Properties.of(Material.SAND, MaterialColor.COLOR_BROWN)
                    .strength(0.5F).speedFactor(0.4F).sound(SoundType.SOUL_SAND)
                    .isValidSpawn(ModBlocks::always).isRedstoneConductor(ModBlocks::always).isViewBlocking(ModBlocks::always).isSuffocating(ModBlocks::always)), ItemGroup.TAB_DECORATIONS);

    public static final RegistryObject<SulfurCaneBlock> SULFUR_CANE_BLOCK = registerBlockWithoutItem("sulfur_cane_block",
            () -> new SulfurCaneBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));


    public static final RegistryObject<ExperienceOrbBlock> EXPERIENCE_ORB_BLOCK = registerBlock("experience_orb_block",
            () -> new ExperienceOrbBlock(AbstractBlock.Properties.of(Material.PLANT).instabreak().sound(SoundType.CROP)), ItemGroup.TAB_FOOD);



    private static Boolean always(BlockState p_235437_0_, IBlockReader p_235437_1_, BlockPos p_235437_2_, EntityType<?> p_235437_3_) {
        return (boolean) true;
    }
    private static boolean always(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return (boolean) true;
    }


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, ItemGroup itemGroup) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, itemGroup);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, ItemGroup itemGroup) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(itemGroup)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }


}
