package com.modding.parmy.networking.packets;

import java.util.List;
import java.util.function.Supplier;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.entity.Drone.DroneEntity;
import com.modding.parmy.utils.DirectionManager;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class MoveDroneC2S {
    private final DirectionManager.Direction[] directions;
    private final float yAngle;
    private final float xAngle;

    public MoveDroneC2S(List<DirectionManager.Direction> directions, float yAngle, float xAngle) {
        this.directions =
            directions == null ? null :
            directions.toArray(new DirectionManager.Direction[0]);
        this.yAngle = yAngle;
        this.xAngle = xAngle;
    }

    public MoveDroneC2S(FriendlyByteBuf buf) {
        int directionsLen = buf.readInt();
        if(directionsLen > 0) {
            this.directions = new DirectionManager.Direction[directionsLen];
            for(int i = 0; i < directionsLen; i++) {
                this.directions[i] = buf.readEnum(DirectionManager.Direction.class);
            };
        } else {
            this.directions = null;
        };
        this.yAngle = buf.readFloat();
        this.xAngle = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        if(this.directions != null) {
            buf.writeInt(this.directions.length);
            for(int i = 0; i < this.directions.length; i++) {
                buf.writeEnum(this.directions[i]);
            };
        } else {
            buf.writeInt(0);
        };
        buf.writeFloat(yAngle);
        buf.writeFloat(xAngle);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if(player != null && ParmyMod.specEnt != null) {
                if(this.directions != null) {
                    ParmyMod.specEnt.setXRot(-1*xAngle);
                    ParmyMod.specEnt.setYRot(yAngle);
                    moveEntity(player, this.directions, 0.4, yAngle, xAngle*-1);
                    ParmyMod.specEnt.setYHeadRot(yAngle);
                    ParmyMod.specEnt.setYBodyRot(yAngle);
                } else {
                    ParmyMod.specEnt.setXRot(xAngle*-1);
                    ParmyMod.specEnt.setYRot(yAngle*-1);
                    ParmyMod.specEnt.setYBodyRot(yAngle);
                    ParmyMod.specEnt.setYHeadRot(yAngle);
                };
            };
        });
        return true;
    };

    public static void moveEntity(Entity player, DirectionManager.Direction[] directions, double speed, float yaw, float pitch) {
        DroneEntity entity = (DroneEntity) ParmyMod.specEnt;
        if(entity != null) {
            Vec3 movement = DirectionManager.getVecByDirections(directions, speed, yaw, pitch);
            player.move(MoverType.SELF, movement);
            entity.move(MoverType.SELF, movement);
        };
    };
};
