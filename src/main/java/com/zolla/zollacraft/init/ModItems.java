package com.zolla.zollacraft.init;

import com.zolla.zollacraft.ZollaCraft;
import com.zolla.zollacraft.item.ZollaSpawnItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ZollaCraft.MOD_ID);

    // Item que invoca a Zolla Girl (clique direito num bloco).
    public static final RegistryObject<Item> ZOLLA_GIRL_SPAWN_EGG =
            ITEMS.register("zolla_girl_spawn_egg",
                    () -> new ZollaSpawnItem(new Item.Properties().group(ItemGroup.MISC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
