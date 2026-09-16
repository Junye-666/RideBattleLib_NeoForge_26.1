package com.jpigeon.ridebattlelib.common.api.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;

public record ClientRiderContext(
        @NotNull LocalPlayer player,
        @NotNull ItemStack driverStack,
        @Nullable Identifier riderId,
        @Nullable Identifier currentFormId,
        @Nullable Identifier pendingFormId,
        @Nullable Map<Identifier, ItemStack> changedItems,
        @Nullable Identifier skillId,
        @Nullable ChangeType changeType
) {
    public enum ChangeType {HENSHIN, SWITCH, UNHENSHIN, PENDING, DRIVER_CHANGE, SKILL}
}
