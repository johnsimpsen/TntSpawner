package me.johns.tntspawner;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TntspawnerCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player can use this command");

            return false;
        }

        if (args.length > 1) {
            sender.sendMessage("There are no arguments for this command");

            return false;
        }

        if (TntTask.isEnabled()) {
            TntTask.disableSpawns();
            sender.sendMessage("TNT Spawning disabled");
        }
        else {
            TntTask.enableSpawns();
            sender.sendMessage("TNT Spawning enabled");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
