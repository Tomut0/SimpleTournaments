package ru.minat0.simpletournaments.managers;

import ru.minat0.simpletournaments.Tournaments;

import java.util.HashSet;

public class TournamentsManager {
    public static HashSet<Tournaments> tournamentsList = new HashSet<>();

    public static boolean containsByName(String name) {
        return tournamentsList.stream().anyMatch(tournament -> tournament.getCleanName().equalsIgnoreCase(name));
    }

    public static boolean containsByCleanName(String name) {
        return tournamentsList.stream().anyMatch(tournament -> tournament.getCleanName().equalsIgnoreCase(name.replaceAll("&[0-f]", "")));
    }
}