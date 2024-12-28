package me.johns.tntspawner;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Tntspawner extends JavaPlugin {

    public BukkitTask task;

    public static int interval = 6;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("TNT Spawner is running");

        getCommand("tnt").setExecutor(new TntspawnerCommand());

        task = getServer().getScheduler().runTaskTimer(this, TntTask.getInstance(), (long) 20 * interval, (long) 20 * interval);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        TntTask.disableSpawns();

        getLogger().info("TNT Spawner has stopped");
    }

    public void setTask() {
        task.cancel();
        task = getServer().getScheduler().runTaskTimer(this, TntTask.getInstance(), (long) 20 * interval, (long) 20 * interval);
    }

    public static Tntspawner getInstance() {
        return getPlugin(Tntspawner.class);
    }
}
