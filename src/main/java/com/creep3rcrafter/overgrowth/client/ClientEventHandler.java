package com.creep3rcrafter.overgrowth.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void clientTick(final ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START){
            OvergrowthClient.dynamicLightingManager.tick();
        }
    }

}
