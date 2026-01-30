package de.maxhenkel.camerautils.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;

public class ClientConfig {
    public final ConfigEntry<Double> thirdPersonOffset;
    public final ConfigEntry<Double> thirdPersonOffsetSensitivity;

    public ClientConfig(ConfigBuilder builder) {
        thirdPersonOffset = builder.doubleEntry("third_person_offset", 0D, -32D, 32D);
        thirdPersonOffsetSensitivity = builder.doubleEntry("third_person_offset_sensitivity", 0.1D, 0.001D, 1D);
    }
}