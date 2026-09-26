package com.jpigeon.ridebattlelib.common.network.packet;

import com.jpigeon.ridebattlelib.common.network.RBLPacket;
import com.jpigeon.ridebattlelib.common.util.PayloadUtils;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import javax.annotation.Nullable;
import java.util.UUID;

public record SkillSyncPacket(
        UUID playerId,
        @Nullable Identifier riderId,
        Identifier skillId
) implements RBLPacket {

    public static final Identifier ID = RBLPacket.ofPath("skill_sync");
    public static final Type<SkillSyncPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, SkillSyncPacket> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC, SkillSyncPacket::playerId,
                    PayloadUtils.nullableIdentifier(), SkillSyncPacket::riderId,
                    Identifier.STREAM_CODEC, SkillSyncPacket::skillId,
                    SkillSyncPacket::new
            );

    @Override
    public Identifier id() {
        return ID;
    }
}
