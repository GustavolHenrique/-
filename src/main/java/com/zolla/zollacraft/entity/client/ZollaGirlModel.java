package com.zolla.zollacraft.entity.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.zolla.zollacraft.entity.custom.ZollaGirlEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Modelo cubico da Zolla Girl (estilo "catgirl" de blocos).
 *
 * A textura e 128x64:
 *  - Metade esquerda (0..64) = skin estilo jogador (cabeca, corpo, bracos, pernas).
 *  - Metade direita (64..128) = partes extras (orelhas de gato, cauda, cabelo, saia).
 *
 * Veja README-TEXTURAS.md para o mapa de UV exato para pintar / gerar a textura.
 */
public class ZollaGirlModel<T extends ZollaGirlEntity> extends EntityModel<T> {

    private final ModelRenderer head;
    private final ModelRenderer earRight;
    private final ModelRenderer earLeft;
    private final ModelRenderer bangs;

    private final ModelRenderer body;
    private final ModelRenderer backHair;
    private final ModelRenderer skirt;
    private final ModelRenderer tail;

    private final ModelRenderer armRight;
    private final ModelRenderer armLeft;
    private final ModelRenderer legRight;
    private final ModelRenderer legLeft;

    public ZollaGirlModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;

        // ---------- CABECA ----------
        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F);

        // Orelhas de gato (filhas da cabeca).
        earRight = new ModelRenderer(this);
        earRight.setRotationPoint(-2.5F, -8.0F, 0.0F);
        earRight.setTextureOffset(64, 0).addBox(-2.0F, -4.0F, -0.5F, 3.0F, 4.0F, 1.0F, 0.0F);
        earRight.rotateAngleZ = -0.35F;
        head.addChild(earRight);

        earLeft = new ModelRenderer(this);
        earLeft.setRotationPoint(2.5F, -8.0F, 0.0F);
        earLeft.mirror = true;
        earLeft.setTextureOffset(64, 8).addBox(-1.0F, -4.0F, -0.5F, 3.0F, 4.0F, 1.0F, 0.0F);
        earLeft.rotateAngleZ = 0.35F;
        head.addChild(earLeft);

        // Franja / cabelo da frente (filha da cabeca).
        bangs = new ModelRenderer(this);
        bangs.setRotationPoint(0.0F, 0.0F, 0.0F);
        bangs.setTextureOffset(0, 32).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 5.0F, 9.0F, 0.0F);
        head.addChild(bangs);

        // ---------- CORPO ----------
        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.setTextureOffset(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F);

        // Cabelo longo nas costas (filho do corpo, vai ate perto das pernas).
        backHair = new ModelRenderer(this);
        backHair.setRotationPoint(0.0F, 0.0F, 0.0F);
        backHair.setTextureOffset(64, 20).addBox(-4.5F, -2.0F, 2.0F, 9.0F, 22.0F, 1.0F, 0.0F);
        body.addChild(backHair);

        // Saia.
        skirt = new ModelRenderer(this);
        skirt.setRotationPoint(0.0F, 0.0F, 0.0F);
        skirt.setTextureOffset(96, 40).addBox(-4.5F, 10.0F, -2.5F, 9.0F, 6.0F, 5.0F, 0.0F);
        body.addChild(skirt);

        // Cauda (filha do corpo).
        tail = new ModelRenderer(this);
        tail.setRotationPoint(0.0F, 10.0F, 2.5F);
        tail.setTextureOffset(80, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 12.0F, 2.0F, 0.0F);
        tail.rotateAngleX = -0.7F;
        body.addChild(tail);

        // ---------- BRACOS ----------
        armRight = new ModelRenderer(this);
        armRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
        armRight.setTextureOffset(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F);

        armLeft = new ModelRenderer(this);
        armLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
        armLeft.mirror = true;
        armLeft.setTextureOffset(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F);

        // ---------- PERNAS ----------
        legRight = new ModelRenderer(this);
        legRight.setRotationPoint(-1.9F, 12.0F, 0.0F);
        legRight.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F);

        legLeft = new ModelRenderer(this);
        legLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
        legLeft.mirror = true;
        legLeft.setTextureOffset(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount,
                                  float ageInTicks, float netHeadYaw, float headPitch) {
        // Cabeca segue o olhar.
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);

        // Caminhada: pernas e bracos balancam.
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.armRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
        this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
        this.armRight.rotateAngleZ = 0.05F;
        this.armLeft.rotateAngleZ = -0.05F;

        // Cauda balanca suavemente.
        this.tail.rotateAngleY = MathHelper.cos(ageInTicks * 0.12F) * 0.3F;
        this.tail.rotateAngleX = -0.7F + MathHelper.sin(ageInTicks * 0.09F) * 0.12F;

        // Orelhas mexem levemente.
        this.earRight.rotateAngleX = MathHelper.sin(ageInTicks * 0.05F) * 0.05F;
        this.earLeft.rotateAngleX = MathHelper.sin(ageInTicks * 0.05F + 1.0F) * 0.05F;

        // Pose sentada.
        if (entity.isSittingPose()) {
            this.legRight.rotateAngleX = -1.4F;
            this.legRight.rotateAngleY = 0.3F;
            this.legLeft.rotateAngleX = -1.4F;
            this.legLeft.rotateAngleY = -0.3F;
            this.armRight.rotateAngleX = -0.3F;
            this.armLeft.rotateAngleX = -0.3F;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight,
                       int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.armRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.armLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.legRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.legLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
