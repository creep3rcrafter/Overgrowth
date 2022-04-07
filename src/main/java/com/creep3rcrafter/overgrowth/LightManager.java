package com.creep3rcrafter.overgrowth;

import com.creep3rcrafter.overgrowth.common.register.ModEffects;
import com.creep3rcrafter.overgrowth.common.register.ModEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightManager {
    public static boolean shouldGlow(LivingEntity entity) {
        if (entity.getType() == ModEntities.GLOOM.get() || entity.hasEffect(ModEffects.LUMINOUS.get())){
            return true;
        }else{
            return false;
        }
    }
}
