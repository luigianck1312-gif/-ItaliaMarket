package it.italiashop.managers;

import it.italiashop.ItaliaShop;
import it.italiashop.models.ShopItem;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class ShopManager {

    private final ItaliaShop plugin;
    private final List<ShopItem> items = new ArrayList<>();
    private final Map<UUID, Map<Material, Long>> cooldowns = new HashMap<>();

    public ShopManager(ItaliaShop plugin) {
        this.plugin = plugin;
        registerItems();
        loadPrices();
        long threeDays = 20L * 60 * 60 * 24 * 3;
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::resetAllPrices, threeDays, threeDays);
    }

    private void add(Material mat, String name, ShopItem.Category cat,
                     double buyPrice, double sellPrice, double minPrice, double maxPrice,
                     boolean rare, int cooldown, int maxStack) {
        // Prezzo vendita sempre al 60% del prezzo acquisto
        double actualSellPrice = buyPrice * 0.6;
        double actualMinPrice = minPrice;
        items.add(new ShopItem(mat, name, cat, buyPrice, actualSellPrice, actualMinPrice, maxPrice, rare, cooldown, maxStack, true));
    }

    private void addSellOnly(Material mat, String name, ShopItem.Category cat, double sellPrice) {
        items.add(new ShopItem(mat, name, cat, -1, sellPrice, sellPrice * 0.3, sellPrice * 1.5, false, 0, 64, false));
    }

    private void registerItems() {
        // ============================================================
        // BLOCCHI BASE
        // ============================================================
        add(Material.DIRT, "Terra", ShopItem.Category.BLOCCHI, 200000, 80000, 50000, 500000, false, 0, 64);
        add(Material.GRASS_BLOCK, "Erba", ShopItem.Category.BLOCCHI, 250000, 100000, 60000, 600000, false, 0, 64);
        add(Material.COARSE_DIRT, "Terra Grezza", ShopItem.Category.BLOCCHI, 200000, 80000, 50000, 500000, false, 0, 64);
        add(Material.PODZOL, "Podzol", ShopItem.Category.BLOCCHI, 256000, 102400, 64000, 640000, false, 0, 64);
        add(Material.ROOTED_DIRT, "Terra con Radici", ShopItem.Category.BLOCCHI, 224000, 89600, 56000, 560000, false, 0, 64);
        add(Material.MUD, "Fango", ShopItem.Category.BLOCCHI, 200000, 80000, 50000, 500000, false, 0, 64);
        add(Material.PACKED_MUD, "Fango Compatto", ShopItem.Category.BLOCCHI, 384000, 153600, 96000, 960000, false, 0, 64);
        add(Material.MUD_BRICKS, "Mattoni di Fango", ShopItem.Category.BLOCCHI, 448000, 179200, 112000, 1120000, false, 0, 64);
        add(Material.MUD_BRICK_STAIRS, "Scale Mattoni Fango", ShopItem.Category.BLOCCHI, 268800, 107520, 67200, 672000, false, 0, 64);
        add(Material.MUD_BRICK_SLAB, "Lastra Mattoni Fango", ShopItem.Category.BLOCCHI, 134400, 53760, 33600, 336000, false, 0, 64);
        add(Material.MUD_BRICK_WALL, "Muro Mattoni Fango", ShopItem.Category.BLOCCHI, 224000, 89600, 56000, 560000, false, 0, 64);
        add(Material.SAND, "Sabbia", ShopItem.Category.BLOCCHI, 300000, 120000, 70000, 700000, false, 0, 64);
        add(Material.RED_SAND, "Sabbia Rossa", ShopItem.Category.BLOCCHI, 320000, 128000, 80000, 800000, false, 0, 64);
        add(Material.GRAVEL, "Ghiaia", ShopItem.Category.BLOCCHI, 350000, 130000, 80000, 750000, false, 0, 64);
        add(Material.CLAY, "Argilla", ShopItem.Category.BLOCCHI, 600000, 240000, 150000, 1500000, false, 0, 64);
        add(Material.CLAY_BALL, "Pallina Argilla", ShopItem.Category.BLOCCHI, 150000, 60000, 37500, 375000, false, 0, 64);
        add(Material.ICE, "Ghiaccio", ShopItem.Category.BLOCCHI, 500000, 200000, 100000, 1000000, false, 0, 64);
        add(Material.PACKED_ICE, "Ghiaccio Compatto", ShopItem.Category.BLOCCHI, 1000000, 400000, 200000, 2000000, false, 0, 64);
        add(Material.BLUE_ICE, "Ghiaccio Blu", ShopItem.Category.BLOCCHI, 2000000, 800000, 400000, 4000000, false, 0, 64);
        add(Material.SNOW_BLOCK, "Blocco Neve", ShopItem.Category.BLOCCHI, 400000, 160000, 80000, 800000, false, 0, 64);
        add(Material.SNOW, "Neve", ShopItem.Category.BLOCCHI, 128000, 51200, 32000, 320000, false, 0, 64);
        add(Material.OBSIDIAN, "Ossidiana", ShopItem.Category.BLOCCHI, 5000000, 2000000, 1000000, 10000000, false, 0, 64);
        add(Material.CRYING_OBSIDIAN, "Ossidiana Piangente", ShopItem.Category.BLOCCHI, 6400000, 2560000, 1280000, 12800000, false, 0, 64);
        add(Material.COBWEB, "Ragnatela", ShopItem.Category.BLOCCHI, 512000, 204800, 128000, 1280000, false, 0, 64);
        add(Material.SPONGE, "Spugna", ShopItem.Category.BLOCCHI, 3200000, 1280000, 640000, 6400000, false, 0, 64);

        // PIETRA E DERIVATI
        add(Material.STONE, "Pietra", ShopItem.Category.PIETRA, 500000, 200000, 100000, 1000000, false, 0, 64);
        add(Material.COBBLESTONE, "Ciottoli", ShopItem.Category.PIETRA, 400000, 150000, 80000, 800000, false, 0, 64);
        add(Material.MOSSY_COBBLESTONE, "Ciottoli Muschiati", ShopItem.Category.PIETRA, 512000, 204800, 102400, 1024000, false, 0, 64);
        add(Material.SMOOTH_STONE, "Pietra Liscia", ShopItem.Category.PIETRA, 576000, 230400, 115200, 1152000, false, 0, 64);
        add(Material.STONE_BRICKS, "Mattoni Pietra", ShopItem.Category.PIETRA, 640000, 256000, 128000, 1280000, false, 0, 64);
        add(Material.MOSSY_STONE_BRICKS, "Mattoni Pietra Muschiati", ShopItem.Category.PIETRA, 768000, 307200, 153600, 1536000, false, 0, 64);
        add(Material.CRACKED_STONE_BRICKS, "Mattoni Pietra Incrinati", ShopItem.Category.PIETRA, 704000, 281600, 140800, 1408000, false, 0, 64);
        add(Material.CHISELED_STONE_BRICKS, "Mattoni Pietra Cesellati", ShopItem.Category.PIETRA, 768000, 307200, 153600, 1536000, false, 0, 64);
        add(Material.STONE_STAIRS, "Scale Pietra", ShopItem.Category.PIETRA, 300000, 120000, 60000, 600000, false, 0, 64);
        add(Material.STONE_SLAB, "Lastra Pietra", ShopItem.Category.PIETRA, 150000, 60000, 30000, 300000, false, 0, 64);
        add(Material.STONE_BRICK_STAIRS, "Scale Mattoni Pietra", ShopItem.Category.PIETRA, 384000, 153600, 76800, 768000, false, 0, 64);
        add(Material.STONE_BRICK_SLAB, "Lastra Mattoni Pietra", ShopItem.Category.PIETRA, 192000, 76800, 38400, 384000, false, 0, 64);
        add(Material.STONE_BRICK_WALL, "Muro Mattoni Pietra", ShopItem.Category.PIETRA, 320000, 128000, 64000, 640000, false, 0, 64);
        add(Material.COBBLESTONE_STAIRS, "Scale Ciottoli", ShopItem.Category.PIETRA, 240000, 96000, 48000, 480000, false, 0, 64);
        add(Material.COBBLESTONE_SLAB, "Lastra Ciottoli", ShopItem.Category.PIETRA, 120000, 48000, 24000, 240000, false, 0, 64);
        add(Material.COBBLESTONE_WALL, "Muro Ciottoli", ShopItem.Category.PIETRA, 200000, 80000, 40000, 400000, false, 0, 64);
        add(Material.BRICKS, "Mattoni", ShopItem.Category.PIETRA, 1000000, 400000, 200000, 2000000, false, 0, 64);
        add(Material.BRICK_STAIRS, "Scale Mattoni", ShopItem.Category.PIETRA, 600000, 240000, 120000, 1200000, false, 0, 64);
        add(Material.BRICK_SLAB, "Lastra Mattoni", ShopItem.Category.PIETRA, 300000, 120000, 60000, 600000, false, 0, 64);
        add(Material.BRICK_WALL, "Muro Mattoni", ShopItem.Category.PIETRA, 500000, 200000, 100000, 1000000, false, 0, 64);
        add(Material.SANDSTONE, "Arenaria", ShopItem.Category.PIETRA, 448000, 179200, 89600, 896000, false, 0, 64);
        add(Material.SMOOTH_SANDSTONE, "Arenaria Liscia", ShopItem.Category.PIETRA, 492800, 197120, 98560, 985600, false, 0, 64);
        add(Material.CHISELED_SANDSTONE, "Arenaria Cesellata", ShopItem.Category.PIETRA, 537600, 215040, 107520, 1075200, false, 0, 64);
        add(Material.CUT_SANDSTONE, "Arenaria Tagliata", ShopItem.Category.PIETRA, 492800, 197120, 98560, 985600, false, 0, 64);
        add(Material.SANDSTONE_STAIRS, "Scale Arenaria", ShopItem.Category.PIETRA, 268800, 107520, 53760, 537600, false, 0, 64);
        add(Material.SANDSTONE_SLAB, "Lastra Arenaria", ShopItem.Category.PIETRA, 134400, 53760, 26880, 268800, false, 0, 64);
        add(Material.SANDSTONE_WALL, "Muro Arenaria", ShopItem.Category.PIETRA, 224000, 89600, 44800, 448000, false, 0, 64);
        add(Material.RED_SANDSTONE, "Arenaria Rossa", ShopItem.Category.PIETRA, 448000, 179200, 89600, 896000, false, 0, 64);
        add(Material.RED_SANDSTONE_STAIRS, "Scale Arenaria Rossa", ShopItem.Category.PIETRA, 268800, 107520, 53760, 537600, false, 0, 64);
        add(Material.RED_SANDSTONE_SLAB, "Lastra Arenaria Rossa", ShopItem.Category.PIETRA, 134400, 53760, 26880, 268800, false, 0, 64);
        add(Material.RED_SANDSTONE_WALL, "Muro Arenaria Rossa", ShopItem.Category.PIETRA, 224000, 89600, 44800, 448000, false, 0, 64);
        add(Material.ANDESITE, "Andesite", ShopItem.Category.PIETRA, 320000, 128000, 64000, 640000, false, 0, 64);
        add(Material.POLISHED_ANDESITE, "Andesite Levigata", ShopItem.Category.PIETRA, 384000, 153600, 76800, 768000, false, 0, 64);
        add(Material.ANDESITE_STAIRS, "Scale Andesite", ShopItem.Category.PIETRA, 192000, 76800, 38400, 384000, false, 0, 64);
        add(Material.ANDESITE_SLAB, "Lastra Andesite", ShopItem.Category.PIETRA, 96000, 38400, 19200, 192000, false, 0, 64);
        add(Material.ANDESITE_WALL, "Muro Andesite", ShopItem.Category.PIETRA, 160000, 64000, 32000, 320000, false, 0, 64);
        add(Material.DIORITE, "Diorite", ShopItem.Category.PIETRA, 320000, 128000, 64000, 640000, false, 0, 64);
        add(Material.POLISHED_DIORITE, "Diorite Levigata", ShopItem.Category.PIETRA, 384000, 153600, 76800, 768000, false, 0, 64);
        add(Material.DIORITE_STAIRS, "Scale Diorite", ShopItem.Category.PIETRA, 192000, 76800, 38400, 384000, false, 0, 64);
        add(Material.DIORITE_SLAB, "Lastra Diorite", ShopItem.Category.PIETRA, 96000, 38400, 19200, 192000, false, 0, 64);
        add(Material.DIORITE_WALL, "Muro Diorite", ShopItem.Category.PIETRA, 160000, 64000, 32000, 320000, false, 0, 64);
        add(Material.GRANITE, "Granito", ShopItem.Category.PIETRA, 320000, 128000, 64000, 640000, false, 0, 64);
        add(Material.POLISHED_GRANITE, "Granito Levigato", ShopItem.Category.PIETRA, 384000, 153600, 76800, 768000, false, 0, 64);
        add(Material.GRANITE_STAIRS, "Scale Granito", ShopItem.Category.PIETRA, 192000, 76800, 38400, 384000, false, 0, 64);
        add(Material.GRANITE_SLAB, "Lastra Granito", ShopItem.Category.PIETRA, 96000, 38400, 19200, 192000, false, 0, 64);
        add(Material.GRANITE_WALL, "Muro Granito", ShopItem.Category.PIETRA, 160000, 64000, 32000, 320000, false, 0, 64);
        add(Material.DEEPSLATE, "Deepslate", ShopItem.Category.PIETRA, 384000, 153600, 76800, 768000, false, 0, 64);
        add(Material.COBBLED_DEEPSLATE, "Deepslate Ciottolose", ShopItem.Category.PIETRA, 345600, 138240, 69120, 691200, false, 0, 64);
        add(Material.POLISHED_DEEPSLATE, "Deepslate Levigata", ShopItem.Category.PIETRA, 422400, 168960, 84480, 844800, false, 0, 64);
        add(Material.DEEPSLATE_BRICKS, "Mattoni Deepslate", ShopItem.Category.PIETRA, 460800, 184320, 92160, 921600, false, 0, 64);
        add(Material.DEEPSLATE_TILES, "Piastrelle Deepslate", ShopItem.Category.PIETRA, 499200, 199680, 99840, 998400, false, 0, 64);
        add(Material.CHISELED_DEEPSLATE, "Deepslate Cesellata", ShopItem.Category.PIETRA, 499200, 199680, 99840, 998400, false, 0, 64);
        add(Material.COBBLED_DEEPSLATE_STAIRS, "Scale Deepslate", ShopItem.Category.PIETRA, 207360, 82944, 41472, 414720, false, 0, 64);
        add(Material.COBBLED_DEEPSLATE_SLAB, "Lastra Deepslate", ShopItem.Category.PIETRA, 103680, 41472, 20736, 207360, false, 0, 64);
        add(Material.COBBLED_DEEPSLATE_WALL, "Muro Deepslate", ShopItem.Category.PIETRA, 172800, 69120, 34560, 345600, false, 0, 64);
        add(Material.TUFF, "Tuff", ShopItem.Category.PIETRA, 256000, 102400, 51200, 512000, false, 0, 64);
        add(Material.POLISHED_TUFF, "Tuff Levigato", ShopItem.Category.PIETRA, 307200, 122880, 61440, 614400, false, 0, 64);
        add(Material.TUFF_BRICKS, "Mattoni Tuff", ShopItem.Category.PIETRA, 358400, 143360, 71680, 716800, false, 0, 64);
        add(Material.TUFF_STAIRS, "Scale Tuff", ShopItem.Category.PIETRA, 153600, 61440, 30720, 307200, false, 0, 64);
        add(Material.TUFF_SLAB, "Lastra Tuff", ShopItem.Category.PIETRA, 76800, 30720, 15360, 153600, false, 0, 64);
        add(Material.TUFF_WALL, "Muro Tuff", ShopItem.Category.PIETRA, 128000, 51200, 25600, 256000, false, 0, 64);
        add(Material.BASALT, "Basalto", ShopItem.Category.PIETRA, 320000, 128000, 64000, 640000, false, 0, 64);
        add(Material.SMOOTH_BASALT, "Basalto Liscio", ShopItem.Category.PIETRA, 384000, 153600, 76800, 768000, false, 0, 64);
        add(Material.POLISHED_BASALT, "Basalto Levigato", ShopItem.Category.PIETRA, 384000, 153600, 76800, 768000, false, 0, 64);
        add(Material.CALCITE, "Calcite", ShopItem.Category.PIETRA, 256000, 102400, 51200, 512000, false, 0, 64);
        add(Material.BLACKSTONE, "Blackstone", ShopItem.Category.PIETRA, 384000, 153600, 76800, 768000, false, 0, 64);
        add(Material.GILDED_BLACKSTONE, "Blackstone Dorata", ShopItem.Category.PIETRA, 768000, 307200, 153600, 1536000, false, 0, 64);
        add(Material.POLISHED_BLACKSTONE, "Blackstone Levigata", ShopItem.Category.PIETRA, 422400, 168960, 84480, 844800, false, 0, 64);
        add(Material.POLISHED_BLACKSTONE_BRICKS, "Mattoni Blackstone", ShopItem.Category.PIETRA, 460800, 184320, 92160, 921600, false, 0, 64);
        add(Material.BLACKSTONE_STAIRS, "Scale Blackstone", ShopItem.Category.PIETRA, 230400, 92160, 46080, 460800, false, 0, 64);
        add(Material.BLACKSTONE_SLAB, "Lastra Blackstone", ShopItem.Category.PIETRA, 115200, 46080, 23040, 230400, false, 0, 64);
        add(Material.BLACKSTONE_WALL, "Muro Blackstone", ShopItem.Category.PIETRA, 192000, 76800, 38400, 384000, false, 0, 64);
        add(Material.PRISMARINE, "Prismarina", ShopItem.Category.PIETRA, 960000, 384000, 192000, 1920000, false, 0, 64);
        add(Material.PRISMARINE_BRICKS, "Mattoni Prismarina", ShopItem.Category.PIETRA, 1248000, 499200, 249600, 2496000, false, 0, 64);
        add(Material.DARK_PRISMARINE, "Prismarina Scura", ShopItem.Category.PIETRA, 1536000, 614400, 307200, 3072000, false, 0, 64);
        add(Material.SEA_LANTERN, "Lanterna Marina", ShopItem.Category.PIETRA, 1920000, 768000, 384000, 3840000, false, 0, 64);
        add(Material.PRISMARINE_STAIRS, "Scale Prismarina", ShopItem.Category.PIETRA, 576000, 230400, 115200, 1152000, false, 0, 64);
        add(Material.PRISMARINE_SLAB, "Lastra Prismarina", ShopItem.Category.PIETRA, 288000, 115200, 57600, 576000, false, 0, 64);

        // LEGNO
        String[] woodTypes = {"OAK", "BIRCH", "SPRUCE", "JUNGLE", "ACACIA", "DARK_OAK", "MANGROVE", "CHERRY"};
        String[] woodNames = {"Quercia", "Betulla", "Abete", "Giungla", "Acacia", "Rovere", "Mangrovia", "Ciliegio"};
        double[] woodBase = {800000, 800000, 800000, 900000, 850000, 850000, 850000, 900000};

        for (int i = 0; i < woodTypes.length; i++) {
            double log = woodBase[i];
            double plank = log * 0.75;
            String w = woodTypes[i];
            String n = woodNames[i];
            try { add(Material.valueOf(w + "_LOG"), "Tronco " + n, ShopItem.Category.LEGNO, log, log*0.4, log*0.2, log*2, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_WOOD"), "Legno " + n, ShopItem.Category.LEGNO, log*0.9, log*0.36, log*0.18, log*1.8, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf("STRIPPED_" + w + "_LOG"), "Tronco Scortecciato " + n, ShopItem.Category.LEGNO, log*0.85, log*0.34, log*0.17, log*1.7, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_PLANKS"), "Assi " + n, ShopItem.Category.LEGNO, plank, plank*0.4, plank*0.2, plank*2, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_STAIRS"), "Scale " + n, ShopItem.Category.LEGNO, plank*0.6, plank*0.24, plank*0.12, plank*1.2, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_SLAB"), "Lastra " + n, ShopItem.Category.LEGNO, plank*0.3, plank*0.12, plank*0.06, plank*0.6, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_FENCE"), "Staccionata " + n, ShopItem.Category.LEGNO, plank*0.5, plank*0.2, plank*0.1, plank*1, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_FENCE_GATE"), "Cancello " + n, ShopItem.Category.LEGNO, plank*0.8, plank*0.32, plank*0.16, plank*1.6, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_DOOR"), "Porta " + n, ShopItem.Category.LEGNO, plank*0.8, plank*0.32, plank*0.16, plank*1.6, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_TRAPDOOR"), "Botola " + n, ShopItem.Category.LEGNO, plank*0.8, plank*0.32, plank*0.16, plank*1.6, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_PRESSURE_PLATE"), "Piattaforma " + n, ShopItem.Category.LEGNO, plank*0.4, plank*0.16, plank*0.08, plank*0.8, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_BUTTON"), "Pulsante " + n, ShopItem.Category.LEGNO, plank*0.3, plank*0.12, plank*0.06, plank*0.6, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_SIGN"), "Cartello " + n, ShopItem.Category.LEGNO, plank*0.5, plank*0.2, plank*0.1, plank*1, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_LEAVES"), "Foglie " + n, ShopItem.Category.LEGNO, 192000, 76800, 38400, 384000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(w + "_SAPLING"), "Germoglio " + n, ShopItem.Category.LEGNO, 320000, 128000, 64000, 640000, false, 0, 64); } catch (Exception ignored) {}
        }

        // Bamboo
        add(Material.BAMBOO, "Bambu", ShopItem.Category.LEGNO, 250000, 100000, 50000, 600000, false, 0, 64);
        add(Material.BAMBOO_BLOCK, "Blocco Bambu", ShopItem.Category.LEGNO, 640000, 256000, 128000, 1280000, false, 0, 64);
        add(Material.BAMBOO_PLANKS, "Assi Bambu", ShopItem.Category.LEGNO, 480000, 192000, 96000, 960000, false, 0, 64);
        add(Material.BAMBOO_MOSAIC, "Mosaico Bambu", ShopItem.Category.LEGNO, 576000, 230400, 115200, 1152000, false, 0, 64);
        add(Material.BAMBOO_STAIRS, "Scale Bambu", ShopItem.Category.LEGNO, 288000, 115200, 57600, 576000, false, 0, 64);
        add(Material.BAMBOO_SLAB, "Lastra Bambu", ShopItem.Category.LEGNO, 144000, 57600, 28800, 288000, false, 0, 64);
        add(Material.BAMBOO_FENCE, "Staccionata Bambu", ShopItem.Category.LEGNO, 240000, 96000, 48000, 480000, false, 0, 64);
        add(Material.BAMBOO_DOOR, "Porta Bambu", ShopItem.Category.LEGNO, 384000, 153600, 76800, 768000, false, 0, 64);
        add(Material.BAMBOO_TRAPDOOR, "Botola Bambu", ShopItem.Category.LEGNO, 384000, 153600, 76800, 768000, false, 0, 64);

        // DECORAZIONI
        String[] colors = {"WHITE", "ORANGE", "MAGENTA", "LIGHT_BLUE", "YELLOW", "LIME", "PINK",
                "GRAY", "LIGHT_GRAY", "CYAN", "PURPLE", "BLUE", "BROWN", "GREEN", "RED", "BLACK"};
        String[] colorNames = {"Bianca", "Arancione", "Magenta", "Azzurra", "Gialla", "Verde Lime", "Rosa",
                "Grigia", "Grigio Chiaro", "Ciano", "Viola", "Blu", "Marrone", "Verde", "Rossa", "Nera"};

        for (int i = 0; i < colors.length; i++) {
            String c = colors[i];
            String cn = colorNames[i];
            try { add(Material.valueOf(c + "_WOOL"), "Lana " + cn, ShopItem.Category.DECORAZIONI, 500000, 200000, 100000, 1000000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_CARPET"), "Tappeto " + cn, ShopItem.Category.DECORAZIONI, 200000, 80000, 40000, 400000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_CONCRETE"), "Calcestruzzo " + cn, ShopItem.Category.DECORAZIONI, 512000, 204800, 102400, 1024000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_CONCRETE_POWDER"), "Polvere Calcestruzzo " + cn, ShopItem.Category.DECORAZIONI, 448000, 179200, 89600, 896000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_TERRACOTTA"), "Terracotta " + cn, ShopItem.Category.DECORAZIONI, 700000, 280000, 140000, 1400000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_GLAZED_TERRACOTTA"), "Terracotta Smaltata " + cn, ShopItem.Category.DECORAZIONI, 832000, 332800, 166400, 1664000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_STAINED_GLASS"), "Vetro " + cn, ShopItem.Category.DECORAZIONI, 704000, 281600, 140800, 1408000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_STAINED_GLASS_PANE"), "Pannello Vetro " + cn, ShopItem.Category.DECORAZIONI, 256000, 102400, 51200, 512000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_BED"), "Letto " + cn, ShopItem.Category.DECORAZIONI, 960000, 384000, 192000, 1920000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_BANNER"), "Stendardo " + cn, ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 2560000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_DYE"), "Colorante " + cn, ShopItem.Category.DECORAZIONI, 192000, 76800, 38400, 384000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_CANDLE"), "Candela " + cn, ShopItem.Category.DECORAZIONI, 320000, 128000, 64000, 640000, false, 0, 64); } catch (Exception ignored) {}
            try { add(Material.valueOf(c + "_SHULKER_BOX"), "Shulker Box " + cn, ShopItem.Category.DECORAZIONI, 32000000, 12800000, 6400000, 64000000, false, 0, 1); } catch (Exception ignored) {}
        }

        add(Material.TERRACOTTA, "Terracotta", ShopItem.Category.DECORAZIONI, 700000, 280000, 140000, 1400000, false, 0, 64);
        add(Material.GLASS, "Vetro", ShopItem.Category.DECORAZIONI, 700000, 280000, 140000, 1400000, false, 0, 64);
        add(Material.GLASS_PANE, "Pannello Vetro", ShopItem.Category.DECORAZIONI, 256000, 102400, 51200, 512000, false, 0, 64);
        add(Material.TINTED_GLASS, "Vetro Tinto", ShopItem.Category.DECORAZIONI, 960000, 384000, 192000, 1920000, false, 0, 64);
        add(Material.CANDLE, "Candela", ShopItem.Category.DECORAZIONI, 320000, 128000, 64000, 640000, false, 0, 64);
        add(Material.SHULKER_BOX, "Shulker Box", ShopItem.Category.DECORAZIONI, 32000000, 12800000, 6400000, 64000000, false, 0, 1);
        add(Material.FLOWER_POT, "Vaso", ShopItem.Category.DECORAZIONI, 192000, 76800, 38400, 384000, false, 0, 64);
        add(Material.ITEM_FRAME, "Cornice", ShopItem.Category.DECORAZIONI, 320000, 128000, 64000, 640000, false, 0, 64);
        add(Material.GLOW_ITEM_FRAME, "Cornice Luminosa", ShopItem.Category.DECORAZIONI, 640000, 256000, 128000, 1280000, false, 0, 64);
        add(Material.PAINTING, "Dipinto", ShopItem.Category.DECORAZIONI, 320000, 128000, 64000, 640000, false, 0, 64);
        add(Material.ARMOR_STAND, "Espositore Armatura", ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 2560000, false, 0, 64);
        add(Material.BOOKSHELF, "Libreria", ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 2560000, false, 0, 64);
        add(Material.CHISELED_BOOKSHELF, "Libreria Cesellata", ShopItem.Category.DECORAZIONI, 1920000, 768000, 384000, 3840000, false, 0, 64);
        add(Material.DECORATED_POT, "Vaso Decorato", ShopItem.Category.DECORAZIONI, 640000, 256000, 128000, 1280000, false, 0, 64);
        add(Material.TORCH, "Torcia", ShopItem.Category.DECORAZIONI, 128000, 51200, 25600, 256000, false, 0, 64);
        add(Material.SOUL_TORCH, "Torcia Anima", ShopItem.Category.DECORAZIONI, 192000, 76800, 38400, 384000, false, 0, 64);
        add(Material.LANTERN, "Lanterna", ShopItem.Category.DECORAZIONI, 320000, 128000, 64000, 640000, false, 0, 64);
        add(Material.SOUL_LANTERN, "Lanterna Anima", ShopItem.Category.DECORAZIONI, 384000, 153600, 76800, 768000, false, 0, 64);
        add(Material.END_ROD, "Bastone End", ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 2560000, false, 0, 64);
        add(Material.CAMPFIRE, "Falò", ShopItem.Category.DECORAZIONI, 640000, 256000, 128000, 1280000, false, 0, 64);
        add(Material.SOUL_CAMPFIRE, "Falò Anima", ShopItem.Category.DECORAZIONI, 960000, 384000, 192000, 1920000, false, 0, 64);
        add(Material.COBWEB, "Ragnatela", ShopItem.Category.DECORAZIONI, 512000, 204800, 102400, 1024000, false, 0, 64);
        add(Material.AMETHYST_BLOCK, "Blocco Ametista", ShopItem.Category.DECORAZIONI, 16000000, 6400000, 3200000, 32000000, false, 0, 64);
        add(Material.AMETHYST_SHARD, "Frammento Ametista", ShopItem.Category.DECORAZIONI, 4000000, 1600000, 800000, 8000000, false, 0, 64);

        // MINERALI
        add(Material.COAL, "Carbone", ShopItem.Category.MINERALI, 1000000, 400000, 200000, 3000000, false, 0, 64);
        add(Material.COAL_ORE, "Minerale Carbone", ShopItem.Category.MINERALI, 1200000, 480000, 240000, 3600000, false, 0, 64);
        add(Material.COAL_BLOCK, "Blocco Carbone", ShopItem.Category.MINERALI, 9000000, 3600000, 1800000, 27000000, false, 0, 64);
        add(Material.IRON_INGOT, "Lingotto Ferro", ShopItem.Category.MINERALI, 5000000, 2000000, 1000000, 15000000, false, 0, 64);
        add(Material.RAW_IRON, "Ferro Grezzo", ShopItem.Category.MINERALI, 4480000, 1792000, 896000, 13440000, false, 0, 64);
        add(Material.IRON_ORE, "Minerale Ferro", ShopItem.Category.MINERALI, 6000000, 2400000, 1200000, 18000000, false, 0, 64);
        add(Material.IRON_NUGGET, "Pepita Ferro", ShopItem.Category.MINERALI, 555556, 222222, 111111, 1666667, false, 0, 64);
        add(Material.IRON_BLOCK, "Blocco Ferro", ShopItem.Category.MINERALI, 45000000, 18000000, 9000000, 135000000, false, 0, 64);
        add(Material.GOLD_INGOT, "Lingotto Oro", ShopItem.Category.MINERALI, 10000000, 4000000, 2000000, 25000000, false, 0, 64);
        add(Material.RAW_GOLD, "Oro Grezzo", ShopItem.Category.MINERALI, 8960000, 3584000, 1792000, 22400000, false, 0, 64);
        add(Material.GOLD_ORE, "Minerale Oro", ShopItem.Category.MINERALI, 12000000, 4800000, 2400000, 30000000, false, 0, 64);
        add(Material.GOLD_NUGGET, "Pepita Oro", ShopItem.Category.MINERALI, 1111111, 444444, 222222, 2777778, false, 0, 64);
        add(Material.GOLD_BLOCK, "Blocco Oro", ShopItem.Category.MINERALI, 90000000, 36000000, 18000000, 225000000, false, 0, 64);
        add(Material.DIAMOND, "Diamante", ShopItem.Category.MINERALI, 50000000, 20000000, 10000000, 150000000, false, 0, 64);
        add(Material.DIAMOND_ORE, "Minerale Diamante", ShopItem.Category.MINERALI, 60000000, 24000000, 12000000, 180000000, false, 0, 64);
        add(Material.DIAMOND_BLOCK, "Blocco Diamante", ShopItem.Category.MINERALI, 450000000, 180000000, 90000000, 1350000000, false, 0, 64);
        add(Material.EMERALD, "Smeraldo", ShopItem.Category.MINERALI, 30000000, 12000000, 6000000, 80000000, false, 0, 64);
        add(Material.EMERALD_ORE, "Minerale Smeraldo", ShopItem.Category.MINERALI, 36000000, 14400000, 7200000, 96000000, false, 0, 64);
        add(Material.EMERALD_BLOCK, "Blocco Smeraldo", ShopItem.Category.MINERALI, 270000000, 108000000, 54000000, 720000000, false, 0, 64);
        add(Material.LAPIS_LAZULI, "Lapislazzuli", ShopItem.Category.MINERALI, 3000000, 1200000, 600000, 8000000, false, 0, 64);
        add(Material.LAPIS_ORE, "Minerale Lapislazzuli", ShopItem.Category.MINERALI, 3600000, 1440000, 720000, 9600000, false, 0, 64);
        add(Material.LAPIS_BLOCK, "Blocco Lapislazzuli", ShopItem.Category.MINERALI, 27000000, 10800000, 5400000, 72000000, false, 0, 64);
        add(Material.REDSTONE, "Redstone", ShopItem.Category.MINERALI, 2000000, 800000, 400000, 6000000, false, 0, 64);
        add(Material.REDSTONE_ORE, "Minerale Redstone", ShopItem.Category.MINERALI, 2400000, 960000, 480000, 7200000, false, 0, 64);
        add(Material.REDSTONE_BLOCK, "Blocco Redstone", ShopItem.Category.MINERALI, 18000000, 7200000, 3600000, 54000000, false, 0, 64);
        add(Material.QUARTZ, "Quarzo", ShopItem.Category.MINERALI, 2500000, 1000000, 500000, 7000000, false, 0, 64);
        add(Material.QUARTZ_BLOCK, "Blocco Quarzo", ShopItem.Category.MINERALI, 10000000, 4000000, 2000000, 25000000, false, 0, 64);
        add(Material.QUARTZ_PILLAR, "Pilastro Quarzo", ShopItem.Category.MINERALI, 10560000, 4224000, 2112000, 26400000, false, 0, 64);
        add(Material.CHISELED_QUARTZ_BLOCK, "Quarzo Cesellato", ShopItem.Category.MINERALI, 10560000, 4224000, 2112000, 26400000, false, 0, 64);
        add(Material.SMOOTH_QUARTZ, "Quarzo Liscio", ShopItem.Category.MINERALI, 10240000, 4096000, 2048000, 25600000, false, 0, 64);
        add(Material.QUARTZ_STAIRS, "Scale Quarzo", ShopItem.Category.MINERALI, 6000000, 2400000, 1200000, 15000000, false, 0, 64);
        add(Material.QUARTZ_SLAB, "Lastra Quarzo", ShopItem.Category.MINERALI, 3000000, 1200000, 600000, 7500000, false, 0, 64);
        add(Material.COPPER_INGOT, "Rame", ShopItem.Category.MINERALI, 1920000, 768000, 384000, 4800000, false, 0, 64);
        add(Material.RAW_COPPER, "Rame Grezzo", ShopItem.Category.MINERALI, 1600000, 640000, 320000, 4000000, false, 0, 64);
        add(Material.COPPER_ORE, "Minerale Rame", ShopItem.Category.MINERALI, 2240000, 896000, 448000, 5600000, false, 0, 64);
        add(Material.COPPER_BLOCK, "Blocco Rame", ShopItem.Category.MINERALI, 17280000, 6912000, 3456000, 43200000, false, 0, 64);
        add(Material.NETHER_QUARTZ_ORE, "Minerale Quarzo Nether", ShopItem.Category.MINERALI, 3000000, 1200000, 600000, 7500000, false, 0, 64);
        add(Material.NETHER_GOLD_ORE, "Minerale Oro Nether", ShopItem.Category.MINERALI, 6400000, 2560000, 1280000, 16000000, false, 0, 64);

        // NATURA
        add(Material.SEAGRASS, "Alghe", ShopItem.Category.NATURA, 200000, 80000, 50000, 500000, false, 0, 64);
        add(Material.KELP, "Alga", ShopItem.Category.NATURA, 150000, 60000, 30000, 400000, false, 0, 64);
        add(Material.SUGAR_CANE, "Canna Zucchero", ShopItem.Category.NATURA, 300000, 120000, 60000, 700000, false, 0, 64);
        add(Material.CACTUS, "Cactus", ShopItem.Category.NATURA, 400000, 160000, 80000, 900000, false, 0, 64);
        add(Material.PUMPKIN, "Zucca", ShopItem.Category.NATURA, 500000, 200000, 100000, 1200000, false, 0, 64);
        add(Material.MELON_SLICE, "Fetta Anguria", ShopItem.Category.NATURA, 25600, 10240, 5120, 64000, false, 0, 64);
        add(Material.VINE, "Rampicante", ShopItem.Category.NATURA, 300000, 120000, 60000, 700000, false, 0, 64);
        add(Material.LILY_PAD, "Ninfea", ShopItem.Category.NATURA, 500000, 200000, 100000, 1200000, false, 0, 64);
        add(Material.DANDELION, "Dente di Leone", ShopItem.Category.NATURA, 200000, 80000, 40000, 500000, false, 0, 64);
        add(Material.POPPY, "Papavero", ShopItem.Category.NATURA, 200000, 80000, 40000, 500000, false, 0, 64);
        add(Material.BLUE_ORCHID, "Orchidea Blu", ShopItem.Category.NATURA, 400000, 160000, 80000, 1000000, false, 0, 64);
        add(Material.ALLIUM, "Allium", ShopItem.Category.NATURA, 400000, 160000, 80000, 1000000, false, 0, 64);
        add(Material.AZURE_BLUET, "Bucaneve", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.RED_TULIP, "Tulipano Rosso", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.ORANGE_TULIP, "Tulipano Arancione", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.WHITE_TULIP, "Tulipano Bianco", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.PINK_TULIP, "Tulipano Rosa", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.OXEYE_DAISY, "Margherita", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.CORNFLOWER, "Fiordaliso", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.LILY_OF_THE_VALLEY, "Mughetto", ShopItem.Category.NATURA, 400000, 160000, 80000, 1000000, false, 0, 64);
        add(Material.WITHER_ROSE, "Rosa Wither", ShopItem.Category.NATURA, 3200000, 1280000, 640000, 8000000, false, 0, 64);
        add(Material.SUNFLOWER, "Girasole", ShopItem.Category.NATURA, 480000, 192000, 96000, 1200000, false, 0, 64);
        add(Material.LILAC, "Lilla", ShopItem.Category.NATURA, 480000, 192000, 96000, 1200000, false, 0, 64);
        add(Material.ROSE_BUSH, "Rosa", ShopItem.Category.NATURA, 480000, 192000, 96000, 1200000, false, 0, 64);
        add(Material.PEONY, "Peonia", ShopItem.Category.NATURA, 480000, 192000, 96000, 1200000, false, 0, 64);
        add(Material.RED_MUSHROOM, "Fungo Rosso", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.BROWN_MUSHROOM, "Fungo Marrone", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.STRING, "Filo", ShopItem.Category.NATURA, 600000, 240000, 120000, 1500000, false, 0, 64);
        add(Material.FEATHER, "Piuma", ShopItem.Category.NATURA, 500000, 200000, 100000, 1200000, false, 0, 64);
        add(Material.LEATHER, "Cuoio", ShopItem.Category.NATURA, 1000000, 400000, 200000, 2500000, false, 0, 64);
        add(Material.INK_SAC, "Inchiostro", ShopItem.Category.NATURA, 800000, 320000, 160000, 2000000, false, 0, 64);
        add(Material.GLOW_INK_SAC, "Inchiostro Luminoso", ShopItem.Category.NATURA, 1600000, 640000, 320000, 4000000, false, 0, 64);
        add(Material.SLIME_BALL, "Muco", ShopItem.Category.NATURA, 2000000, 800000, 400000, 5000000, false, 0, 64);
        add(Material.GUNPOWDER, "Polvere da Sparo", ShopItem.Category.NATURA, 1280000, 512000, 256000, 3200000, false, 0, 64);
        add(Material.BONE, "Osso", ShopItem.Category.NATURA, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.BONE_MEAL, "Farina d'Osso", ShopItem.Category.NATURA, 800000, 320000, 160000, 2000000, false, 0, 64);
        add(Material.BONE_BLOCK, "Blocco Ossa", ShopItem.Category.NATURA, 5760000, 2304000, 1152000, 14400000, false, 0, 64);
        add(Material.FLINT, "Selce", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.HONEYCOMB, "Favo", ShopItem.Category.NATURA, 960000, 384000, 192000, 2400000, false, 0, 64);
        add(Material.HONEYCOMB_BLOCK, "Blocco Favo", ShopItem.Category.NATURA, 3840000, 1536000, 768000, 9600000, false, 0, 64);
        add(Material.HONEY_BLOCK, "Blocco Miele", ShopItem.Category.NATURA, 2560000, 1024000, 512000, 6400000, false, 0, 64);
        add(Material.EGG, "Uovo", ShopItem.Category.NATURA, 192000, 76800, 38400, 480000, false, 0, 16);
        add(Material.COD, "Merluzzo", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.SALMON, "Salmone", ShopItem.Category.NATURA, 384000, 153600, 76800, 960000, false, 0, 64);
        add(Material.TROPICAL_FISH, "Pesce Tropicale", ShopItem.Category.NATURA, 512000, 204800, 102400, 1280000, false, 0, 64);
        add(Material.PUFFERFISH, "Pesce Palla", ShopItem.Category.NATURA, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.PHANTOM_MEMBRANE, "Membrana Phantom", ShopItem.Category.NATURA, 3200000, 1280000, 640000, 8000000, false, 0, 64);
        add(Material.TURTLE_SCUTE, "Scudo Tartaruga", ShopItem.Category.NATURA, 5120000, 2048000, 1024000, 12800000, false, 0, 64);
        add(Material.NAUTILUS_SHELL, "Conchiglia Nautilo", ShopItem.Category.NATURA, 3200000, 1280000, 640000, 8000000, false, 0, 64);
        add(Material.PRISMARINE_SHARD, "Frammento Prismarina", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.PRISMARINE_CRYSTALS, "Cristalli Prismarina", ShopItem.Category.NATURA, 512000, 204800, 102400, 1280000, false, 0, 64);
        add(Material.SPIDER_EYE, "Occhio Ragno", ShopItem.Category.NATURA, 960000, 384000, 192000, 2400000, false, 0, 64);
        add(Material.FERMENTED_SPIDER_EYE, "Occhio Ragno Fermentato", ShopItem.Category.NATURA, 1600000, 640000, 320000, 4000000, false, 0, 64);
        add(Material.RABBIT_HIDE, "Pelle Coniglio", ShopItem.Category.NATURA, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.RABBIT_FOOT, "Zampa Coniglio", ShopItem.Category.NATURA, 1920000, 768000, 384000, 4800000, false, 0, 64);
        add(Material.WHEAT, "Grano", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.WHEAT_SEEDS, "Semi Grano", ShopItem.Category.NATURA, 128000, 51200, 25600, 320000, false, 0, 64);
        add(Material.POTATO, "Patata", ShopItem.Category.NATURA, 256000, 102400, 51200, 640000, false, 0, 64);
        add(Material.CARROT, "Carota", ShopItem.Category.NATURA, 256000, 102400, 51200, 640000, false, 0, 64);
        add(Material.BEETROOT, "Barbabietola", ShopItem.Category.NATURA, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.BEETROOT_SEEDS, "Semi Barbabietola", ShopItem.Category.NATURA, 128000, 51200, 25600, 320000, false, 0, 64);
        add(Material.PUMPKIN_SEEDS, "Semi Zucca", ShopItem.Category.NATURA, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.MELON_SEEDS, "Semi Anguria", ShopItem.Category.NATURA, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.APPLE, "Mela", ShopItem.Category.NATURA, 512000, 204800, 102400, 1280000, false, 0, 64);
        add(Material.SWEET_BERRIES, "Bacche Dolci", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.GLOW_BERRIES, "Bacche Luminose", ShopItem.Category.NATURA, 384000, 153600, 76800, 960000, false, 0, 64);
        add(Material.COCOA_BEANS, "Fave di Cacao", ShopItem.Category.NATURA, 512000, 204800, 102400, 1280000, false, 0, 64);
        add(Material.SPORE_BLOSSOM, "Fiore Spora", ShopItem.Category.NATURA, 1280000, 512000, 256000, 3200000, false, 0, 64);
        add(Material.AZALEA, "Azalea", ShopItem.Category.NATURA, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.FLOWERING_AZALEA, "Azalea in Fiore", ShopItem.Category.NATURA, 960000, 384000, 192000, 2400000, false, 0, 64);
        add(Material.MOSS_BLOCK, "Blocco Muschio", ShopItem.Category.NATURA, 512000, 204800, 102400, 1280000, false, 0, 64);
        add(Material.MOSS_CARPET, "Tappeto Muschio", ShopItem.Category.NATURA, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.BREEZE_ROD, "Bastone Brezza", ShopItem.Category.NATURA, 6400000, 2560000, 1280000, 16000000, false, 0, 64);
        add(Material.ARMADILLO_SCUTE, "Scudo Armadillo", ShopItem.Category.NATURA, 5120000, 2048000, 1024000, 12800000, false, 0, 64);
        add(Material.TORCHFLOWER_SEEDS, "Semi Torcia Fiore", ShopItem.Category.NATURA, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.PITCHER_POD, "Baccello Brocca", ShopItem.Category.NATURA, 768000, 307200, 153600, 1920000, false, 0, 64);
        add(Material.PINK_PETALS, "Petali Rosa", ShopItem.Category.NATURA, 512000, 204800, 102400, 1280000, false, 0, 64);

        // NETHER
        add(Material.NETHERRACK, "Netherrack", ShopItem.Category.NETHER, 300000, 120000, 60000, 700000, false, 0, 64);
        add(Material.NETHER_BRICKS, "Mattoni Nether", ShopItem.Category.NETHER, 6400000, 2560000, 1280000, 16000000, false, 0, 64);
        add(Material.RED_NETHER_BRICKS, "Mattoni Nether Rossi", ShopItem.Category.NETHER, 7680000, 3072000, 1536000, 19200000, false, 0, 64);
        add(Material.NETHER_BRICK_STAIRS, "Scale Nether", ShopItem.Category.NETHER, 3840000, 1536000, 768000, 9600000, false, 0, 64);
        add(Material.NETHER_BRICK_SLAB, "Lastra Nether", ShopItem.Category.NETHER, 1920000, 768000, 384000, 4800000, false, 0, 64);
        add(Material.NETHER_BRICK_FENCE, "Staccionata Nether", ShopItem.Category.NETHER, 3200000, 1280000, 640000, 8000000, false, 0, 64);
        add(Material.NETHER_BRICK_WALL, "Muro Nether", ShopItem.Category.NETHER, 3200000, 1280000, 640000, 8000000, false, 0, 64);
        add(Material.NETHER_WART, "Verruca Nether", ShopItem.Category.NETHER, 2000000, 800000, 400000, 5000000, false, 0, 64);
        add(Material.NETHER_WART_BLOCK, "Blocco Verruca Nether", ShopItem.Category.NETHER, 1280000, 512000, 256000, 3200000, false, 0, 64);
        add(Material.BLAZE_ROD, "Bastone Blaze", ShopItem.Category.NETHER, 5000000, 2000000, 1000000, 12000000, false, 0, 64);
        add(Material.BLAZE_POWDER, "Polvere Blaze", ShopItem.Category.NETHER, 3000000, 1200000, 600000, 8000000, false, 0, 64);
        add(Material.GHAST_TEAR, "Lacrima Ghast", ShopItem.Category.NETHER, 8000000, 3200000, 1600000, 20000000, false, 0, 64);
        add(Material.MAGMA_CREAM, "Crema Magma", ShopItem.Category.NETHER, 3000000, 1200000, 600000, 8000000, false, 0, 64);
        add(Material.SOUL_SAND, "Sabbia Anima", ShopItem.Category.NETHER, 1000000, 400000, 200000, 2500000, false, 0, 64);
        add(Material.SOUL_SOIL, "Terra Anima", ShopItem.Category.NETHER, 768000, 307200, 153600, 1920000, false, 0, 64);
        add(Material.GLOWSTONE_DUST, "Polvere Glowstone", ShopItem.Category.NETHER, 2000000, 800000, 400000, 5000000, false, 0, 64);
        add(Material.GLOWSTONE, "Glowstone", ShopItem.Category.NETHER, 4000000, 1600000, 800000, 10000000, false, 0, 64);
        add(Material.WITHER_SKELETON_SKULL, "Teschio Wither", ShopItem.Category.NETHER, 20000000, 8000000, 4000000, 50000000, false, 0, 64);
        add(Material.CRIMSON_STEM, "Fungo Cremisi", ShopItem.Category.NETHER, 500000, 200000, 100000, 1200000, false, 0, 64);
        add(Material.WARPED_STEM, "Fungo Deformato", ShopItem.Category.NETHER, 500000, 200000, 100000, 1200000, false, 0, 64);
        add(Material.CRIMSON_PLANKS, "Assi Cremisi", ShopItem.Category.NETHER, 375000, 150000, 75000, 900000, false, 0, 64);
        add(Material.WARPED_PLANKS, "Assi Deformate", ShopItem.Category.NETHER, 375000, 150000, 75000, 900000, false, 0, 64);
        add(Material.CRIMSON_NYLIUM, "Nylium Cremisi", ShopItem.Category.NETHER, 512000, 204800, 102400, 1280000, false, 0, 64);
        add(Material.WARPED_NYLIUM, "Nylium Deformato", ShopItem.Category.NETHER, 512000, 204800, 102400, 1280000, false, 0, 64);
        add(Material.SHROOMLIGHT, "Shroomlight", ShopItem.Category.NETHER, 1600000, 640000, 320000, 4000000, false, 0, 64);
        add(Material.MAGMA_BLOCK, "Blocco Magma", ShopItem.Category.NETHER, 960000, 384000, 192000, 2400000, false, 0, 64);
        add(Material.GOLD_NUGGET, "Pepita Oro", ShopItem.Category.NETHER, 1111111, 444444, 222222, 2777778, false, 0, 64);
        add(Material.WEEPING_VINES, "Viti Piangenti", ShopItem.Category.NETHER, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.TWISTING_VINES, "Viti Attorcigliate", ShopItem.Category.NETHER, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.CRIMSON_ROOTS, "Radici Cremisi", ShopItem.Category.NETHER, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.WARPED_ROOTS, "Radici Deformate", ShopItem.Category.NETHER, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.CRIMSON_FUNGUS, "Fungo Cremisi", ShopItem.Category.NETHER, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.WARPED_FUNGUS, "Fungo Deformato", ShopItem.Category.NETHER, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.WARPED_WART_BLOCK, "Blocco Verruca Deformata", ShopItem.Category.NETHER, 1280000, 512000, 256000, 3200000, false, 0, 64);

        // END
        add(Material.END_STONE, "Pietra End", ShopItem.Category.END, 2000000, 800000, 400000, 5000000, false, 0, 64);
        add(Material.END_STONE_BRICKS, "Mattoni End", ShopItem.Category.END, 2200000, 880000, 440000, 5500000, false, 0, 64);
        add(Material.END_STONE_BRICK_STAIRS, "Scale Mattoni End", ShopItem.Category.END, 1320000, 528000, 264000, 3300000, false, 0, 64);
        add(Material.END_STONE_BRICK_SLAB, "Lastra Mattoni End", ShopItem.Category.END, 660000, 264000, 132000, 1650000, false, 0, 64);
        add(Material.END_STONE_BRICK_WALL, "Muro Mattoni End", ShopItem.Category.END, 1100000, 440000, 220000, 2750000, false, 0, 64);
        add(Material.PURPUR_BLOCK, "Purpur", ShopItem.Category.END, 3000000, 1200000, 600000, 7500000, false, 0, 64);
        add(Material.PURPUR_PILLAR, "Pilastro Purpur", ShopItem.Category.END, 3072000, 1228800, 614400, 7680000, false, 0, 64);
        add(Material.PURPUR_STAIRS, "Scale Purpur", ShopItem.Category.END, 1800000, 720000, 360000, 4500000, false, 0, 64);
        add(Material.PURPUR_SLAB, "Lastra Purpur", ShopItem.Category.END, 900000, 360000, 180000, 2250000, false, 0, 64);
        add(Material.ENDER_PEARL, "Perla Ender", ShopItem.Category.END, 5000000, 2000000, 1000000, 12000000, false, 0, 16);
        add(Material.ENDER_EYE, "Occhio Ender", ShopItem.Category.END, 10000000, 4000000, 2000000, 25000000, false, 0, 64);
        add(Material.DRAGON_BREATH, "Respiro Drago", ShopItem.Category.END, 15000000, 6000000, 3000000, 40000000, false, 0, 64);
        add(Material.SHULKER_SHELL, "Guscio Shulker", ShopItem.Category.END, 20000000, 8000000, 4000000, 50000000, false, 0, 64);
        add(Material.CHORUS_FRUIT, "Frutto Coro", ShopItem.Category.END, 3000000, 1200000, 600000, 7000000, false, 0, 64);
        add(Material.CHORUS_FLOWER, "Fiore Coro", ShopItem.Category.END, 5000000, 2000000, 1000000, 12000000, false, 0, 64);
        add(Material.POPPED_CHORUS_FRUIT, "Frutto Coro Esploso", ShopItem.Category.END, 3840000, 1536000, 768000, 9600000, false, 0, 64);
        add(Material.END_ROD, "Bastone End", ShopItem.Category.END, 1280000, 512000, 256000, 3200000, false, 0, 64);
        add(Material.END_CRYSTAL, "Cristallo End", ShopItem.Category.END, 32000000, 12800000, 6400000, 80000000, false, 0, 64);
        add(Material.ECHO_SHARD, "Frammento Eco", ShopItem.Category.END, 32000000, 12800000, 6400000, 80000000, false, 0, 64);

        // CONTENITORI
        add(Material.CHEST, "Forziere", ShopItem.Category.DECORAZIONI, 960000, 384000, 192000, 2400000, false, 0, 64);
        add(Material.TRAPPED_CHEST, "Forziere a Trappola", ShopItem.Category.DECORAZIONI, 1152000, 460800, 230400, 2880000, false, 0, 64);
        add(Material.BARREL, "Botte", ShopItem.Category.DECORAZIONI, 960000, 384000, 192000, 2400000, false, 0, 64);
        add(Material.ENDER_CHEST, "Forziere Ender", ShopItem.Category.END, 32000000, 12800000, 6400000, 80000000, false, 0, 64);

        // REDSTONE E MECCANISMI
        add(Material.REDSTONE_TORCH, "Torcia Redstone", ShopItem.Category.DECORAZIONI, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.REPEATER, "Ripetitore", ShopItem.Category.DECORAZIONI, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.COMPARATOR, "Comparatore", ShopItem.Category.DECORAZIONI, 960000, 384000, 192000, 2400000, false, 0, 64);
        add(Material.OBSERVER, "Osservatore", ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 3200000, false, 0, 64);
        add(Material.PISTON, "Pistone", ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 3200000, false, 0, 64);
        add(Material.STICKY_PISTON, "Pistone Appiccicoso", ShopItem.Category.DECORAZIONI, 1920000, 768000, 384000, 4800000, false, 0, 64);
        add(Material.SLIME_BLOCK, "Blocco Muco", ShopItem.Category.DECORAZIONI, 2240000, 896000, 448000, 5600000, false, 0, 64);
        add(Material.HONEY_BLOCK, "Blocco Miele", ShopItem.Category.DECORAZIONI, 2560000, 1024000, 512000, 6400000, false, 0, 64);
        add(Material.DISPENSER, "Distributore", ShopItem.Category.DECORAZIONI, 960000, 384000, 192000, 2400000, false, 0, 64);
        add(Material.DROPPER, "Espulsore", ShopItem.Category.DECORAZIONI, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.HOPPER, "Tramoggia", ShopItem.Category.DECORAZIONI, 2560000, 1024000, 512000, 6400000, false, 0, 64);
        add(Material.LEVER, "Leva", ShopItem.Category.DECORAZIONI, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.STONE_BUTTON, "Pulsante Pietra", ShopItem.Category.DECORAZIONI, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.TARGET, "Bersaglio", ShopItem.Category.DECORAZIONI, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.DAYLIGHT_DETECTOR, "Rilevatore Luce", ShopItem.Category.DECORAZIONI, 960000, 384000, 192000, 2400000, false, 0, 64);
        add(Material.TRIPWIRE_HOOK, "Gancio Filo", ShopItem.Category.DECORAZIONI, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.REDSTONE_LAMP, "Lampada Redstone", ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 3200000, false, 0, 64);

        // PORTE FERRO E BOTOLE
        add(Material.IRON_DOOR, "Porta Ferro", ShopItem.Category.DECORAZIONI, 3200000, 1280000, 640000, 8000000, false, 0, 64);
        add(Material.IRON_TRAPDOOR, "Botola Ferro", ShopItem.Category.DECORAZIONI, 3200000, 1280000, 640000, 8000000, false, 0, 64);

        // OGGETTI UTILI
        add(Material.BUCKET, "Secchio", ShopItem.Category.NATURA, 1920000, 768000, 384000, 4800000, false, 0, 16);
        add(Material.WATER_BUCKET, "Secchio d'Acqua", ShopItem.Category.NATURA, 2240000, 896000, 448000, 5600000, false, 0, 16);
        add(Material.LAVA_BUCKET, "Secchio di Lava", ShopItem.Category.NATURA, 3200000, 1280000, 640000, 8000000, false, 0, 16);
        add(Material.MILK_BUCKET, "Secchio di Latte", ShopItem.Category.NATURA, 1280000, 512000, 256000, 3200000, false, 0, 16);
        add(Material.COMPASS, "Bussola", ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 3200000, false, 0, 64);
        add(Material.CLOCK, "Orologio", ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 3200000, false, 0, 64);
        add(Material.NAME_TAG, "Targhetta", ShopItem.Category.DECORAZIONI, 3200000, 1280000, 640000, 8000000, false, 0, 64);
        add(Material.LEAD, "Guinzaglio", ShopItem.Category.NATURA, 960000, 384000, 192000, 2400000, false, 0, 64);
        add(Material.SADDLE, "Sella", ShopItem.Category.NATURA, 6400000, 2560000, 1280000, 16000000, false, 0, 1);
        add(Material.ITEM_FRAME, "Cornice", ShopItem.Category.DECORAZIONI, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.GLOW_ITEM_FRAME, "Cornice Luminosa", ShopItem.Category.DECORAZIONI, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.PAINTING, "Dipinto", ShopItem.Category.DECORAZIONI, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.ARMOR_STAND, "Espositore Armatura", ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 3200000, false, 0, 64);
        add(Material.SPYGLASS, "Cannocchiale", ShopItem.Category.DECORAZIONI, 3200000, 1280000, 640000, 8000000, false, 0, 64);
        add(Material.RECOVERY_COMPASS, "Bussola Recupero", ShopItem.Category.RARI, 19200000, 7680000, 3840000, 48000000, false, 0, 1);
        add(Material.PAPER, "Carta", ShopItem.Category.DECORAZIONI, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.BOOK, "Libro", ShopItem.Category.DECORAZIONI, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.FIRE_CHARGE, "Palla di Fuoco", ShopItem.Category.NETHER, 640000, 256000, 128000, 1600000, false, 0, 64);
        add(Material.FIREWORK_ROCKET, "Razzo", ShopItem.Category.DECORAZIONI, 320000, 128000, 64000, 800000, false, 0, 64);

        // BARCHE
        add(Material.OAK_BOAT, "Barca Quercia", ShopItem.Category.LEGNO, 1920000, 768000, 384000, 4800000, false, 0, 1);
        add(Material.SPRUCE_BOAT, "Barca Abete", ShopItem.Category.LEGNO, 1920000, 768000, 384000, 4800000, false, 0, 1);
        add(Material.BIRCH_BOAT, "Barca Betulla", ShopItem.Category.LEGNO, 1920000, 768000, 384000, 4800000, false, 0, 1);
        add(Material.JUNGLE_BOAT, "Barca Giungla", ShopItem.Category.LEGNO, 1920000, 768000, 384000, 4800000, false, 0, 1);
        add(Material.ACACIA_BOAT, "Barca Acacia", ShopItem.Category.LEGNO, 1920000, 768000, 384000, 4800000, false, 0, 1);
        add(Material.DARK_OAK_BOAT, "Barca Rovere", ShopItem.Category.LEGNO, 1920000, 768000, 384000, 4800000, false, 0, 1);
        add(Material.MANGROVE_BOAT, "Barca Mangrovia", ShopItem.Category.LEGNO, 1920000, 768000, 384000, 4800000, false, 0, 1);
        add(Material.CHERRY_BOAT, "Barca Ciliegio", ShopItem.Category.LEGNO, 1920000, 768000, 384000, 4800000, false, 0, 1);
        add(Material.BAMBOO_RAFT, "Zattera Bambu", ShopItem.Category.LEGNO, 1920000, 768000, 384000, 4800000, false, 0, 1);

        // MINECART
        add(Material.MINECART, "Vagoncino", ShopItem.Category.DECORAZIONI, 1920000, 768000, 384000, 4800000, false, 0, 1);
        add(Material.CHEST_MINECART, "Vagoncino Forziere", ShopItem.Category.DECORAZIONI, 2560000, 1024000, 512000, 6400000, false, 0, 1);
        add(Material.FURNACE_MINECART, "Vagoncino Fornace", ShopItem.Category.DECORAZIONI, 2240000, 896000, 448000, 5600000, false, 0, 1);
        add(Material.HOPPER_MINECART, "Vagoncino Tramoggia", ShopItem.Category.DECORAZIONI, 3200000, 1280000, 640000, 8000000, false, 0, 1);

        // BLOCCHI SPECIALI
        add(Material.DRIPSTONE_BLOCK, "Blocco Stalattite", ShopItem.Category.PIETRA, 256000, 102400, 51200, 640000, false, 0, 64);
        add(Material.POINTED_DRIPSTONE, "Stalattite", ShopItem.Category.PIETRA, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.MYCELIUM, "Micelio", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64);
        add(Material.DEAD_BUSH, "Cespuglio Secco", ShopItem.Category.NATURA, 96000, 38400, 19200, 240000, false, 0, 64);
        add(Material.TALL_GRASS, "Erba Alta", ShopItem.Category.NATURA, 128000, 51200, 25600, 320000, false, 0, 64);
        add(Material.FERN, "Felce", ShopItem.Category.NATURA, 128000, 51200, 25600, 320000, false, 0, 64);
        add(Material.LARGE_FERN, "Felce Grande", ShopItem.Category.NATURA, 192000, 76800, 38400, 480000, false, 0, 64);
        add(Material.GOAT_HORN, "Corno di Capra", ShopItem.Category.NATURA, 3200000, 1280000, 640000, 8000000, false, 0, 1);
        add(Material.BRUSH, "Pennello", ShopItem.Category.DECORAZIONI, 1280000, 512000, 256000, 3200000, false, 0, 1);

        // BLOCCHI QUARTZ EXTRA
        add(Material.QUARTZ_BRICKS, "Mattoni Quarzo", ShopItem.Category.MINERALI, 10880000, 4352000, 2176000, 27200000, false, 0, 64);

        // RARI
        add(Material.TOTEM_OF_UNDYING, "Totem", ShopItem.Category.RARI, 500000000, 200000000, 100000000, 1000000000, true, 7, 1);
        add(Material.ELYTRA, "Elitra", ShopItem.Category.RARI, 1000000000, 400000000, 200000000, 2000000000, true, 7, 1);
        add(Material.HEART_OF_THE_SEA, "Cuore del Mare", ShopItem.Category.RARI, 200000000, 80000000, 40000000, 500000000, true, 3, 1);
        add(Material.NETHER_STAR, "Stella Nether", ShopItem.Category.RARI, 300000000, 120000000, 60000000, 700000000, true, 3, 1);
        add(Material.TRIDENT, "Tridente", ShopItem.Category.RARI, 150000000, 60000000, 30000000, 400000000, true, 3, 1);
        add(Material.DRAGON_EGG, "Uovo del Drago", ShopItem.Category.RARI, -1, 3200000000.0, 1600000000.0, 3200000000.0, true, 0, 1);
    }

    public List<ShopItem> getItemsByCategory(ShopItem.Category category) {
        List<ShopItem> result = new ArrayList<>();
        for (ShopItem item : items) {
            if (item.getCategory() == category && item.isBuyable()) result.add(item);
        }
        return result;
    }

    public ShopItem getShopItem(Material material) {
        for (ShopItem item : items) {
            if (item.getMaterial() == material) return item;
        }
        return null;
    }

    public List<ShopItem> getAllItems() { return items; }

    public boolean hasCooldown(UUID uuid, Material material) {
        if (!cooldowns.containsKey(uuid)) return false;
        Map<Material, Long> pc = cooldowns.get(uuid);
        if (!pc.containsKey(material)) return false;
        ShopItem item = getShopItem(material);
        if (item == null) return false;
        long ms = (long) item.getCooldownDays() * 24 * 60 * 60 * 1000;
        return System.currentTimeMillis() - pc.get(material) < ms;
    }

    public long getRemainingCooldown(UUID uuid, Material material) {
        if (!cooldowns.containsKey(uuid)) return 0;
        Map<Material, Long> pc = cooldowns.get(uuid);
        if (!pc.containsKey(material)) return 0;
        ShopItem item = getShopItem(material);
        if (item == null) return 0;
        long ms = (long) item.getCooldownDays() * 24 * 60 * 60 * 1000;
        return Math.max(0, ms - (System.currentTimeMillis() - pc.get(material)));
    }

    public void setCooldown(UUID uuid, Material material) {
        cooldowns.computeIfAbsent(uuid, k -> new HashMap<>()).put(material, System.currentTimeMillis());
    }

    public double getSkipCooldownCost(Material material) {
        ShopItem item = getShopItem(material);
        return item != null ? item.getCurrentPrice() * 2 : 0;
    }

    public void skipCooldown(UUID uuid, Material material) {
        if (cooldowns.containsKey(uuid)) cooldowns.get(uuid).remove(material);
    }

    public void resetAllPrices() {
        for (ShopItem item : items) item.resetPrices();
        plugin.getLogger().info("Prezzi shop resettati!");
        savePrices();
    }

    public void savePrices() {
        File file = new File(plugin.getDataFolder(), "prices.yml");
        YamlConfiguration config = new YamlConfiguration();
        for (ShopItem item : items) {
            config.set("prices." + item.getMaterial().name() + ".buy", item.getCurrentPrice());
            config.set("prices." + item.getMaterial().name() + ".sell", item.getCurrentSellPrice());
        }
        try { config.save(file); } catch (Exception e) { e.printStackTrace(); }
    }

    public void loadPrices() {
        File file = new File(plugin.getDataFolder(), "prices.yml");
        if (!file.exists()) return;
    }
}
