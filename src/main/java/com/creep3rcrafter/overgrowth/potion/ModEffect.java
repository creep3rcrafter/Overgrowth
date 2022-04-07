package com.creep3rcrafter.overgrowth.potion;

import com.creep3rcrafter.overgrowth.Overgrowth;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ModEffect extends Effect {
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Overgrowth.MOD_ID, "main"), () -> "1", "1"::equals, "1"::equals);

    public ModEffect(EffectType category, int color) {
        super(category, color);
    }
    /*
    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        //super.applyEffectTick(livingEntity, amplifier);
        //INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with((Supplier<Entity>) livingEntity), this);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeModifierManager attributeMap, int amplifier) {
        //super.removeAttributeModifiers(livingEntity, attributeMap, amplifier);
        //INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> Entity.),this);
    }
     */
}
