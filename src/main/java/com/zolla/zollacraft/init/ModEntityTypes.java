package com.zolla.zollacraft.init;

import com.zolla.zollacraft.ZollaCraft;
import com.zolla.zollacraft.entity.custom.ZollaGirlEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, ZollaCraft.MOD_ID);

    public static final RegistryObject<EntityType<ZollaGirlEntity>> ZOLLA_GIRL =
            ENTITY_TYPES.register("zolla_girl",
                    () -> EntityType.Builder.create(ZollaGirlEntity::new, EntityClassification.CREATURE)
                            .size(0.6f, 1.8f)
                            .build(new ResourceLocation(ZollaCraft.MOD_ID, "zolla_girl").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
