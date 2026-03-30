package com.jpigeon.ridebattlelib.client.network;

import com.jpigeon.ridebattlelib.client.cache.ClientDriverDataCache;
import com.jpigeon.ridebattlelib.client.cache.ClientTransformedCache;
import com.jpigeon.ridebattlelib.common.network.payload.DriverDataDiffPayload;
import com.jpigeon.ridebattlelib.common.network.payload.DriverDataSyncPayload;
import com.jpigeon.ridebattlelib.common.network.payload.HenshinStateSyncPayload;
import net.minecraft.client.Minecraft;

public final class ClientPacketHandler {
    public static void handleHenshinStateSync(HenshinStateSyncPayload payload) {
        Minecraft.getInstance().execute(() -> ClientTransformedCache.update(
                payload.playerId(),
                payload.isTransformed(),
                payload.state(),
                payload.currentFormId(),
                payload.pendingFormId()
        ));
    }

    public static void handleDriverDataSync(DriverDataSyncPayload payload) {
        Minecraft.getInstance().execute(() -> {
            ClientDriverDataCache.setMainItems(payload.playerId(), payload.mainItems());
            ClientDriverDataCache.setAuxItems(payload.playerId(), payload.auxItems());
        });
    }

    public static void handleDriverDataDiff(DriverDataDiffPayload payload) {
        Minecraft.getInstance().execute(() -> ClientDriverDataCache.applyChanges(payload.playerId(), payload.changes(), payload.fullSync()));
    }
}
