package com.modding.parmy.networking.packets;

import java.util.function.Supplier;

import com.modding.parmy.ParmyMod;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class LeaveCameraC2S {
    public LeaveCameraC2S() {}

    public LeaveCameraC2S(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            if(ParmyMod.specEnt == null)
                return;
            ParmyMod.specEnt.setFlying(false);
            Minecraft mc = Minecraft.getInstance();
            mc.options.hideGui = false;
            ParmyMod.specEnt = null;
            player.connection.send(
                new ClientboundSetCameraPacket(player)
            );
        });
        return true;
    }
}
