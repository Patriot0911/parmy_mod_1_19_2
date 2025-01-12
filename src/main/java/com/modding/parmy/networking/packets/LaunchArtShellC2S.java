package com.modding.parmy.networking.packets;

import java.util.function.Supplier;

import com.modding.parmy.entity.ModEntityTypes;
import com.modding.parmy.entity.ArtilleryCannonDef.ArtilleryShellDefProjectile;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class LaunchArtShellC2S {
    public LaunchArtShellC2S() {}

    public LaunchArtShellC2S(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    private float getInLimit(float val) {
        return val > 360 ? 360.0f :
            val < 0 ? 0 : val;
    };

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            ArtilleryShellDefProjectile projectile = new ArtilleryShellDefProjectile(
                ModEntityTypes.ARTILLERY_SHELL_DEF.get(),
                level
            );

            projectile.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());

            float pitch = player.getXRot();
            float yaw = player.getYRot();

            // System.out.println("Pitch (X): " + pitch);
            // System.out.println("Yaw (Y): " + yaw);

            double pitchRadians = Math.toRadians(-pitch);
            double yawRadians = Math.toRadians(yaw);

            double x = Math.cos(pitchRadians) * -Math.sin(yawRadians);
            double z = Math.cos(pitchRadians) * Math.cos(yawRadians);
            double y = Math.sin(pitchRadians);

            double power = 3.0;
            projectile.setDeltaMovement(x * power, y * power, z * power);

            level.addFreshEntity(projectile);
        });
        return true;
    }
}
