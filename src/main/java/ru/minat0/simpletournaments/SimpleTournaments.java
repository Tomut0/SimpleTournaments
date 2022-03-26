package ru.minat0.simpletournaments;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.minat0.simpletournaments.commands.GeneralCommands;
import ru.minat0.simpletournaments.managers.ConfigManager;
import ru.minat0.simpletournaments.managers.DatabaseManager;
import ru.minat0.simpletournaments.utility.Helper;

import java.util.Set;

import static org.bukkit.Bukkit.getPluginManager;
import static ru.minat0.simpletournaments.managers.DatabaseManager.DBType;

public class SimpleTournaments extends JavaPlugin {
    private static SimpleTournaments instance;

    private static ConfigManager configManager;
    private static DatabaseManager databaseManager;
    private static PaperCommandManager commandManager;

    public static PaperCommandManager getCommandManager() {
        return commandManager;
    }

    public static FileConfiguration getConfiguration() {
        return configManager.getConfig();
    }

    public static ConfigManager getConfigurationManager() {
        return configManager;
    }

    public static SimpleTournaments getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        registerManagers();
        registerDependencies();
        registerEvents();

        commandManager.registerCommand(new GeneralCommands(), true);
        commandManager.getLocales();

        commandManager.enableUnstableAPI("help");
    }

    private void registerManagers() {
        configManager = new ConfigManager(this, this.getDataFolder(), "config", true, true);
        configManager.reloadConfig();
        commandManager = new PaperCommandManager(this);

        DBType dbType = DBType.valueOf(getConfiguration().getString("DataSource.backend"));
        databaseManager = new DatabaseManager(this, configManager.getConfig(), dbType);
    }

    private void registerEvents() {
        Set<Class<? extends Listener>> events = Helper.getSubTypesOf("ru.minat0.simpletournaments.events", Listener.class);
        for (Class<? extends Listener> c : events) {
            try {
                getPluginManager().registerEvents(c.getDeclaredConstructor().newInstance(), this);
            } catch (Exception ex) {
                getLogger().severe("Error registering event: " + ex.getMessage());
            }
        }
    }

    private void registerDependencies() {
        commandManager.registerDependency(ConfigManager.class, configManager);
        commandManager.registerDependency(DatabaseManager.class, databaseManager);
    }
}