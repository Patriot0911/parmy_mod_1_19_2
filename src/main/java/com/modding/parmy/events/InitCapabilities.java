package com.modding.parmy.events;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.capabilities.CapabilitiesRegisterData;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = ParmyMod.MOD_ID)
public class InitCapabilities {
    private static CapabilitiesRegisterData<?>[] toInitPlayerCapibilities = {
        // new CapabilitiesRegisterData<DroneItemCap>(DroneItemProvider.class, "properties_item_parmy_drone", DroneItemProvider.DRONE_ITEM_TOKEN)
    };

    @SubscribeEvent
    public static void onAttachEntityCapabilites(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            for(int i = 0; i < toInitPlayerCapibilities.length; i++) {
                CapabilitiesRegisterData<?> data = toInitPlayerCapibilities[i];
                if(!event.getObject().getCapability(data.capability).isPresent()) {
                    try {
                        ICapabilityProvider provider = data.provider.getDeclaredConstructor().newInstance();
                        event.addCapability(
                            new ResourceLocation(ParmyMod.MOD_ID, data.resourceName),
                            provider
                        );
                    } catch(Exception e) {
                        // ParmyMod.LOGGER.error("Cannot create provider instance for capability", e);
                    };
                };
            };
        };
    };
};
