package com.modding.parmy.entity.ArtilleryCannonDef;

import com.modding.parmy.ParmyMod;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ArtilleryShellDefModel extends AnimatedGeoModel<ArtilleryShellDefProjectile> {
    @Override
    public ResourceLocation getModelResource(ArtilleryShellDefProjectile object) {
        return new ResourceLocation(ParmyMod.MOD_ID, "geo/drone_bomb.geo.json");
    };
    @Override
    public ResourceLocation getTextureResource(ArtilleryShellDefProjectile object) {
        return new ResourceLocation(ParmyMod.MOD_ID, "textures/entity/drone_shell_texture.png");
    };
    @Override
    public ResourceLocation getAnimationResource(ArtilleryShellDefProjectile animatable) {
        return new ResourceLocation(ParmyMod.MOD_ID, "animations/drone.model.animation.json");
    }
};