package it.italiashop.listeners;

import it.italiashop.ItaliaShop;
import it.italiashop.gui.PvPGUI;
import it.italiashop.gui.ShopGUI;
import it.italiashop.managers.ItemValueRegistry;
import it.italiashop.models.ShopItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopListener implements Listener {

    private final ItaliaShop plugin;

    public ShopListener(ItaliaShop plugin) {
        this.plugin = plugin;
    }

    // Proteggi i villager NPC da danni
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Villager villager)) return;
        if (plugin.getNpcManager().isShopVillager(villager) || plugin.getNpcManager().isPvpVillager(villager)) {
            e.setCancelled(true);
        }
    }

    // Interazione con NPC
    @EventHandler
    public void onVillagerInteract(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager villager)) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        Player player = e.getPlayer();
        e.setCancelled(true);

        if (plugin.getNpcManager().isShopVillager(villager)) {
            plugin.getShopGUI().openMainMenu(player);
        } else if (plugin.getNpcManager().isPvpVillager(villager)) {
            plugin.getPvpGUI().openMainMenu(player);
        }
    }

    // Valore oggetto nel lore quando si tiene in mano
    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        // Non aggiornare se ha una GUI aperta
        if (ShopGUI.openGUI.containsKey(player.getUniqueId())) return;
        if (PvPGUI.openGUI.containsKey(player.getUniqueId())) return;

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            ItemStack item = player.getInventory().getItem(e.getNewSlot());
            if (item == null || item.getType().isAir()) return;
            // Solo aggiorna se ha un valore
            double buy = ItemValueRegistry.getBuyPrice(item.getType());
            double sell = ItemValueRegistry.getSellPrice(item.getType());
            if (buy <= 0 && sell <= 0) return;
            updateItemLore(player, item, e.getNewSlot());
        }, 1L);
    }

    private void updateItemLore(Player player, ItemStack item, int slot) {
        Material mat = item.getType();
        double buyPrice = ItemValueRegistry.getBuyPrice(mat);
        double sellPrice = ItemValueRegistry.getSellPrice(mat);
        if (sellPrice <= 0 && buyPrice <= 0) return;

        // Non modificare items con enchant o NBT speciali
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

        // Rimuovi vecchi valori se presenti
        lore.removeIf(line -> {
            String stripped = ChatColor.stripColor(line);
            return stripped.startsWith("Acquisto:") || stripped.startsWith("Vendita:");
        });

        if (buyPrice > 0) lore.add(ChatColor.DARK_GRAY + "Acquisto: " + ChatColor.GREEN + "$" + ShopGUI.formatPrice(buyPrice));
        if (sellPrice > 0) lore.add(ChatColor.DARK_GRAY + "Vendita: " + ChatColor.RED + "$" + ShopGUI.formatPrice(sellPrice));

        meta.setLore(lore);
        item.setItemMeta(meta);
        player.getInventory().setItem(slot, item);
    }

    // Morte in arena
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        if (!plugin.getArenaManager().isFighting(victim.getUniqueId())) return;

        e.setCancelled(true);
        e.setDeathMessage(null);
        victim.setHealth(1);
        plugin.getArenaManager().onFighterDeath(victim);
    }

    // Click GUI
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();

        String shopGui = ShopGUI.openGUI.get(uuid);
        String pvpGui = PvPGUI.openGUI.get(uuid);

        // Cancella solo se siamo in una GUI del plugin
        if (shopGui == null && pvpGui == null) return;

        // Controlla che sia l'inventario del plugin e non quello del player
        String title = e.getView().getTitle();
        if (!title.contains("Shop") && !title.contains("Compra") && !title.contains("Vendi") &&
            !title.contains("Arena") && !title.contains("Inviti") && !title.contains("Scommessa") &&
            !title.contains("avversario")) return;

        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;

        if (shopGui != null) handleShopClick(player, shopGui, e.getSlot(), e.getCurrentItem());
        if (pvpGui != null) handlePvpClick(player, pvpGui, e.getSlot(), e.getCurrentItem(), e.isRightClick());
    }

    private void handleShopClick(Player player, String gui, int slot, ItemStack clicked) {
        UUID uuid = player.getUniqueId();

        switch (gui) {
            case "main" -> {
                if (slot == 11) { player.closeInventory(); plugin.getShopGUI().openCategoryMenu(player); }
                else if (slot == 15) { player.closeInventory(); plugin.getShopGUI().openSellInventory(player); }
                else if (slot == 22) player.closeInventory();
            }
            case "categories" -> {
                ShopItem.Category cat = switch (slot) {
                    case 10 -> ShopItem.Category.BLOCCHI;
                    case 11 -> ShopItem.Category.PIETRA;
                    case 12 -> ShopItem.Category.LEGNO;
                    case 13 -> ShopItem.Category.DECORAZIONI;
                    case 14 -> ShopItem.Category.MINERALI;
                    case 19 -> ShopItem.Category.NATURA;
                    case 20 -> ShopItem.Category.NETHER;
                    case 21 -> ShopItem.Category.END;
                    case 22 -> ShopItem.Category.RARI;
                    default -> null;
                };
                if (cat != null) { player.closeInventory(); plugin.getShopGUI().openCategory(player, cat); }
                else if (slot == 36) { player.closeInventory(); plugin.getShopGUI().openMainMenu(player); }
            }
            default -> {
                if (gui.startsWith("category_")) {
                    if (slot == 49) { player.closeInventory(); plugin.getShopGUI().openCategoryMenu(player); return; }
                    String matName = getLoreHidden(clicked);
                    if (matName != null) {
                        try {
                            Material mat = Material.valueOf(matName);
                            ShopItem shopItem = plugin.getShopManager().getShopItem(mat);
                            if (shopItem != null) { player.closeInventory(); plugin.getShopGUI().openBuyMenu(player, shopItem); }
                        } catch (Exception ignored) {}
                    }
                } else if (gui.equals("buy_item")) {
                    handleBuyClick(player, slot, clicked);
                } else if (gui.equals("sell_inventory")) {
                    if (slot == 49) { player.closeInventory(); plugin.getShopGUI().openMainMenu(player); return; }
                    String matName = getLoreHidden(clicked);
                    if (matName != null) {
                        try {
                            Material mat = Material.valueOf(matName);
                            player.closeInventory();
                            plugin.getShopGUI().openSellQuantityMenu(player, mat);
                        } catch (Exception ignored) {}
                    }
                } else if (gui.startsWith("sell_qty_")) {
                    handleSellQtyClick(player, gui, slot);
                }
            }
        }
    }

    private void handleBuyClick(Player player, int slot, ItemStack clicked) {
        UUID uuid = player.getUniqueId();
        ShopItem item = ShopGUI.selectedItem.get(uuid);
        if (item == null) return;

        if (slot == 22) { player.closeInventory(); plugin.getShopGUI().openCategory(player, item.getCategory()); return; }

        boolean hasCooldown = item.isRare() && plugin.getShopManager().hasCooldown(uuid, item.getMaterial());

        if (hasCooldown && slot == 11) {
            double cost = plugin.getShopManager().getSkipCooldownCost(item.getMaterial());
            if (!plugin.getEconomy().has(player, cost)) { player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi!"); return; }
            plugin.getEconomy().withdrawPlayer(player, cost);
            plugin.getShopManager().skipCooldown(uuid, item.getMaterial());
            player.sendMessage(ChatColor.GREEN + "Cooldown saltato!");
            player.closeInventory();
            plugin.getShopGUI().openBuyMenu(player, item);
            return;
        }

        int amount = switch (slot) {
            case 10 -> 1;
            case 13 -> item.getMaxStack() == 1 ? 1 : 32;
            case 16 -> item.getMaxStack() == 1 ? 1 : 64;
            default -> 0;
        };
        if (amount == 0) return;

        double cost = item.getCurrentPrice() / 64.0 * amount;
        if (item.getMaxStack() == 1) cost = item.getCurrentPrice();

        if (!plugin.getEconomy().has(player, cost)) { player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi!"); return; }
        if (player.getInventory().firstEmpty() == -1) { player.sendMessage(ChatColor.RED + "Inventario pieno!"); return; }

        plugin.getEconomy().withdrawPlayer(player, cost);
        player.getInventory().addItem(new ItemStack(item.getMaterial(), amount));
        item.onBuy(amount);
        if (item.isRare()) plugin.getShopManager().setCooldown(uuid, item.getMaterial());
        player.sendMessage(ChatColor.GREEN + "Comprato x" + amount + " " + item.getDisplayName() + " per $" + ShopGUI.formatPrice(cost));
        plugin.getShopManager().savePrices();
    }

    private void handleSellQtyClick(Player player, String gui, int slot) {
        if (slot == 22) { player.closeInventory(); plugin.getShopGUI().openSellInventory(player); return; }

        String matName = gui.replace("sell_qty_", "");
        Material mat;
        try { mat = Material.valueOf(matName); } catch (Exception e) { return; }

        double sellPrice = ItemValueRegistry.getSellPrice(mat);
        int inInventory = countInInventory(player, mat);

        int amount = switch (slot) {
            case 9 -> 1;
            case 11 -> 16;
            case 13 -> 32;
            case 15 -> 64;
            case 17 -> inInventory;
            default -> 0;
        };
        if (amount == 0) return;
        if (inInventory < amount) { player.sendMessage(ChatColor.RED + "Non hai abbastanza oggetti!"); return; }

        double income = sellPrice * amount;
        player.getInventory().removeItem(new ItemStack(mat, amount));
        plugin.getEconomy().depositPlayer(player, income);

        // Aggiorna prezzo dinamico se è un oggetto dello shop
        ShopItem shopItem = plugin.getShopManager().getShopItem(mat);
        if (shopItem != null) { shopItem.onSell(amount); plugin.getShopManager().savePrices(); }

        player.sendMessage(ChatColor.GREEN + "Venduto x" + amount + " " + ShopGUI.formatMaterialName(mat.name()) + " per $" + ShopGUI.formatPrice(income));
        player.closeInventory();
    }

    private void handlePvpClick(Player player, String gui, int slot, ItemStack clicked, boolean rightClick) {
        UUID uuid = player.getUniqueId();
        switch (gui) {
            case "pvp_main" -> {
                if (slot == 11) { player.closeInventory(); plugin.getPvpGUI().openPlayerList(player); }
                else if (slot == 15) { player.closeInventory(); plugin.getPvpGUI().openInvites(player); }
                else if (slot == 22) player.closeInventory();
            }
            case "pvp_playerlist" -> {
                if (slot == 49) { player.closeInventory(); plugin.getPvpGUI().openMainMenu(player); return; }
                String uuidStr = getLoreHidden(clicked);
                if (uuidStr != null) {
                    try { UUID targetUUID = UUID.fromString(uuidStr); player.closeInventory(); plugin.getPvpGUI().openBetMenu(player, targetUUID); }
                    catch (Exception ignored) {}
                }
            }
            case "pvp_bet" -> {
                if (slot == 22) { player.closeInventory(); plugin.getPvpGUI().openPlayerList(player); return; }
                double bet = switch (slot) {
                    case 10 -> 100000;
                    case 11 -> 500000;
                    case 12 -> 1000000;
                    case 13 -> 5000000;
                    case 14 -> 10000000;
                    case 15 -> 50000000;
                    case 16 -> 100000000;
                    default -> 0;
                };
                if (bet > 0) {
                    UUID targetUUID = PvPGUI.selectedTarget.get(uuid);
                    if (targetUUID == null) return;
                    Player target = org.bukkit.Bukkit.getPlayer(targetUUID);
                    if (target == null) { player.sendMessage(ChatColor.RED + "Giocatore non online!"); return; }
                    if (!plugin.getEconomy().has(player, bet)) { player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi!"); return; }
                    plugin.getArenaManager().sendChallenge(player, target, bet);
                    player.closeInventory();
                    player.sendMessage(ChatColor.GREEN + "Sfida inviata!");
                }
            }
            case "pvp_invites" -> {
                if (slot == 49) { player.closeInventory(); plugin.getPvpGUI().openMainMenu(player); return; }
                String uuidStr = getLoreHidden(clicked);
                if (uuidStr != null) {
                    try {
                        UUID challengerUUID = UUID.fromString(uuidStr);
                        if (rightClick) plugin.getArenaManager().declineChallenge(player, challengerUUID);
                        else plugin.getArenaManager().acceptChallenge(player, challengerUUID);
                        player.closeInventory();
                    } catch (Exception ignored) {}
                }
            }
        }
    }

    private String getLoreHidden(ItemStack item) {
        if (item.getItemMeta() == null) return null;
        List<String> lore = item.getItemMeta().getLore();
        if (lore == null || lore.isEmpty()) return null;
        return ChatColor.stripColor(lore.get(lore.size() - 1));
    }

    private int countInInventory(Player player, Material mat) {
        int count = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.getType() == mat) count += stack.getAmount();
        }
        return count;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();
        ShopGUI.openGUI.remove(uuid);
        PvPGUI.openGUI.remove(uuid);
        ShopGUI.selectedItem.remove(uuid);
        PvPGUI.selectedTarget.remove(uuid);
    }
}
