package com.modding.parmy.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CapabilitiesRegisterData<T> {
    public Class<? extends ICapabilityProvider> provider;
    public String resourceName;
    public Capability<T> capability;

    public CapabilitiesRegisterData(
        Class<? extends ICapabilityProvider> provider,
        String resource,
        Capability<T> cap
    ) {
        this.provider = provider;
        this.resourceName = resource;
        this.capability = cap;
    };
};
