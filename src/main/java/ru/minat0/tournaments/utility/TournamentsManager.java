package ru.minat0.tournaments.utility;

import org.bukkit.ChatColor;
import ru.minat0.tournaments.Tournaments;

import java.util.HashSet;

public class TournamentsManager {
    public static HashSet<Tournaments> tournamentsList = new HashSet<>();

    public static boolean containsByName(String name) {
        return tournamentsList.stream().anyMatch(tournament -> tournament.getCleanName().equalsIgnoreCase(name));
    }

    public static boolean containsByCleanName(String name) {
        return tournamentsList.stream().anyMatch(tournament -> tournament.getCleanName().equalsIgnoreCase(ChatColor.stripColor(name)));
    }
}