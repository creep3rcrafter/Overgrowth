package com.creep3rcrafter.overgrowth.common;

import com.creep3rcrafter.overgrowth.Overgrowth;
import com.creep3rcrafter.overgrowth.common.register.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.io.IOException;
import java.util.function.Supplier;

public class EventHandler {

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Overgrowth.MOD_ID, "main"), () -> "1", "1"::equals, "1"::equals);

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTrackedEntity(PlayerEvent.StartTracking event) {

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPotionAdded(PotionEvent.PotionAddedEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity.hasEffect(ModEffects.NULLIFIER.get())){
            return;
        }
        //PotionUtils
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerCraft(PlayerEvent.ItemCraftedEvent event) {

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {

    }

    // Right Click Block
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {

    }

    // Right Click Item
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {

    }

    // Right Click Entity
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {

    }

    // Attack Entity
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onAttackEntity(AttackEntityEvent event) {

    }

    // Change Equipment
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChangeEquipment(LivingEquipmentChangeEvent event) {

    }

    // Entity Drops
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityDrops(LivingDropsEvent event) {

    }

    // Player Death
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(LivingDeathEvent event) throws IOException {

    }

    // Capabilities
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerClone(PlayerEvent.Clone event) {

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) throws IOException {

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) throws IOException {

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) throws IOException {

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) throws IOException {

    }

}
