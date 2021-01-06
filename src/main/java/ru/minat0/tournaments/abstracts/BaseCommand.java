package ru.minat0.tournaments.abstracts;

import org.bukkit.entity.Player;

public abstract class BaseCommand {

    public abstract String getCommandDescription();
    public abstract String getCommandPermission();
    public abstract String getCommandParameters();

    public abstract void initialize(String[] args, Player sender);
}
