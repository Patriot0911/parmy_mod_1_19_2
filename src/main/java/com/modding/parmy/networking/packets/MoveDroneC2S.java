package com.modding.parmy.networking.packets;

import java.util.function.Supplier;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.entity.Drone.DroneEntity;
import com.modding.parmy.utils.DirectionManager;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class MoveDroneC2S {
    private final DirectionManager.Direction direction;
    private final float yAngle;
    private final float xAngle;

    public MoveDroneC2S(DirectionManager.Direction direction, float yAngle, float xAngle) {
        this.direction = direction;
        this.yAngle = yAngle;
        this.xAngle = xAngle;
    }

    public MoveDroneC2S(FriendlyByteBuf buf) {
        this.direction = buf.readNullable(buffer -> buffer.readEnum(DirectionManager.Direction.class));
        this.yAngle = buf.readFloat();
        this.xAngle = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNullable(direction, (buffer, dir) -> buffer.writeEnum(dir));
        buf.writeFloat(yAngle);
        buf.writeFloat(xAngle);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if(player != null) {
                if(direction != null) {
                    moveEntity(direction, 0.2, yAngle, xAngle);
                };
                ParmyMod.specEnt.setYRot(yAngle*-1);
                ParmyMod.specEnt.setXRot(xAngle*-1);
                ParmyMod.specEnt.setYHeadRot(yAngle);
            };
        });
        return true;
    };

    public static void moveEntity(DirectionManager.Direction direction, double speed, float yaw, float pitch) {
        DroneEntity entity = (DroneEntity) ParmyMod.specEnt;
        if(entity != null) {
            Vec3 movement = DirectionManager.getVecByDirection(direction, speed, yaw, pitch);
            entity.move(MoverType.SELF, movement);
        };
    };
};
