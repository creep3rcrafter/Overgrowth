package creep3rcrafter.overgrowth;

import creep3rcrafter.overgrowth.block.ModBlocks;
import creep3rcrafter.overgrowth.commands.Commands;
import creep3rcrafter.overgrowth.common.EventHandler;
import creep3rcrafter.overgrowth.item.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod(Overgrowth.MOD_ID)
public class Overgrowth {
	
	public static final String MOD_ID = "overgrowth";
	
	public Overgrowth() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModItems.register(eventBus);
		ModBlocks.register(eventBus);
		eventBus.addListener(this::commonSetup);
		eventBus.addListener(this::clientSetup);
		}
	
	private void commonSetup(final FMLCommonSetupEvent event) {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.register(new EventHandler());
		eventBus.register(new Commands());
		
	}

	private void clientSetup(final FMLClientSetupEvent event) {

	}

}
