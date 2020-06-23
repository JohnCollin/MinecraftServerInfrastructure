package com.constexpr.infrastructurecore.command.player;

import co.aikar.commands.annotation.*;
import com.constexpr.infrastructurecore.command.InfrastructureCommand;
import org.bukkit.entity.Player;

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
    public static void onSuicideCommand(Player player, String[] args) {
        // Perform the killing operation on the Player Command Sender.
        player.setHealth(0.0);

        // Report operation back to the player.
        player.sendMessage("Successfully Killed " + player.getName() + ".");
    }
}