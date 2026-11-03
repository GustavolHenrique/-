package com.zolla.zollacraft;

import com.zolla.zollacraft.entity.custom.ZollaGirlEntity;
import com.zolla.zollacraft.init.ModEntityTypes;
import com.zolla.zollacraft.init.ModItems;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Ponto de entrada do mod Zolla Craft.
 *
 * Adiciona a entidade "Zolla Girl" (uma catgirl feita de cubos) como aliada
 * domesticavel para Minecraft 1.16.5 (Forge).
 */
@Mod(ZollaCraft.MOD_ID)
public class ZollaCraft {

    public static final String MOD_ID = "zollacraft";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public ZollaCraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Registra os DeferredRegisters.
        ModEntityTypes.register(modEventBus);
        ModItems.register(modEventBus);

        // Ciclo de vida.
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Zolla Craft carregando...");
    }
}
