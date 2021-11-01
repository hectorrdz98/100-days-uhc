package dev.sasukector.hundreddaysuhc;

import dev.sasukector.hundreddaysuhc.commands.DeathsCommand;
import dev.sasukector.hundreddaysuhc.commands.PlayedCommand;
import dev.sasukector.hundreddaysuhc.controllers.BoardController;
import dev.sasukector.hundreddaysuhc.controllers.DeathsController;
import dev.sasukector.hundreddaysuhc.events.SpawnEvents;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class HundredDaysUHC extends JavaPlugin {

    private static @Getter HundredDaysUHC instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(ChatColor.DARK_PURPLE + "HundredDaysUHC startup!");
        instance = this;

        // Register events
        this.getServer().getPluginManager().registerEvents(new SpawnEvents(), this);
        DeathsController.getInstance().loadDeathsFromFile();
        Bukkit.getOnlinePlayers().forEach(player -> BoardController.getInstance().newPlayerBoard(player));

        // Register commands
        Objects.requireNonNull(HundredDaysUHC.getInstance().getCommand("deaths")).setExecutor(new DeathsCommand());
        Objects.requireNonNull(HundredDaysUHC.getInstance().getCommand("played")).setExecutor(new PlayedCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(ChatColor.DARK_PURPLE + "HundredDaysUHC shutdown!");
    }
}
