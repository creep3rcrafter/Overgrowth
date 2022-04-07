package com.creep3rcrafter.overgrowth.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

@OnlyIn(Dist.CLIENT)
public class OvergrowthClient {

    public static DynamicLightingManager dynamicLightingManager;

    public static void init() {
        dynamicLightingManager = new DynamicLightingManager();
        MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
    }

}
