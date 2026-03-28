package com.jpigeon.ridebattlelib.common.network.payload;

import com.jpigeon.ridebattlelib.RideBattleLib;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record ReturnItemsPayload() implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "return_items");
    public static final Type<@NotNull ReturnItemsPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<@NotNull RegistryFriendlyByteBuf, @NotNull ReturnItemsPayload> STREAM_CODEC =
            StreamCodec.unit(new ReturnItemsPayload());

    @Override
    public @NotNull Type<?> type() {
        return TYPE;
    }
}