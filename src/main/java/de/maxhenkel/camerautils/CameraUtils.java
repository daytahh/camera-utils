package de.maxhenkel.camerautils;

import com.mojang.blaze3d.platform.InputConstants;
import de.maxhenkel.camerautils.config.ClientConfig;
import de.maxhenkel.configbuilder.ConfigBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class CameraUtils implements ClientModInitializer {
    private static final Minecraft mc = Minecraft.getInstance();

    public static final String MODID = "camerautils";
    public static ClientConfig CLIENT_CONFIG;

    public static KeyMapping.Category CATEGORY_CAMERAUTILS;
    public static KeyMapping.Category CATEGORY_CAMERAUTILS_SETTINGS;

    public static KeyMapping THIRD_PERSON_OFFSET;

    @Override
    public void onInitializeClient() {

        CATEGORY_CAMERAUTILS = KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(MODID, "camerautils"));
        CATEGORY_CAMERAUTILS_SETTINGS = KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(MODID, "camerautils_settings"));

        THIRD_PERSON_OFFSET = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_offset", InputConstants.UNKNOWN.getValue(), CATEGORY_CAMERAUTILS));

        CLIENT_CONFIG = ConfigBuilder
                .builder(ClientConfig::new)
                .path(mc.gameDirectory.toPath().resolve("config").resolve(MODID).resolve("camerautils.properties"))
                .build();
    }

    public static boolean onScroll(double amount) {
        if (!THIRD_PERSON_OFFSET.isDown() || mc.options.getCameraType().isFirstPerson()) return false;
        double distance = CLIENT_CONFIG.thirdPersonOffset.get();
        double sensitivity = CLIENT_CONFIG.thirdPersonOffsetSensitivity.get();
        distance = Math.max(-32D, Math.min(32D, distance + (-amount * sensitivity)));
        CLIENT_CONFIG.thirdPersonOffset.set(distance);
        CLIENT_CONFIG.thirdPersonOffset.save();
        mc.player.displayClientMessage(Component.translatable("message.camerautils.third_person_offset", round(distance, 2)), true);
        return true;
    }

    private static double round(double value, int digits) {
        return Math.round(value * Math.pow(10D, digits)) / Math.pow(10D, digits);
    }
}
