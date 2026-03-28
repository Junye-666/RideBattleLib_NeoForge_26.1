package com.jpigeon.ridebattlelib.common.network.payload;

import com.jpigeon.ridebattlelib.RideBattleLib;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record SoundPayload(UUID playerId, Identifier soundId, float volume, float pitch) implements CustomPacketPayload {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "play_sound");


    public static final StreamCodec<@NotNull RegistryFriendlyByteBuf, @NotNull SoundPayload> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC, SoundPayload::playerId,
                    Identifier.STREAM_CODEC, SoundPayload::soundId,
                    ByteBufCodecs.FLOAT, SoundPayload::volume,
                    ByteBufCodecs.FLOAT, SoundPayload::pitch,
                    SoundPayload::new
            );


    public static final Type<@NotNull SoundPayload> TYPE = new Type<>(ID);

    @Override
    public @NotNull CustomPacketPayload.Type<?> type() { return TYPE; }
}