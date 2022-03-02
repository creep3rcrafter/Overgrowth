package creep3rcrafter.overgrowth.block;

import java.util.function.Supplier;

import creep3rcrafter.overgrowth.Overgrowth;
import creep3rcrafter.overgrowth.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Overgrowth.MOD_ID);
	
	public static final RegistryObject<Block> TEST_BLOCK = registerBlock("test_block", 
			() -> new Block(AbstractBlock.Properties.of(Material.METAL).harvestLevel(2).harvestTool(ToolType.PICKAXE).strength(2f)), ItemGroup.TAB_FOOD);
	
	public static final RegistryObject<Block> NYLIUM_FARMLAND = registerBlock("nylium_farmland", 
			() -> new NyliumFarmlandBlock(AbstractBlock.Properties.of(Material.DIRT).randomTicks().strength(0.6F).sound(SoundType.GRAVEL)), ItemGroup.TAB_DECORATIONS);

	public static final RegistryObject<NetherCropBlock> NETHER_CARROTS = registerBlockWithoutItem("nether_carrots", 
			() -> new NetherCropBlock(AbstractBlock.Properties.of(Material.PLANT).air().noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
	public static final RegistryObject<NetherCropBlock> NETHER_BEETROOTS = registerBlockWithoutItem("nether_beetroots", 
			() -> new NetherCropBlock(AbstractBlock.Properties.of(Material.PLANT).air().noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
	
	
	
	
	
	private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}
	private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, ItemGroup itemGroup) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn, itemGroup);
		return toReturn;
	}
	private static <T extends Block>RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
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
		//NETHER_CARROTS.get().setItem(ModItems.NETHER_CARROT.get());
	}
}
