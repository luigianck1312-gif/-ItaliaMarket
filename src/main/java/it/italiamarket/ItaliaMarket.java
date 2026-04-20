package it.italiamarket;

import it.italiamarket.commands.MarketCommand;
import it.italiamarket.gui.MarketGUI;
import it.italiamarket.listeners.MarketListener;
import it.italiamarket.managers.MarketManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ItaliaMarket extends JavaPlugin {

    private MarketManager marketManager;
    private MarketGUI marketGUI;
    private Economy economy;

    @Override
    public void onEnable() {
        getLogger().info("ItaliaMarket avviato!");
        getDataFolder().mkdirs();

        if (!setupEconomy()) {
            getLogger().severe("Vault non trovato! ItaliaMarket si disabilita.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        marketManager = new MarketManager(this);
        marketGUI = new MarketGUI(this);

        getCommand("market").setExecutor(new MarketCommand(this));
        getServer().getPluginManager().registerEvents(new MarketListener(this), this);

        getLogger().info("ItaliaMarket caricato con successo!");
    }

    @Override
    public void onDisable() {
        if (marketManager != null) marketManager.save();
        getLogger().info("ItaliaMarket disattivato!");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return economy != null;
    }

    public MarketManager getMarketManager() { return marketManager; }
    public MarketGUI getMarketGUI() { return marketGUI; }
    public Economy getEconomy() { return economy; }
}
