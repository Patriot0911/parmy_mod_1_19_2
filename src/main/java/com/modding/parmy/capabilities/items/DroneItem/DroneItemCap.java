package com.modding.parmy.capabilities.items.DroneItem;

import com.modding.parmy.interfaces.ICapabilityState;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class DroneItemCap implements ICapabilityState<DroneItemCap> {
    public static final int TOTAL_MAX_BOMBS_COUNT = 3;

    private float droneHealth;
    private int bombsCount;
    private int maxBombsCount;

    public DroneItemCap() {
        this.droneHealth = 20.0f;
        this.bombsCount = 1;
        this.maxBombsCount = 1;
    };
    public DroneItemCap(float health, int bCount, int maxCount) {
        this.droneHealth = health;
        this.bombsCount = bCount;
        this.maxBombsCount = maxCount;
    };

    public float getDroneHealth() {
        return this.droneHealth;
    };

    public float setDroneHealth(float health) {
        this.droneHealth = health;
        return this.droneHealth;
    };

    public int getDroneBombsCount() {
        return this.bombsCount;
    };

    public int getDroneMaxBombsCount() {
        return this.maxBombsCount;
    };

    public void copyFrom(DroneItemCap source) {
        this.droneHealth = source.droneHealth;
        this.bombsCount = source.bombsCount;
        this.maxBombsCount = source.maxBombsCount;
    };

    public void saveNBTData(CompoundTag nTag) {
        nTag.putFloat("parmy_drone_health", this.droneHealth);
        nTag.putInt("parmy_drone_bombs_count", this.bombsCount);
        nTag.putInt("parmy_drone_max_bombs_count", this.maxBombsCount);
    };

    public void loadNBTData(CompoundTag nTag) {
        this.droneHealth = nTag.getFloat("parmy_drone_health");
        this.bombsCount = nTag.getInt("parmy_drone_bombs_count");
        this.maxBombsCount = nTag.getInt("parmy_drone_max_bombs_count");
    };
};
