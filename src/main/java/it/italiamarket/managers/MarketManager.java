package it.italiamarket.managers;

import it.italiamarket.ItaliaMarket;
import it.italiamarket.models.MarketListing;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class MarketManager {

    private final ItaliaMarket plugin;
    private final Map<String, MarketListing> listings = new LinkedHashMap<>();

    public MarketManager(ItaliaMarket plugin) {
        this.plugin = plugin;
        load();
    }

    public List<MarketListing> getAllListings() {
        return new ArrayList<>(listings.values());
    }

    public List<MarketListing> getListingsByPlayer(UUID uuid) {
        return listings.values().stream()
                .filter(l -> l.getSellerUUID().equals(uuid))
                .collect(Collectors.toList());
    }

    public List<MarketListing> searchListings(String query) {
        String q = query.toLowerCase();
        return listings.values().stream()
                .filter(l -> l.getItem().getType().name().toLowerCase().contains(q) ||
                        (l.getItem().getItemMeta() != null &&
                                l.getItem().getItemMeta().hasDisplayName() &&
                                l.getItem().getItemMeta().getDisplayName().toLowerCase().contains(q)))
                .collect(Collectors.toList());
    }

    public MarketListing getListing(String id) {
        return listings.get(id);
    }

    public String addListing(UUID sellerUUID, String sellerName, ItemStack item, double price) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        MarketListing listing = new MarketListing(id, sellerUUID, sellerName, item, price);
        listings.put(id, listing);
        save();
        return id;
    }

    public boolean removeListing(String id) {
        boolean removed = listings.remove(id) != null;
        if (removed) save();
        return removed;
    }

    public boolean buyListing(Player buyer, String id) {
        MarketListing listing = listings.get(id);
        if (listing == null) return false;
        if (listing.getSellerUUID().equals(buyer.getUniqueId())) return false;

        Economy eco = plugin.getEconomy();
        if (!eco.has(buyer, listing.getPrice())) return false;

        // Controlla spazio inventario
        if (buyer.getInventory().firstEmpty() == -1) return false;

        // Preleva soldi dal compratore
        eco.withdrawPlayer(buyer, listing.getPrice());

        // Dai soldi al venditore
        eco.depositPlayer(plugin.getServer().getOfflinePlayer(listing.getSellerUUID()), listing.getPrice());

        // Dai oggetto al compratore
        buyer.getInventory().addItem(listing.getItem());

        // Notifica al venditore se online
        Player seller = plugin.getServer().getPlayer(listing.getSellerUUID());
        if (seller != null) {
            seller.sendMessage("§a[Mercato] §f" + buyer.getName() + " ha comprato §e" +
                    listing.getItem().getType().name() + " §fper §a$" + listing.getPrice());
        }

        listings.remove(id);
        save();
        return true;
    }

    public void save() {
        File file = new File(plugin.getDataFolder(), "market.yml");
        YamlConfiguration config = new YamlConfiguration();

        for (MarketListing l : listings.values()) {
            String base = "listings." + l.getId();
            config.set(base + ".seller", l.getSellerUUID().toString());
            config.set(base + ".sellerName", l.getSellerName());
            config.set(base + ".price", l.getPrice());
            config.set(base + ".timestamp", l.getTimestamp());
            config.set(base + ".item", l.getItem());
        }

        try { config.save(file); } catch (Exception e) { e.printStackTrace(); }
    }

    public void load() {
        File file = new File(plugin.getDataFolder(), "market.yml");
        if (!file.exists()) return;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!config.isConfigurationSection("listings")) return;

        for (String id : config.getConfigurationSection("listings").getKeys(false)) {
            try {
                String base = "listings." + id;
                UUID seller = UUID.fromString(config.getString(base + ".seller"));
                String sellerName = config.getString(base + ".sellerName", "Sconosciuto");
                double price = config.getDouble(base + ".price");
                ItemStack item = config.getItemStack(base + ".item");
                if (item == null) continue;

                MarketListing listing = new MarketListing(id, seller, sellerName, item, price);
                listings.put(id, listing);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
