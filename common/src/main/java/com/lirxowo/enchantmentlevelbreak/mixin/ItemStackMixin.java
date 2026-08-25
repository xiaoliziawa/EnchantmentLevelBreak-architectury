package com.lirxowo.enchantmentlevelbreak.mixin;

import com.lirxowo.enchantmentlevelbreak.config.ModConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "enchant", at = @At("HEAD"), cancellable = true)
    private void onEnchant(Holder<Enchantment> enchantment, int level, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;
        if (stack.isEmpty() || level <= 0) {
            return;
        }

        int clampedLevel = Math.min(level, ModConfig.getInstance().getMaxEnchantmentLevel());
        DataComponentType<ItemEnchantments> type = stack.is(Items.ENCHANTED_BOOK)
                ? DataComponents.STORED_ENCHANTMENTS
                : DataComponents.ENCHANTMENTS;

        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(stack.getOrDefault(type, ItemEnchantments.EMPTY));
        mutable.set(enchantment, clampedLevel);
        stack.set(type, mutable.toImmutable());
        ci.cancel();
    }
}
