package com.modding.parmy.entity.Drone;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.modding.parmy.entity.ModEntityTypes;
import com.modding.parmy.entity.DroneBomb.DroneBombProjectile;
import com.modding.parmy.enums.DroneTypes;
import com.modding.parmy.items.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
    private boolean isFlying = true;
    private DroneTypes type;
    private int bombsCount = 0;
    private List<DroneBombProjectile> bombsList;
    private int bombStrength = 8;

    public DroneEntity(EntityType<? extends FlyingMob> pEntityType, Level level) {
        super(pEntityType, level);
        this.type = DroneTypes.DROP_BOMB;
        this.bombsCount = 1;
        this.bombsList = new ArrayList<DroneBombProjectile>();
    };

    public DroneEntity(EntityType<? extends FlyingMob> pEntityType, Level level, DroneTypes type) {
        super(pEntityType, level);
        this.type = type;
        if(type.equals(DroneTypes.DROP_BOMB)) {
            this.bombsCount = 1;
            this.bombsList = new ArrayList<DroneBombProjectile>();
        };
    };

    public DroneEntity(EntityType<? extends FlyingMob> pEntityType, Level level, DroneTypes type, int count) {
        super(pEntityType, level);
        this.type = type;
        this.bombsCount = count;
        if(type.equals(DroneTypes.DROP_BOMB)) {
            this.bombsCount = 1;
            this.bombsList = new ArrayList<DroneBombProjectile>();
        };
    };

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(ModItems.DRONE_ITEM.get());
    };

    @Override
    public boolean isAlliedTo(Entity entity) {
        return entity instanceof Player; // add owner
    };

    public void setFlying(boolean flying) {
        this.isFlying = flying;
    };

    public DroneTypes getDroneType() {
        return type;
    };

    public boolean dropBomb() {
        if(!this.type.equals(DroneTypes.DROP_BOMB) || this.bombsCount < 1)
            return false;
        --this.bombsCount;
        Vec3 position = this.position();
        DroneBombProjectile projectile = new DroneBombProjectile(
            ModEntityTypes.DRONE_BOMB.get(),
            level,
            position.x,
            position.y,
            position.z,
            bombStrength,
            UUID.randomUUID()
        );
        projectile.setDeltaMovement(0, -2, 0);
        level.addFreshEntity(projectile);
        if(this.bombsList != null) {
            this.bombsList.add(projectile);
        };
        return true;
    };

    public List<DroneBombProjectile> getBombsList() {
        return this.bombsList;
    };

    @Override
    public void aiStep() {
        super.aiStep();
        if(!isFlying) {
            if(!this.isOnGround()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.08, 0.0));
            };
            if(this.isOnGround()) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.9, -0.5, 0.9));
            };
        };
    };


    @SuppressWarnings("removal")
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
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
    };

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_STRAY_AMBIENT;
    };

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.DOLPHIN_HURT;
    };

    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    };

    protected float getSoundVolume() {
        return 0.2F;
    };
};
