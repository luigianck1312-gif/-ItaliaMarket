package it.italiashop.gui;

import it.italiashop.ItaliaShop;
import it.italiashop.managers.ItemValueRegistry;
import it.italiashop.models.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ShopGUI {

    private final ItaliaShop plugin;

    public static final Map<UUID, String> openGUI = new HashMap<>();
    public static final Map<UUID, ShopItem> selectedItem = new HashMap<>();
    public static final Map<UUID, String> shopMode = new HashMap<>(); // "buy" or "sell"

    public ShopGUI(ItaliaShop plugin) {
        this.plugin = plugin;
    }

    // Menu principale - scegli compra o vendi
    public void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Shop - SteelThrone");

        ItemStack buyBtn = createItemWithLore(Material.LIME_WOOL,
                ChatColor.GREEN + "Compra",
                Arrays.asList(ChatColor.GRAY + "Sfoglia gli oggetti disponibili all'acquisto"));
        inv.setItem(11, buyBtn);

        ItemStack sellBtn = createItemWithLore(Material.RED_WOOL,
                ChatColor.RED + "Vendi",
                Arrays.asList(ChatColor.GRAY + "Vendi oggetti dal tuo inventario"));
        inv.setItem(15, sellBtn);

        inv.setItem(22, createItem(Material.BARRIER, ChatColor.RED + "Chiudi"));

        openGUI.put(player.getUniqueId(), "main");
        player.openInventory(inv);
    }

    // Menu categorie per comprare
    public void openCategoryMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.GREEN + "Compra - Categoria");

        inv.setItem(10, createItemWithLore(Material.DIRT, ChatColor.YELLOW + "Blocchi Base", Collections.singletonList(ChatColor.GRAY + "Terra, Sabbia, Ghiaccio...")));
        inv.setItem(11, createItemWithLore(Material.STONE, ChatColor.YELLOW + "Pietra & Roccia", Collections.singletonList(ChatColor.GRAY + "Pietra, Deepslate, Tuff...")));
        inv.setItem(12, createItemWithLore(Material.OAK_LOG, ChatColor.YELLOW + "Legno", Collections.singletonList(ChatColor.GRAY + "Tronchi, Assi, Porte...")));
        inv.setItem(13, createItemWithLore(Material.WHITE_WOOL, ChatColor.YELLOW + "Decorazioni", Collections.singletonList(ChatColor.GRAY + "Lana, Vetro, Candele...")));
        inv.setItem(14, createItemWithLore(Material.DIAMOND, ChatColor.AQUA + "Minerali", Collections.singletonList(ChatColor.GRAY + "Ferro, Oro, Diamante...")));
        inv.setItem(19, createItemWithLore(Material.OAK_LEAVES, ChatColor.GREEN + "Natura & Piante", Collections.singletonList(ChatColor.GRAY + "Fiori, Mob Drop, Cibo...")));
        inv.setItem(20, createItemWithLore(Material.NETHERRACK, ChatColor.RED + "Nether", Collections.singletonList(ChatColor.GRAY + "Blaze, Ghast, Nether...")));
        inv.setItem(21, createItemWithLore(Material.END_STONE, ChatColor.LIGHT_PURPLE + "End", Collections.singletonList(ChatColor.GRAY + "Ender, Chorus, Shulker...")));
        inv.setItem(22, createItemWithLore(Material.TOTEM_OF_UNDYING, ChatColor.GOLD + "Rari", Collections.singletonList(ChatColor.GRAY + "Totem, Elitra...")));
        inv.setItem(36, createItem(Material.ARROW, ChatColor.WHITE + "Torna"));

        openGUI.put(player.getUniqueId(), "categories");
        shopMode.put(player.getUniqueId(), "buy");
        player.openInventory(inv);
    }

    // Lista oggetti per categoria
    public void openCategory(Player player, ShopItem.Category category) {
        List<ShopItem> items = plugin.getShopManager().getItemsByCategory(category);
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Compra - " + getCategoryName(category));

        int slot = 0;
        for (ShopItem item : items) {
            if (slot >= 45) break;
            inv.setItem(slot++, createBuyItemDisplay(player, item));
        }

        inv.setItem(49, createItem(Material.ARROW, ChatColor.WHITE + "Torna"));
        openGUI.put(player.getUniqueId(), "category_" + category.name());
        player.openInventory(inv);
    }

    // Menu acquisto singolo oggetto
    public void openBuyMenu(Player player, ShopItem item) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GREEN + "Compra - " + item.getDisplayName());

        inv.setItem(13, createBuyItemDisplay(player, item));

        boolean hasCooldown = item.isRare() && plugin.getShopManager().hasCooldown(player.getUniqueId(), item.getMaterial());

        if (hasCooldown) {
            long remaining = plugin.getShopManager().getRemainingCooldown(player.getUniqueId(), item.getMaterial());
            long days = remaining / (1000 * 60 * 60 * 24);
            long hours = (remaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            double skipCost = plugin.getShopManager().getSkipCooldownCost(item.getMaterial());

            inv.setItem(11, createItemWithLore(Material.CLOCK,
                    ChatColor.YELLOW + "In cooldown",
                    Arrays.asList(
                            ChatColor.GRAY + "Rimanente: " + ChatColor.WHITE + days + "g " + hours + "h",
                            ChatColor.GRAY + "Skip: " + ChatColor.GREEN + "$" + formatPrice(skipCost),
                            ChatColor.YELLOW + "Clicca per pagare e comprare subito!"
                    )));
        } else {
            if (item.getMaxStack() == 1) {
                inv.setItem(13, createItemWithLore(Material.LIME_WOOL,
                        ChatColor.GREEN + "Compra x1",
                        Collections.singletonList(ChatColor.GRAY + "Prezzo: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentPrice()))));
            } else {
                inv.setItem(10, createItemWithLore(Material.LIME_WOOL,
                        ChatColor.GREEN + "Compra x1",
                        Collections.singletonList(ChatColor.GRAY + "$" + formatPrice(item.getCurrentPrice() / 64))));
                inv.setItem(13, createItemWithLore(Material.LIME_WOOL,
                        ChatColor.GREEN + "Compra x32",
                        Collections.singletonList(ChatColor.GRAY + "$" + formatPrice(item.getCurrentPrice() / 2))));
                inv.setItem(16, createItemWithLore(Material.LIME_WOOL,
                        ChatColor.GREEN + "Compra x64",
                        Collections.singletonList(ChatColor.GRAY + "$" + formatPrice(item.getCurrentPrice()))));
            }
        }

        inv.setItem(22, createItem(Material.ARROW, ChatColor.WHITE + "Torna"));
        selectedItem.put(player.getUniqueId(), item);
        openGUI.put(player.getUniqueId(), "buy_item");
        player.openInventory(inv);
    }

    // Menu vendita - mostra inventario player
    public void openSellInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Vendi - Seleziona oggetto");

        int slot = 0;
        Set<Material> shown = new HashSet<>();
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack == null || stack.getType().isAir()) continue;
            if (shown.contains(stack.getType())) continue;
            if (!ItemValueRegistry.hasValue(stack.getType())) continue;
            if (slot >= 45) break;

            double sellPrice = ItemValueRegistry.getSellPrice(stack.getType());
            if (sellPrice <= 0) continue;

            ItemStack display = stack.clone();
            display.setAmount(1);
            ItemMeta meta = display.getItemMeta();
            if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(display.getType());

            String itemName = meta.hasDisplayName() ? meta.getDisplayName() : formatMaterialName(stack.getType().name());
            meta.setDisplayName(ChatColor.YELLOW + itemName);

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Vendita x1: " + ChatColor.GREEN + "$" + formatPrice(sellPrice));
            lore.add(ChatColor.GRAY + "Hai: " + ChatColor.WHITE + countInInventory(player, stack.getType()));
            lore.add(ChatColor.YELLOW + "Clicca per vendere");
            lore.add(ChatColor.BLACK + stack.getType().name());
            meta.setLore(lore);
            display.setItemMeta(meta);

            inv.setItem(slot++, display);
            shown.add(stack.getType());
        }

        inv.setItem(49, createItem(Material.ARROW, ChatColor.WHITE + "Torna"));
        openGUI.put(player.getUniqueId(), "sell_inventory");
        player.openInventory(inv);
    }

    // Menu vendita quantita
    public void openSellQuantityMenu(Player player, Material material) {
        double sellPrice = ItemValueRegistry.getSellPrice(material);
        int inInventory = countInInventory(player, material);
        String name = formatMaterialName(material.name());

        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.RED + "Vendi - " + name);

        ItemStack display = new ItemStack(material);
        ItemMeta meta = display.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.YELLOW + name);
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Vendita x1: " + ChatColor.GREEN + "$" + formatPrice(sellPrice),
                    ChatColor.GRAY + "Hai: " + ChatColor.WHITE + inInventory
            ));
            display.setItemMeta(meta);
        }
        inv.setItem(13, display);

        if (inInventory >= 1)
            inv.setItem(9, createItemWithLore(Material.RED_WOOL, ChatColor.RED + "Vendi x1",
                    Collections.singletonList(ChatColor.GREEN + "$" + formatPrice(sellPrice))));
        if (inInventory >= 16)
            inv.setItem(11, createItemWithLore(Material.RED_WOOL, ChatColor.RED + "Vendi x16",
                    Collections.singletonList(ChatColor.GREEN + "$" + formatPrice(sellPrice * 16))));
        if (inInventory >= 32)
            inv.setItem(13, createItemWithLore(Material.RED_WOOL, ChatColor.RED + "Vendi x32",
                    Collections.singletonList(ChatColor.GREEN + "$" + formatPrice(sellPrice * 32))));
        if (inInventory >= 64)
            inv.setItem(15, createItemWithLore(Material.RED_WOOL, ChatColor.RED + "Vendi x64",
                    Collections.singletonList(ChatColor.GREEN + "$" + formatPrice(sellPrice * 64))));
        inv.setItem(17, createItemWithLore(Material.GOLD_BLOCK, ChatColor.GOLD + "Vendi tutto (" + inInventory + ")",
                Collections.singletonList(ChatColor.GREEN + "$" + formatPrice(sellPrice * inInventory))));

        inv.setItem(22, createItem(Material.ARROW, ChatColor.WHITE + "Torna"));

        openGUI.put(player.getUniqueId(), "sell_qty_" + material.name());
        player.openInventory(inv);
    }

    private int countInInventory(Player player, Material mat) {
        int count = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.getType() == mat) count += stack.getAmount();
        }
        return count;
    }

    private ItemStack createBuyItemDisplay(Player player, ShopItem item) {
        boolean hasCooldown = item.isRare() && plugin.getShopManager().hasCooldown(player.getUniqueId(), item.getMaterial());
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Acquisto x1: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentPrice() / (item.getMaxStack() == 1 ? 1 : 64)));
        lore.add(ChatColor.GRAY + "Vendita x1: " + ChatColor.RED + "$" + formatPrice(item.getCurrentSellPrice() / (item.getMaxStack() == 1 ? 1 : 64)));
        if (item.isRare()) lore.add(ChatColor.GOLD + "Oggetto Raro - Cooldown: " + item.getCooldownDays() + "g");
        if (hasCooldown) lore.add(ChatColor.RED + "In cooldown!");
        lore.add(ChatColor.YELLOW + "Clicca per comprare");
        lore.add(ChatColor.BLACK + item.getMaterial().name());

        ItemStack stack = new ItemStack(item.getMaterial());
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.YELLOW + item.getDisplayName());
            meta.setLore(lore);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack createItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) { meta.setDisplayName(name); item.setItemMeta(meta); }
        return item;
    }

    private ItemStack createItemWithLore(Material mat, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) { meta.setDisplayName(name); meta.setLore(lore); item.setItemMeta(meta); }
        return item;
    }

    private String getCategoryName(ShopItem.Category cat) {
        return switch (cat) {
            case BLOCCHI -> "Blocchi Base";
            case PIETRA -> "Pietra & Roccia";
            case LEGNO -> "Legno";
            case DECORAZIONI -> "Decorazioni";
            case MINERALI -> "Minerali";
            case NATURA -> "Natura & Piante";
            case NETHER -> "Nether";
            case END -> "End";
            case RARI -> "Rari";
        };
    }

    public static String formatPrice(double price) {
        if (price >= 1_000_000_000) return String.format("%.1fMld", price / 1_000_000_000);
        if (price >= 1_000_000) return String.format("%.1fMln", price / 1_000_000);
        if (price >= 1_000) return String.format("%.1fK", price / 1_000);
        return String.valueOf((long) price);
    }

    public static String formatMaterialName(String name) {
        String[] words = name.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words) sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        return sb.toString().trim();
    }
}
