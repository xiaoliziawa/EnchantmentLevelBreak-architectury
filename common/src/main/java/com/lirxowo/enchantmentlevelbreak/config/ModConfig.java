package com.lirxowo.enchantmentlevelbreak.config;

public abstract class ModConfig {
    private static ModConfig INSTANCE;

    public static void setInstance(ModConfig instance) {
        INSTANCE = instance;
    }

    public static ModConfig getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Config not initialized! Platform must set config instance.");
        }
        return INSTANCE;
    }

    public abstract boolean isUseRomanNumerals();
    public abstract boolean isAllowAnyEnchantment();
    public abstract boolean isAllowVanillaLevelStacking();
    public abstract boolean isAllowLevelStacking();
    public abstract int getMaxEnchantmentLevel();
    public abstract int getRomanNumeralsThreshold();
}
