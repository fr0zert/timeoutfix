package fr0zert.timeoutfix;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {
    public static ConfigLoader INSTANCE;
    public TomlParseResult config;

    public ConfigLoader() {
        ServerLifecycleEvents.SERVER_STARTED.register(this::loadConfig);
    }

    private String readDefaultConfig() {
        var resourceName = "assets/timeoutfix/default_config.toml";
        try (var in = ConfigLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) {
                TimeOutFix.LOGGER.error("Default config not found in resources: {}", resourceName);
                throw new IllegalStateException("Default config not found in resources: " + resourceName);
            }
            return new String(in.readAllBytes());
        } catch (IOException e) {
            TimeOutFix.LOGGER.error("Failed to read default config", e);
            throw new RuntimeException("Failed to read default config", e);
        }
    }

    public void loadConfig(MinecraftServer server) {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path configFile = configDir.resolve("TimeOutFixConfig.toml");

        if (!Files.exists(configFile)) {
            try {
                Files.createDirectories(configDir);
                String defaultConfig = readDefaultConfig();
                Files.writeString(configFile, defaultConfig);
            } catch (IOException e) {
                TimeOutFix.LOGGER.error("Failed to write default config file", e);
                throw new RuntimeException("Failed to write default config file", e);
            }
        }

        try {
            config = Toml.parse(Files.readString(configFile));
            if (config.hasErrors()) {
                TimeOutFix.LOGGER.error("TOML error: {}", config.errors());
            } else {
                TimeOutFix.LOGGER.info("Config loaded: {}", config.toMap());
            }
        } catch (IOException e) {
            TimeOutFix.LOGGER.error("Failed to read config file", e);
            throw new RuntimeException("Failed to read config file", e);
        }
    }
}