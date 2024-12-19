package me.johns.tntspawner;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class TntTask implements Runnable{

    private static final TntTask instance = new TntTask();

    private static boolean enabled = false;

    private TntTask() {
    }

    @Override
    public void run() {

        if (enabled) {
            for (Player player : Bukkit.getOnlinePlayers()) {

                player.getWorld().spawnEntity(player.getLocation(), EntityType.TNT);
            }
        }
    }

    public static void enableSpawns() {
        enabled = true;
    }

    public static void disableSpawns() {
        enabled = false;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static TntTask getInstance() {
        return instance;
    }


}

