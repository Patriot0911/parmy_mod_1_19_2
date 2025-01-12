package com.modding.parmy.events;

import java.util.List;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.networking.NetworkManager;
import com.modding.parmy.networking.packets.DropDroneBombC2S;
import com.modding.parmy.networking.packets.LaunchArtShellC2S;
import com.modding.parmy.networking.packets.MoveDroneC2S;
import com.modding.parmy.networking.packets.LeaveCameraC2S;
import com.modding.parmy.utils.DirectionManager;
import com.modding.parmy.utils.KeyBinding;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    private static final Minecraft mc = Minecraft.getInstance();

    @Mod.EventBusSubscriber(modid = ParmyMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.SPAWN_DRONE_KEY.isDown()) {
                NetworkManager.sendToServer(new LeaveCameraC2S());
            };
            if(KeyBinding.SPAWN_DRONE_BOMB_KEY.isDown()) {
                if(ParmyMod.specEnt != null) {
                    NetworkManager.sendToServer(
                        new DropDroneBombC2S()
                    );
                }
            };
            if(KeyBinding.LAUNCH_SHELL_KEY.isDown()) {
                NetworkManager.sendToServer(
                    new LaunchArtShellC2S()
                );
            };
        }
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END)
                return;
            if(
                Minecraft.getInstance().player != null &&
                Minecraft.getInstance().level != null &&
                Minecraft.getInstance().getCameraEntity() != null &&
                ParmyMod.specEnt != null
            ) {
                List<DirectionManager.Direction> dirs = DirectionManager.getDirectionsByKeys();
                NetworkManager.sendToServer(
                    new MoveDroneC2S(
                        dirs,
                        mc.player.getYRot(),
                        mc.player.getXRot()*-1
                    )
                );
            }
        }
    }
}
