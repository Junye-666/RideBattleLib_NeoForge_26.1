package com.jpigeon.ridebattlelib.common.api.example;

import com.jpigeon.ridebattlelib.RideBattleLib;
import com.jpigeon.ridebattlelib.common.api.builder.DynamicMappingBuilder;
import com.jpigeon.ridebattlelib.common.config.FormConfig;
import com.jpigeon.ridebattlelib.common.config.RiderConfig;
import com.jpigeon.ridebattlelib.common.config.TriggerType;
import com.jpigeon.ridebattlelib.common.registry.RiderRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Items;

import java.util.List;

public class ExampleDynamicForm {
    public static final Identifier TEST_RIDER_BETA =
            Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "test_beta");

    public static final Identifier BETA_BASE_FORM =
            Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "beta_base_form");

    public static final Identifier BETA_SLOT_1 =
            Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "beta_slot_1");

    public static final Identifier BETA_SLOT_2 =
            Identifier.fromNamespaceAndPath(RideBattleLib.MODID, "beta_slot_2");

    public static final RiderConfig riderBeta = new RiderConfig(TEST_RIDER_BETA)
            .setMainDriverItem(Items.NETHERITE_LEGGINGS, EquipmentSlot.LEGS)
            .addMainDriverSlot(
                    BETA_SLOT_1,
                    List.of(Items.EMERALD, Items.DIAMOND),
                    true,
                    true)
            .addMainDriverSlot(
                    BETA_SLOT_2,
                    List.of(Items.REDSTONE, Items.GLOWSTONE_DUST),
                    true,
                    true)
            .setAllowDynamicForms(true);

    public static final FormConfig baseForm = new FormConfig(BETA_BASE_FORM)
            .setTriggerType(TriggerType.KEY)
            .setArmor(
                    Items.LEATHER_HELMET,
                    Items.LEATHER_CHESTPLATE,
                    null,
                    Items.LEATHER_BOOTS)
            .addRequiredItem(BETA_SLOT_1, Items.AIR)
            .addRequiredItem(BETA_SLOT_2, Items.AIR)
            .addAttribute(
                    Identifier.fromNamespaceAndPath("minecraft", "generic.max_health"),
                    8.0,
                    AttributeModifier.Operation.ADD_VALUE);


    public static void betaRider() {
        // 注册RiderConfig
        riderBeta
                .addForm(baseForm)
                .setBaseForm(BETA_BASE_FORM);

        // 允许基础形态以空驱动器变身
        baseForm.setAllowsEmptyDriver(true);

        RiderRegistry.registerRider(riderBeta);
        // registerDynamicMappings();
        buildDynamicMappings();
    }

    private static void buildDynamicMappings() {
        DynamicMappingBuilder.forRider(TEST_RIDER_BETA)
                .armor(Items.DIAMOND, EquipmentSlot.HEAD, Items.DIAMOND_HELMET)
                .effects(Items.DIAMOND, MobEffects.JUMP_BOOST, MobEffects.ABSORPTION)
                .grantedItem(Items.DIAMOND, Items.DIAMOND_AXE)

                .armor(Items.EMERALD, Items.TURTLE_HELMET)
                .effect(Items.EMERALD, MobEffects.RESISTANCE)
                .grantedItem(Items.EMERALD, Items.GOLDEN_CARROT)

                .armor(Items.REDSTONE, Items.IRON_CHESTPLATE)
                .effect(Items.REDSTONE, MobEffects.STRENGTH)

                .armor(Items.GLOWSTONE_DUST, Items.GOLDEN_CHESTPLATE)
                .effect(Items.GLOWSTONE_DUST, MobEffects.SPEED)

                .undersuit(
                        Items.SKELETON_SKULL,
                        Items.CHAINMAIL_CHESTPLATE,
                        null,
                        Items.CHAINMAIL_BOOTS
                )
                .register();
    }

    public static void init() {
        betaRider();
    }
}
