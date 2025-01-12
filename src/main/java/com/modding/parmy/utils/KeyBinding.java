package com.modding.parmy.utils;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBinding {
    public static final String KEY_CATEGORY_DRONE = "key.category.parmy.drones";
    private static final String KEY_ART_SHELL_LAUNCH = "key.parmy.launch_art_shell";
    private static final String KEY_SPAWN_DRONE = "key.parmy.spawn_drone";
    private static final String KEY_SPAWN_DRONE_BOMB = "key.parmy.spawn_drone_bomb";
    private static final String KEY_MOVE_FWD_DRONE = "key.parmy.move_forward_drone";
    private static final String KEY_MOVE_BWD_DRONE = "key.parmy.move_backward_drone";
    private static final String KEY_MOVE_LEFT_DRONE = "key.parmy.move_left_drone";
    private static final String KEY_MOVE_RIGHT_DRONE = "key.parmy.move_right_drone";
    private static final String KEY_MOVE_UP_DRONE = "key.parmy.move_up_drone";
    private static final String KEY_MOVE_DOWN_DRONE = "key.parmy.move_down_drone";

    // TEST ART ACTIONS
    public static final KeyMapping LAUNCH_SHELL_KEY = new KeyMapping(
        KEY_ART_SHELL_LAUNCH,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_H,
        KEY_CATEGORY_DRONE
    );

    // TEST DRONE ACTIONS
    public static final KeyMapping SPAWN_DRONE_KEY = new KeyMapping(
        KEY_SPAWN_DRONE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_C,
        KEY_CATEGORY_DRONE
    );
    public static final KeyMapping SPAWN_DRONE_BOMB_KEY = new KeyMapping(
        KEY_SPAWN_DRONE_BOMB,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_F,
        KEY_CATEGORY_DRONE
    );

    // DRONE MOVEMENT
    public static final KeyMapping MOVE_UP_KEY = new KeyMapping(
        KEY_MOVE_UP_DRONE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_SPACE,
        KEY_CATEGORY_DRONE
    );
    public static final KeyMapping MOVE_DOWN_KEY = new KeyMapping(
        KEY_MOVE_DOWN_DRONE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_LEFT_SHIFT,
        KEY_CATEGORY_DRONE
    );
    public static final KeyMapping MOVE_FORWARD_KEY = new KeyMapping(
        KEY_MOVE_FWD_DRONE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_W,
        KEY_CATEGORY_DRONE
    );
    public static final KeyMapping MOVE_BACKWARD_KEY = new KeyMapping(
        KEY_MOVE_BWD_DRONE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_S,
        KEY_CATEGORY_DRONE
    );
    public static final KeyMapping MOVE_LEFT_KEY = new KeyMapping(
        KEY_MOVE_LEFT_DRONE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_A,
        KEY_CATEGORY_DRONE
    );
    public static final KeyMapping MOVE_RIGHT_KEY = new KeyMapping(
        KEY_MOVE_RIGHT_DRONE,
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_D,
        KEY_CATEGORY_DRONE
    );
}
