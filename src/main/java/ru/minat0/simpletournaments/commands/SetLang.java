package ru.minat0.simpletournaments.commands;

import org.bukkit.entity.Player;
import ru.minat0.simpletournaments.abstracts.BaseCommand;
import ru.minat0.simpletournaments.managers.LocaleManager;

import java.util.Locale;

public class SetLang extends BaseCommand {
    private static final String commandDescription = "Установить отображаемый язык";
    private static final String commandPermission = "tournaments.setlang";
    private static final String commandParameters = "§7[§fкод языка§7]";

    @Override
    public String getCommandDescription() {
        return commandDescription;
    }

    @Override
    public String getCommandPermission() {
        return commandPermission;
    }

    @Override
    public String getCommandParameters() {
        return commandParameters;
    }

    @Override
    public void initialize(String[] args, Player p) {
        Locale userLocale = new Locale(args[1]);
        LocaleManager.getMessage();
        //p.sendMessage(LocaleManager.getMessage());
    }
}
