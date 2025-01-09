package com.modding.parmy.interfaces;

import net.minecraft.nbt.CompoundTag;

public interface ICapabilityState<T> {
    void copyFrom(T source);
    void saveNBTData(CompoundTag nTag);
    void loadNBTData(CompoundTag nTag);
}