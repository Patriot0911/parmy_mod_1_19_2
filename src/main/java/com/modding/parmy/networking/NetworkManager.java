package com.modding.parmy.networking;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.networking.packets.MoveDroneC2S;
import com.modding.parmy.networking.packets.SpawnCowC2S;
import com.modding.parmy.networking.packets.DropDroneBombC2S;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkManager {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int nextId() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ParmyMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(SpawnCowC2S.class, nextId(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SpawnCowC2S::new)
                .encoder(SpawnCowC2S::toBytes)
                .consumerMainThread(SpawnCowC2S::handle)
                .add();

        net.messageBuilder(MoveDroneC2S.class, nextId(), NetworkDirection.PLAY_TO_SERVER)
            .decoder(MoveDroneC2S::new)
            .encoder(MoveDroneC2S::toBytes)
            .consumerMainThread(MoveDroneC2S::handle)
            .add();

        net.messageBuilder(DropDroneBombC2S.class, nextId(), NetworkDirection.PLAY_TO_SERVER)
            .decoder(DropDroneBombC2S::new)
            .encoder(DropDroneBombC2S::toBytes)
            .consumerMainThread(DropDroneBombC2S::handle)
            .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
