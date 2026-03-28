package com.jpigeon.ridebattlelib.common.network.payload;

import com.jpigeon.ridebattlelib.RideBattleLib;
import com.jpigeon.ridebattlelib.common.data.HenshinState;
import com.jpigeon.ridebattlelib.common.util.PayloadUtils;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record HenshinStateSyncPayload(
        UUID playerId,
        boolean isTransformed,
        HenshinState state,
        Identifier currentFormId,
        Identifier pendingFormId
) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "henshin_state_sync");
    public static final Type<@NotNull HenshinStateSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<@NotNull RegistryFriendlyByteBuf, @NotNull HenshinStateSyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC, HenshinStateSyncPayload::playerId,
                    ByteBufCodecs.BOOL, HenshinStateSyncPayload::isTransformed,
                    ByteBufCodecs.fromCodec(HenshinState.CODEC), HenshinStateSyncPayload::state,
                    PayloadUtils.nullableResourceLocation(), HenshinStateSyncPayload::currentFormId,
                    PayloadUtils.nullableResourceLocation(), HenshinStateSyncPayload::pendingFormId,
                    HenshinStateSyncPayload::new
            );

    @Override
    public @NotNull Type<?> type() {
        return TYPE;
    }
}