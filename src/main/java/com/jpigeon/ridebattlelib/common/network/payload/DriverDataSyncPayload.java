package com.jpigeon.ridebattlelib.common.network.payload;

import com.jpigeon.ridebattlelib.RideBattleLib;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record DriverDataSyncPayload(
        UUID playerId,
        Map<Identifier, ItemStack> mainItems,
        Map<Identifier, ItemStack> auxItems
) implements CustomPacketPayload {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "driver_sync");

    public static final StreamCodec<@NotNull RegistryFriendlyByteBuf, @NotNull DriverDataSyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC,
                    DriverDataSyncPayload::playerId,
                    createMapCodec(),
                    DriverDataSyncPayload::mainItems,
                    createMapCodec(),
                    DriverDataSyncPayload::auxItems,
                    DriverDataSyncPayload::new
            );

    private static StreamCodec<@NotNull RegistryFriendlyByteBuf, @NotNull Map<Identifier, ItemStack>> createMapCodec() {
        return StreamCodec.of(
                (buf, map) -> {
                    buf.writeVarInt(map.size());
                    for (Map.Entry<Identifier, ItemStack> entry : map.entrySet()) {
                        Identifier.STREAM_CODEC.encode(buf, entry.getKey());
                        ItemStack.STREAM_CODEC.encode(buf, entry.getValue());
                    }
                },
                buf -> {
                    Map<Identifier, ItemStack> map = new HashMap<>();
                    int size = buf.readVarInt();
                    for (int i = 0; i < size; i++) {
                        Identifier key = Identifier.STREAM_CODEC.decode(buf);
                        ItemStack value = ItemStack.STREAM_CODEC.decode(buf);
                        map.put(key, value);
                    }
                    return map;
                }
        );
    }

    public static final Type<@NotNull DriverDataSyncPayload> TYPE = new Type<>(ID);

    @Override
    public @NotNull Type<?> type() { return TYPE; }
}
