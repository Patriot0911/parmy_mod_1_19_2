package com.modding.parmy.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.phys.Vec3;

public class DirectionManager {
    public static enum Direction {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT,
        UP,
        DOWN,
    };

    public static Vec3 getVecByDirection(DirectionManager.Direction direction) {
        Vec3 vec = switch (direction) {
            case FORWARD -> new Vec3(0, 0, 1);
            case BACKWARD -> new Vec3(0, 0, -1);
            case LEFT -> new Vec3(1, 0, 0);
            case RIGHT -> new Vec3(-1, 0, 0);
            case UP -> new Vec3(0, 1, 0);
            case DOWN -> new Vec3(0, -1, 0);
        };
        return vec;
    };

    public static Vec3 getVecByDirections(DirectionManager.Direction[] directions, double power, float yaw, float pitch) {
        Vec3 baseVec = new Vec3(0, 0, 0);
        for(int i = 0; i < directions.length; i++) {
            baseVec = baseVec.add(
                getVecByDirection(directions[i])
            );
        };
        float yawRadians = (float) Math.toRadians(yaw);
        double x = baseVec.x * Math.cos(yawRadians) - baseVec.z * Math.sin(yawRadians);
        double z = baseVec.x * Math.sin(yawRadians) + baseVec.z * Math.cos(yawRadians);
        return new Vec3(x, baseVec.y, z).normalize().scale(power);
    };

    public static List<Direction> getDirectionsByKeys() {
        List<Direction> dirs = new ArrayList<Direction>();
        if(KeyBinding.MOVE_FORWARD_KEY.isDown()) {
            dirs.add(Direction.FORWARD);
        };
        if(KeyBinding.MOVE_BACKWARD_KEY.isDown()) {
            dirs.add(Direction.BACKWARD);
        };
        if(KeyBinding.MOVE_LEFT_KEY.isDown()) {
            dirs.add(Direction.LEFT);
        };
        if(KeyBinding.MOVE_RIGHT_KEY.isDown()) {
            dirs.add(Direction.RIGHT);
        };
        if(KeyBinding.MOVE_DOWN_KEY.isDown()) {
            dirs.add(Direction.DOWN);
        };
        if(KeyBinding.MOVE_UP_KEY.isDown()) {
            dirs.add(Direction.UP);
        };
        return dirs.size() > 0 ? dirs : null;
    };
};

