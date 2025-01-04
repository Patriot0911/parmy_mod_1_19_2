package com.modding.parmy.events;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.networking.NetworkManager;
import com.modding.parmy.networking.packets.MoveDroneC2S;
import com.modding.parmy.networking.packets.SpawnCowC2S;
import com.modding.parmy.utils.DirectionManager;
import com.modding.parmy.utils.KeyBinding;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
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
                System.out.println("tested2");
                if(ParmyMod.specEnt == null) {
                    NetworkManager.sendToServer(new SpawnCowC2S());
                } else {
                    ParmyMod.specEnt.setFlying(false);
                    ParmyMod.specEnt = null;
                    mc.setCameraEntity(null);
                };
            };
            if(Screen.hasShiftDown()) {
                event.setCanceled(true);
            };
        }
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {


            // mc.gameRenderer.


            // mc.setScreen(null);
            // mc.createTelemetryManager().
            // setRenderViewEntity();
            if(
                Minecraft.getInstance().player != null &&
                Minecraft.getInstance().level != null &&
                Minecraft.getInstance().getCameraEntity() != null &&
                ParmyMod.specEnt != null
            ) {
                mc.gameRenderer.setRenderHand(false);
                DirectionManager.Direction dir = DirectionManager.getDirectionByKey();
                NetworkManager.sendToServer(
                    new MoveDroneC2S(
                        dir,
                        mc.player.getYRot(),
                        mc.player.getXRot()*-1
                    )
                );
            }
        }
    }
}
