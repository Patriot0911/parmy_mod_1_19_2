package com.modding.parmy.items;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.entity.ModEntityTypes;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, ParmyMod.MOD_ID);

    public static final RegistryObject<Item> CHOMPER_SPAWN_EGG = ITEMS.register("drone_spawn_egg",
        () -> new ForgeSpawnEggItem(ModEntityTypes.DRONE, 0x22b341, 0x19732e,
            new Item.Properties().tab(CreativeModeTab.TAB_MISC))
    );

    public static void register(IEventBus eventBus) {
        System.out.println("TESTED ITEMS");
        ITEMS.register(eventBus);
    }
}
