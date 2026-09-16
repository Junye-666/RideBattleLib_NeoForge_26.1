package com.jpigeon.ridebattlelib.common.network;

import com.jpigeon.ridebattlelib.RideBattleLib;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public interface RBLPacket extends CustomPacketPayload {
    Identifier id();

    @Override
    default Type<? extends CustomPacketPayload> type() {
        return new Type<>(id());
    }

    static Identifier ofPath(String path) {
        return Identifier.fromNamespaceAndPath(RideBattleLib.MODID, path);
    }
}
