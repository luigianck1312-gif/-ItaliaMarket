package it.italiashop;

import it.italiashop.commands.NPCCommand;
import it.italiashop.gui.PvPGUI;
import it.italiashop.gui.ShopGUI;
import it.italiashop.listeners.ShopListener;
import it.italiashop.managers.ArenaManager;
import it.italiashop.managers.NPCManager;
import it.italiashop.managers.ShopManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ItaliaShop extends JavaPlugin {

    private ShopManager shopManager;
    private ArenaManager arenaManager;
    private NPCManager npcManager;
    private ShopGUI shopGUI;
    private PvPGUI pvpGUI;
    private Economy economy;

    @Override
    public void onEnable() {
        getLogger().info("ItaliaShop avviato!");
        getDataFolder().mkdirs();

        if (!setupEconomy()) {
            getLogger().severe("Vault non trovato! ItaliaShop si disabilita.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        shopManager = new ShopManager(this);
        arenaManager = new ArenaManager(this);
        shopGUI = new ShopGUI(this);
        pvpGUI = new PvPGUI(this);
        npcManager = new NPCManager(this);

        getCommand("npc").setExecutor(new NPCCommand(this));
        getServer().getPluginManager().registerEvents(new ShopListener(this), this);

        getLogger().info("ItaliaShop caricato con successo!");
    }

    @Override
    public void onDisable() {
        if (shopManager != null) shopManager.savePrices();
        if (npcManager != null) npcManager.save();
        getLogger().info("ItaliaShop disattivato!");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return economy != null;
    }

    public ShopManager getShopManager() { return shopManager; }
    public ArenaManager getArenaManager() { return arenaManager; }
    public NPCManager getNpcManager() { return npcManager; }
    public ShopGUI getShopGUI() { return shopGUI; }
    public PvPGUI getPvpGUI() { return pvpGUI; }
    public Economy getEconomy() { return economy; }
}
