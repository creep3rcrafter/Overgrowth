package creep3rcrafter.overgrowth.register;

import creep3rcrafter.overgrowth.Overgrowth;
import creep3rcrafter.overgrowth.entity.RisingBlockEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Overgrowth.MOD_ID);

    public static final RegistryObject<EntityType<RisingBlockEntity>> RISING_BLOCK = ENTITY_TYPES.register("rising_block",
            () -> EntityType.Builder.<RisingBlockEntity>of(RisingBlockEntity::new, EntityClassification.MISC)
                    .sized(0.98f, 0.98f).clientTrackingRange(10).updateInterval(20)
                    .build(Overgrowth.MOD_ID + "rising_block"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
