package com.jpigeon.ridebattlelib.common.network.packet;

import com.jpigeon.ridebattlelib.common.network.RBLPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public record SoundPacket(SoundEvent sound, float volume, float pitch) implements RBLPacket {

    public static final Identifier ID = RBLPacket.ofPath("play_sound");

    public static final StreamCodec<RegistryFriendlyByteBuf, SoundPacket> STREAM_CODEC =
            StreamCodec.composite(
                    SoundEvent.DIRECT_STREAM_CODEC, SoundPacket::sound,
                    ByteBufCodecs.FLOAT, SoundPacket::volume,
                    ByteBufCodecs.FLOAT, SoundPacket::pitch,
                    SoundPacket::new
            );


    public static final Type<SoundPacket> TYPE = new Type<>(ID);

    @Override
    public Identifier id() {
        return ID;
    }
}
