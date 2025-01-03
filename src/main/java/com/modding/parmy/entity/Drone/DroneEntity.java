package com.modding.parmy.entity.Drone;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DroneEntity extends FlyingMob implements IAnimatable {
    @SuppressWarnings("removal")
    private AnimationFactory factory = new AnimationFactory(this);

    public DroneEntity(EntityType<? extends FlyingMob> pEntityType, Level level) {
        super(pEntityType, level);
    }

    private boolean isFlying = false;

    public void setFlying(boolean flying) {
        this.isFlying = flying;
    };

    @Override
    public void aiStep() {
        super.aiStep();

        if(!isFlying) {
            if (!this.isOnGround()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.08, 0.0));
            }

            if (this.isOnGround()) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.9, -0.5, 0.9));
            }
        }
    }

    @SuppressWarnings("removal")
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        System.out.println("ID_TAG");
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.drone.moving", true));
            return PlayState.CONTINUE;
        };
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.drone.idle", true));
        return PlayState.CONTINUE;
    };

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(
            new AnimationController(this, "controller", 1, this::predicate)
        );
    };

    @Override
    public AnimationFactory getFactory() {
        return factory;
    };

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f).build();
    };


    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_STRAY_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.DOLPHIN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }
};
