package me.xdgrlnw.client;

import me.xdgrlnw.util.DirectionEnum;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class xdgrlnwSimpleHUDOverlayClient implements ClientModInitializer {
    static MinecraftClient client = MinecraftClient.getInstance();
    public static void renderHUD(DrawContext context) {
        CompletableFuture.runAsync(() -> hudOverlay(context), MinecraftClient.getInstance()::executeTask);}

    public static void hudOverlay(DrawContext context) {
        if (client.player != null) {
            boolean compass = client.player.getStackInHand(Hand.MAIN_HAND).getItem().equals(Items.COMPASS)
                    || client.player.getStackInHand(Hand.OFF_HAND).getItem().equals(Items.COMPASS)
                    || client.player.getStackInHand(Hand.MAIN_HAND).getItem().equals(Items.RECOVERY_COMPASS)
                    || client.player.getStackInHand(Hand.OFF_HAND).getItem().equals(Items.RECOVERY_COMPASS);
            boolean clock = client.player.getStackInHand(Hand.MAIN_HAND).getItem().equals(Items.CLOCK)
                    || client.player.getStackInHand(Hand.OFF_HAND).getItem().equals(Items.CLOCK);
            ArrayList<Text> hud = new ArrayList<>();
            if (compass) {
                hud.add(PositionText());
                for (int i = 0; i < hud.size(); i++) {
                    context.drawTextWithShadow(client.textRenderer, hud.get(i), 4, 4
                            + i * (client.textRenderer.fontHeight + 2), 0xFFFFFF);
                }
            }
            if (clock) {
                hud.add(TimeText());
                for (int i = 0; i < hud.size(); i++) {
                    context.drawTextWithShadow(client.textRenderer, hud.get(i), 4, 4
                            + i * (client.textRenderer.fontHeight + 2), 0xFFFFFF);
                }
            }
        }
    }
    public static Text PositionText() {
        assert client.player != null;
        int x = (int) client.player.getX();
        int y = (int) client.player.getY();
        int z = (int) client.player.getZ();
        float degrees = MathHelper.wrapDegrees(Objects.requireNonNull(client.getCameraEntity()).getYaw());
        DirectionEnum directionEnum = DirectionEnum.getByYawDegrees(degrees);
        return Text.translatable("xdgrlnw.hud.compass", x, y, z, directionEnum).formatted(Formatting.BOLD);
    }

    public static Text TimeText() {
        assert client.player != null;
        long DayCountText = client.player.getWorld().getTimeOfDay() / 24000L;
        LocalTime TimeReal = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = TimeReal.format(formatter);
        return Text.translatable("xdgrlnw.hud.clock", DayCountText, formattedTime).formatted(Formatting.BOLD);

    }

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((
                drawContext, tickCounter) ->
                CompletableFuture.runAsync(() -> renderHUD(drawContext),
                        MinecraftClient.getInstance()::executeTask));
    }
}
