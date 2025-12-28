package com.lirxowo.enchantmentlevelbreak.mixin;

import net.minecraft.world.item.enchantment.ItemEnchantments;
import com.lirxowo.enchantmentlevelbreak.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEnchantments.Mutable.class)
public class ItemEnchantmentsConstructorMixin {
    @ModifyConstant(method = "set", constant = @Constant(intValue = 255))
    private int modifySetMaxLevel(int value) {
        return ModConfig.getInstance().getMaxEnchantmentLevel();
    }

    @ModifyConstant(method = "upgrade", constant = @Constant(intValue = 255))
    private int modifyUpgradeMaxLevel(int value) {
        return ModConfig.getInstance().getMaxEnchantmentLevel();
    }
}
