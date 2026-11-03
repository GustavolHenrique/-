package com.zolla.zollacraft.entity.client;

import com.zolla.zollacraft.ZollaCraft;
import com.zolla.zollacraft.entity.custom.ZollaGirlEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ZollaGirlRenderer extends MobRenderer<ZollaGirlEntity, ZollaGirlModel<ZollaGirlEntity>> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ZollaCraft.MOD_ID, "textures/entity/zolla_girl.png");

    public ZollaGirlRenderer(EntityRendererManager manager) {
        super(manager, new ZollaGirlModel<>(), 0.4F);
    }

    @Override
    public ResourceLocation getEntityTexture(ZollaGirlEntity entity) {
        return TEXTURE;
    }
}
