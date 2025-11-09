package com.lirxowo.enchantmentlevelbreak.neoforge;

import com.lirxowo.enchantmentlevelbreak.Enchantmentlevelbreak;
import com.lirxowo.enchantmentlevelbreak.command.CEnchantCommand;
import com.lirxowo.enchantmentlevelbreak.neoforge.config.NeoForgeConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(Enchantmentlevelbreak.MOD_ID)
public class EnchantmentlevelbreakNeoForge {

    public EnchantmentlevelbreakNeoForge(ModContainer modContainer) {
        NeoForgeConfig.init(modContainer);
        Enchantmentlevelbreak.init();
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CEnchantCommand.register(event.getDispatcher());
    }
}
