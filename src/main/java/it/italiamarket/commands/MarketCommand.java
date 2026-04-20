package it.italiamarket.commands;

import it.italiamarket.ItaliaMarket;
import it.italiamarket.listeners.MarketListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MarketCommand implements CommandExecutor {

    private final ItaliaMarket plugin;

    public MarketCommand(ItaliaMarket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo i giocatori possono usare questo comando!");
            return true;
        }

        if (args.length == 0) {
            // Apri mercato
            plugin.getMarketGUI().openMarket(player, 0, null);
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "sell" -> {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Uso: /market sell <prezzo>");
                    return true;
                }

                double price;
                try {
                    price = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Prezzo non valido!");
                    return true;
                }

                if (price <= 0) {
                    player.sendMessage(ChatColor.RED + "Il prezzo deve essere maggiore di 0!");
                    return true;
                }

                ItemStack item = player.getInventory().getItemInMainHand();
                if (item == null || item.getType().isAir()) {
                    player.sendMessage(ChatColor.RED + "Tieni un oggetto in mano!");
                    return true;
                }

                // Rimuovi oggetto dall'inventario
                ItemStack toSell = item.clone();
                player.getInventory().setItemInMainHand(null);

                plugin.getMarketManager().addListing(
                        player.getUniqueId(),
                        player.getName(),
                        toSell,
                        price
                );

                player.sendMessage(ChatColor.GREEN + "✔ Oggetto messo in vendita per $" + price + "!");
                player.sendMessage(ChatColor.GRAY + "Usa /market per vedere il mercato.");
            }

            case "cerca" -> {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Uso: /market cerca <nome>");
                    return true;
                }
                String query = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
                plugin.getMarketGUI().openMarket(player, 0, query);
            }

            case "miei" -> {
                plugin.getMarketGUI().openMieiAnnunci(player);
            }

            default -> {
                player.sendMessage(ChatColor.YELLOW + "=== ItaliaMarket ===");
                player.sendMessage(ChatColor.WHITE + "/market " + ChatColor.GRAY + "- Apri il mercato");
                player.sendMessage(ChatColor.WHITE + "/market sell <prezzo> " + ChatColor.GRAY + "- Metti in vendita l'oggetto in mano");
                player.sendMessage(ChatColor.WHITE + "/market cerca <nome> " + ChatColor.GRAY + "- Cerca un oggetto");
                player.sendMessage(ChatColor.WHITE + "/market miei " + ChatColor.GRAY + "- I tuoi annunci");
            }
        }

        return true;
    }
}
