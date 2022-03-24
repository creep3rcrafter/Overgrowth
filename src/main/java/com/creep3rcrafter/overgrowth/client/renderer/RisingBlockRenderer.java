package com.creep3rcrafter.overgrowth.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.creep3rcrafter.overgrowth.entity.RisingBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class RisingBlockRenderer extends EntityRenderer<RisingBlockEntity> {

    public RisingBlockRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
        this.shadowRadius = 0.5F;
    }

    public void render(RisingBlockEntity risingBlockEntity, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int p_225623_6_) {
        BlockState blockstate = risingBlockEntity.getBlockState();
        if (blockstate.getRenderShape() == BlockRenderType.MODEL) {
            World world = risingBlockEntity.getLevel();
            if (blockstate != world.getBlockState(risingBlockEntity.blockPosition()) && blockstate.getRenderShape() != BlockRenderType.INVISIBLE) {
                matrixStack.pushPose();
                BlockPos blockpos = new BlockPos(risingBlockEntity.getX(), risingBlockEntity.getBoundingBox().maxY, risingBlockEntity.getZ());
                matrixStack.translate(-0.5D, 0.0D, -0.5D);
                BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
                for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.chunkBufferLayers()) {
                    if (RenderTypeLookup.canRenderInLayer(blockstate, type)) {
                        net.minecraftforge.client.ForgeHooksClient.setRenderLayer(type);
                        blockrendererdispatcher.getModelRenderer()
                                .tesselateBlock(world, blockrendererdispatcher
                                        .getBlockModel(blockstate), blockstate, blockpos, matrixStack,
                                        renderTypeBuffer.getBuffer(type), false, new Random(),
                                        blockstate.getSeed(risingBlockEntity.getStartPos()), OverlayTexture.NO_OVERLAY);
                    }
                }
                net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
                matrixStack.popPose();
                super.render(risingBlockEntity, p_225623_2_, p_225623_3_, matrixStack, renderTypeBuffer, p_225623_6_);
            }
        }
    }

    public ResourceLocation getTextureLocation(RisingBlockEntity risingBlockEntity) {
        return AtlasTexture.LOCATION_BLOCKS;
    }
}
