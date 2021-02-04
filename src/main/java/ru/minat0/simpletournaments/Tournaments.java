package ru.minat0.simpletournaments;

import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.UUID;

public class Tournaments {
    private String name;
    private HashSet<UUID> participants;
    private HashSet<Selection> selections;

    public HashSet<UUID> getParticipants() {
        return participants;
    }

    public void setParticipants(HashSet<UUID> participants) {
        this.participants = participants;
    }

    public HashSet<Selection> getSelections() {
        return selections;
    }

    public void setSelections(HashSet<Selection> selections) {
        this.selections = selections;
    }

    /**
     * Returns the colored name of tournaments
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
    }

    /**
     * Returns the name of tournaments without colors
     *
     * @return String
     */
    public String getCleanName() {
        return ChatColor.stripColor(name);
    }
}
