package com.creep3rcrafter.overgrowth.mixin;

import com.creep3rcrafter.overgrowth.client.DynamicLightingManager;
import com.creep3rcrafter.overgrowth.client.OvergrowthClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(at = @At("HEAD"), method = "clearLevel(Lnet/minecraft/client/gui/screen/Screen;)V")
    private void clearLevelHook(Screen screenIn, CallbackInfo callback) {
        OvergrowthClient.dynamicLightingManager.cleanUp();
    }
}
