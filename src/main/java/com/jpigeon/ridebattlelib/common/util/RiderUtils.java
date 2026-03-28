package com.jpigeon.ridebattlelib.common.util;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.HashMap;
import java.util.Map;

public class RiderUtils {
    public static final String NULL_MARKER = "ridebattlelib:null";
    public static final Identifier NULL = Identifier.parse(NULL_MARKER);

    public static Map<Identifier, ItemStackTemplate> toTemplateMap(Map<Identifier, ItemStack> source) {
        Map<Identifier, ItemStackTemplate> result = new HashMap<>();
        for (Map.Entry<Identifier, ItemStack> entry : source.entrySet()) {
            ItemStack stack = entry.getValue();
            ItemStackTemplate template = stack.isEmpty() ? null : ItemStackTemplate.fromNonEmptyStack(stack);
            result.put(entry.getKey(), template);
        }
        return result;
    }

    public static Map<Identifier, ItemStack> toStackMap(Map<Identifier, ItemStackTemplate> source) {
        Map<Identifier, ItemStack> result = new HashMap<>();
        for (Map.Entry<Identifier, ItemStackTemplate> entry : source.entrySet()) {
            ItemStackTemplate template = entry.getValue();
            ItemStack stack = template == null ? null : template.create();
            result.put(entry.getKey(), stack);
        }
        return result;
    }
}
