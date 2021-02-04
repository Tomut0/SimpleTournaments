package ru.minat0.simpletournaments.managers;

import ru.minat0.simpletournaments.utility.ErrorsUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;

/**
 * @author RoinujNosde
 */
public class LocaleManager {
    public static Locale locale = new Locale("en_EN");

    private static ArrayList<Locale> availableLocales;

    private static void loadAvailableLocales() {
        ArrayList<Locale> locales = new ArrayList<>();

        try {
            URI uri = LocaleManager.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            FileSystem fileSystem = FileSystems.newFileSystem(URI.create("jar:" + uri.toString()), Collections.emptyMap());
            Files.walk(fileSystem.getPath("/")).forEach(p -> {
                String name = p.getFileName() == null ? "" : p.getFileName().toString();
                if (name.startsWith("messages") && name.endsWith(".yml")) {
                    Locale locale = getLocaleByFileName(name);
                    locales.add(locale);
                }
            });
            fileSystem.close();
        } catch (IOException | URISyntaxException ex) {
            ErrorsUtil.warning("An error occurred while getting the available languages" + ex.getMessage());
        }
        locales.sort(Comparator.comparing(Locale::toLanguageTag));
        availableLocales = locales;
    }

    private static Locale getLocaleByFileName(String name) {
        Locale locale;
        String[] extensionSplit = name.split(".yml");
        String[] split = extensionSplit[0].split("_");
        if (split.length == 2) {
            locale = new Locale(split[1]);
        } else {
            locale = new Locale(split[1], split[2]);
        }
        return locale;
    }

    public static List<Locale> getAvailableLocales() {
        if (availableLocales != null) {
            return availableLocales;
        }
        loadAvailableLocales();
        return availableLocales;
    }

    public static void getMessage() {

        //return localeConfig.getString(string);
    }
}
