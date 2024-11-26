package me.xdgrlnw.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class BossbarMixin {

  @Shadow
  @Final
  private MinecraftClient client;

  @Inject(at = @At("HEAD"), method = "render", cancellable = true)
  public void init(final CallbackInfo info) {
    if (client.player != null) {
      boolean compass = client.player.getStackInHand(Hand.MAIN_HAND).getItem().equals(Items.COMPASS)
              || client.player.getStackInHand(Hand.OFF_HAND).getItem().equals(Items.COMPASS)
              || client.player.getStackInHand(Hand.MAIN_HAND).getItem().equals(Items.RECOVERY_COMPASS)
              || client.player.getStackInHand(Hand.OFF_HAND).getItem().equals(Items.RECOVERY_COMPASS);
      boolean clock = client.player.getStackInHand(Hand.MAIN_HAND).getItem().equals(Items.CLOCK)
              || client.player.getStackInHand(Hand.OFF_HAND).getItem().equals(Items.CLOCK);
      if (clock || compass) {
        info.cancel();
      }
    }
  }
}