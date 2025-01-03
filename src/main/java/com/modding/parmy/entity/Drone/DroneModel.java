package com.modding.parmy.entity.Drone;

import com.modding.parmy.ParmyMod;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DroneModel extends AnimatedGeoModel<DroneEntity> {
    @Override
    public ResourceLocation getModelResource(DroneEntity object) {
        return new ResourceLocation(ParmyMod.MOD_ID, "geo/test_drone.geo.json");
    };
    @Override
    public ResourceLocation getTextureResource(DroneEntity object) {
        return new ResourceLocation(ParmyMod.MOD_ID, "textures/entity/drone_texture.png");
    };
    @Override
    public ResourceLocation getAnimationResource(DroneEntity animatable) {
        return new ResourceLocation(ParmyMod.MOD_ID, "animations/drone.model.animation.json");
    };
};