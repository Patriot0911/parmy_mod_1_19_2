package com.modding.parmy.capabilities.items.DroneItem;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class DroneItemProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<DroneItemCap> DRONE_ITEM_TOKEN = CapabilityManager.get(
        new CapabilityToken<DroneItemCap>() {}
    );

    private DroneItemCap itemData = null;
    private final LazyOptional<DroneItemCap> optional = LazyOptional.of(this::initDroneItemCap);

    private DroneItemCap initDroneItemCap() {
        if(this.itemData == null) {
            this.itemData = new DroneItemCap();
        };
        return this.itemData;
    };

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        initDroneItemCap().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        initDroneItemCap().loadNBTData(nbt);
    };

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == DRONE_ITEM_TOKEN) {
            return optional.cast();
        };
        return LazyOptional.empty();
    };
};
