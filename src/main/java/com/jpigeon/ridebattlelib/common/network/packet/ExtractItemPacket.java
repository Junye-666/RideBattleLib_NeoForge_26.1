package com.jpigeon.ridebattlelib.common.network.packet;

import com.jpigeon.ridebattlelib.common.network.RBLPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record ExtractItemPacket(Identifier slotId) implements RBLPacket {
    public static final Identifier ID = RBLPacket.ofPath("extract_item");

    public static final StreamCodec<RegistryFriendlyByteBuf, ExtractItemPacket> STREAM_CODEC =
            StreamCodec.composite(
                    Identifier.STREAM_CODEC,
                    ExtractItemPacket::slotId,
                    ExtractItemPacket::new
            );

    public static final Type<ExtractItemPacket> TYPE = new Type<>(ID);

    @Override
    public Identifier id() {
        return ID;
    }
}
