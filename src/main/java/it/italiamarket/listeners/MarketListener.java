package it.italiamarket.listeners;

import it.italiamarket.ItaliaMarket;
import it.italiamarket.gui.MarketGUI;
import it.italiamarket.models.MarketListing;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MarketListener implements Listener {

    private final ItaliaMarket plugin;
    public static final Set<UUID> waitingSearch = new HashSet<>();

    public MarketListener(ItaliaMarket plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (!waitingSearch.contains(player.getUniqueId())) return;

        e.setCancelled(true);
        waitingSearch.remove(player.getUniqueId());
        String query = e.getMessage();

        plugin.getServer().getScheduler().runTask(plugin, () ->
                plugin.getMarketGUI().openMarket(player, 0, query));
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();
        String gui = MarketGUI.openGUI.get(uuid);
        if (gui == null) return;

        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;

        String title = e.getView().getTitle();

        // === MERCATO PRINCIPALE ===
        if (gui.equals("market")) {
            int slot = e.getSlot();

            if (slot == 53) { // Chiudi
                player.closeInventory();
                MarketGUI.openGUI.remove(uuid);
                return;
            }

            if (slot == 45) { // I miei annunci
                player.closeInventory();
                plugin.getMarketGUI().openMieiAnnunci(player);
                return;
            }

            if (slot == 46) { // Cerca
                player.closeInventory();
                MarketGUI.openGUI.remove(uuid);
                waitingSearch.add(uuid);
                player.sendMessage(ChatColor.YELLOW + "Scrivi in chat il nome dell'oggetto da cercare:");
                return;
            }

            if (slot == 48) { // Pagina precedente
                int page = MarketGUI.currentPage.getOrDefault(uuid, 0);
                String search = MarketGUI.searchQuery.get(uuid);
                player.closeInventory();
                plugin.getMarketGUI().openMarket(player, page - 1, search);
                return;
            }

            if (slot == 50) { // Pagina successiva
                int page = MarketGUI.currentPage.getOrDefault(uuid, 0);
                String search = MarketGUI.searchQuery.get(uuid);
                player.closeInventory();
                plugin.getMarketGUI().openMarket(player, page + 1, search);
                return;
            }

            // Click su oggetto
            if (slot < 45 && e.getCurrentItem() != null) {
                // Recupera ID dal lore
                if (e.getCurrentItem().getItemMeta() == null) return;
                java.util.List<String> lore = e.getCurrentItem().getItemMeta().getLore();
                if (lore == null || lore.isEmpty()) return;
                String idRaw = lore.get(lore.size() - 1);
                String id = ChatColor.stripColor(idRaw);

                MarketListing listing = plugin.getMarketManager().getListing(id);
                if (listing == null) {
                    player.sendMessage(ChatColor.RED + "Questo oggetto non è più disponibile!");
                    player.closeInventory();
                    plugin.getMarketGUI().openMarket(player, 0, null);
                    return;
                }

                if (listing.getSellerUUID().equals(uuid)) {
                    player.sendMessage(ChatColor.RED + "Non puoi comprare il tuo stesso oggetto!");
                    return;
                }

                player.closeInventory();
                plugin.getMarketGUI().openConfirmBuy(player, listing);
            }
        }

        // === I MIEI ANNUNCI ===
        else if (gui.equals("miei")) {
            int slot = e.getSlot();

            if (slot == 49) { // Torna al mercato
                player.closeInventory();
                plugin.getMarketGUI().openMarket(player, 0, null);
                return;
            }

            if (slot < 45 && e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta() == null) return;
                java.util.List<String> lore = e.getCurrentItem().getItemMeta().getLore();
                if (lore == null || lore.isEmpty()) return;
                String id = ChatColor.stripColor(lore.get(lore.size() - 1));

                MarketListing listing = plugin.getMarketManager().getListing(id);
                if (listing == null) {
                    player.sendMessage(ChatColor.RED + "Annuncio non trovato!");
                    return;
                }

                // Ritira oggetto
                plugin.getMarketManager().removeListing(id);
                player.getInventory().addItem(listing.getItem());
                player.sendMessage(ChatColor.GREEN + "Oggetto ritirato dal mercato!");
                player.closeInventory();
                plugin.getMarketGUI().openMieiAnnunci(player);
            }
        }

        // === CONFERMA ACQUISTO ===
        else if (gui.equals("confirm")) {
            int slot = e.getSlot();

            if (slot == 11) { // Conferma
                String listingId = MarketGUI.pendingBuy.get(uuid);
                if (listingId == null) { player.closeInventory(); return; }

                boolean success = plugin.getMarketManager().buyListing(player, listingId);
                MarketGUI.pendingBuy.remove(uuid);

                if (success) {
                    player.sendMessage(ChatColor.GREEN + "✔ Acquisto completato!");
                } else {
                    player.sendMessage(ChatColor.RED + "Acquisto fallito! Soldi insufficienti o inventario pieno.");
                }
                player.closeInventory();
                plugin.getMarketGUI().openMarket(player, 0, null);
            }

            if (slot == 15) { // Annulla
                MarketGUI.pendingBuy.remove(uuid);
                player.closeInventory();
                plugin.getMarketGUI().openMarket(player, 0, null);
            }
        }
    }
}
