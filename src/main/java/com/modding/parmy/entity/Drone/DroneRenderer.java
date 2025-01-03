package com.modding.parmy.entity.Drone;

import javax.annotation.Nullable;

import com.modding.parmy.ParmyMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DroneRenderer extends GeoEntityRenderer<DroneEntity> {
    public DroneRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DroneModel());
        this.shadowRadius = 0.3f;
    };

    @Override
    public ResourceLocation getTextureLocation(DroneEntity instance) {
        return new ResourceLocation(ParmyMod.MOD_ID, "textures/entity/drone_texture.png");
    };

    @Override
    public RenderType getRenderType(
        DroneEntity animatable, float partialTicks, PoseStack stack,
        @Nullable MultiBufferSource renderTypeBuffer,
        @Nullable VertexConsumer vertexBuilder, int packedLightIn,
        ResourceLocation textureLocation
    ) {
        stack.scale(0.8f, 0.8f, 0.8f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    };
}
