package creep3rcrafter.overgrowth.client;

import creep3rcrafter.overgrowth.Overgrowth;
import creep3rcrafter.overgrowth.client.renderer.RisingBlockRenderer;
import creep3rcrafter.overgrowth.register.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Overgrowth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onclientSetup(FMLClientSetupEvent event){
    }
}
