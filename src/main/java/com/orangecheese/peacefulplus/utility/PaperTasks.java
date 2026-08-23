package com.orangecheese.peacefulplus.utility;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperTasks {
    private static JavaPlugin plugin;

    public static void initialize(JavaPlugin plugin) {
        PaperTasks.plugin = plugin;
    }

    public static void nextTick(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }
}