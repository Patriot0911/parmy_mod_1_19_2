package com.modding.parmy;

import com.modding.parmy.entity.ModEntityTypes;
import com.modding.parmy.entity.Drone.DroneEntity;
import com.modding.parmy.entity.Drone.DroneRenderer;
import com.modding.parmy.entity.DroneBomb.DroneBombModelRenderer;
import com.modding.parmy.items.ModItems;
import com.modding.parmy.networking.NetworkManager;
import com.modding.parmy.utils.KeyBinding;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(ParmyMod.MOD_ID)
public class ParmyMod
{
    public static final String MOD_ID = "parmy";

    public static DroneEntity specEnt = null;

    public ParmyMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        GeckoLib.initialize();
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    };

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetworkManager.register();
        });
    };

    @Mod.EventBusSubscriber(modid = ParmyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.DRONE.get(), DroneEntity.setAttributes());
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.DRONE.get(), DroneRenderer::new);
            EntityRenderers.register(ModEntityTypes.DRONE_BOMB.get(), DroneBombModelRenderer::new);
            ItemProperties.register(
                ModItems.DRONE_ITEM.get(),
                new ResourceLocation("drone_item"),
                (stack, world, entity, seed) -> { return 0; }
            );
        };
    };

    @Mod.EventBusSubscriber(modid = ParmyMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SPAWN_DRONE_KEY);
            event.register(KeyBinding.MOVE_FORWARD_KEY);
            event.register(KeyBinding.MOVE_BACKWARD_KEY);
            event.register(KeyBinding.MOVE_LEFT_KEY);
            event.register(KeyBinding.MOVE_RIGHT_KEY);
            event.register(KeyBinding.SPAWN_DRONE_BOMB_KEY);
        }
    }
}
