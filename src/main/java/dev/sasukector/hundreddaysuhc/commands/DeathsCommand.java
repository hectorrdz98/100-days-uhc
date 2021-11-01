package dev.sasukector.hundreddaysuhc.commands;

import dev.sasukector.hundreddaysuhc.controllers.DeathsController;
import dev.sasukector.hundreddaysuhc.helpers.ServerUtilities;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DeathsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            player.playSound(player.getLocation(), "minecraft:block.note_block.bell", 1, 1);
            StringBuilder playersDeath = new StringBuilder();
            for (UUID uuid : DeathsController.getInstance().getPlayerDeaths()) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                playersDeath.append("\n- ").append(offlinePlayer.getName());
            }
            ServerUtilities.sendServerMessage(player, ServerUtilities.getMiniMessage()
                    .parse("<bold><color:#F4A261>Jugadores muertos:</color></bold><color:#E76F51>" +
                    playersDeath + "</color>"));
        }
        return true;
    }

}
