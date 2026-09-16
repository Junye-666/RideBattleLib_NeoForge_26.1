package com.jpigeon.ridebattlelib.common.network.packet;

import com.jpigeon.ridebattlelib.RideBattleLib;
import com.jpigeon.ridebattlelib.common.network.RBLPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;


public record SwitchFormPacket(Identifier formId) implements RBLPacket {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "switch_form");

    public static final StreamCodec<RegistryFriendlyByteBuf, SwitchFormPacket> STREAM_CODEC =
            StreamCodec.composite(
                    Identifier.STREAM_CODEC,
                    SwitchFormPacket::formId,
                    SwitchFormPacket::new
            );

    public static final Type<SwitchFormPacket> TYPE = new Type<>(ID);

    @Override
    public Identifier id() {
        return ID;
    }
}
