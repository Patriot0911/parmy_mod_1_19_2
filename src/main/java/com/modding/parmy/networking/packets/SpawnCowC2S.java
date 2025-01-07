package com.modding.parmy.networking.packets;

import java.util.function.Supplier;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.entity.ModEntityTypes;
import com.modding.parmy.entity.Drone.DroneEntity;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class SpawnCowC2S {
    public SpawnCowC2S() {}

    public SpawnCowC2S(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            Vec3 position = player.position();
            ParmyMod.specEnt = new DroneEntity(ModEntityTypes.DRONE.get(), level);
            ParmyMod.specEnt.setPos(position.x, position.y, position.z);
            level.addFreshEntity(ParmyMod.specEnt);
            Minecraft mc = Minecraft.getInstance();
            mc.options.hideGui = true;
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.getModelViewStack().pushPose();
            RenderSystem.getModelViewStack().setIdentity();
            RenderSystem.applyModelViewMatrix();

            player.connection.send(
                new ClientboundSetCameraPacket(ParmyMod.specEnt)
            );
        });
        return true;
    }
}
