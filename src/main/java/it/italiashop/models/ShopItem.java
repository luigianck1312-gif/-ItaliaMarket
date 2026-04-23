package it.italiashop.models;

import org.bukkit.Material;

public class ShopItem {

    public enum Category {
        BLOCCHI, PIETRA, LEGNO, DECORAZIONI, MINERALI, NATURA, NETHER, END, RARI
    }

    private final Material material;
    private final String displayName;
    private final Category category;
    private final double basePrice;
    private final double baseSellPrice;
    private final double minPrice;
    private final double maxPrice;
    private double currentPrice;
    private double currentSellPrice;
    private int buyCount;
    private int sellCount;
    private final boolean rare;
    private final int cooldownDays;
    private final int maxStack;
    private final boolean buyable;

    public ShopItem(Material material, String displayName, Category category,
                    double basePrice, double baseSellPrice,
                    double minPrice, double maxPrice,
                    boolean rare, int cooldownDays, int maxStack, boolean buyable) {
        this.material = material;
        this.displayName = displayName;
        this.category = category;
        this.basePrice = basePrice;
        this.baseSellPrice = baseSellPrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.currentPrice = basePrice;
        this.currentSellPrice = baseSellPrice;
        this.rare = rare;
        this.cooldownDays = cooldownDays;
        this.maxStack = maxStack;
        this.buyable = buyable;
    }

    public void onBuy(int amount) {
        buyCount += amount;
        double factor = 1.0 + (buyCount / 64.0) * 0.02;
        currentPrice = Math.min(basePrice * factor, maxPrice);
        currentSellPrice = Math.min(baseSellPrice * (1.0 + (buyCount / 64.0) * 0.01), baseSellPrice * 1.5);
    }

    public void onSell(int amount) {
        sellCount += amount;
        double factor = 1.0 - (sellCount / 64.0) * 0.02;
        currentPrice = Math.max(basePrice * factor, minPrice);
        currentSellPrice = Math.max(baseSellPrice * (1.0 - (sellCount / 64.0) * 0.01), baseSellPrice * 0.3);
    }

    public void resetPrices() {
        buyCount = 0;
        sellCount = 0;
        currentPrice = basePrice;
        currentSellPrice = baseSellPrice;
    }

    public Material getMaterial() { return material; }
    public String getDisplayName() { return displayName; }
    public Category getCategory() { return category; }
    public double getCurrentPrice() { return currentPrice; }
    public double getCurrentSellPrice() { return currentSellPrice; }
    public double getMinPrice() { return minPrice; }
    public double getMaxPrice() { return maxPrice; }
    public boolean isRare() { return rare; }
    public int getCooldownDays() { return cooldownDays; }
    public int getMaxStack() { return maxStack; }
    public boolean isBuyable() { return buyable; }
}
