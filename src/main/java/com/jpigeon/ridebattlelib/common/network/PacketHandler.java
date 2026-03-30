package com.jpigeon.ridebattlelib.common.network;

import com.jpigeon.ridebattlelib.RideBattleLib;
import com.jpigeon.ridebattlelib.client.network.ClientPacketHandler;
import com.jpigeon.ridebattlelib.common.network.payload.*;
import com.jpigeon.ridebattlelib.server.system.DriverSystem;
import com.jpigeon.ridebattlelib.server.system.HenshinSystem;
import com.jpigeon.ridebattlelib.server.system.SkillSystem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PacketHandler {
    public static void register(final RegisterPayloadHandlersEvent event) {
        event.registrar(RideBattleLib.MODID)
                .versioned("2.0.0").optional()
                .playToServer(DriverActionPayload.TYPE, DriverActionPayload.STREAM_CODEC,
                        (payload, context) -> {
                            Player targetPlayer = context.player().level().getPlayerByUUID(payload.playerId());
                            if (targetPlayer != null) {
                                HenshinSystem.getInstance().driverAction(targetPlayer);
                            }
                        })
                .playToServer(HenshinPayload.TYPE, HenshinPayload.STREAM_CODEC,
                        (payload, context) -> {
                            Player targetPlayer = context.player().level().getPlayerByUUID(payload.playerId());
                            if (targetPlayer != null) {
                                HenshinSystem.getInstance().henshin(targetPlayer, payload.riderId());
                            }
                        })
                .playToServer(UnhenshinPayload.TYPE, UnhenshinPayload.STREAM_CODEC,
                        (payload, context) -> {
                            Player targetPlayer = context.player().level().getPlayerByUUID(payload.playerId());
                            if (targetPlayer != null) {
                                HenshinSystem.getInstance().unHenshin(targetPlayer);
                            }
                        })
                .playToServer(SwitchFormPayload.TYPE, SwitchFormPayload.STREAM_CODEC,
                        (payload, context) -> {
                            Player targetPlayer = context.player().level().getPlayerByUUID(payload.playerId());
                            if (targetPlayer != null) {
                                HenshinSystem.getInstance().switchForm(context.player(), payload.formId());
                            }
                        })
                .playToServer(InsertItemPayload.TYPE, InsertItemPayload.STREAM_CODEC,
                        (payload, context) -> {
                            Player targetPlayer = context.player().level().getPlayerByUUID(payload.playerId());
                            if (targetPlayer != null) {
                                DriverSystem.getInstance().insertItem(targetPlayer, payload.slotId(), payload.stack());
                            }
                        })
                .playToServer(ReturnItemsPayload.TYPE, ReturnItemsPayload.STREAM_CODEC,
                        (payload, context) -> DriverSystem.getInstance().returnItems(context.player()))
                .playToServer(ExtractItemPayload.TYPE, ExtractItemPayload.STREAM_CODEC,
                        (payload, context) -> {
                            Player targetPlayer = context.player().level().getPlayerByUUID(payload.playerId());
                            if (targetPlayer != null) {
                                DriverSystem.getInstance().extractItem(context.player(), payload.slotId());
                            }
                        })
                .playToServer(
                        RotateSkillPayload.TYPE, RotateSkillPayload.STREAM_CODEC,
                        (payload, context) -> {
                            // 获取正确的玩家对象
                            Player targetPlayer = context.player().level().getPlayerByUUID(payload.playerId());
                            if (targetPlayer != null) {
                                SkillSystem.rotateSkill(targetPlayer);
                            }
                        }
                )
                .playToServer(
                        TriggerSkillPayload.TYPE, TriggerSkillPayload.STREAM_CODEC,
                        (payload, context) -> {
                            // 获取正确的玩家对象
                            Player targetPlayer = context.player().level().getPlayerByUUID(payload.playerId());
                            if (targetPlayer != null) {
                                SkillSystem.triggerCurrentSkill(targetPlayer);
                            }
                        }
                )
                .playToServer(
                        SoundPayload.TYPE, SoundPayload.STREAM_CODEC,
                        (payload, context) -> {
                            // 服务端处理
                            ServerPlayer sender = (ServerPlayer) context.player();

                            if (!sender.getUUID().equals(payload.playerId())) return;

                            // 获取音效
                            Optional<Holder.Reference<@NotNull SoundEvent>> sound = BuiltInRegistries.SOUND_EVENT.get(payload.soundId());
                            if (sound.isEmpty()) return;
                            SoundEvent soundEvent = sound.get().value();

                            // 服务端广播
                            sender.level().playSound(null, sender, soundEvent, SoundSource.PLAYERS, payload.volume(), payload.pitch());
                        }
                )

                .playToClient(HenshinStateSyncPayload.TYPE, HenshinStateSyncPayload.STREAM_CODEC,
                        (payload, context) -> ClientPacketHandler.handleHenshinStateSync(payload))

                .playToClient(DriverDataSyncPayload.TYPE, DriverDataSyncPayload.STREAM_CODEC,
                        (payload, context) -> ClientPacketHandler.handleDriverDataSync(payload))

                .playToClient(DriverDataDiffPayload.TYPE, DriverDataDiffPayload.STREAM_CODEC,
                        (payload, context) -> ClientPacketHandler.handleDriverDataDiff(payload))
        ;
    }
}
