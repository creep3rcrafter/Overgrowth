package com.creep3rcrafter.overgrowth.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.creep3rcrafter.overgrowth.common.register.ModEffects;
import com.creep3rcrafter.overgrowth.common.register.ModEntities;
import com.creep3rcrafter.overgrowth.common.register.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@OnlyIn(Dist.CLIENT)
public class DynamicLightingManager {

    public Map<BlockPos, LightData> SOURCES = new ConcurrentHashMap<>();

    public void tick() {
        Minecraft MC = Minecraft.getInstance();//try potion server stuff
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (MC.player != null && MC.level != null && MC.player.tickCount % 2 == 0) {
            ServerWorld serverWorld = server.getLevel(MC.level.dimension());
            SOURCES.forEach((blockPos, data) -> data.shouldStay = false);
            for (LivingEntity livingEntity : serverWorld.getEntitiesOfClass(LivingEntity.class, MC.player.getBoundingBox().inflate(32))) {
                if (shouldGlow(livingEntity)) {
                    if (hasEffect(livingEntity)) {
                        SOURCES.put(livingEntity.blockPosition().above((int) livingEntity.getEyeHeight()), new LightData(getStrength(livingEntity)));
                    } else {
                        SOURCES.put(livingEntity.blockPosition().above((int) livingEntity.getEyeHeight()), new LightData());
                    }
                }
            }
            for (ItemEntity itemEntity : serverWorld.getEntitiesOfClass(ItemEntity.class, MC.player.getBoundingBox().inflate(64))) {
                if (itemEntity.getItem().getItem() == Items.TORCH
                        || itemEntity.getItem().getItem() == Items.SOUL_TORCH
                        || itemEntity.getItem().getItem() == Items.GLOWSTONE
                        || itemEntity.getItem().getItem() == Items.GLOWSTONE_DUST
                        || itemEntity.getItem().getItem() == Items.BLAZE_POWDER
                        || itemEntity.getItem().getItem() == Items.BLAZE_ROD
                        || itemEntity.getItem().getItem() == ModItems.SOUL_PAPER.get()
                        || itemEntity.getItem().getItem() == ModItems.SOUL_BOTTLE.get()) {
                    SOURCES.put(itemEntity.blockPosition().above((int) itemEntity.getEyeHeight()), new LightData(8));
                }
            }
            if (!SOURCES.isEmpty()) {
                SOURCES.forEach((blockPos, data) -> MC.level.getChunkSource().getLightEngine().checkBlock(blockPos));
                SOURCES.entrySet().removeIf(entry -> !entry.getValue().shouldStay);
            }
        }

    }

    public static boolean shouldGlow(LivingEntity entity) {
        if (entity.getType() == ModEntities.GLOOM.get() || hasEffect(entity)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasEffect(LivingEntity entity) {
        if (entity.hasEffect(ModEffects.LUMINOUS.get())) {
            return true;
        } else {
            return false;
        }
    }

    public static int getStrength(LivingEntity entity) {
        if (entity.hasEffect(ModEffects.LUMINOUS.get())) {
            int strength = entity.getEffect(ModEffects.LUMINOUS.get()).getAmplifier();
            if (strength == 0) {
                return 0;
            } else if (strength > 0 && strength <= 15) {
                return strength;
            } else if (strength > 15) {
                return 15;
            }
        }
        return 0;
    }

    public void cleanUp() {
        Minecraft MC = Minecraft.getInstance();
        if (SOURCES.size() > 0 && MC.level != null) {
            SOURCES.forEach((blockPos, data) ->
            {
                data.shouldStay = false;
                MC.level.getChunkSource().getLightEngine().checkBlock(blockPos);
            });
            SOURCES.clear();
        }
    }

    public class LightData {
        public boolean shouldStay;
        public int power;

        public LightData(int power) {
            this.power = power;
            this.shouldStay = true;
        }

        public LightData() {
            this.power = 10;
            this.shouldStay = true;
        }
    }
}