package dev.maprankoverlay.mixin;

import dev.maprankoverlay.MapRankOverlayClient;
import dev.maprankoverlay.MapRanks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
    @Shadow protected int x;
    @Shadow protected int y;
    @Shadow protected ScreenHandler handler;

    @Inject(method = "drawSlot", at = @At("TAIL"))
    private void mapRankOverlay$drawRank(
            DrawContext context,
            Slot slot,
            int mouseX,
            int mouseY,
            CallbackInfo ci
    ) {
        if (!MapRankOverlayClient.isEnabled()) {
            return;
        }

        MapIdComponent mapId = slot.getStack().get(DataComponentTypes.MAP_ID);
        if (mapId == null) {
            return;
        }

        Map<Integer, Integer> ranks = MapRanks.rankVisibleMaps(handler.slots);
        Integer rank = ranks.get(mapId.id());
        if (rank == null) {
            return;
        }

        int left = this.x + slot.x;
        int top = this.y + slot.y;

        // Lowest map ID: bright border. Pure rendering; no slot interaction occurs.
        if (rank == 1) {
            int border = 0xFFFFE14A;
            context.fill(left - 1, top - 1, left + 17, top, border);
            context.fill(left - 1, top + 16, left + 17, top + 17, border);
            context.fill(left - 1, top, left, top + 16, border);
            context.fill(left + 16, top, left + 17, top + 16, border);
        }

        String label = Integer.toString(rank);
        MinecraftClient client = MinecraftClient.getInstance();
        int textWidth = client.textRenderer.getWidth(label);

        // Small dark badge in the lower-right corner of the map slot.
        int textX = left + 15 - textWidth;
        int textY = top + 8;
        context.fill(textX - 1, textY - 1, left + 16, top + 17, 0xB0000000);
        context.drawText(client.textRenderer, Text.literal(label), textX, textY, 0xFFFFFFFF, true);
    }
}
