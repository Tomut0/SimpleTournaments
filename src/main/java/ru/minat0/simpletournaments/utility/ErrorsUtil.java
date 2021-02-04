package ru.minat0.simpletournaments.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import ru.minat0.simpletournaments.SimpleTournaments;

import java.util.logging.Level;

public class ErrorsUtil {
    public static void error(String message) {
        Bukkit.getLogger().log(Level.SEVERE, ChatColor.DARK_RED + "[SimpleTournaments]" + message);
    }

    public static void warning(String message) {
        Bukkit.getLogger().log(Level.WARNING, ChatColor.YELLOW + "[SimpleTournaments]" + message);
    }

    /**
     * Sends a message to the console
     *
     * @author RoinujNosde
     * @param message message to send
     * @param respectUserDecision should the message be sent if debug is false?
     */
    public static void debug(String message, boolean respectUserDecision) {
        if (respectUserDecision) {
            if (!SimpleTournaments.getConfiguration().getConfig().getBoolean("debug")) {
                return;
            }
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[SimpleTournaments] " + message);
    }
}
