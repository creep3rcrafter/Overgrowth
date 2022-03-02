package creep3rcrafter.overgrowth.item;

import creep3rcrafter.overgrowth.Overgrowth;
import creep3rcrafter.overgrowth.block.ModBlocks;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

	// Ideas Fire(Ignis), luminous, Blistering, Scorching(ardore), Searing, Pyro,
	// Sparking

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Overgrowth.MOD_ID);
	
	public static RegistryObject<Item> TEST = ITEMS.register("test", 
			() -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD)));
	public static RegistryObject<Item> CHERRY = ITEMS.register("cherry", 
			() -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(Foods.CHERRY)));
	
	
	public static RegistryObject<Item> NETHER_CARROT = ITEMS.register("nether_carrot", 
			() -> new BlockNamedItem(ModBlocks.NETHER_CARROTS.get(), (new Item.Properties()).food(Foods.NETHER_CARROT).tab(ItemGroup.TAB_FOOD)));
	public static RegistryObject<Item> NETHER_BEETROOT = ITEMS.register("nether_beetroot", 
			() -> new BlockNamedItem(ModBlocks.NETHER_BEETROOTS.get(), (new Item.Properties()).food(Foods.NETHER_BEETROOT).tab(ItemGroup.TAB_FOOD)));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
		//ModBlocks.NETHER_CARROTS.get().setItem(NETHER_CARROT.get());
	}

}
