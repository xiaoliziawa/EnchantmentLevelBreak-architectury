package com.lirxowo.enchantmentlevelbreak.neoforge.config;

import com.lirxowo.enchantmentlevelbreak.Enchantmentlevelbreak;
import com.lirxowo.enchantmentlevelbreak.config.ModConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Enchantmentlevelbreak.MOD_ID)
public class NeoForgeConfig extends ModConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue USE_ROMAN_NUMERALS_VALUE = BUILDER
            .comment("Use roman numerals for enchantment levels instead of arabic numbers")
            .define("useRomanNumerals", true);

    private static final ModConfigSpec.BooleanValue ALLOW_ANY_ENCHANTMENT_VALUE = BUILDER
            .comment("Allow applying any enchantment book to any item in anvil")
            .define("allowAnyEnchantment", false);

    private static final ModConfigSpec.BooleanValue ALLOW_VANILLA_LEVEL_STACKING_VALUE = BUILDER
            .comment("Allow vanilla enchantment level stacking in anvil (e.g. 4+4=5, same level +1)")
            .define("allowVanillaLevelStacking", true);

    private static final ModConfigSpec.BooleanValue ALLOW_LEVEL_STACKING_VALUE = BUILDER
            .comment("Allow unlimited enchantment level stacking in anvil (e.g. 4+4=8 instead of vanilla's 4+4=5)")
            .define("allowLevelStacking", false);

    private static final ModConfigSpec.IntValue ROMAN_NUMERALS_THRESHOLD_VALUE = BUILDER
            .comment("Maximum level to use roman numerals (levels above this will use arabic numbers)")
            .defineInRange("romanNumeralsThreshold", 5000, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue MAX_ENCHANTMENT_LEVEL_VALUE = BUILDER
            .comment("Maximum enchantment level allowed (range: 255 to " + Integer.MAX_VALUE + ")")
            .defineInRange("maxEnchantmentLevel", Integer.MAX_VALUE, 255, Integer.MAX_VALUE);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean useRomanNumerals;
    private static boolean allowAnyEnchantment;
    private static boolean allowVanillaLevelStacking;
    private static boolean allowLevelStacking;
    private static int romanNumeralsThreshold;
    private static int maxEnchantmentLevel;

    public static void init(ModContainer modContainer) {
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, SPEC);
        ModConfig.setInstance(new NeoForgeConfig());
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent event) {
        useRomanNumerals = USE_ROMAN_NUMERALS_VALUE.get();
        allowAnyEnchantment = ALLOW_ANY_ENCHANTMENT_VALUE.get();
        allowVanillaLevelStacking = ALLOW_VANILLA_LEVEL_STACKING_VALUE.get();
        allowLevelStacking = ALLOW_LEVEL_STACKING_VALUE.get();
        romanNumeralsThreshold = ROMAN_NUMERALS_THRESHOLD_VALUE.get();
        maxEnchantmentLevel = MAX_ENCHANTMENT_LEVEL_VALUE.get();
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
    public int getMaxEnchantmentLevel() {
        return maxEnchantmentLevel;
    }

    @Override
    public int getRomanNumeralsThreshold() {
        return romanNumeralsThreshold;
    }
}
