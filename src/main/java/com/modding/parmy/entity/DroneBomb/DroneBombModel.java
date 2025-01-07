package com.modding.parmy.entity.DroneBomb;

import com.modding.parmy.ParmyMod;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DroneBombModel extends AnimatedGeoModel<DroneBombProjectile> {
    @Override
    public ResourceLocation getModelResource(DroneBombProjectile object) {
        return new ResourceLocation(ParmyMod.MOD_ID, "geo/drone_bomb.geo.json");
    };
    @Override
    public ResourceLocation getTextureResource(DroneBombProjectile object) {
        return new ResourceLocation(ParmyMod.MOD_ID, "textures/entity/drone_shell_texture.png");
    };
    @Override
    public ResourceLocation getAnimationResource(DroneBombProjectile animatable) {
        return new ResourceLocation(ParmyMod.MOD_ID, "animations/drone.model.animation.json");
    }
};