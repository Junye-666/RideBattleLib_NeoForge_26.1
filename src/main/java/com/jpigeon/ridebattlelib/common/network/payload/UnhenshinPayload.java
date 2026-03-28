package com.jpigeon.ridebattlelib.common.network.payload;

import com.jpigeon.ridebattlelib.RideBattleLib;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record UnhenshinPayload(UUID playerId) implements CustomPacketPayload {
    public static final Identifier ID =
            Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "unhenshin");

    public static final StreamCodec<@NotNull RegistryFriendlyByteBuf, @NotNull UnhenshinPayload> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC,
                    UnhenshinPayload::playerId,
                    UnhenshinPayload::new
            );

    public static final Type<@NotNull UnhenshinPayload> TYPE = new Type<>(ID);

    @Override
    public @NotNull Type<?> type() { return TYPE; }
}
