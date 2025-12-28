package com.lirxowo.enchantmentlevelbreak.fabric;

import com.lirxowo.enchantmentlevelbreak.Enchantmentlevelbreak;
import com.lirxowo.enchantmentlevelbreak.command.CEnchantCommand;
import com.lirxowo.enchantmentlevelbreak.fabric.config.FabricConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class EnchantmentlevelbreakFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FabricConfig.init();
        Enchantmentlevelbreak.init();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            CEnchantCommand.register(dispatcher)
        );
    }
}
