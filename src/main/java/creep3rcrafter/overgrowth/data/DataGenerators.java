package creep3rcrafter.overgrowth.data;

import creep3rcrafter.overgrowth.Overgrowth;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
@EventBusSubscriber(modid = Overgrowth.MOD_ID, bus = Bus.MOD)
public class DataGenerators {
	private DataGenerators () {
		
	}
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		ModItemModelProvider modItemModelProvider = new ModItemModelProvider(gen, existingFileHelper);
		ModBlockStateProvider modBlockStateProvider = new ModBlockStateProvider(gen, existingFileHelper);
		gen.addProvider(modBlockStateProvider);
		gen.addProvider(modItemModelProvider);
	}
}
