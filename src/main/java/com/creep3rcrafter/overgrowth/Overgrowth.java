package com.creep3rcrafter.overgrowth;

import com.creep3rcrafter.creep3rcore.util.register.Register;
import com.creep3rcrafter.overgrowth.common.register.ModBlocks;
import com.creep3rcrafter.overgrowth.common.commands.Commands;
import com.creep3rcrafter.overgrowth.common.EventHandler;
import com.creep3rcrafter.overgrowth.common.register.ModEntities;
import com.creep3rcrafter.overgrowth.common.register.ModItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
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
		Register.register(eventBus, ModItems.ITEMS);
		Register.register(eventBus, ModBlocks.BLOCKS);
		Register.register(eventBus, ModEntities.ENTITY_TYPES);
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
		RenderTypeLookup.setRenderLayer(ModBlocks.XP_PLANT_BLOCK.get(), RenderType.cutout());
	}
}
