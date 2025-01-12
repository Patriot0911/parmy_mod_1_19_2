package com.modding.parmy.items;

import java.util.List;

import com.modding.parmy.ParmyMod;
import com.modding.parmy.capabilities.items.DroneItem.DroneItemProvider;
import com.modding.parmy.entity.ModEntityTypes;
import com.modding.parmy.entity.Drone.DroneEntity;
import com.modding.parmy.enums.DroneTypes;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class DroneItem extends Item {
    private static final Properties basicProps = new Properties()
        .tab(CreativeModeTab.TAB_TOOLS)
        .rarity(Rarity.COMMON)
        .stacksTo(1);

    public DroneItem(Properties props) {
        super(props);
    };
    public DroneItem() {
        super(basicProps);
    };

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new DroneItemProvider();
    };

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if(!level.isClientSide && player != null) {
            final InteractionResult[] result = {InteractionResult.PASS};
            stack.getCapability(DroneItemProvider.DRONE_ITEM_TOKEN).ifPresent(
                (state) -> {
                    int bombsCount = state.getDroneBombsCount();
                    float drone_health = state.getDroneHealth();
                    if(drone_health <= 0) {
                        result[0] = InteractionResult.FAIL;
                        return;
                    };
                    Vec3 position = context.getClickLocation();
                    System.out.println(bombsCount);
                    ParmyMod.specEnt = new DroneEntity(ModEntityTypes.DRONE.get(), level, DroneTypes.DROP_BOMB, bombsCount);
                    ParmyMod.specEnt.setHealth(drone_health);
                    ParmyMod.specEnt.setPos(position.x, position.y, position.z);
                    level.addFreshEntity(ParmyMod.specEnt);
                    Minecraft mc = Minecraft.getInstance();
                    mc.options.hideGui = true;
                    RenderSystem.setShader(GameRenderer::getPositionShader);
                    RenderSystem.getModelViewStack().pushPose();
                    RenderSystem.getModelViewStack().setIdentity();
                    RenderSystem.applyModelViewMatrix();
                    ((ServerPlayer) player).connection.send(
                        new ClientboundSetCameraPacket(ParmyMod.specEnt)
                    );
                    stack.shrink(1);
                    if(stack.isEmpty()) {
                        player.getInventory().removeItem(stack);
                    };
                    result[0] = InteractionResult.SUCCESS;
                }
            );
            return result[0];
        };
        return super.useOn(context);
    };

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, level, list, flag);
        if(Screen.hasShiftDown()) {
            stack.getCapability(DroneItemProvider.DRONE_ITEM_TOKEN).ifPresent(
                (state) -> {
                    list.add(
                        Component.literal("Health: " + state.getDroneHealth())
                    );
                    list.add(
                        Component.literal("Bombs: " + state.getDroneBombsCount())
                    );
                }
            );
        };
    };
};
