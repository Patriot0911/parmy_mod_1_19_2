package com.modding.parmy.events;

import com.modding.parmy.ParmyMod;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = ParmyMod.MOD_ID)
public class InitCapabilities {
    @SubscribeEvent
    public static void onAttachItemCapabilites(AttachCapabilitiesEvent<ItemStack> event) {
    };
};
