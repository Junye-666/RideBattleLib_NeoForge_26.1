package com.jpigeon.ridebattlelib.common.network.payload;

import com.jpigeon.ridebattlelib.RideBattleLib;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record InsertItemPayload(UUID playerId, Identifier slotId, ItemStack stack) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "insert_item");

    public static final StreamCodec<@NotNull RegistryFriendlyByteBuf, @NotNull InsertItemPayload> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            InsertItemPayload::playerId,
            Identifier.STREAM_CODEC,
            InsertItemPayload::slotId,
            ItemStack.OPTIONAL_STREAM_CODEC,
            InsertItemPayload::stack,
            InsertItemPayload::new
    );

    public static final Type<@NotNull InsertItemPayload> TYPE = new Type<>(ID);

    @Override
    public @NotNull Type<?> type() { return TYPE; }
}
