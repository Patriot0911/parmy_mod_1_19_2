package com.modding.parmy.networking.packets;

import java.util.function.Supplier;

import com.modding.parmy.entity.ModEntityTypes;
import com.modding.parmy.entity.DroneBomb.DroneBombProjectile;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class DropDroneBombC2S {
    public DropDroneBombC2S() {

    }

    public DropDroneBombC2S(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public static void launchExplodingProjectile(ServerLevel level, double x, double y, double z, float explosionStrength) {
        DroneBombProjectile projectile = new DroneBombProjectile(ModEntityTypes.DRONE_BOMB.get(), level, x, y, z, explosionStrength);
        projectile.setDeltaMovement(0, -2, 0);
        level.addFreshEntity(projectile);
    };

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            Vec3 position = player.position();
            launchExplodingProjectile(level, position.x, position.y, position.z, 15);
        });
        return true;
    }
}
