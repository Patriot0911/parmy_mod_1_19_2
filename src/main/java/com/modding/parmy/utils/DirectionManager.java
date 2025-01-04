package com.modding.parmy.utils;

import net.minecraft.world.phys.Vec3;

public class DirectionManager {
    public static enum Direction {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    public static Vec3 getVecByDirection(DirectionManager.Direction direction, double power, float yaw, float pitch) {
        Vec3 baseVec = switch (direction) {
            case FORWARD -> new Vec3(0, 0, power);
            case BACKWARD -> new Vec3(0, 0, -1 * power);
            case LEFT -> new Vec3(-1 * power, 0, 0);
            case RIGHT -> new Vec3(power, 0, 0);
        };
        // float yawRadians = (float) Math.toRadians(yaw);
        // float pitchRadians = (float) Math.toRadians(pitch);
        // double x = baseVec.x * Math.cos(yawRadians) - baseVec.z * Math.sin(yawRadians);
        // double z = baseVec.x * Math.sin(yawRadians) + baseVec.z * Math.cos(yawRadians);
        // double y = baseVec.y - Math.sin(pitchRadians) * power;
        return baseVec;
    };

    public static Direction getDirectionByKey() {
        if(KeyBinding.MOVE_FORWARD_KEY.isDown()) {
            return Direction.FORWARD;
        };
        if(KeyBinding.MOVE_BACKWARD_KEY.isDown()) {
            return Direction.BACKWARD;
        };
        if(KeyBinding.MOVE_LEFT_KEY.isDown()) {
            return Direction.LEFT;
        };
        if(KeyBinding.MOVE_RIGHT_KEY.isDown()) {
            return Direction.RIGHT;
        };
        return null;
    };
};

