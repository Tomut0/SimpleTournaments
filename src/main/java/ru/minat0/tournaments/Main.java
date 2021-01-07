package ru.minat0.tournaments;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import ru.minat0.tournaments.utility.CommandManager;
import ru.minat0.tournaments.utility.ConfigManager;
import ru.minat0.tournaments.utility.LocaleManager;

import java.util.Set;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    private static Main instance;

    private ConfigManager config;
    private LocaleManager locationManager;

    @Override
    public void onEnable() {
        instance = this;

        this.config = new ConfigManager(this, this.getDataFolder(), "config", true, true);

        this.locationManager = new LocaleManager(config.getConfig() != null ? config.getConfig().getString("lang") : null);

        registerEvents();
        getCommand("tournaments").setExecutor(new CommandManager());

    }

    public static Main getInstance() {
        return instance;
    }

    private void registerEvents() {
        Reflections reflections = new Reflections("ru.minat0.tournaments.events");
        Set<Class<? extends Listener>> listeners = reflections.getSubTypesOf(Listener.class);
        for (Class<? extends Listener> c : listeners) {
            try {
                getServer().getPluginManager().registerEvents(c.getDeclaredConstructor().newInstance(), this);
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Error registering event", ex);
            }
        }
    }

    public ConfigManager getConfiguration() {
        return config;
    }

    public LocaleManager getLocationManager() {
        return locationManager;
    }
}