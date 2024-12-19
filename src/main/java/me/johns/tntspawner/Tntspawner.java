package me.johns.tntspawner;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Tntspawner extends JavaPlugin {

    private BukkitTask task;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("TNT Spawner is running");

        getCommand("tnt").setExecutor(new TntspawnerCommand());

        task = getServer().getScheduler().runTaskTimer(this, TntTask.getInstance(), 0, 20 * 6);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        TntTask.disableSpawns();

        getLogger().info("TNT Spawner has stopped");
    }

    public static Tntspawner getInstance() {
        return getPlugin(Tntspawner.class);
    }
}
