package com.creep3rcrafter.overgrowth.common.register;

import com.creep3rcrafter.creep3rcore.util.Utils;
import com.creep3rcrafter.overgrowth.Overgrowth;
import com.creep3rcrafter.overgrowth.common.util.TempUtils;
import com.creep3rcrafter.overgrowth.potion.ItemDamage;
import com.creep3rcrafter.overgrowth.potion.ModEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.creep3rcrafter.overgrowth.common.util.TempUtils.damageItem;

public class ModEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Overgrowth.MOD_ID);

    public static final RegistryObject<Effect> LUMINOUS = EFFECTS.register("luminous",
            () -> new ModEffect(EffectType.NEUTRAL, 14872831));

    public static final RegistryObject<Effect> EXPLOSIVE = EFFECTS.register("explosive",
            () -> new ModEffect(EffectType.NEUTRAL, 4605510) {
                public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
                    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                    ServerWorld serverWorld = server.getLevel(livingEntity.level.dimension());
                    if (!livingEntity.isSpectator()) {
                        if (livingEntity.level.dimension() == ServerWorld.NETHER) {
                            Utils.explode(serverWorld, livingEntity.blockPosition(), amplifier, true);
                        } else {
                            Utils.explode(serverWorld, livingEntity.blockPosition(), amplifier);
                        }
                    }
                }

                public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
                    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                    ServerWorld serverWorld = server.getLevel(livingEntity.level.dimension());
                    if (!livingEntity.isSpectator()) {
                        if (livingEntity.level.dimension() == ServerWorld.NETHER) {
                            Utils.explode(serverWorld, livingEntity.blockPosition(), amplifier, true);
                        } else {
                            Utils.explode(serverWorld, livingEntity.blockPosition(), amplifier);
                        }
                    }
                }

                public boolean isInstantenous() {
                    return true;
                }

                public boolean isDurationEffectTick(int duration, int amplifier) {
                    return duration >= 1;
                }
            });
    public static final RegistryObject<Effect> THUNDEROUS = EFFECTS.register("thunderous",
            () -> new ModEffect(EffectType.NEUTRAL, 798798) {

                public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
                    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                    ServerWorld serverWorld = server.getLevel(livingEntity.level.dimension());

                    TempUtils.lightning(livingEntity, serverWorld, amplifier);
                }

                public boolean isDurationEffectTick(int duration, int amplifier) {
                    return duration >= 1;
                }

                public boolean isInstantenous() {
                    return false;
                }

            });
    public static final RegistryObject<Effect> OXIDATION = EFFECTS.register("oxidation",
            () -> new ModEffect(EffectType.HARMFUL, 65280) {
                public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
                    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                    ServerWorld serverWorld = server.getLevel(livingEntity.level.dimension());
                    Random random = new Random();
                    if (!livingEntity.isSpectator() && serverWorld != null && serverWorld.getServer().getTickCount() % 10 == 0) {
                        if (livingEntity instanceof ServerPlayerEntity) {
                            PlayerInventory inventory = ((ServerPlayerEntity) livingEntity).inventory;
                            for (int i = 0; i < inventory.getContainerSize(); i++) {
                                Item item = inventory.getItem(i).getItem();
                                if (inventory.getItem(i).isDamageableItem()) {
                                    int chance = random.nextInt(10);
                                    if (chance >= 5) {
                                        if (ItemDamage.getItemDamageType(item) == ItemDamage.ItemDamageType.IRON) {
                                            inventory.getItem(i).hurtAndBreak((ItemDamage.ItemDamageType.IRON.damage / 2) * amplifier, livingEntity, source -> {
                                                source.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
                                            });
                                        } else if (ItemDamage.getItemDamageType(item) == ItemDamage.ItemDamageType.NETHERITE) {
                                            inventory.getItem(i).hurtAndBreak((ItemDamage.ItemDamageType.NETHERITE.damage / 2) * amplifier, livingEntity, source -> {
                                                source.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
                                            });
                                        }
                                    }
                                } else if (!inventory.getItem(i).isDamageableItem() &&
                                        (item == Items.ACTIVATOR_RAIL
                                                || item == Items.ANVIL
                                                || item == Items.BLAST_FURNACE
                                                || item == Items.BUCKET
                                                || item == Items.LAVA_BUCKET
                                                || item == Items.WATER_BUCKET
                                                || item == Items.MILK_BUCKET
                                                || item == Items.CAULDRON
                                                || item == Items.CHAIN
                                                || item == Items.DETECTOR_RAIL
                                                || item == Items.HEAVY_WEIGHTED_PRESSURE_PLATE
                                                || item == Items.HOPPER
                                                || item == Items.IRON_BARS
                                                || item == Items.IRON_DOOR
                                                || item == Items.IRON_TRAPDOOR
                                                || item == Items.IRON_NUGGET
                                                || item == Items.MINECART
                                                || item == Items.PISTON
                                                || item == Items.RAIL
                                                || item == Items.SMITHING_TABLE
                                                || item == Items.STONECUTTER
                                                || item == Items.LODESTONE
                                                || item == Items.NETHERITE_SCRAP
                                                || item == Items.NETHERITE_INGOT
                                                || item == Items.NETHERITE_BLOCK
                                                || item == Items.IRON_INGOT)) {
                                    int chance = random.nextInt(10);
                                    if (chance >= 8) {
                                        if (!(item == Items.LODESTONE
                                                || item == Items.NETHERITE_SCRAP
                                                || item == Items.NETHERITE_INGOT
                                                || item == Items.NETHERITE_BLOCK)) {
                                            inventory.getItem(i).shrink(amplifier);
                                        } else {
                                            chance = random.nextInt(10);
                                            if (chance >= 7) {
                                                inventory.getItem(i).shrink(amplifier + (random.nextInt(5) - 1));
                                            }
                                        }

                                    }
                                }
                            }
                        } else if (livingEntity instanceof IronGolemEntity) {
                            livingEntity.hurt(DamageSource.MAGIC, (float) (ItemDamage.getEntityDamageType(livingEntity).damage / 2) * amplifier);
                        } else {
                            if (livingEntity.hasItemInSlot(EquipmentSlotType.MAINHAND)) {
                                damageItem(livingEntity, EquipmentSlotType.MAINHAND, amplifier, random);
                            }
                            if (livingEntity.hasItemInSlot(EquipmentSlotType.OFFHAND)) {
                                damageItem(livingEntity, EquipmentSlotType.OFFHAND, amplifier, random);
                            }
                            if (livingEntity.hasItemInSlot(EquipmentSlotType.HEAD)) {
                                damageItem(livingEntity, EquipmentSlotType.HEAD, amplifier, random);
                            }
                            if (livingEntity.hasItemInSlot(EquipmentSlotType.CHEST)) {
                                damageItem(livingEntity, EquipmentSlotType.CHEST, amplifier, random);
                            }
                            if (livingEntity.hasItemInSlot(EquipmentSlotType.LEGS)) {
                                damageItem(livingEntity, EquipmentSlotType.LEGS, amplifier, random);
                            }
                            if (livingEntity.hasItemInSlot(EquipmentSlotType.FEET)) {
                                damageItem(livingEntity, EquipmentSlotType.FEET, amplifier, random);
                            }
                        }
                    }
                }

                public boolean isDurationEffectTick(int duration, int amplifier) {
                    return duration >= 1;
                }

                public boolean isInstantenous() {
                    return false;
                }
            });
    public static final RegistryObject<Effect> NULLIFIER = EFFECTS.register("nullifier",
            () -> new ModEffect(EffectType.NEUTRAL, 16777215) {
                public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
                    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                    ServerWorld serverWorld = server.getLevel(livingEntity.level.dimension());
                    Random random = new Random();
                    if (!livingEntity.isSpectator() && serverWorld != null) {
                        livingEntity.getActiveEffects().removeIf(effect -> (effect.getEffect() != this.getEffect()));
                    }
                }

                public boolean isDurationEffectTick(int duration, int amplifier) {
                    return duration >= 1;
                }

                public boolean isInstantenous() {
                    return false;
                }
            });
    public static final RegistryObject<Effect> REVIVAL = EFFECTS.register("revival",
            () -> new ModEffect(EffectType.BENEFICIAL, 12648703) {
            });
    public static final RegistryObject<Effect> INVERSION = EFFECTS.register("inversion",
            () -> new ModEffect(EffectType.NEUTRAL, 40447) {
            });
}