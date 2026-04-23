package it.italiashop.gui;

import it.italiashop.ItaliaShop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class PvPGUI {

    private final ItaliaShop plugin;
    public static final Map<UUID, String> openGUI = new HashMap<>();
    public static final Map<UUID, UUID> selectedTarget = new HashMap<>();

    public PvPGUI(ItaliaShop plugin) {
        this.plugin = plugin;
    }

    public void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.RED + "Arena PvP");

        inv.setItem(11, createItemWithLore(Material.DIAMOND_SWORD, ChatColor.RED + "Sfida un Giocatore",
                Collections.singletonList(ChatColor.GRAY + "Scegli avversario e scommessa")));
        inv.setItem(15, createItemWithLore(Material.PAPER, ChatColor.YELLOW + "Inviti ricevuti",
                Collections.singletonList(ChatColor.GRAY + "Accetta o rifiuta sfide")));
        inv.setItem(22, createItem(Material.BARRIER, ChatColor.RED + "Chiudi"));

        openGUI.put(player.getUniqueId(), "pvp_main");
        player.openInventory(inv);
    }

    public void openPlayerList(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Scegli avversario");

        int slot = 0;
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.equals(player)) continue;
            if (plugin.getArenaManager().isFighting(online.getUniqueId())) continue;
            if (slot >= 45) break;

            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            if (meta != null) {
                meta.setOwningPlayer(online);
                meta.setDisplayName(ChatColor.YELLOW + online.getName());
                meta.setLore(Arrays.asList(
                        ChatColor.GRAY + "Clicca per sfidare",
                        ChatColor.BLACK + online.getUniqueId().toString()
                ));
                skull.setItemMeta(meta);
            }
            inv.setItem(slot++, skull);
        }

        inv.setItem(49, createItem(Material.ARROW, ChatColor.WHITE + "Torna"));
        openGUI.put(player.getUniqueId(), "pvp_playerlist");
        player.openInventory(inv);
    }

    public void openBetMenu(Player player, UUID targetUUID) {
        Player target = Bukkit.getPlayer(targetUUID);
        if (target == null) { player.sendMessage(ChatColor.RED + "Giocatore non trovato!"); return; }

        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.RED + "Scommessa vs " + target.getName());

        inv.setItem(10, createItemWithLore(Material.GOLD_NUGGET, ChatColor.YELLOW + "$100K", Collections.singletonList(ChatColor.GRAY + "Scommetti $100,000")));
        inv.setItem(11, createItemWithLore(Material.GOLD_INGOT, ChatColor.YELLOW + "$500K", Collections.singletonList(ChatColor.GRAY + "Scommetti $500,000")));
        inv.setItem(12, createItemWithLore(Material.GOLD_BLOCK, ChatColor.YELLOW + "$1M", Collections.singletonList(ChatColor.GRAY + "Scommetti $1,000,000")));
        inv.setItem(13, createItemWithLore(Material.DIAMOND, ChatColor.YELLOW + "$5M", Collections.singletonList(ChatColor.GRAY + "Scommetti $5,000,000")));
        inv.setItem(14, createItemWithLore(Material.DIAMOND_BLOCK, ChatColor.YELLOW + "$10M", Collections.singletonList(ChatColor.GRAY + "Scommetti $10,000,000")));
        inv.setItem(15, createItemWithLore(Material.EMERALD_BLOCK, ChatColor.YELLOW + "$50M", Collections.singletonList(ChatColor.GRAY + "Scommetti $50,000,000")));
        inv.setItem(16, createItemWithLore(Material.NETHERITE_INGOT, ChatColor.YELLOW + "$100M", Collections.singletonList(ChatColor.GRAY + "Scommetti $100,000,000")));

        inv.setItem(22, createItem(Material.ARROW, ChatColor.WHITE + "Torna"));
        selectedTarget.put(player.getUniqueId(), targetUUID);
        openGUI.put(player.getUniqueId(), "pvp_bet");
        player.openInventory(inv);
    }

    public void openInvites(Player player) {
        List<Map.Entry<UUID, Double>> invites = plugin.getArenaManager().getPendingChallengesFor(player.getUniqueId());
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Inviti ricevuti");

        if (invites.isEmpty()) {
            inv.setItem(22, createItemWithLore(Material.BARRIER, ChatColor.RED + "Nessun invito",
                    Collections.singletonList(ChatColor.GRAY + "Non hai inviti pendenti")));
        } else {
            int slot = 0;
            for (Map.Entry<UUID, Double> entry : invites) {
                if (slot >= 45) break;
                UUID challengerUUID = entry.getKey();
                double bet = entry.getValue();
                Player challenger = Bukkit.getPlayer(challengerUUID);
                String challengerName = challenger != null ? challenger.getName() : "Sconosciuto";

                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) skull.getItemMeta();
                if (meta != null) {
                    if (challenger != null) meta.setOwningPlayer(challenger);
                    meta.setDisplayName(ChatColor.YELLOW + challengerName);
                    meta.setLore(Arrays.asList(
                            ChatColor.GRAY + "Scommessa: " + ChatColor.GREEN + "$" + ShopGUI.formatPrice(bet),
                            ChatColor.GREEN + "Click sinistro: Accetta",
                            ChatColor.RED + "Click destro: Rifiuta",
                            ChatColor.BLACK + challengerUUID.toString()
                    ));
                    skull.setItemMeta(meta);
                }
                inv.setItem(slot++, skull);
            }
        }

        inv.setItem(49, createItem(Material.ARROW, ChatColor.WHITE + "Torna"));
        openGUI.put(player.getUniqueId(), "pvp_invites");
        player.openInventory(inv);
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
}
