package dev.sasukector.hundreddaysuhc.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import dev.sasukector.hundreddaysuhc.HundredDaysUHC;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.*;

public class DeathsController {

    private static DeathsController instance = null;
    private final @Getter List<UUID> playerDeaths;

    public static DeathsController getInstance() {
        if (instance == null) {
            instance = new DeathsController();
        }
        return instance;
    }

    public DeathsController() {
        this.playerDeaths = new ArrayList<>();
    }

    public void addPlayerToDeaths(Player player) {
        this.playerDeaths.add(player.getUniqueId());
    }

    private List<UUID> convertJSONArrayToMap(JsonArray playerDeaths) {
        List<UUID> deaths = new ArrayList<>();
        playerDeaths.forEach(jsonElement -> deaths.add(UUID.fromString(jsonElement.getAsString())));
        return deaths;
    }

    private JsonArray convertListToJsonArray(List<UUID> deaths) {
        JsonArray jsonArray = new JsonArray();
        for (UUID uuid : deaths) {
            jsonArray.add(uuid.toString());
        }
        return jsonArray;
    }

    private JsonArray getDeathsJSONArray() {
        JsonArray deathsArray = new JsonArray();
        File configFile = new File(HundredDaysUHC.getInstance().getDataFolder(), "deaths.json");
        if (!configFile.exists()) {
            HundredDaysUHC.getInstance().saveResource(configFile.getName(), false);
        }
        try {
            String baseJson = Files.readString(configFile.toPath());
            if (baseJson != null && !baseJson.isEmpty()) {
                deathsArray = new Gson().fromJson(baseJson, JsonArray.class);
            }
        } catch (Exception e) {
            Bukkit.getLogger().info(ChatColor.RED + "Error while getting JSON file for deaths: " + e);
            e.printStackTrace();
        }
        return deathsArray;
    }

    public void loadDeathsFromFile() {
        List<UUID> loadedPlayerDeaths = this.convertJSONArrayToMap(this.getDeathsJSONArray());
        this.playerDeaths.clear();
        this.playerDeaths.addAll(loadedPlayerDeaths);
    }

    public void saveDeathsToFile() {
        File configFile = new File(HundredDaysUHC.getInstance().getDataFolder(), "deaths.json");
        if (!configFile.exists()) {
            HundredDaysUHC.getInstance().saveResource(configFile.getName(), false);
        }
        try {
            FileWriter fileWriter = new FileWriter(configFile, false);
            fileWriter.write(convertListToJsonArray(this.playerDeaths).toString());
            fileWriter.close();
        } catch (Exception e) {
            Bukkit.getLogger().info(ChatColor.RED + "Error while writing JSON file for deaths: " + e);
            e.printStackTrace();
        }
    }

}
