package com.creep3rcrafter.overgrowth.mixin;

import com.creep3rcrafter.overgrowth.client.DynamicLightingManager;
import com.creep3rcrafter.overgrowth.client.OvergrowthClient;
import com.creep3rcrafter.overgrowth.common.register.ModEffects;
import com.creep3rcrafter.overgrowth.common.register.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.lighting.BlockLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(BlockLightEngine.class)
public class BlockLightEngineMixin {
    @Inject(at = @At(value = "RETURN"), method = "getLightEmission(J)I", cancellable = true)
    private void modifyLightValue(long longPos, CallbackInfoReturnable<Integer> callback) {
        BlockPos pos = BlockPos.of(longPos);
        /*
        for (int i = 0; i < OvergrowthClient.dynamicLightingManager.lightData.size(); i++) {
            if (OvergrowthClient.dynamicLightingManager.lightData.get(i).blockPos() == pos && OvergrowthClient.dynamicLightingManager.lightData.get(i).shouldStay)
                callback.setReturnValue(OvergrowthClient.dynamicLightingManager.lightData.get(i).power);
        }
         */
        if (OvergrowthClient.dynamicLightingManager.SOURCES.containsKey(pos) && OvergrowthClient.dynamicLightingManager.SOURCES.get(pos).shouldStay)
            callback.setReturnValue(OvergrowthClient.dynamicLightingManager.SOURCES.get(pos).power);
    }
}
