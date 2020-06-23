/*
 * MIT License
 *
 * Copyright (c) 2020 Collin Johnson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.constexpr.infrastructurecore.command.player;

import co.aikar.commands.annotation.*;
import com.constexpr.infrastructurecore.command.InfrastructureCommand;
import com.constexpr.infrastructurecore.utilities.player.item.ItemEnchantExitValue;
import com.constexpr.infrastructurecore.utilities.player.item.ItemUtilities;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Command Class for the /enchant command.
 *
 * @author constexpr
 * @version 1.0.0-ALPHA
 * @since 1.0.0-ALPHA
 */
@CommandAlias("enchant|enchantment")
public class CommandEnchant extends InfrastructureCommand {
    private static HashMap<String, Enchantment> enchantmentByAliases = new HashMap<>();

    // TODO Use GSON to store this data, for now this will work just fine.
    static {
        enchantmentByAliases.put("alldamage|alldmg|sharpness|", Enchantment.DAMAGE_ALL);
        enchantmentByAliases.put("arthropodsdamage|ardmg|baneofarthropods|", Enchantment.DAMAGE_ARTHROPODS);
        enchantmentByAliases.put("undeaddamage|smite|", Enchantment.DAMAGE_UNDEAD);
        enchantmentByAliases.put("digspeed|efficiency|", Enchantment.DIG_SPEED);
        enchantmentByAliases.put("durability|dura|unbreaking|", Enchantment.DURABILITY);
        enchantmentByAliases.put("fireaspect|fire|", Enchantment.FIRE_ASPECT);
        enchantmentByAliases.put("knockback|", Enchantment.KNOCKBACK);
        enchantmentByAliases.put("blockslootbonus|fortune|", Enchantment.LOOT_BONUS_BLOCKS);
        enchantmentByAliases.put("mobslootbonus|mobloot|looting|", Enchantment.LOOT_BONUS_MOBS);
        enchantmentByAliases.put("oxygen|respiration|", Enchantment.OXYGEN);
        enchantmentByAliases.put("protection|prot|", Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantmentByAliases.put("explosionsprotection|expprot|blastprotection|", Enchantment.PROTECTION_EXPLOSIONS);
        enchantmentByAliases.put("fallprotection|fallprot|featherfall|featherfalling|", Enchantment.PROTECTION_FALL);
        enchantmentByAliases.put("fireprotection|fireprot|", Enchantment.PROTECTION_FIRE);
        enchantmentByAliases.put("projectileprotection|projprot|", Enchantment.PROTECTION_PROJECTILE);
        enchantmentByAliases.put("silktouch|", Enchantment.SILK_TOUCH);
        enchantmentByAliases.put("waterworker|aquaaffinity|", Enchantment.WATER_WORKER);
        enchantmentByAliases.put("firearrow|flame|", Enchantment.ARROW_FIRE);
        enchantmentByAliases.put("arrowdamage|power|", Enchantment.ARROW_DAMAGE);
        enchantmentByAliases.put("arrowknockback|arrowkb|punch|", Enchantment.ARROW_KNOCKBACK);
        enchantmentByAliases.put("infinitearrows|infarrows|infinity|", Enchantment.ARROW_INFINITE);
    }

    /**
     * Command Handler for the command /enchant.
     *
     * @param player The Player Command Sender.
     * @since 1.0.0-ALPHA
     */
    @Default
    @CatchUnknown
    @Syntax("<+tag> [enchantment name] [level]")
    @CommandPermission("infrastructure.enchant")
    @Description("Enchant an item.")
    public static void onEnchantCommand(Player player, String[] args) {
        if(args.length < 1) {
            player.sendMessage("For a list of enchants: /enchant list");
            return;
        }

        // Get the enchantment information from the player command.
        int enchantmentLevel;
        Enchantment enchantment = CommandEnchant.getEnchantmentByAlias(args[0]);

        // Get the ItemStack in the player's hand
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(args.length < 2) {
            enchantmentLevel = 1;
        } else {
            UtilPair parseAttempt = parseEnchantmentLevel(player, args[1]);
            if(parseAttempt.itemEnchantExitValue == ItemEnchantExitValue.ILLEGAL_ARGUMENT) {
                handleEnchantCommandResult(player, parseAttempt.itemEnchantExitValue);
                return;
            }
            enchantmentLevel = parseAttempt.parseResult;
        }

        // Perform Operation and get Exit Value
        boolean playerHasPermission = player.hasPermission("infrastructure.enchant.unsafe");
        ItemEnchantExitValue exitValue = playerHasPermission ?
                ItemUtilities.enchantItemUnsafe(itemStack, enchantment, enchantmentLevel)
              : ItemUtilities.enchantItem(itemStack, enchantment, enchantmentLevel);

        // Report Exit Code to the player.
        handleEnchantCommandResult(player, exitValue);
    }

    /**
     * Refactored I/O Handler for Operation Status Reporting.
     *
     * @param player The Player Command Sender.
     * @param exitValue The Result of the Operation.
     *
     * @since 1.0.0-ALPHA
     */
    private static void handleEnchantCommandResult(Player player, ItemEnchantExitValue exitValue) {
        switch(exitValue) {
            case SUCCESS:
                player.sendMessage("Successfully enchanted item.");
                break;
            case SUCCESS_UNSAFE:
                player.sendMessage("Successfully enchanted item. (UNSAFE)");
                break;
            case SUCCESS_REMOVE:
                player.sendMessage("Successfully removed enchant from item.");
                break;
            case AIR_ITEM:
                player.sendMessage("You cannot enchant air!");
                break;
            case ILLEGAL_ITEM:
                player.sendMessage("You cannot repair that item!");
                break;
            case UNSAFE_ENCHANT_LEVEL:
                player.sendMessage("You can't enchant this item to that level.");
                break;
            case ILLEGAL_ENCHANTMENT:
                player.sendMessage("For a list of enchantments: /enchants list");
                break;
            case ILLEGAL_ARGUMENT:
                player.sendMessage("Usage: /enchant <enchantment> [level]");
                break;
        }
    }

    /**
     * Refactored Handler for parsing the enchantment level.
     *
     * @param player The Player Command Sender.
     * @param arg The Argument to be parsed.
     *
     * @since 1.0.0-ALPHA
     */
    private static UtilPair parseEnchantmentLevel(Player player, String arg) {
        int tempParse = 0;
        try {
            tempParse = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return new UtilPair(0, ItemEnchantExitValue.ILLEGAL_ARGUMENT);
        }

        return new UtilPair(tempParse, ItemEnchantExitValue.SUCCESS);
    }

    /**
     * Refactored Handler for String alias to the Enchantment Enum Object.
     *
     * @param alias The enchantment alias.
     *
     * @since 1.0.0-ALPHA
     */
    private static @Nullable Enchantment getEnchantmentByAlias(String alias) {
        for(String aliasGroup : enchantmentByAliases.keySet())
            if(aliasGroup.contains(alias + "|"))
                return enchantmentByAliases.get(aliasGroup);

        return null;
    }

    /**
     * Util Class used for the parseEnchantmentLevel function.
     *
     * @author constexpr
     * @version 1.0.0-ALPHA
     * @since 1.0.0-ALPHA
     */
    private static class UtilPair {
        public int parseResult;
        public ItemEnchantExitValue itemEnchantExitValue;

        public UtilPair(int parseResult, ItemEnchantExitValue itemEnchantExitValue) {
            this.parseResult = parseResult;
            this.itemEnchantExitValue = itemEnchantExitValue;
        }
    }
}