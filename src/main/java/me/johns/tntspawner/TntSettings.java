package me.johns.tntspawner;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TntSettings {

    private final static TntSettings instance = new TntSettings();

    public static Set<UUID> players = new HashSet<>();

    private TntSettings() {

    }

    private final String path = "Tnt.players";
    private File file;
    private YamlConfiguration config;

    public void load() {
        String filePath = "settings.yml";
        file = new File(Tntspawner.getInstance().getDataFolder(), filePath);

        //Check if file exists
        if (!file.exists()) {
            Tntspawner.getInstance().saveResource(filePath, false);
        }

        //Enable file parsing
        config = new YamlConfiguration();
        config.options().parseComments(true);


        //Load Yaml list of players into the hashset
        try {
            config.load(file);

            players.clear();

            List<String> list = config.getStringList(path);

            for (String id : list) {
                UUID uuid = UUID.fromString(id);
                players.add(uuid);
            }
        }
        catch (Exception e) {
            System.err.println("Error loading settings.yml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void save() {

        String filePath = "settings.yml";

        //Check if file exists
        if (!file.exists()) {
            Tntspawner.getInstance().saveResource(filePath, false);
        }

        try {
            List<String> uuidList = players.stream()
                    .map(UUID::toString)
                    .collect(Collectors.toList());

            config.set(path, uuidList);

            config.save(file);
        }
        catch (Exception e) {
            System.err.println("Error saving settings.yml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Returns true if the player being added is not in the list of players already and has logged onto the server before
    public boolean add(String name) {

        try {
            UUID id = stringToUUID(name);

            if (!players.contains(id) && id != null) {

                players.add(id);

                //Save the list each time it updates
                save();

                return true;
            }
        }
        catch (Exception e) {
            System.err.println("Error adding player: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    //Returns true if the player being removed is in the list of players
    public boolean remove(String name) {

        try {
            UUID id = stringToUUID(name);

            if (players.contains(id) && id != null) {

                players.remove(id);

                //Save the list each time it updates
                save();

                return true;
            }

        }
        catch (Exception e) {
            System.err.println("Error removing player: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    //Converts a string player name into a UUID object
    public static UUID stringToUUID(String name) {
        Player p = Bukkit.getServer().getPlayer(name);

        //Check if online
        if (p != null)
            return p.getUniqueId();
        //Returns uuid even if offline (although the player must have logged in before)
        else if (Bukkit.getOfflinePlayerIfCached(name) != null)
            return Bukkit.getOfflinePlayerIfCached(name).getUniqueId();
        else
            return null;
    }

    public static TntSettings getInstance() {
        return instance;
    }

}
