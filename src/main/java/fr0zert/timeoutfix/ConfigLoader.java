package fr0zert.timeoutfix;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigLoader {
    public static ConfigLoader INSTANCE;
    public Properties config;

    public ConfigLoader() {
        ServerLifecycleEvents.SERVER_STARTED.register(this::loadConfig);
    }

    private Properties readDefaultConfig() {
        String resourceName = "assets/timeoutfix/default_config.properties";
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) {
                TimeOutFix.LOGGER.error("Default config not found in resources: {}", resourceName);
                throw new IllegalStateException("Default config not found in resources: " + resourceName);
            }
            Properties props = new Properties();
            props.load(in);
            return props;
        } catch (IOException e) {
            TimeOutFix.LOGGER.error("Failed to read default config", e);
            throw new RuntimeException("Failed to read default config", e);
        }
    }

    public void loadConfig(MinecraftServer server) {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path configFile = configDir.resolve("TimeOutFixConfig.properties");

        // Create config if missing
        if (!Files.exists(configFile)) {
            try {
                Files.createDirectories(configDir);
                Properties defaultConfig = readDefaultConfig();
                try (OutputStream out = Files.newOutputStream(configFile)) {
                    defaultConfig.store(out, "TimeOutFix Configuration");
                }
            } catch (IOException e) {
                TimeOutFix.LOGGER.error("Failed to write default config file", e);
                throw new RuntimeException("Failed to write default config file", e);
            }
        }

        // Load config
        config = new Properties();
        try (InputStream in = Files.newInputStream(configFile)) {
            config.load(in);
            TimeOutFix.LOGGER.info("Config loaded: {}", config);
        } catch (IOException e) {
            TimeOutFix.LOGGER.error("Failed to read config file", e);
            throw new RuntimeException("Failed to read config file", e);
        }
    }
}