package me.johns.tntspawner;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TntTask implements Runnable{

    private static final TntTask instance = new TntTask();

    private static boolean enabled = false;

    private TntTask() {
    }

    @Override
    public void run() {

        if (enabled) {
            for (UUID uuid : TntSettings.players) {

                Player player = Bukkit.getPlayer(uuid);

                if (player != null)
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.TNT);
            }
        }
    }

    public void enableSpawns() {
        enabled = true;
    }

    public void disableSpawns() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public static TntTask getInstance() {
        return instance;
    }
}

