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
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Command Class for the /suicide command.
 *
 * @author constexpr
 * @version 1.0.0-ALPHA
 * @since 1.0.0-ALPHA
 */
@CommandAlias("suicide|esuicide")
public class CommandSuicide extends InfrastructureCommand {
    /**
     * Command Handler for command /suicide.
     *
     * @param player The Player Command Sender.
     * @since 1.0.0-ALPHA
     */
    @Default
    @CatchUnknown
    @Syntax("<+tag>")
    @CommandPermission("infrastructure.suicide")
    @Description("Causes you to perish.")
    public static void onSuicideCommand(@NotNull Player player, String[] args) {
        // Perform the killing operation on the Player Command Sender.
        player.setHealth(0.0);

        // Report operation back to the player.
        player.sendMessage("Successfully Killed " + player.getName() + ".");
    }
}