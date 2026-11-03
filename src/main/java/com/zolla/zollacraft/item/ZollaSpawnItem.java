package com.zolla.zollacraft.item;

import com.zolla.zollacraft.entity.custom.ZollaGirlEntity;
import com.zolla.zollacraft.init.ModEntityTypes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Item que invoca uma Zolla Girl ao clicar com o botao direito num bloco.
 *
 * Em 1.16.5 o {@code SpawnEggItem} vanilla exige o EntityType ja construido,
 * o que nao combina com DeferredRegister; por isso usamos um item proprio.
 */
public class ZollaSpawnItem extends Item {

    public ZollaSpawnItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();

        if (!world.isRemote && world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            ItemStack stack = context.getItem();
            BlockPos pos = context.getPos();
            Direction face = context.getFace();

            // Posiciona em cima do bloco clicado.
            BlockPos spawnPos = pos.offset(face);

            ZollaGirlEntity entity = ModEntityTypes.ZOLLA_GIRL.get().create(serverWorld);
            if (entity != null) {
                entity.setLocationAndAngles(
                        spawnPos.getX() + 0.5D,
                        spawnPos.getY(),
                        spawnPos.getZ() + 0.5D,
                        MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F),
                        0.0F);
                serverWorld.addEntity(entity);

                PlayerEntity player = context.getPlayer();
                if (player == null || !player.abilities.isCreativeMode) {
                    stack.shrink(1);
                }
            }
        }

        return ActionResultType.func_233537_a_(world.isRemote);
    }
}
