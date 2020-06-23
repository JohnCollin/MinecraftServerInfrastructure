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
import com.constexpr.infrastructurecore.utilities.player.item.ItemRepairExitValue;
import com.constexpr.infrastructurecore.utilities.player.item.ItemUtilities;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Command Class for the /repair command.
 *
 * @author constexpr
 * @version 1.0.0-ALPHA
 * @since 1.0.0-ALPHA
 */
@CommandAlias("repair|fix|efix|erepair")
public class CommandRepair extends InfrastructureCommand {

    /**
     * Subcommand Handler for the hand subcommand of /repair.
     *
     * @param player The Player Command Sender.
     *
     * @since 1.0.0-ALPHA
     */
    @Default
    @Subcommand("hand")
    @Syntax("<+tag> [hand/all]")
    @CommandPermission("infrastructure.repair")
    @Description("Repair one or more items.")
    public static void onRepairHandCommand(Player player) {
        // Get the ItemStack in the Player's Main Hand.
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        // Perform the repairing operation on the ItemStack and get the operation exit code.
        ItemRepairExitValue exitValue = ItemUtilities.repairItem(itemStack);

        // Report Exit Code back to the player.
        handleHandRepairCommandResult(player, itemStack, exitValue);
    }

    /**
     * Subcommand Handler for the all subcommand of repair.
     *
     * @param player The Player Command Sender.
     *
     * @since 1.0.0-ALPHA
     */
    @Subcommand("all")
    @Syntax("<+tag> [hand/all]")
    @CommandPermission("infrastructure.repair")
    @Description("Repair one or more items.")
    public static void onRepairAllCommand(Player player) {
        // Get the PlayerInventory object from the Player.
        PlayerInventory playerInventory = player.getInventory();

        // Perform the repairing operation on the PlayerInventory and get the operation exit code.
        ItemRepairExitValue exitValue = ItemUtilities.repairItemCollection(playerInventory);

        // Report Exit Code back to the player.
        handleAllRepairCommandResult(player, exitValue);
    }

    /**
     * Refactored I/O Handler for Operation Status Reporting.
     *
     * @param player The Player Command Sender.
     * @param itemStack The Operated ItemStack.
     * @param exitValue The Result of the Operation.
     *
     * @since 1.0.0-ALPHA
     */
    private static void handleHandRepairCommandResult(Player player, ItemStack itemStack, ItemRepairExitValue exitValue) {
        switch(exitValue) {
            case SUCCESS:
            case PARTIAL_SUCCESS:
                player.sendMessage("Successfully Repaired Your" + itemStack.getType().toString() + ".");
                break;
            case AIR_ITEM:
                player.sendMessage("You cannot repair air!");
                break;
            case ILLEGAL_ITEM:
                player.sendMessage("You cannot repair that item!");
                break;
        }
    }

    /**
     * Refactored I/O Handler for Operation Status Reporting.
     *
     * @param player The Player Command Sender.
     * @param exitValue The Result of the Operation.
     *
     * @since 1.0.0-ALPHA
     */
    private static void handleAllRepairCommandResult(Player player, ItemRepairExitValue exitValue) {
        switch(exitValue) {
            case SUCCESS:
                player.sendMessage("Successfully Repaired Your Items.");
                break;
            case PARTIAL_SUCCESS:
                player.sendMessage("Successfully Repaired Most of Your Items.");
                break;
            case AIR_ITEM:
                player.sendMessage("You cannot repair air!");
                break;
            case ILLEGAL_ITEM:
                player.sendMessage("You cannot repair that item!");
                break;
        }
    }
}
