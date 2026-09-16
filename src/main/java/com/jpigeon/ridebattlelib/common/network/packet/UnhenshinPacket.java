package com.jpigeon.ridebattlelib.common.network.packet;

import com.jpigeon.ridebattlelib.common.network.RBLPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public final class UnhenshinPacket implements RBLPacket {
    private UnhenshinPacket() {
    }

    public static final Identifier ID = RBLPacket.ofPath("unhenshin");

    public static final UnhenshinPacket INSTANCE = new UnhenshinPacket();

    public static final StreamCodec<RegistryFriendlyByteBuf, UnhenshinPacket> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);

    public static final Type<UnhenshinPacket> TYPE = new Type<>(ID);

    @Override
    public Identifier id() {
        return ID;
    }
}
