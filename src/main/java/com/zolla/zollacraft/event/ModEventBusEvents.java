package com.zolla.zollacraft.event;

import com.zolla.zollacraft.ZollaCraft;
import com.zolla.zollacraft.entity.custom.ZollaGirlEntity;
import com.zolla.zollacraft.init.ModEntityTypes;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Eventos no MOD event bus (setup / registro de atributos de entidade).
 */
@Mod.EventBusSubscriber(modid = ZollaCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.ZOLLA_GIRL.get(), ZollaGirlEntity.setAttributes().create());
    }
}
