package com.modding.parmy.events;

import com.modding.parmy.ParmyMod;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ParmyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public void onStopRiding(EntityMountEvent event) {
        System.out.println("MOD_ID");
        Entity entity = event.getEntity();

        // Перевірка, чи гравець є власником об'єкта, на якому він їде
        if (entity instanceof Player && ParmyMod.specEnt != null) {

            // if (shouldPreventStopRiding(player)) {
                event.setCanceled(true);
            // }
        }
    }
}
