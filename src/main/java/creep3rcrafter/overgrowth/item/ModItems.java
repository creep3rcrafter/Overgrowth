package creep3rcrafter.overgrowth.item;

import creep3rcrafter.overgrowth.Overgrowth;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	
	//Ideas Fire(Ignis), luminous, Blistering, Scorching(ardore), Searing, Pyro, Sparking
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Overgrowth.MOD_ID);
	public static RegistryObject<Item> Test = ITEMS.register("test", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD)));
	public static RegistryObject<Item> PEPPER = ITEMS.register("pepper", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(Foods.PEPPER)));
			
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

}
