package it.italiamarket.models;

import org.bukkit.inventory.ItemStack;
import java.util.UUID;

public class MarketListing {

    private final String id;
    private final UUID sellerUUID;
    private final String sellerName;
    private final ItemStack item;
    private final double price;
    private final long timestamp;

    public MarketListing(String id, UUID sellerUUID, String sellerName, ItemStack item, double price) {
        this.id = id;
        this.sellerUUID = sellerUUID;
        this.sellerName = sellerName;
        this.item = item.clone();
        this.price = price;
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() { return id; }
    public UUID getSellerUUID() { return sellerUUID; }
    public String getSellerName() { return sellerName; }
    public ItemStack getItem() { return item.clone(); }
    public double getPrice() { return price; }
    public long getTimestamp() { return timestamp; }
}
