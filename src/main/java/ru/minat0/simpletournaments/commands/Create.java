package ru.minat0.simpletournaments.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.minat0.simpletournaments.Tournaments;
import ru.minat0.simpletournaments.abstracts.BaseCommand;
import ru.minat0.simpletournaments.managers.TournamentsManager;

import java.util.Arrays;

public class Create extends BaseCommand {
    private static final String commandDescription = "Создать турнир";
    private static final String commandPermission = "tournaments.create";
    private static final String commandParameters = "§7[§fнаименование турнира§7]";

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

    public void initialize(String[] args, Player p) {
        String name = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        if (name.length() > 0) {
            Tournaments tournament = new Tournaments();
            if (!TournamentsManager.containsByCleanName(name)) {
                tournament.setName(name);
                p.sendMessage("§aТурнир " + ChatColor.translateAlternateColorCodes('&', tournament.getName()) + "§a был успешно создан.");
                p.sendMessage(ChatColor.GRAY + "Следующий этап: выделите две точки, используя WE.");

                TournamentsManager.tournamentsList.add(tournament);

            } else p.sendMessage("123");
        } else p.sendMessage(ChatColor.DARK_RED + "Отсутствует наименование турнира!");
    }
}
