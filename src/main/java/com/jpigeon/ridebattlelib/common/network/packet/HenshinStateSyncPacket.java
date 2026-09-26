package com.jpigeon.ridebattlelib.common.network.packet;

import com.jpigeon.ridebattlelib.common.data.HenshinState;
import com.jpigeon.ridebattlelib.common.network.RBLPacket;
import com.jpigeon.ridebattlelib.common.util.PayloadUtils;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.UUID;

public record HenshinStateSyncPacket(
        UUID playerId,
        boolean isTransformed,
        HenshinState state,
        Identifier riderId,
        Identifier currentFormId,
        Identifier pendingFormId
) implements RBLPacket {

    public static final Identifier ID = RBLPacket.ofPath("henshin_state_sync");

    public static final StreamCodec<RegistryFriendlyByteBuf, HenshinStateSyncPacket> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC, HenshinStateSyncPacket::playerId,
                    ByteBufCodecs.BOOL, HenshinStateSyncPacket::isTransformed,
                    StreamCodec.of(
                            (buf, s) -> buf.writeByte(s.ordinal()),
                            buf -> HenshinState.values()[buf.readByte()]
                    ), HenshinStateSyncPacket::state,
                    PayloadUtils.nullableIdentifier(), HenshinStateSyncPacket::riderId,
                    PayloadUtils.nullableIdentifier(), HenshinStateSyncPacket::currentFormId,
                    PayloadUtils.nullableIdentifier(), HenshinStateSyncPacket::pendingFormId,
                    HenshinStateSyncPacket::new
            );

    public static final Type<HenshinStateSyncPacket> TYPE = new Type<>(ID);


    @Override
    public Identifier id() {
        return ID;
    }
}
