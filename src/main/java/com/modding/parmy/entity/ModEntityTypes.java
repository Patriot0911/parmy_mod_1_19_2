package com.modding.parmy.entity;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.entity.ArtilleryCannonDef.ArtilleryShellDefProjectile;
import com.modding.parmy.entity.Drone.DroneEntity;
import com.modding.parmy.entity.DroneBomb.DroneBombProjectile;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ParmyMod.MOD_ID);

    public static final RegistryObject<EntityType<DroneEntity>> DRONE =
        ENTITY_TYPES.register("drone",
            () -> EntityType.Builder.<DroneEntity>of(DroneEntity::new, MobCategory.MISC)
                .sized(0.6f, 0.4f)
                .clientTrackingRange(16)
                .updateInterval(2)
                .build(
                    new ResourceLocation(ParmyMod.MOD_ID, "drone").toString()
                )
        );

    public static final RegistryObject<EntityType<DroneBombProjectile>> DRONE_BOMB =
        ENTITY_TYPES.register("drone_bomb",
            () -> EntityType.Builder.<DroneBombProjectile>of(DroneBombProjectile::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(64)
                .updateInterval(1)
                .build("drone_bomb")
        );

    public static final RegistryObject<EntityType<ArtilleryShellDefProjectile>> ARTILLERY_SHELL_DEF =
        ENTITY_TYPES.register("art_shell_def",
            () -> EntityType.Builder.<ArtilleryShellDefProjectile>of(ArtilleryShellDefProjectile::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(64)
                .updateInterval(1)
                .build("art_shell_def")
        );


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
