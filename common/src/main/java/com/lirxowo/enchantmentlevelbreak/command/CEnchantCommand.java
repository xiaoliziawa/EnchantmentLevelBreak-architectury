package com.lirxowo.enchantmentlevelbreak.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Optional;

public class CEnchantCommand {

    private static final SuggestionProvider<CommandSourceStack> SUGGEST_ENCHANTMENTS = (context, builder) ->
            SharedSuggestionProvider.suggestResource(context.getSource().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).keySet(), builder);

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("cenchant")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("enchantment", StringArgumentType.greedyString())
                        .suggests(SUGGEST_ENCHANTMENTS)
                        .executes(context -> enchantItem(context.getSource(),
                                StringArgumentType.getString(context, "enchantment"),
                                1))
                        .then(Commands.argument("level", IntegerArgumentType.integer(1))
                                .executes(context -> enchantItem(context.getSource(),
                                        StringArgumentType.getString(context, "enchantment"),
                                        IntegerArgumentType.getInteger(context, "level"))))));
    }

    private static int enchantItem(CommandSourceStack source, String enchantmentInput, int level) throws CommandSyntaxException {
        if (level <= 0 || level > Integer.MAX_VALUE) {
            source.sendFailure(Component.translatable("command.enchantmentlevelbreak.error.level_too_high"));
            return 0;
        }
        
        Player player = source.getPlayerOrException();
        ItemStack itemStack = player.getMainHandItem();

        if (itemStack.isEmpty()) {
            source.sendFailure(Component.translatable("command.enchantmentlevelbreak.error.no_item"));
            return 0;
        }

        String[] parts = enchantmentInput.split("\\s+", 2);
        String enchantmentName = parts[0];
        if (parts.length > 1) {
            try {
                long parsedLevel = Long.parseLong(parts[1]);
                if (parsedLevel > Integer.MAX_VALUE || parsedLevel < 1) {
                    source.sendFailure(Component.translatable("command.enchantmentlevelbreak.error.level_too_high"));
                    return 0;
                }
                level = (int) parsedLevel;
            } catch (NumberFormatException e) {
                source.sendFailure(Component.translatable("command.enchantmentlevelbreak.error.level_too_high"));
                return 0;
            }
        }

        ResourceLocation enchantmentId;
        if (!enchantmentName.contains(":")) {
            enchantmentId = ResourceLocation.fromNamespaceAndPath("minecraft", enchantmentName);
        } else {
            enchantmentId = ResourceLocation.parse(enchantmentName);
        }

        Holder<Enchantment> enchantment;
        Optional<Holder.Reference<Enchantment>> enchantmentOpt = source.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(enchantmentId);
        if (enchantmentOpt.isEmpty()) {
            source.sendFailure(Component.translatable("command.enchantmentlevelbreak.error.invalid_enchantment", enchantmentName));
            return 0;
        }
        enchantment = enchantmentOpt.get();

        ItemEnchantments currentEnchantments = itemStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(currentEnchantments);
        mutable.set(enchantment, level);
        itemStack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());

        int finalLevel = level;
        source.sendSuccess(() -> Component.translatable("command.enchantmentlevelbreak.success", Enchantment.getFullname(enchantment, finalLevel).getString()), true);

        return 1;
    }
}
