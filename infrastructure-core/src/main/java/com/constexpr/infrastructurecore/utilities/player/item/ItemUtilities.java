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

package com.constexpr.infrastructurecore.utilities.player.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Utilities Class with Static Item Factory Methods
 * <p>
 * This utility is used for processing items using Bukkit utilites
 * that do not already exist in easy-implementation.
 *
 * @author constexpr
 * @version 1.0.0-ALPHA
 * @since 1.0.0-ALPHA
 */
public class ItemUtilities {
    /**
     * Item Exit Values that inform status of operation as return value
     *
     * @since 1.0.0-ALPHA
     */
    public enum ItemRepairExitValue {
        /**
         * Item Operation Completed Successfully with no errors or warnings.
         *
         * @since 1.0.0-ALPHA
         */
        SUCCESS(),

        /**
         * Item Operation Completed Succesfully with warnings.
         *
         * @since 1.0.0-ALPHA
         */
        PARTIAL_SUCCESS(),

        /**
         * Item Operation was not completed because items in question were null.
         *
         * @since 1.0.0-ALPHA
         */
        AIR_ITEM(),

        /**
         * Item Operation was not completed because items were illegal.
         *
         * @since 1.0.0-ALPHA
         */
        ILLEGAL_ITEM()
    }

    /**
     * Sets a given ItemStack's damage amount to 0, assuming that it's repairable.
     *
     * @param itemStack ItemStack to repair
     * @return The Operation Exit Code.
     *
     * @since 1.0.0-ALPHA
     */
    public static ItemRepairExitValue repairItem(ItemStack itemStack) {
        // Material Type AIR Guard Statement
        if(itemStack.getType() == Material.AIR) return ItemRepairExitValue.AIR_ITEM;

        // Return Exit Value of setItemDurability() method.
        return ItemUtilities.setItemDurability(itemStack, 0);
    }

    /**
     * Repairs a given Iterable ItemStack Collection, assuming the items are repairable.
     *
     * @param itemCollection The Collection of ItemStacks to Repair.
     * @return The Operation Exit Code.
     *
     * @since 1.0.0-ALPHA
     */
    public static ItemRepairExitValue repairItemCollection(Iterable<ItemStack> itemCollection) {
        // Return Combined Exit Value of setItemCollectionDurability() method.
        return ItemUtilities.setItemCollectionDurability(itemCollection, 0);
    }

    /**
     * Sets a given ItemStack's durability.
     *
     * @param itemStack ItemStack to manipulate
     * @param damage The amount of damage to apply to the ItemStack.
     * @return The Operation Exit Code.
     *
     * @since 1.0.0-ALPHA
     */
    public static ItemRepairExitValue setItemDurability(ItemStack itemStack, int damage) {
        // Material Type AIR Guard Statements
        if(itemStack == null) return ItemRepairExitValue.ILLEGAL_ITEM;
        if(itemStack.getType() == Material.AIR) return ItemRepairExitValue.AIR_ITEM;

        // Get ItemStack ItemMeta in order to perform durability operation
        ItemMeta itemMeta = itemStack.getItemMeta();

        // Illegal Item Type for Specific Operation Guard Statement
        if(!(itemMeta instanceof Damageable)) return ItemRepairExitValue.ILLEGAL_ITEM;

        // Perform durability operation and re-assign ItemMeta to ItemStack
        ((Damageable) itemMeta).setDamage(damage);
        itemStack.setItemMeta(itemMeta);

        // Exit Value Success
        return ItemRepairExitValue.SUCCESS;
    }

    /**
     * Set an entire collection of ItemStacks to a certain damage level.
     *
     * @param itemCollection The Collection of ItemStacks to manipulate.
     * @param damage The amount of damge to apply to all members of the collection.
     * @return The Operation Exit Code.
     *
     * @since 1.0.0-ALPHA
     */
    public static ItemRepairExitValue setItemCollectionDurability(Iterable<ItemStack> itemCollection, int damage) {
        // Assume primarily that the operation will be successful unless flagged.
        ItemRepairExitValue exitValue = ItemRepairExitValue.SUCCESS;

        // Perform operations on each of the ItemStacks and record partial successes
        for(ItemStack itemStack : itemCollection)
            if(repairItemCollectionMember(itemStack, damage) == ItemRepairExitValue.PARTIAL_SUCCESS)
                exitValue = ItemRepairExitValue.PARTIAL_SUCCESS;

        // Return the exit value as a report of the operation.
        return exitValue;
    }

    /**
     * Repairs a member of an ItemStack collection (collection method specific)
     *
     * @param itemStack ItemStack to operate on.
     * @param damage The amount of damage to apply to the ItemStack.
     * @return The Operation Exit Code
     *
     * @since 1.0.0-ALPHA
     */
    private static ItemRepairExitValue repairItemCollectionMember(ItemStack itemStack, int damage) {
        // Complete the operation for the value and get exit value.
        ItemRepairExitValue itemExitValue = setItemDurability(itemStack, damage);

        // Determine if the exit value should be flagged for reporting to CommandSender.
        if(itemExitValue != ItemRepairExitValue.SUCCESS)
            return ItemRepairExitValue.PARTIAL_SUCCESS;

        // Return for unflagged operation.
        return ItemRepairExitValue.SUCCESS;
    }
}
