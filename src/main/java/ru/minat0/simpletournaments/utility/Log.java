package ru.minat0.simpletournaments.utility;

import java.util.logging.Logger;

import static org.bukkit.ChatColor.BLUE;
import static ru.minat0.simpletournaments.SimpleTournaments.getConfiguration;

public class Log {
    private static final Logger logger = Logger.getLogger("SimpleTournaments");

    public static Logger getLogger() {
        return logger;
    }

    /**
     * Sends a message to the console
     *
     * @param message             message to send
     * @param respectUserDecision should the message be sent if debug is false?
     * @author RoinujNosde
     */
    public static void debug(String message, boolean respectUserDecision) {
        if (respectUserDecision) {
            if (!getConfiguration().getBoolean("debug", false)) {
                return;
            }
        }
        logger.info(BLUE + "[SimpleTournaments] " + message);
    }

    public static void debug(String message) {
        debug(message, true);
    }
}
