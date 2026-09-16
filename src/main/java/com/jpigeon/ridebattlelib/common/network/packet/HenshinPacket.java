package com.jpigeon.ridebattlelib.common.network.packet;

import com.jpigeon.ridebattlelib.common.network.RBLPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record HenshinPacket(Identifier riderId) implements RBLPacket {
    public static final Identifier ID = RBLPacket.ofPath("henshin");

    public static final StreamCodec<RegistryFriendlyByteBuf, HenshinPacket> STREAM_CODEC =
            StreamCodec.composite(
                    Identifier.STREAM_CODEC,
                    HenshinPacket::riderId,
                    HenshinPacket::new
            );

    public static final Type<HenshinPacket> TYPE = new Type<>(ID);


    @Override
    public Identifier id() {
        return ID;
    }
}
