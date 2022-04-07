package com.creep3rcrafter.overgrowth.client.render.entity;

import com.creep3rcrafter.overgrowth.Overgrowth;
import com.creep3rcrafter.overgrowth.client.model.entity.GloomModel;
import com.creep3rcrafter.overgrowth.common.entity.GloomEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GloomRenderer extends MobRenderer<GloomEntity, GloomModel<GloomEntity>> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Overgrowth.MOD_ID, "textures/entity/gloom.png");

    public GloomRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new GloomModel<>(), 0f);
    }

    public int getBlockLightLevel(BlazeEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(GloomEntity entity) {
        return TEXTURE;
    }
}
