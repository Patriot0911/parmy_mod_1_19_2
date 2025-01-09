package com.modding.parmy.entity.DroneBomb;

import java.util.UUID;

import com.modding.parmy.entity.Drone.DroneEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DroneBombProjectile extends Projectile implements IAnimatable {
    @SuppressWarnings("removal")
    private AnimationFactory factory = new AnimationFactory(this);
    private float explosionStrength;
    private UUID droneParentId;

    public DroneBombProjectile(EntityType<? extends DroneBombProjectile> type, Level level) {
        super(type, level);
        this.explosionStrength = 10.0F;
        this.droneParentId = null;
    };

    public DroneBombProjectile(EntityType<? extends DroneBombProjectile> type, Level level, double x, double y, double z, float explosionStrength, UUID parentUuid) {
        super(type, level);
        this.setPos(x, y, z);
        this.explosionStrength = explosionStrength;
        this.droneParentId = parentUuid;
        // setOwner();
    };

    @Override
    public boolean isNoGravity() {
        return false;
    };

    @Override
    protected void onHit(HitResult p_37260_) {
        super.onHit(p_37260_);
        this.discard();
    };

    @Override
    public void tick() {
        super.tick();
        if(!this.level.isClientSide) {
            Vec3 deltaMovement = this.getDeltaMovement();
            this.move(MoverType.SELF, deltaMovement);

            if(!this.isOnGround() && !this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.05, 0.0));
            };

            BlockPos pos = this.blockPosition();
            BlockPos bPos = this.blockPosition().below();
            if(!this.level.getBlockState(pos).isAir() || !this.level.getBlockState(bPos).isAir()) {
                explode();
                this.discard();
            };
        };
    };

    private void explode() {
        this.level.explode(
            this,
            this.getX(),
            this.getY(),
            this.getZ(),
            explosionStrength,
            Explosion.BlockInteraction.DESTROY
        );
        // for(int i = 0; i < 10; i++) {
        //     level.addParticle(
        //         ParticleTypes.ENCHANT,
        //         this.getX() + random.nextGaussian() * 0.8,
        //         this.getY() + random.nextGaussian() * 0.8,
        //         this.getZ() + random.nextGaussian() * 0.8,
        //         random.nextGaussian() * 0.4,
        //         random.nextGaussian() * 0.4,
        //         random.nextGaussian() * 0.4
        //     );
        // };
    };

    public DroneEntity getParent(ServerLevel level) {
        if(droneParentId == null)
            return null;
        Entity ent = level.getEntity(this.droneParentId);
        return ent instanceof DroneEntity ? (DroneEntity) ent : null;
    };

    public boolean setParent(UUID droneUuid) {
        if(this.droneParentId != null)
            return false;
        this.droneParentId = droneUuid;
        return true;
    };

    @Override
    public void registerControllers(AnimationData data) {};

    @Override
    public AnimationFactory getFactory() {
        return factory;
    };

    @Override
    protected void defineSynchedData() {};

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.explosionStrength = tag.getFloat("ExplosionStrength");
        this.droneParentId = tag.getUUID("ParentUuid");
    };

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putUUID("ParentUuid", droneParentId);
        tag.putFloat("ExplosionStrength", this.explosionStrength);
    };

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    };
};

