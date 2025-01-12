package com.modding.parmy.entity.ArtilleryCannonDef;


import com.modding.parmy.entity.ModEntityTypes;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ArtilleryShellDefProjectile extends Projectile implements IAnimatable {
    @SuppressWarnings("removal")
    private AnimationFactory factory = new AnimationFactory(this);
    private static final float GRAVITY = 0.05f;
    private boolean isPosAngle = true;
    private boolean canSpawn = true;

    public ArtilleryShellDefProjectile(EntityType<? extends ArtilleryShellDefProjectile> type, Level level, boolean spawn) {
        super(type, level);
        canSpawn = spawn;
    };

    public ArtilleryShellDefProjectile(EntityType<? extends ArtilleryShellDefProjectile> type, Level level) {
        super(type, level);
    };

    public ArtilleryShellDefProjectile(EntityType<? extends ArtilleryShellDefProjectile> type, Level level, double x, double y, double z) {
        super(type, level);
        this.setPos(x, y, z);
        canSpawn = true;
    };
    @Override
    public boolean isNoGravity() {
        return false;
    };

    @Override
    protected void onHit(HitResult p_37260_) {
        if(!this.level.isClientSide) {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 8.0f, Explosion.BlockInteraction.DESTROY);
            this.discard();
        };
    };

    @Override
    public void tick() {
        super.tick();

        // Класичний варіант використання гравітації
        // if(!this.isNoGravity()) {
        //     this.setDeltaMovement(this.getDeltaMovement().add(0.0, -GRAVITY, 0.0));
        // };

        //
        if(!this.isNoGravity()) {
            if(isPosAngle && canSpawn) {
                if(this.getDeltaMovement().y < 0) {
                    System.out.println(this.getDeltaMovement());
                    System.out.println("this.getDeltaMovement()");
                    if(!this.level.isClientSide) {
                        for(int i = 0; i < 5; i++) {
                            ArtilleryShellDefProjectile projectile = new ArtilleryShellDefProjectile(
                                ModEntityTypes.ARTILLERY_SHELL_DEF.get(),
                                level,
                                false
                            );
                            projectile.setPos(
                                this.getX() + Math.random()*15,
                                this.getY() + Math.random()*15,
                                this.getZ() + Math.random()*15
                            );
                            projectile.setDeltaMovement(this.getDeltaMovement());
                            level.addFreshEntity(projectile);
                        };
                        isPosAngle = false;
                    };
                };
            };
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -GRAVITY, 0.0));
        };

        if(this.horizontalCollision || this.verticalCollision) {
            HitResult hitResult = this.level.clip(
                new ClipContext(
                    this.position(),
                    this.position().add(this.getDeltaMovement()),
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this
                )
            );
            this.onHit(hitResult);
        };

        this.move(MoverType.SELF, this.getDeltaMovement());
    };

    // private void explode() {
    //     Entity parent = this.getParent(
    //         (ServerLevel) level
    //     );
    //     setOwner(parent);
    //     this.level.explode(
    //         parent == null ? this : parent,
    //         this.getX(),
    //         this.getY(),
    //         this.getZ(),
    //         explosionStrength,
    //         Explosion.BlockInteraction.DESTROY
    //     );
    // };

    @Override
    public void registerControllers(AnimationData data) {};

    @Override
    public AnimationFactory getFactory() {
        return factory;
    };

    @Override
    protected void defineSynchedData() {};

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    };
};

