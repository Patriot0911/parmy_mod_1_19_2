package com.modding.parmy.entity;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.entity.Drone.DroneEntity;

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
                () -> EntityType.Builder.of(DroneEntity::new, MobCategory.MISC)
                    .sized(0.6f, 0.4f)
                    .build(
                        new ResourceLocation(ParmyMod.MOD_ID, "drone").toString()
                    )
            );


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
