package com.lirxowo.enchantmentlevelbreak.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lirxowo.enchantmentlevelbreak.config.ModConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FabricConfig extends ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("enchantmentlevelbreak.json");
    private static FabricConfig INSTANCE;

    private boolean useRomanNumerals = true;
    private boolean allowAnyEnchantment = false;
    private boolean allowVanillaLevelStacking = true;
    private boolean allowLevelStacking = false;
    private int romanNumeralsThreshold = 5000;
    private int maxEnchantmentLevel = 2147483647;

    public static void init() {
        load();
        ModConfig.setInstance(INSTANCE);
    }

    public static void load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                    INSTANCE = GSON.fromJson(reader, FabricConfig.class);
                }
            } else {
                INSTANCE = new FabricConfig();
                INSTANCE.save();
            }
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
            INSTANCE = new FabricConfig();
        }
    }

    private void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }

    @Override
    public boolean isUseRomanNumerals() {
        return useRomanNumerals;
    }

    @Override
    public boolean isAllowAnyEnchantment() {
        return allowAnyEnchantment;
    }

    @Override
    public boolean isAllowVanillaLevelStacking() {
        return allowVanillaLevelStacking;
    }

    @Override
    public boolean isAllowLevelStacking() {
        return allowLevelStacking;
    }

    @Override
    public int getRomanNumeralsThreshold() {
        return romanNumeralsThreshold;
    }

    @Override
    public int getMaxEnchantmentLevel() {
        return maxEnchantmentLevel;
    }
}
