package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.Camera;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D"))
    private double getAttributeValue(LivingEntity instance, Holder<Attribute> attribute) {
        double distance = instance.getAttributeValue(attribute);
        distance = distance + CameraUtils.CLIENT_CONFIG.thirdPersonOffset.get();
        distance = Math.max(0D, distance);
        distance = Math.min(32D, distance);
        return distance;
    }
}
