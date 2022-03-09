package creep3rcrafter.overgrowth;

import creep3rcrafter.overgrowth.client.renderer.RisingBlockRenderer;
import creep3rcrafter.overgrowth.register.ModBlocks;
import creep3rcrafter.overgrowth.commands.Commands;
import creep3rcrafter.overgrowth.common.EventHandler;
import creep3rcrafter.overgrowth.register.ModEntities;
import creep3rcrafter.overgrowth.register.ModItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
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
		ModEntities.register(eventBus);
		eventBus.addListener(this::commonSetup);
		eventBus.addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.register(new EventHandler());
		eventBus.register(new Commands());

	}

	private void clientSetup(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(ModBlocks.NETHER_CARROTS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.NETHER_BEETROOTS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.NETHER_POTATOS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SULFUR_CANE_BLOCK.get(), RenderType.cutout());
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.RISING_BLOCK.get(), RisingBlockRenderer::new);
	}
}
