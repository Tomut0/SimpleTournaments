package ru.minat0.tournaments.utility;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.minat0.tournaments.Main;

import java.io.File;

public class LocaleManager {
    public FileConfiguration locale;
    protected File file;
    protected String langCode;

    public LocaleManager(String langCode) {
        if (langCode != null) {
            this.langCode = langCode;
        } else {
            this.langCode = "en_EN";
        }
        create();
    }

    public void create() {
        if (file == null) {
            reloadFile();
        }
        if (file.exists()) {
            return;
        }

        file.getParentFile().mkdirs();
        Main.getInstance().saveResource("lang/messages_" + langCode +".yml", false);

        if (locale == null) {
            reloadLocale();
        }
    }

    private File reloadFile() {
        file = new File(Main.getInstance().getDataFolder() + File.separator + "lang/", "messages_" + langCode);
        return file;
    }

    public FileConfiguration reloadLocale() {
        locale = YamlConfiguration.loadConfiguration(file);
        return locale;
    }
}
