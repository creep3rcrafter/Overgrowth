package com.creep3rcrafter.overgrowth;

import com.creep3rcrafter.creep3rcore.util.register.Register;
import com.creep3rcrafter.overgrowth.client.ClientEventHandler;
import com.creep3rcrafter.overgrowth.client.DynamicLightingManager;
import com.creep3rcrafter.overgrowth.client.OvergrowthClient;
import com.creep3rcrafter.overgrowth.client.render.entity.GloomRenderer;
import com.creep3rcrafter.overgrowth.common.EventHandler;
import com.creep3rcrafter.overgrowth.common.entity.GloomEntity;
import com.creep3rcrafter.overgrowth.common.register.*;
import com.creep3rcrafter.overgrowth.common.commands.Commands;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Overgrowth.MOD_ID)
public class Overgrowth {

	public static final String MOD_ID = "overgrowth";
	public static final Logger LOGGER = LogManager.getLogger();


	public Overgrowth() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		Register.register(eventBus, ModEntities.ENTITY_TYPES);
		Register.register(eventBus, ModItems.ITEMS);
		Register.register(eventBus, ModBlocks.BLOCKS);
		Register.register(eventBus, ModEffects.EFFECTS);
		Register.register(eventBus, ModPotions.POTIONS);
		Register.register(eventBus, ModRecipes.RECIPES);

		eventBus.addListener(this::commonSetup);
		eventBus.addListener(this::clientSetup);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> OvergrowthClient.init());

		MinecraftForge.EVENT_BUS.register(EventHandler.class);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.register(new Commands());
		DeferredWorkQueue.runLater(() ->{
			GlobalEntityTypeAttributes.put(ModEntities.GLOOM.get(), GloomEntity.setCustomAttribute().build());
		});


	}
	private void clientSetup(final FMLClientSetupEvent event) {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		RenderTypeLookup.setRenderLayer(ModBlocks.NETHER_CARROTS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.NETHER_BEETROOTS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.NETHER_POTATOS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SULFUR_CANE_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.XP_PLANT_BLOCK.get(), RenderType.cutout());
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.GLOOM.get(), GloomRenderer::new);
	}
}
