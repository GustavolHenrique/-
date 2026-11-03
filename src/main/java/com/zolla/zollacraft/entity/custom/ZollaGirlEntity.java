package com.zolla.zollacraft.entity.custom;

import javax.annotation.Nullable;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Zolla Girl: uma catgirl feita de cubos que serve como aliada.
 *
 * - Domesticavel com peixe cru (bacalhau / {@link Items#COD}).
 * - Segue o dono, senta ao receber shift/clique, e defende o dono atacando
 *   monstros proximos.
 */
public class ZollaGirlEntity extends TameableEntity {

    /** Item usado para domesticar. Troque aqui se quiser outro item. */
    private static final Item TAMING_ITEM = Items.COD;

    public ZollaGirlEntity(EntityType<? extends TameableEntity> type, World world) {
        super(type, world);
        // Nao se afoga facilmente e trata portas/portoes.
        this.setTamed(false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.1D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));

        // Alvos: defende o dono e ataca monstros.
        this.targetSelector.addGoal(1, new net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new net.minecraft.entity.ai.goal.OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new net.minecraft.entity.ai.goal.NearestAttackableTargetGoal<>(
                this, MonsterEntity.class, false));
    }

    public static AttributeModifierMap.MutableAttribute setAttributes() {
        return TameableEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 24.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();

        if (this.world.isRemote) {
            boolean flag = this.isOwner(player) || this.isTamed()
                    || (item == TAMING_ITEM && !this.isTamed());
            return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {
            if (this.isTamed()) {
                if (this.isOwner(player)) {
                    // Cura com comida se estiver ferida.
                    if (item.isFood() && this.getHealth() < this.getMaxHealth()) {
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }
                        this.heal((float) (item.getFood() != null ? item.getFood().getHealing() : 4));
                        return ActionResultType.SUCCESS;
                    }

                    // Alterna sentada/em pe.
                    ActionResultType result = super.func_230254_b_(player, hand);
                    if ((!result.isSuccessOrConsume()) || this.isChild()) {
                        this.func_233687_w_(!this.func_233684_eK_());
                        this.isJumping = false;
                        this.getNavigator().clearPath();
                        this.setAttackTarget(null);
                    }
                    return ActionResultType.SUCCESS;
                }
            } else if (item == TAMING_ITEM) {
                // Tentativa de domesticar.
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }

                if (this.rand.nextInt(3) == 0) {
                    this.setTamedBy(player);
                    this.getNavigator().clearPath();
                    this.setAttackTarget(null);
                    this.func_233687_w_(true);
                    this.world.setEntityState(this, (byte) 7); // coracoes
                } else {
                    this.world.setEntityState(this, (byte) 6); // fumaca
                }

                return ActionResultType.SUCCESS;
            }

            return super.func_230254_b_(player, hand);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) {
        // Nao se reproduz (so via ovo de spawn).
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CAT_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(net.minecraft.util.DamageSource source) {
        return SoundEvents.ENTITY_CAT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CAT_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }

    /** Usado pelo renderer: inclina a cabeca quando sentada, etc. (helper). */
    public boolean isSittingPose() {
        return this.func_233684_eK_();
    }
}
