package it.italiashop.commands;

import it.italiashop.ItaliaShop;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommand implements CommandExecutor {

    private final ItaliaShop plugin;

    public NPCCommand(ItaliaShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo i giocatori!");
            return true;
        }
        if (!player.hasPermission("italiashop.admin")) {
            player.sendMessage(ChatColor.RED + "Non hai i permessi!");
            return true;
        }
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Uso: /npc <place|remove> <mercato|pvp>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "place" -> {
                switch (args[1].toLowerCase()) {
                    case "mercato" -> { plugin.getNpcManager().spawnShopVillager(player.getLocation()); player.sendMessage(ChatColor.GREEN + "NPC Shop piazzato!"); }
                    case "pvp" -> { plugin.getNpcManager().spawnPvpVillager(player.getLocation()); player.sendMessage(ChatColor.GREEN + "NPC Arena piazzato!"); }
                    default -> player.sendMessage(ChatColor.RED + "Usa: mercato o pvp");
                }
            }
            case "remove" -> {
                switch (args[1].toLowerCase()) {
                    case "mercato" -> { plugin.getNpcManager().removeShopVillager(); player.sendMessage(ChatColor.GREEN + "NPC Shop rimosso!"); }
                    case "pvp" -> { plugin.getNpcManager().removePvpVillager(); player.sendMessage(ChatColor.GREEN + "NPC Arena rimosso!"); }
                    default -> player.sendMessage(ChatColor.RED + "Usa: mercato o pvp");
                }
            }
            default -> player.sendMessage(ChatColor.RED + "Usa: place o remove");
        }
        return true;
    }
}
