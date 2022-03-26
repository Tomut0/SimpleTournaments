package ru.minat0.simpletournaments.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;

import static org.bukkit.ChatColor.GREEN;
import static ru.minat0.simpletournaments.SimpleTournaments.getConfigurationManager;

@CommandAlias("tournaments|tournament|st")
public class GeneralCommands extends BaseCommand {

    @HelpCommand
    public void doHelp(CommandSender sender, @Name("Page/command") CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("reload|rl")
    public class Reload extends BaseCommand {

        @Description("{@@reload.cfg.command}")
        @Subcommand("config|cfg")
        public void onConfig(CommandSender sender) {
            getConfigurationManager().reloadConfig();
            sender.sendMessage(GREEN + "[MineTail] Конфигурация была успешно перезагружена!");
        }

        @Description("{@@reload.db.command}")
        @Subcommand("database|db")
        public void onDatabase(CommandSender sender) {
            sender.sendMessage(GREEN + "[MineTail] База данных была успешно перезагружена!");
        }
    }
}
