package ru.minat0.tournaments.commands;

import org.bukkit.entity.Player;
import ru.minat0.tournaments.Main;
import ru.minat0.tournaments.abstracts.BaseCommand;

public class Reload extends BaseCommand {
    private static final String commandDescription = "Перезагрузить конфигруацию плагина";
    private static final String commandPermission = "tournaments.reload";


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
        return null;
    }

    @Override
    public void initialize(String[] args, Player sender) {
        Main.getInstance().getConfiguration().reloadConfig();
        sender.sendMessage("§8[§aTournaments§8]§f Конфигурация была §2успешно§f перезагружена!");
    }
}
