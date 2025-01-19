package me.johns.tntspawner;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TntspawnerCommand implements CommandExecutor, TabExecutor {

    private final TextColor green = TextColor.color(0x00FF00);
    private final TextColor red = TextColor.color(0xFF0000);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (!(sender instanceof Player)) {
            Component msg = Component.text("Only a player can use this command", red);
            sender.sendMessage(msg);

            return true;
        }

        else if (args.length > 1 && (!args[0].equalsIgnoreCase("interval") && !args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove")) && !args[0].equalsIgnoreCase("list")) {
            Component msg = Component.text("There are no arguments for this command", red);
            sender.sendMessage(msg);

            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("enable")) {
                if (TntTask.getInstance().isEnabled()) {
                    Component msg = Component.text("This plugin is already enabled!", red);
                    sender.sendMessage(msg);

                    return true;
                }
                else {
                    Component msg = Component.text("Plugin has been enabled!", green);
                    sender.sendMessage(msg);
                    Component msg2 = Component.text("TNT will start spawning", green);
                    sender.sendMessage(msg2);

                    TntTask.getInstance().enableSpawns();

                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("disable")) {
                if (!TntTask.getInstance().isEnabled()) {
                    Component msg = Component.text("This plugin is already disabled!", red);
                    sender.sendMessage(msg);

                    return true;
                }
                else {
                    Component msg = Component.text("Plugin has been disabled!", green);
                    sender.sendMessage(msg);
                    Component msg2 = Component.text("TNT will stop spawning", green);
                    sender.sendMessage(msg2);

                    TntTask.getInstance().disableSpawns();

                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("interval")) {
                Component msg = Component.text("Please specify the interval in seconds!", red);
                sender.sendMessage(msg);

                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                Component msg;

                //Check if player list is empty
                if (TntSettings.players.isEmpty()) {
                    msg = Component.text("There are no players");
                    sender.sendMessage(msg);
                    return true;
                }

                msg = Component.text("List Of Players: ");
                sender.sendMessage(msg);

                for (UUID id : TntSettings.players) {

                    //Check if player is online
                    if (Bukkit.getPlayer(id) != null)
                        msg = Component.text(Bukkit.getPlayer(id).getName());
                    else if (Bukkit.getOfflinePlayerIfCached(Bukkit.getOfflinePlayer(id).getName()) != null)
                        msg = Component.text(Bukkit.getOfflinePlayerIfCached(Bukkit.getOfflinePlayer(id).getName()).getName());
                    else
                        msg = Component.text("Error", red);

                    sender.sendMessage(msg);
                }

                return true;
            }
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                Component msg = Component.text("Please specify the name of the player!", red);
                sender.sendMessage(msg);

                return true;
            }
        }

        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("interval")) {

                if (args[1].equalsIgnoreCase("default")) {
                    int newInterval = 6;
                    Tntspawner.interval = newInterval;

                    Component msg = Component.text("Interval set to " + newInterval + " seconds", green);
                    sender.sendMessage(msg);

                    Tntspawner.getInstance().setTask();

                    return true;
                }

                try {
                    int newInterval = Integer.parseInt(args[1]);
                    Tntspawner.interval = newInterval;

                    Component msg = Component.text("Interval set to " + newInterval + " seconds", green);
                    sender.sendMessage(msg);

                    Tntspawner.getInstance().setTask();

                    return true;
                }
                catch (Exception e) {
                    Component msg = Component.text("Invalid argument!", red);
                    sender.sendMessage(msg);

                    return true;
                }
            }
            else if (args[0].equalsIgnoreCase("add")) {
                String name = args[1];
                boolean added = TntSettings.getInstance().add(name);

                if (added) {
                    Component msg = Component.text(args[1] + " has been added!", green);
                    sender.sendMessage(msg);

                    return true;
                }
                else {
                    Component msg = Component.text(args[1] + " has already been added or has never played on this server!", red);
                    sender.sendMessage(msg);

                    return true;
                }
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                String name = args[1];
                boolean removed = TntSettings.getInstance().remove(name);

                if (removed) {
                    Component msg = Component.text(args[1] + " has been removed!", green);
                    sender.sendMessage(msg);

                    return true;
                }
                else {
                    Component msg = Component.text(args[1] + " has already been removed or has never played on this server!", red);
                    sender.sendMessage(msg);

                    return true;
                }
            }
        }

        return false;
    }

    /*
    /tnt enable - enables the tnt spawning
    /tnt disable - disables the tnt spawning
    /tnt interval <seconds> - sets the interval of the tnt to inputted seconds
    /tnt add <player> - adds that player to list of players tnt wil spawn on
    /tnt remove <player> - removes a player from a list of players tnt will spawn on
    /tnt list - displays a list of all players that are added
     */

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1)
            return List.of("enable", "disable", "interval", "add", "remove", "list");

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("interval")) {
                return List.of("default");
            }
            else if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {

                //List of online players
                return null;
            }
        }

        return new ArrayList<>();
    }
}
