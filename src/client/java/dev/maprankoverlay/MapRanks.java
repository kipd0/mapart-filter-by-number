package dev.maprankoverlay;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MapRanks {
    private MapRanks() {}

    public static Integer getMapId(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return null;
        }

        MapIdComponent mapId = stack.get(DataComponentTypes.MAP_ID);
        return mapId == null ? null : mapId.id();
    }

    /**
     * Returns map ID -> visual rank (1 = lowest ID) for every filled map visible
     * in the currently open handled screen. Duplicate copies of the same map ID
     * intentionally receive the same rank.
     */
    public static Map<Integer, Integer> rankVisibleMaps(List<Slot> slots) {
        List<Integer> ids = new ArrayList<>();

        for (Slot slot : slots) {
            Integer id = getMapId(slot.getStack());
            if (id != null) {
                ids.add(id);
            }
        }

        ids.sort(Comparator.naturalOrder());

        Map<Integer, Integer> result = new LinkedHashMap<>();
        int rank = 1;
        for (Integer id : ids) {
            if (!result.containsKey(id)) {
                result.put(id, rank++);
            }
        }

        return result;
    }
}
