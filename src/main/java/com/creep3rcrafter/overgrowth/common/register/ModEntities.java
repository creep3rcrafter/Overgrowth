package com.creep3rcrafter.overgrowth.common.register;

import com.creep3rcrafter.overgrowth.Overgrowth;
import com.creep3rcrafter.overgrowth.common.entity.GloomEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Overgrowth.MOD_ID);

    public static final RegistryObject<EntityType<GloomEntity>>
            GLOOM = ENTITY_TYPES.register("gloom", () -> {
        return EntityType.Builder.<GloomEntity>of(GloomEntity::new, EntityClassification.CREATURE)
                .sized(0.77f, 0.77f)
                .clientTrackingRange(10)
                .build(new ResourceLocation(Overgrowth.MOD_ID, "gloom").toString());
    });
}
