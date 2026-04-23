package it.italiashop.managers;

import it.italiashop.ItaliaShop;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.io.File;
import java.util.UUID;

public class NPCManager {

    private final ItaliaShop plugin;
    private Villager shopVillager = null;
    private Villager pvpVillager = null;
    private Location shopLocation = null;
    private Location pvpLocation = null;
    private UUID shopUUID = null;
    private UUID pvpUUID = null;

    public NPCManager(ItaliaShop plugin) {
        this.plugin = plugin;
        load();
    }

    public void spawnShopVillager(Location loc) {
        removeShopVillager();
        shopLocation = loc.clone();
        shopVillager = spawnVillager(loc, ChatColor.GOLD + "" + ChatColor.BOLD + "Shop", Villager.Profession.CARTOGRAPHER);
        shopUUID = shopVillager.getUniqueId();
        save();
    }

    public void spawnPvpVillager(Location loc) {
        removePvpVillager();
        pvpLocation = loc.clone();
        pvpVillager = spawnVillager(loc, ChatColor.RED + "" + ChatColor.BOLD + "Arena PvP", Villager.Profession.ARMORER);
        pvpUUID = pvpVillager.getUniqueId();
        save();
    }

    public void removeShopVillager() {
        if (shopVillager != null && !shopVillager.isDead()) shopVillager.remove();
        // Cerca per UUID se riferimento perso
        if (shopUUID != null) {
            for (World w : Bukkit.getWorlds()) {
                for (Entity e : w.getEntities()) {
                    if (e.getUniqueId().equals(shopUUID)) { e.remove(); break; }
                }
            }
        }
        shopVillager = null;
        shopLocation = null;
        shopUUID = null;
        save();
    }

    public void removePvpVillager() {
        if (pvpVillager != null && !pvpVillager.isDead()) pvpVillager.remove();
        if (pvpUUID != null) {
            for (World w : Bukkit.getWorlds()) {
                for (Entity e : w.getEntities()) {
                    if (e.getUniqueId().equals(pvpUUID)) { e.remove(); break; }
                }
            }
        }
        pvpVillager = null;
        pvpLocation = null;
        pvpUUID = null;
        save();
    }

    private Villager spawnVillager(Location loc, String name, Villager.Profession profession) {
        Villager v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        v.setCustomName(name);
        v.setCustomNameVisible(true);
        v.setAI(false);
        v.setInvulnerable(true);
        v.setSilent(true);
        v.setProfession(profession);
        v.setVillagerLevel(5);
        v.setPersistent(true);
        v.setRemoveWhenFarAway(false);
        return v;
    }

    public boolean isShopVillager(Entity entity) {
        if (shopVillager != null && shopVillager.equals(entity)) return true;
        return shopUUID != null && shopUUID.equals(entity.getUniqueId());
    }

    public boolean isPvpVillager(Entity entity) {
        if (pvpVillager != null && pvpVillager.equals(entity)) return true;
        return pvpUUID != null && pvpUUID.equals(entity.getUniqueId());
    }

    public void save() {
        File file = new File(plugin.getDataFolder(), "npcs.yml");
        YamlConfiguration config = new YamlConfiguration();

        if (shopLocation != null) {
            config.set("shop.world", shopLocation.getWorld().getName());
            config.set("shop.x", shopLocation.getX());
            config.set("shop.y", shopLocation.getY());
            config.set("shop.z", shopLocation.getZ());
            config.set("shop.yaw", (double) shopLocation.getYaw());
            if (shopUUID != null) config.set("shop.uuid", shopUUID.toString());
        }

        if (pvpLocation != null) {
            config.set("pvp.world", pvpLocation.getWorld().getName());
            config.set("pvp.x", pvpLocation.getX());
            config.set("pvp.y", pvpLocation.getY());
            config.set("pvp.z", pvpLocation.getZ());
            config.set("pvp.yaw", (double) pvpLocation.getYaw());
            if (pvpUUID != null) config.set("pvp.uuid", pvpUUID.toString());
        }

        try { config.save(file); } catch (Exception e) { e.printStackTrace(); }
    }

    public void load() {
        File file = new File(plugin.getDataFolder(), "npcs.yml");
        if (!file.exists()) return;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config.contains("shop")) {
            World w = Bukkit.getWorld(config.getString("shop.world", "world"));
            if (w != null) {
                shopLocation = new Location(w,
                        config.getDouble("shop.x"), config.getDouble("shop.y"), config.getDouble("shop.z"),
                        (float) config.getDouble("shop.yaw"), 0);

                // Cerca villager esistente per UUID
                String uuidStr = config.getString("shop.uuid");
                if (uuidStr != null) {
                    try {
                        shopUUID = UUID.fromString(uuidStr);
                        // Cerca nelle entità del mondo
                        for (Entity e : w.getEntities()) {
                            if (e.getUniqueId().equals(shopUUID) && e instanceof Villager v) {
                                shopVillager = v;
                                makeNPC(v, ChatColor.GOLD + "" + ChatColor.BOLD + "Shop", Villager.Profession.CARTOGRAPHER);
                                return;
                            }
                        }
                    } catch (Exception ignored) {}
                }
                // Se non trovato, spawna nuovo
                spawnShopVillager(shopLocation);
            }
        }

        if (config.contains("pvp")) {
            World w = Bukkit.getWorld(config.getString("pvp.world", "world"));
            if (w != null) {
                pvpLocation = new Location(w,
                        config.getDouble("pvp.x"), config.getDouble("pvp.y"), config.getDouble("pvp.z"),
                        (float) config.getDouble("pvp.yaw"), 0);

                String uuidStr = config.getString("pvp.uuid");
                if (uuidStr != null) {
                    try {
                        pvpUUID = UUID.fromString(uuidStr);
                        for (Entity e : w.getEntities()) {
                            if (e.getUniqueId().equals(pvpUUID) && e instanceof Villager v) {
                                pvpVillager = v;
                                makeNPC(v, ChatColor.RED + "" + ChatColor.BOLD + "Arena PvP", Villager.Profession.ARMORER);
                                return;
                            }
                        }
                    } catch (Exception ignored) {}
                }
                spawnPvpVillager(pvpLocation);
            }
        }
    }

    private void makeNPC(Villager v, String name, Villager.Profession profession) {
        v.setCustomName(name);
        v.setCustomNameVisible(true);
        v.setAI(false);
        v.setInvulnerable(true);
        v.setSilent(true);
        v.setProfession(profession);
        v.setVillagerLevel(5);
        v.setPersistent(true);
        v.setRemoveWhenFarAway(false);
    }
}
