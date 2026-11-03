package com.zolla.zollacraft.client;

import com.zolla.zollacraft.ZollaCraft;
import com.zolla.zollacraft.entity.client.ZollaGirlRenderer;
import com.zolla.zollacraft.init.ModEntityTypes;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Setup do lado cliente: registra o renderer da entidade.
 */
@Mod.EventBusSubscriber(modid = ZollaCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() ->
                RenderingRegistry.registerEntityRenderingHandler(
                        ModEntityTypes.ZOLLA_GIRL.get(), ZollaGirlRenderer::new));
    }
}
