package com.jpigeon.ridebattlelib.common.network.packet;

import com.jpigeon.ridebattlelib.common.network.RBLPacket;
import com.jpigeon.ridebattlelib.common.util.PayloadUtils;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record DriverDataDiffPacket(
        UUID playerId,
        Identifier riderId,
        boolean isAux,
        Map<Identifier, ItemStack> changes
) implements RBLPacket {

    public static final Identifier ID = RBLPacket.ofPath("driver_diff_sync");

    public static final Type<DriverDataDiffPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, DriverDataDiffPacket> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC, DriverDataDiffPacket::playerId,
                    PayloadUtils.nullableIdentifier(), DriverDataDiffPacket::riderId,
                    ByteBufCodecs.BOOL, DriverDataDiffPacket::isAux,
                    createChangesCodec(), DriverDataDiffPacket::changes,
                    DriverDataDiffPacket::new
            );

    private static StreamCodec<RegistryFriendlyByteBuf, Map<Identifier, ItemStack>> createChangesCodec() {
        return StreamCodec.of(
                (buf, changes) -> {
                    buf.writeVarInt(changes.size());
                    for (var e : changes.entrySet()) {
                        Identifier.STREAM_CODEC.encode(buf, e.getKey());
                        if (e.getValue().isEmpty()) {
                            buf.writeBoolean(false);
                        } else {
                            buf.writeBoolean(true);
                            ItemStack.STREAM_CODEC.encode(buf, e.getValue());
                        }
                    }
                },
                buf -> {
                    Map<Identifier, ItemStack> changes = new HashMap<>();
                    int size = buf.readVarInt();
                    for (int i = 0; i < size; i++) {
                        Identifier slotId = Identifier.STREAM_CODEC.decode(buf);
                        changes.put(slotId,
                                buf.readBoolean() ? ItemStack.STREAM_CODEC.decode(buf) : ItemStack.EMPTY);
                    }
                    return changes;
                }
        );
    }

    @Override
    public Identifier id() {
        return ID;
    }
}
