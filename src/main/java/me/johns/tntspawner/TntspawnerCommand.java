package me.johns.tntspawner;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        if (args.length > 1 && !args[0].equalsIgnoreCase("interval")) {
            Component msg = Component.text("There are no arguments for this command", red);
            sender.sendMessage(msg);

            return true;
        }


        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("enable")) {
                if (TntTask.isEnabled()) {
                    Component msg = Component.text("This plugin is already enabled!", red);
                    sender.sendMessage(msg);

                    return true;
                }
                else {
                    Component msg = Component.text("Plugin has been enabled!", green);
                    sender.sendMessage(msg);
                    Component msg2 = Component.text("TNT will start spawning", green);
                    sender.sendMessage(msg2);

                    TntTask.enableSpawns();

                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("disable")) {
                if (!TntTask.isEnabled()) {
                    Component msg = Component.text("This plugin is already disabled!", red);
                    sender.sendMessage(msg);

                    return true;
                }
                else {
                    Component msg = Component.text("Plugin has been disabled!", green);
                    sender.sendMessage(msg);
                    Component msg2 = Component.text("TNT will stop spawning", green);
                    sender.sendMessage(msg2);

                    TntTask.disableSpawns();

                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("interval")) {
                Component msg = Component.text("Please specify the interval in seconds!", red);
                sender.sendMessage(msg);

                return true;
            }
        }

        if (args.length == 2) {
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
        }

        return false;
    }

    /*

    /tnt enable - enables the tnt spawning
    /tnt disable - disables the tnt spawning
    /tnt interval <seconds> - sets the interval of the tnt to inputted seconds

     */

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1)
            return Arrays.asList("enable", "disable", "interval");

        if (args.length == 2 && args[0].equalsIgnoreCase("interval")) {
            return List.of("default");
        }

        return new ArrayList<>();
    }
}
