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

/**
 * Exit Code for Item Operation
 *
 * @author constexpr
 * @version 1.0.0-ALPHA
 * @since 1.0.0-ALPHA
 */
public enum ItemEnchantExitValue {
    /**
     * Item Operation Completed Successfully with no errors or warnings.
     *
     * @since 1.0.0-ALPHA
     */
    SUCCESS(),

    /**
     * Item Operation Completed Successfully with warnings.
     *
     * @since 1.0.0-ALPHA
     */
    SUCCESS_UNSAFE(),

    /**
     * Item Operation Completed Successfully.
     *
     * @since 1.0.0-ALPHA
     */
    SUCCESS_REMOVE(),

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
    ILLEGAL_ITEM(),

    /**
     * Item Operation was not completed because the method allows only prescribed enchant levels.
     *
     * @since 1.0.0-ALPHA
     */
    UNSAFE_ENCHANT_LEVEL(),

    /**
     * Item Operation was not completed because the enchantment was invalid or null.
     *
     * @since 1.0.0-ALPHA
     */
    ILLEGAL_ENCHANTMENT(),

    /**
     * Item Operation (specifically parsing) was not completed due to illegal arguments.
     */
    ILLEGAL_ARGUMENT()
}
