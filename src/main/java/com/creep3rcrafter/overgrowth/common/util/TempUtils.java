package com.creep3rcrafter.overgrowth.common.util;

import com.creep3rcrafter.overgrowth.potion.ItemDamage;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TempUtils {
    public static void lightning(LivingEntity livingEntity, ServerWorld serverWorld, int amplifier) {
        lightning(livingEntity, serverWorld);
        if (!livingEntity.isSpectator() && serverWorld != null && serverWorld.getServer().getTickCount() % 10 == 0) {
            for (int i = 0; i < amplifier; i++) {
                Random random = new Random();
                BlockPos entityPos = livingEntity.blockPosition();
                BlockPos blockPos = entityPos.offset(random.nextInt(amplifier) - (amplifier / 2), random.nextInt(amplifier) - (amplifier / 2), random.nextInt(amplifier) - (amplifier / 2));
                LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(serverWorld);
                lightningboltentity.moveTo(Vector3d.atBottomCenterOf(blockPos));
                lightningboltentity.setCause(livingEntity instanceof ServerPlayerEntity ? (ServerPlayerEntity) livingEntity : null);
                serverWorld.addFreshEntity(lightningboltentity);
            }
        }
    }

    public static void lightning(LivingEntity livingEntity, ServerWorld serverWorld) {
        if (!livingEntity.isSpectator() && serverWorld != null && serverWorld.getServer().getTickCount() % 20 == 0) {
            BlockPos entityPos = livingEntity.blockPosition();
            LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(serverWorld);
            lightningboltentity.moveTo(Vector3d.atBottomCenterOf(entityPos));
            lightningboltentity.setCause(livingEntity instanceof ServerPlayerEntity ? (ServerPlayerEntity) livingEntity : null);
            serverWorld.addFreshEntity(lightningboltentity);
        }
    }

    public static void damageItem(LivingEntity livingEntity, EquipmentSlotType equipmentSlotType, int amplifier, Random random) {
        if (livingEntity.getItemBySlot(equipmentSlotType).isDamageableItem()) {
            int chance = random.nextInt(10);
            if (chance >= 5) {
                Item item = livingEntity.getItemBySlot(equipmentSlotType).getItem();

                if (ItemDamage.getItemDamageType(item) == ItemDamage.ItemDamageType.IRON) {
                    livingEntity.getItemBySlot(equipmentSlotType).hurtAndBreak((ItemDamage.ItemDamageType.IRON.damage / 2) * amplifier, livingEntity, source -> {
                        source.broadcastBreakEvent(equipmentSlotType);
                    });
                }
                if (ItemDamage.getItemDamageType(item) == ItemDamage.ItemDamageType.NETHERITE) {
                    livingEntity.getItemBySlot(equipmentSlotType).hurtAndBreak((ItemDamage.ItemDamageType.NETHERITE.damage / 2) * amplifier, livingEntity, source -> {
                        source.broadcastBreakEvent(equipmentSlotType);
                    });
                }
            }
        }
    }

    public List<BlockPos> getNearbyBlocks(LivingEntity livingEntity, int radius) {
        List<BlockPos> blockPositions = new ArrayList<BlockPos>();
        for (int x = livingEntity.blockPosition().getX() - radius; x <= livingEntity.blockPosition().getX() + radius; x++) {
            for (int y = livingEntity.blockPosition().getY() - radius; y <= livingEntity.blockPosition().getY() + radius; y++) {
                for (int z = livingEntity.blockPosition().getZ() - radius; z <= livingEntity.blockPosition().getZ() + radius; z++) {
                    blockPositions.add(new BlockPos(x, y, z));
                }
            }
        }
        return blockPositions;
    }

    public int getDistanceToEntity(LivingEntity livingEntity, BlockPos pos) {
        double deltaX = livingEntity.getX() - pos.getX();
        double deltaY = livingEntity.getY() - pos.getY();
        double deltaZ = livingEntity.getZ() - pos.getZ();
        return (int) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }

    public int getValue(LivingEntity livingEntity, BlockPos pos) {
        int i = 15;
        i = i - getDistanceToEntity(livingEntity, pos);
        if (i < 0) {
            return 0;
        }
        if (i > 15) {
            return 15;
        }
        return i;
    }
}
