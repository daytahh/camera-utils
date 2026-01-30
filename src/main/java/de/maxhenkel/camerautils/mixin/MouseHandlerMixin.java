package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Inject(at = @At("HEAD"), method = "onScroll", cancellable = true)
    private void onScroll(long window, double d, double amount, CallbackInfo info) {
        if (window != Minecraft.getInstance().getWindow().handle()) return;
        if (CameraUtils.onScroll(amount)) info.cancel();
    }
}
