package it.italiamarket.gui;

import it.italiamarket.ItaliaMarket;
import it.italiamarket.models.MarketListing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MarketGUI {

    private final ItaliaMarket plugin;

    public static final Map<UUID, String> openGUI = new HashMap<>(); // uuid -> tipo gui
    public static final Map<UUID, Integer> currentPage = new HashMap<>();
    public static final Map<UUID, String> searchQuery = new HashMap<>();
    public static final Map<UUID, String> pendingBuy = new HashMap<>(); // uuid -> listing id

    public MarketGUI(ItaliaMarket plugin) {
        this.plugin = plugin;
    }

    public void openMarket(Player player, int page, String search) {
        List<MarketListing> listings = search != null ?
                plugin.getMarketManager().searchListings(search) :
                plugin.getMarketManager().getAllListings();

        int itemsPerPage = 45;
        int totalPages = Math.max(1, (int) Math.ceil(listings.size() / (double) itemsPerPage));
        page = Math.max(0, Math.min(page, totalPages - 1));

 String title = search != null ?
        ChatColor.GREEN + "Ricerca: " + search :
        ChatColor.GREEN + "Mercato";

        Inventory inv = Bukkit.createInventory(null, 54, title);

        // Riempi con listings
        int start = page * itemsPerPage;
        int end = Math.min(start + itemsPerPage, listings.size());

        for (int i = start; i < end; i++) {
            MarketListing listing = listings.get(i);
            ItemStack display = listing.getItem().clone();
            ItemMeta meta = display.getItemMeta();
            if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(display.getType());

            String itemName = meta != null && meta.hasDisplayName() ?
                    meta.getDisplayName() :
                    formatMaterialName(listing.getItem().getType().name());

            meta.setDisplayName(ChatColor.YELLOW + itemName);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Venditore: " + ChatColor.WHITE + listing.getSellerName());
            lore.add(ChatColor.GRAY + "Prezzo: " + ChatColor.GREEN + "$" + listing.getPrice());
            lore.add(ChatColor.GRAY + "Quantità: " + ChatColor.WHITE + listing.getItem().getAmount());
            lore.add("");
            lore.add(ChatColor.YELLOW + "Clicca per comprare!");
            lore.add(ChatColor.BLACK + listing.getId()); // ID nascosto
            meta.setLore(lore);
            display.setItemMeta(meta);
            inv.setItem(i - start, display);
        }

        // Bottoni navigazione in basso
        // Slot 45: I miei annunci
        ItemStack miei = createItem(Material.CHEST, ChatColor.AQUA + "I miei annunci",
                Collections.singletonList(ChatColor.GRAY + "Vedi i tuoi oggetti in vendita"));
        inv.setItem(45, miei);

        // Slot 46: Cerca
        ItemStack cerca = createItem(Material.COMPASS, ChatColor.YELLOW + "Cerca oggetto",
                Collections.singletonList(ChatColor.GRAY + "Digita nel chat il nome da cercare"));
        inv.setItem(46, cerca);

        // Slot 48: Pagina precedente
        if (page > 0) {
            ItemStack prev = createItem(Material.ARROW, ChatColor.WHITE + "← Pagina precedente",
                    Collections.singletonList(ChatColor.GRAY + "Pagina " + page + "/" + totalPages));
            inv.setItem(48, prev);
        }

        // Slot 49: Info pagina
        ItemStack info = createItem(Material.PAPER, ChatColor.WHITE + "Pagina " + (page + 1) + "/" + totalPages,
                Collections.singletonList(ChatColor.GRAY + listings.size() + " oggetti in vendita"));
        inv.setItem(49, info);

        // Slot 50: Pagina successiva
        if (page < totalPages - 1) {
            ItemStack next = createItem(Material.ARROW, ChatColor.WHITE + "Pagina successiva →",
                    Collections.singletonList(ChatColor.GRAY + "Pagina " + (page + 2) + "/" + totalPages));
            inv.setItem(50, next);
        }

        // Slot 53: Chiudi
        ItemStack close = createItem(Material.BARRIER, ChatColor.RED + "Chiudi", Collections.emptyList());
        inv.setItem(53, close);

        openGUI.put(player.getUniqueId(), "market");
        currentPage.put(player.getUniqueId(), page);
        if (search != null) searchQuery.put(player.getUniqueId(), search);
        else searchQuery.remove(player.getUniqueId());

        player.openInventory(inv);
    }

    public void openMieiAnnunci(Player player) {
        List<MarketListing> myListings = plugin.getMarketManager().getListingsByPlayer(player.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.AQUA + "I miei annunci");

        for (int i = 0; i < Math.min(myListings.size(), 45); i++) {
            MarketListing listing = myListings.get(i);
            ItemStack display = listing.getItem().clone();
            ItemMeta meta = display.getItemMeta();
            if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(display.getType());

            String itemName = meta != null && meta.hasDisplayName() ?
                    meta.getDisplayName() :
                    formatMaterialName(listing.getItem().getType().name());

            meta.setDisplayName(ChatColor.YELLOW + itemName);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Prezzo: " + ChatColor.GREEN + "$" + listing.getPrice());
            lore.add(ChatColor.GRAY + "Quantità: " + ChatColor.WHITE + listing.getItem().getAmount());
            lore.add("");
            lore.add(ChatColor.RED + "Clicca per ritirare!");
            lore.add(ChatColor.BLACK + listing.getId());
            meta.setLore(lore);
            display.setItemMeta(meta);
            inv.setItem(i, display);
        }

        // Bottone torna indietro
        ItemStack back = createItem(Material.ARROW, ChatColor.WHITE + "← Torna al mercato", Collections.emptyList());
        inv.setItem(49, back);

        openGUI.put(player.getUniqueId(), "miei");
        player.openInventory(inv);
    }

    public void openConfirmBuy(Player player, MarketListing listing) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "Conferma acquisto");

        // Oggetto da comprare al centro
        ItemStack display = listing.getItem().clone();
        ItemMeta meta = display.getItemMeta();
        if (meta != null) {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Venditore: " + ChatColor.WHITE + listing.getSellerName());
            lore.add(ChatColor.GRAY + "Prezzo: " + ChatColor.GREEN + "$" + listing.getPrice());
            meta.setLore(lore);
            display.setItemMeta(meta);
        }
        inv.setItem(13, display);

        // Conferma
        ItemStack confirm = createItem(Material.LIME_WOOL, ChatColor.GREEN + "✔ Conferma acquisto",
                Collections.singletonList(ChatColor.GRAY + "Pagherai $" + listing.getPrice()));
        inv.setItem(11, confirm);

        // Annulla
        ItemStack cancel = createItem(Material.RED_WOOL, ChatColor.RED + "✖ Annulla",
                Collections.singletonList(ChatColor.GRAY + "Torna al mercato"));
        inv.setItem(15, cancel);

        pendingBuy.put(player.getUniqueId(), listing.getId());
        openGUI.put(player.getUniqueId(), "confirm");
        player.openInventory(inv);
    }

    private ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private String formatMaterialName(String name) {
        String[] words = name.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
