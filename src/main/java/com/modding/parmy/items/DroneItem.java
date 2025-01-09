package com.modding.parmy.items;

import java.util.List;

import com.modding.parmy.ParmyMod;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DroneItem extends Item {
    private static final Properties basicProps = new Properties()
        .tab(CreativeModeTab.TAB_TOOLS)
        .rarity(Rarity.COMMON)
        .stacksTo(1);

    private static final int startDroneHealth = 20;
    private static final int startBombsCount = 1;

    public DroneItem(Properties props) {
        super(props);
    };
    public DroneItem() {
        super(basicProps);
    };

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if(!level.isClientSide && player != null) {
            System.out.println("tested s");
            CompoundTag tag = stack.getOrCreateTag();
            int bombsCount = tag.getInt("bombs_count");
            int drone_health = tag.getInt("drone_health");
            if(drone_health <= 0) {
                return InteractionResult.PASS;
            };
            player.sendSystemMessage(Component.literal("Triggered spawn!"));
            Vec3 position = player.position();
            ParmyMod.specEnt = new DroneEntity(ModEntityTypes.DRONE.get(), level, DroneTypes.DROP_BOMB, bombsCount);
            ParmyMod.specEnt.setHealth((float) drone_health);
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
            return InteractionResult.SUCCESS;
        };
        return super.useOn(context);
    };

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slot, boolean selected) {
        if(!level.isClientSide) {
            createTags(itemStack, startDroneHealth, startBombsCount);
        };
        super.inventoryTick(itemStack, level, entity, slot, selected);
    };

    @Override
    public void onCraftedBy(ItemStack itemStack, Level level, Player player) {
        super.onCraftedBy(itemStack, level, player);
        createTags(itemStack, startDroneHealth, startBombsCount);
    };

    private void createTags(ItemStack itemStack, int droneHealth, int bombsCount) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if(
            !tag.contains("drone_health") ||
            !tag.contains("bombs_count")
        ) {
            tag.putInt("drone_health", startDroneHealth);
            tag.putInt("bombs_count", startBombsCount);
        };
    };

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, level, list, flag);
        if(Screen.hasShiftDown()) {
            CompoundTag tag = stack.getTag();
            if(
                tag == null ||
                !tag.contains("drone_health") ||
                !tag.contains("bombs_count")
            ) {
                createTags(stack, startDroneHealth, startBombsCount);
                return;
            };
            int health = tag.getInt("drone_health");
            int bombsCount = tag.getInt("bombs_count");
            list.add(
                Component.literal("Health: " + health)
            );
            list.add(
                Component.literal("Bombs: " + bombsCount)
            );
        };
    };
};
