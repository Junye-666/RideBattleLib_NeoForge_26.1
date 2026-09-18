package com.jpigeon.ridebattlelib.common.api.registry;

import net.minecraft.resources.Identifier;

public interface IRiderPack {
    Identifier riderId();

    default void registerCommon() {
    }

    default void registerClient() {
    }
}
