package ru.minat0.simpletournaments;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import ru.minat0.simpletournaments.managers.CommandManager;
import ru.minat0.simpletournaments.managers.ConfigManager;
import ru.minat0.simpletournaments.managers.DatabaseManager;
import ru.minat0.simpletournaments.utility.ErrorsUtil;

import java.util.Set;

public class SimpleTournaments extends JavaPlugin {
    private static SimpleTournaments instance;

    private static ConfigManager config;
    private static final DatabaseManager db = new DatabaseManager();

    @Override
    public void onEnable() {
        instance = this;

        config = new ConfigManager(this, this.getDataFolder(), "config", true, true);

        registerEvents();
        getCommand("tournaments").setExecutor(new CommandManager());
    }

    public static SimpleTournaments getInstance() {
        return instance;
    }

    private void registerEvents() {
        Reflections reflections = new Reflections("ru.minat0.tournaments.events");
        Set<Class<? extends Listener>> listeners = reflections.getSubTypesOf(Listener.class);
        Bukkit.getLogger().info(listeners.toString());
        for (Class<? extends Listener> c : listeners) {
            try {
                getServer().getPluginManager().registerEvents(c.getDeclaredConstructor().newInstance(), this);
            } catch (Exception ex) {
                ErrorsUtil.error("Error registering event: " + ex.getMessage());
            }
        }
    }

    public static ConfigManager getConfiguration() {
        return config;
    }

    @NotNull
    public static DatabaseManager getDB() {
        return db;
    }

    /*public LocaleManager getLocationManager() {
        return locationManager;
    }*/
}