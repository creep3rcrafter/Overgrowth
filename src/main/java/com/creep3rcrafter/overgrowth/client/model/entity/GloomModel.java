package com.creep3rcrafter.overgrowth.client.model.entity;

import com.creep3rcrafter.overgrowth.common.entity.GloomEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GloomModel<T extends GloomEntity> extends EntityModel<T> {
    private final ModelRenderer main;
    private final ModelRenderer head;
    private final ModelRenderer leaves;
    private final ModelRenderer leave_west_r1;
    private final ModelRenderer leave_south_r1;
    private final ModelRenderer leave_north_r1;

    public GloomModel() {
        texWidth = 64;
        texHeight = 64;

        main = new ModelRenderer(this);
        main.setPos(0.0F, 25.0f, 0.0F);


        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        main.addChild(head);
        head.texOffs(0, 20).addBox(-4.0F, -12.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        head.texOffs(32, 32).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        head.texOffs(32, 28).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        head.texOffs(0, 0).addBox(-5.0F, -13.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);

        leaves = new ModelRenderer(this);
        leaves.setPos(1.0F, -14.0F, 0.0F);
        head.addChild(leaves);
        leaves.texOffs(-6, 36).addBox(-10.0F, 0.0F, -3.0F, 8.0F, 0.0F, 6.0F, 0.0F, false);

        leave_west_r1 = new ModelRenderer(this);
        leave_west_r1.setPos(0.0F, 0.0F, 0.0F);
        leaves.addChild(leave_west_r1);
        setRotationAngle(leave_west_r1, 0.0F, 3.1416F, 0.0F);
        leave_west_r1.texOffs(-6, 36).addBox(-8.0F, 0.0F, -3.0F, 8.0F, 0.0F, 6.0F, 0.0F, false);

        leave_south_r1 = new ModelRenderer(this);
        leave_south_r1.setPos(-1.0F, 0.0F, 1.0F);
        leaves.addChild(leave_south_r1);
        setRotationAngle(leave_south_r1, 0.0F, 1.5708F, 0.0F);
        leave_south_r1.texOffs(-6, 36).addBox(-8.0F, 0.0F, -3.0F, 8.0F, 0.0F, 6.0F, 0.0F, false);

        leave_north_r1 = new ModelRenderer(this);
        leave_north_r1.setPos(-1.0F, 0.0F, -1.0F);
        leaves.addChild(leave_north_r1);
        setRotationAngle(leave_north_r1, 0.0F, -1.5708F, 0.0F);
        leave_north_r1.texOffs(-6, 36).addBox(-8.0F, 0.0F, -3.0F, 8.0F, 0.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        main.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
