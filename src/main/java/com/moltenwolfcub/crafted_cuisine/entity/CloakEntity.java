package com.moltenwolfcub.crafted_cuisine.entity;

import com.moltenwolfcub.crafted_cuisine.init.ModSounds;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CloakEntity extends Monster implements IAnimatable {
    private static final int MAX_HEALTH = 1;
    private static final int ATTACK_DAMAGE = 1;
    private static final int ATTACK_SPEED = 1;
    private static final int ATTACK_KNOCKBACK = 1;
    private static final int KNOCKBACK_RESISTANCE = 1;
    private static final int SPEED = 1;
    private static final int ARMOR = 1;

    private AnimationFactory factory = new AnimationFactory(this);

    public CloakEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, MAX_HEALTH)      
            .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE)
            .add(Attributes.ATTACK_SPEED, ATTACK_SPEED)
            .add(Attributes.MOVEMENT_SPEED, SPEED)
            .add(Attributes.ATTACK_KNOCKBACK, ATTACK_KNOCKBACK)
            .add(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE)
            .add(Attributes.ARMOR, ARMOR)

            .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(
            this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cloak.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cloak.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.CLOAK_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SCULK_SENSOR_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }
    
}
