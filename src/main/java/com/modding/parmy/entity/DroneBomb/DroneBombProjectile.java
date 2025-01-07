package com.modding.parmy.entity.DroneBomb;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DroneBombProjectile extends Projectile implements IAnimatable {
    @SuppressWarnings("removal")
    private AnimationFactory factory = new AnimationFactory(this);
    private float explosionStrength;

    public DroneBombProjectile(EntityType<? extends DroneBombProjectile> type, Level level) {
        super(type, level);
        this.explosionStrength = 20.0F;
    }

    public DroneBombProjectile(EntityType<? extends DroneBombProjectile> type, Level level, double x, double y, double z, float explosionStrength) {
        super(type, level);
        this.setPos(x, y, z);
        this.explosionStrength = explosionStrength;
    }


    @Override
    public boolean isNoGravity() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.level.isClientSide) {
            Vec3 deltaMovement = this.getDeltaMovement();
            this.move(MoverType.SELF, deltaMovement);

            BlockPos pos = this.blockPosition();
            BlockPos bPos = this.blockPosition().below();
            if (!this.level.getBlockState(pos).isAir() || !this.level.getBlockState(bPos).isAir()) {
                explode();
                this.discard();
            }
        }
    }

    private void explode() {
        this.level.explode(
            this,
            this.getX(),
            this.getY(),
            this.getZ(),
            explosionStrength,
            Explosion.BlockInteraction.DESTROY
        );
    }

    @Override
    public void registerControllers(AnimationData data) {};

    @Override
    public AnimationFactory getFactory() {
        return factory;
    };


    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.explosionStrength = tag.getFloat("ExplosionStrength");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("ExplosionStrength", this.explosionStrength);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

