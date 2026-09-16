package com.jpigeon.ridebattlelib.common.network.packet;

import com.jpigeon.ridebattlelib.common.network.RBLPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public final class ReturnItemsPacket implements RBLPacket {
    private ReturnItemsPacket() {
    }

    public static final Identifier ID = RBLPacket.ofPath("return_items");

    public static final ReturnItemsPacket INSTANCE = new ReturnItemsPacket();

    public static final StreamCodec<RegistryFriendlyByteBuf, ReturnItemsPacket> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);

    public static final Type<ReturnItemsPacket> TYPE = new Type<>(ID);

    @Override
    public Identifier id() {
        return ID;
    }
}
