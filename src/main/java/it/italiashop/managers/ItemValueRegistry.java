package it.italiashop.managers;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class ItemValueRegistry {

    // {buyPrice, sellPrice} per singola unita. buyPrice=-1 = non acquistabile
    private static final Map<Material, double[]> VALUES = new HashMap<>();

    static {
        // ============================================================
        // TERRA E ROCCIA BASE
        // ============================================================
        v(Material.DIRT, 3125, 1250);
        v(Material.GRASS_BLOCK, 3906, 1563);
        v(Material.PODZOL, 4000, 1600);
        v(Material.MYCELIUM, 5000, 2000);
        v(Material.ROOTED_DIRT, 3500, 1400);
        v(Material.COARSE_DIRT, 3125, 1250);
        v(Material.MUD, 3125, 1250);
        v(Material.STONE, 7813, 3125);
        v(Material.COBBLESTONE, 6250, 2500);
        v(Material.MOSSY_COBBLESTONE, 8000, 3200);
        v(Material.SMOOTH_STONE, 9000, 3600);
        v(Material.BEDROCK, -1, 50000000);
        v(Material.GRAVEL, 5469, 2188);
        v(Material.SAND, 4688, 1875);
        v(Material.RED_SAND, 5000, 2000);
        v(Material.CLAY, 9375, 3750);
        v(Material.DIRT_PATH, 3000, 1200);

        // ANDESITE DIORITE GRANITO
        double andesite = 5000;
        v(Material.ANDESITE, andesite, andesite*0.4);
        v(Material.POLISHED_ANDESITE, andesite*1.2, andesite*0.48);
        v(Material.ANDESITE_STAIRS, andesite*0.6, andesite*0.24);
        v(Material.ANDESITE_SLAB, andesite*0.3, andesite*0.12);
        v(Material.ANDESITE_WALL, andesite*0.5, andesite*0.2);
        v(Material.POLISHED_ANDESITE_STAIRS, andesite*0.72, andesite*0.29);
        v(Material.POLISHED_ANDESITE_SLAB, andesite*0.36, andesite*0.14);

        double diorite = 5000;
        v(Material.DIORITE, diorite, diorite*0.4);
        v(Material.POLISHED_DIORITE, diorite*1.2, diorite*0.48);
        v(Material.DIORITE_STAIRS, diorite*0.6, diorite*0.24);
        v(Material.DIORITE_SLAB, diorite*0.3, diorite*0.12);
        v(Material.DIORITE_WALL, diorite*0.5, diorite*0.2);
        v(Material.POLISHED_DIORITE_STAIRS, diorite*0.72, diorite*0.29);
        v(Material.POLISHED_DIORITE_SLAB, diorite*0.36, diorite*0.14);

        double granite = 5000;
        v(Material.GRANITE, granite, granite*0.4);
        v(Material.POLISHED_GRANITE, granite*1.2, granite*0.48);
        v(Material.GRANITE_STAIRS, granite*0.6, granite*0.24);
        v(Material.GRANITE_SLAB, granite*0.3, granite*0.12);
        v(Material.GRANITE_WALL, granite*0.5, granite*0.2);
        v(Material.POLISHED_GRANITE_STAIRS, granite*0.72, granite*0.29);
        v(Material.POLISHED_GRANITE_SLAB, granite*0.36, granite*0.14);

        // PIETRA E DERIVATI
        double stone = 7813;
        v(Material.STONE_STAIRS, stone*0.6, stone*0.24);
        v(Material.STONE_SLAB, stone*0.3, stone*0.12);
        v(Material.STONE_BUTTON, stone*0.3, stone*0.12);
        v(Material.STONE_PRESSURE_PLATE, stone*0.4, stone*0.16);
        v(Material.COBBLESTONE_STAIRS, 6250*0.6, 6250*0.24);
        v(Material.COBBLESTONE_SLAB, 6250*0.3, 6250*0.12);
        v(Material.COBBLESTONE_WALL, 6250*0.5, 6250*0.2);
        v(Material.MOSSY_COBBLESTONE_STAIRS, 8000*0.6, 8000*0.24);
        v(Material.MOSSY_COBBLESTONE_SLAB, 8000*0.3, 8000*0.12);
        v(Material.MOSSY_COBBLESTONE_WALL, 8000*0.5, 8000*0.2);
        v(Material.SMOOTH_STONE_SLAB, 9000*0.3, 9000*0.12);

        // MATTONI DI PIETRA
        double stonebrick = 8000;
        v(Material.STONE_BRICKS, stonebrick, stonebrick*0.4);
        v(Material.MOSSY_STONE_BRICKS, stonebrick*1.2, stonebrick*0.48);
        v(Material.CRACKED_STONE_BRICKS, stonebrick*1.1, stonebrick*0.44);
        v(Material.CHISELED_STONE_BRICKS, stonebrick*1.2, stonebrick*0.48);
        v(Material.STONE_BRICK_STAIRS, stonebrick*0.6, stonebrick*0.24);
        v(Material.STONE_BRICK_SLAB, stonebrick*0.3, stonebrick*0.12);
        v(Material.STONE_BRICK_WALL, stonebrick*0.5, stonebrick*0.2);
        v(Material.MOSSY_STONE_BRICK_STAIRS, stonebrick*0.72, stonebrick*0.29);
        v(Material.MOSSY_STONE_BRICK_SLAB, stonebrick*0.36, stonebrick*0.14);
        v(Material.MOSSY_STONE_BRICK_WALL, stonebrick*0.6, stonebrick*0.24);

        // MATTONI DI ARGILLA
        double bricks = 15625;
        v(Material.BRICKS, bricks, bricks*0.4);
        v(Material.BRICK_STAIRS, bricks*0.6, bricks*0.24);
        v(Material.BRICK_SLAB, bricks*0.3, bricks*0.12);
        v(Material.BRICK_WALL, bricks*0.5, bricks*0.2);

        // ARENARIA
        double sand_b = 7000;
        v(Material.SANDSTONE, sand_b, sand_b*0.4);
        v(Material.SMOOTH_SANDSTONE, sand_b*1.1, sand_b*0.44);
        v(Material.CHISELED_SANDSTONE, sand_b*1.2, sand_b*0.48);
        v(Material.CUT_SANDSTONE, sand_b*1.1, sand_b*0.44);
        v(Material.SANDSTONE_STAIRS, sand_b*0.6, sand_b*0.24);
        v(Material.SANDSTONE_SLAB, sand_b*0.3, sand_b*0.12);
        v(Material.SANDSTONE_WALL, sand_b*0.5, sand_b*0.2);
        v(Material.SMOOTH_SANDSTONE_STAIRS, sand_b*0.66, sand_b*0.26);
        v(Material.SMOOTH_SANDSTONE_SLAB, sand_b*0.33, sand_b*0.13);
        v(Material.CUT_SANDSTONE_SLAB, sand_b*0.33, sand_b*0.13);
        v(Material.RED_SANDSTONE, sand_b, sand_b*0.4);
        v(Material.SMOOTH_RED_SANDSTONE, sand_b*1.1, sand_b*0.44);
        v(Material.CHISELED_RED_SANDSTONE, sand_b*1.2, sand_b*0.48);
        v(Material.CUT_RED_SANDSTONE, sand_b*1.1, sand_b*0.44);
        v(Material.RED_SANDSTONE_STAIRS, sand_b*0.6, sand_b*0.24);
        v(Material.RED_SANDSTONE_SLAB, sand_b*0.3, sand_b*0.12);
        v(Material.RED_SANDSTONE_WALL, sand_b*0.5, sand_b*0.2);
        v(Material.SMOOTH_RED_SANDSTONE_STAIRS, sand_b*0.66, sand_b*0.26);
        v(Material.SMOOTH_RED_SANDSTONE_SLAB, sand_b*0.33, sand_b*0.13);
        v(Material.CUT_RED_SANDSTONE_SLAB, sand_b*0.33, sand_b*0.13);

        // DEEPSLATE
        double deepslate = 6000;
        v(Material.DEEPSLATE, deepslate, deepslate*0.4);
        v(Material.COBBLED_DEEPSLATE, deepslate*0.9, deepslate*0.36);
        v(Material.POLISHED_DEEPSLATE, deepslate*1.1, deepslate*0.44);
        v(Material.DEEPSLATE_BRICKS, deepslate*1.2, deepslate*0.48);
        v(Material.DEEPSLATE_TILES, deepslate*1.3, deepslate*0.52);
        v(Material.CHISELED_DEEPSLATE, deepslate*1.3, deepslate*0.52);
        v(Material.CRACKED_DEEPSLATE_BRICKS, deepslate*1.1, deepslate*0.44);
        v(Material.CRACKED_DEEPSLATE_TILES, deepslate*1.2, deepslate*0.48);
        v(Material.COBBLED_DEEPSLATE_STAIRS, deepslate*0.54, deepslate*0.22);
        v(Material.COBBLED_DEEPSLATE_SLAB, deepslate*0.27, deepslate*0.11);
        v(Material.COBBLED_DEEPSLATE_WALL, deepslate*0.45, deepslate*0.18);
        v(Material.POLISHED_DEEPSLATE_STAIRS, deepslate*0.66, deepslate*0.26);
        v(Material.POLISHED_DEEPSLATE_SLAB, deepslate*0.33, deepslate*0.13);
        v(Material.POLISHED_DEEPSLATE_WALL, deepslate*0.55, deepslate*0.22);
        v(Material.DEEPSLATE_BRICK_STAIRS, deepslate*0.72, deepslate*0.29);
        v(Material.DEEPSLATE_BRICK_SLAB, deepslate*0.36, deepslate*0.14);
        v(Material.DEEPSLATE_BRICK_WALL, deepslate*0.6, deepslate*0.24);
        v(Material.DEEPSLATE_TILE_STAIRS, deepslate*0.78, deepslate*0.31);
        v(Material.DEEPSLATE_TILE_SLAB, deepslate*0.39, deepslate*0.16);
        v(Material.DEEPSLATE_TILE_WALL, deepslate*0.65, deepslate*0.26);
        v(Material.REINFORCED_DEEPSLATE, -1, 500000);

        // BLACKSTONE
        double blackstone = 6000;
        v(Material.BLACKSTONE, blackstone, blackstone*0.4);
        v(Material.GILDED_BLACKSTONE, blackstone*2, blackstone*0.8);
        v(Material.POLISHED_BLACKSTONE, blackstone*1.1, blackstone*0.44);
        v(Material.CHISELED_POLISHED_BLACKSTONE, blackstone*1.3, blackstone*0.52);
        v(Material.POLISHED_BLACKSTONE_BRICKS, blackstone*1.2, blackstone*0.48);
        v(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, blackstone*1.1, blackstone*0.44);
        v(Material.BLACKSTONE_STAIRS, blackstone*0.6, blackstone*0.24);
        v(Material.BLACKSTONE_SLAB, blackstone*0.3, blackstone*0.12);
        v(Material.BLACKSTONE_WALL, blackstone*0.5, blackstone*0.2);
        v(Material.POLISHED_BLACKSTONE_STAIRS, blackstone*0.66, blackstone*0.26);
        v(Material.POLISHED_BLACKSTONE_SLAB, blackstone*0.33, blackstone*0.13);
        v(Material.POLISHED_BLACKSTONE_WALL, blackstone*0.55, blackstone*0.22);
        v(Material.POLISHED_BLACKSTONE_BRICK_STAIRS, blackstone*0.72, blackstone*0.29);
        v(Material.POLISHED_BLACKSTONE_BRICK_SLAB, blackstone*0.36, blackstone*0.14);
        v(Material.POLISHED_BLACKSTONE_BRICK_WALL, blackstone*0.6, blackstone*0.24);
        v(Material.POLISHED_BLACKSTONE_BUTTON, blackstone*0.3, blackstone*0.12);
        v(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE, blackstone*0.4, blackstone*0.16);

        // TUFF
        double tuff = 4000;
        v(Material.TUFF, tuff, tuff*0.4);
        v(Material.POLISHED_TUFF, tuff*1.2, tuff*0.48);
        v(Material.CHISELED_TUFF, tuff*1.3, tuff*0.52);
        v(Material.TUFF_BRICKS, tuff*1.4, tuff*0.56);
        v(Material.CHISELED_TUFF_BRICKS, tuff*1.5, tuff*0.6);
        v(Material.TUFF_STAIRS, tuff*0.6, tuff*0.24);
        v(Material.TUFF_SLAB, tuff*0.3, tuff*0.12);
        v(Material.TUFF_WALL, tuff*0.5, tuff*0.2);
        v(Material.POLISHED_TUFF_STAIRS, tuff*0.72, tuff*0.29);
        v(Material.POLISHED_TUFF_SLAB, tuff*0.36, tuff*0.14);
        v(Material.POLISHED_TUFF_WALL, tuff*0.6, tuff*0.24);
        v(Material.TUFF_BRICK_STAIRS, tuff*0.84, tuff*0.34);
        v(Material.TUFF_BRICK_SLAB, tuff*0.42, tuff*0.17);
        v(Material.TUFF_BRICK_WALL, tuff*0.7, tuff*0.28);

        // CALCITE E BASALT
        v(Material.CALCITE, 4000, 1600);
        v(Material.BASALT, 5000, 2000);
        v(Material.SMOOTH_BASALT, 6000, 2400);
        v(Material.POLISHED_BASALT, 6000, 2400);

        // MUD
        v(Material.MUD, 3125, 1250);
        v(Material.PACKED_MUD, 6000, 2400);
        v(Material.MUD_BRICKS, 7000, 2800);
        v(Material.MUD_BRICK_STAIRS, 7000*0.6, 7000*0.24);
        v(Material.MUD_BRICK_SLAB, 7000*0.3, 7000*0.12);
        v(Material.MUD_BRICK_WALL, 7000*0.5, 7000*0.2);
        v(Material.MUDDY_MANGROVE_ROOTS, 5000, 2000);

        // ============================================================
        // LEGNO - tutte le varianti
        // ============================================================
        double[] woodPrices = {12500, 12500, 12500, 14063, 13281, 13281, 13281, 13281, 13281};
        String[] woodTypes = {"OAK", "BIRCH", "SPRUCE", "JUNGLE", "ACACIA", "DARK_OAK", "MANGROVE", "CHERRY", "BAMBOO"};

        for (int i = 0; i < woodTypes.length; i++) {
            double log = woodPrices[i];
            double plank = log * 0.75;
            String w = woodTypes[i];

            try { v(Material.valueOf(w + "_LOG"), log, log*0.4); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_WOOD"), log*0.9, log*0.36); } catch (Exception ignored) {}
            try { v(Material.valueOf("STRIPPED_" + w + "_LOG"), log*0.85, log*0.34); } catch (Exception ignored) {}
            try { v(Material.valueOf("STRIPPED_" + w + "_WOOD"), log*0.8, log*0.32); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_PLANKS"), plank, plank*0.4); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_STAIRS"), plank*0.6, plank*0.24); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_SLAB"), plank*0.3, plank*0.12); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_FENCE"), plank*0.5, plank*0.2); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_FENCE_GATE"), plank*0.8, plank*0.32); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_DOOR"), plank*0.8, plank*0.32); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_TRAPDOOR"), plank*0.8, plank*0.32); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_PRESSURE_PLATE"), plank*0.4, plank*0.16); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_BUTTON"), plank*0.3, plank*0.12); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_SIGN"), plank*0.5, plank*0.2); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_HANGING_SIGN"), plank*0.6, plank*0.24); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_BOAT"), plank*3, plank*1.2); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_CHEST_BOAT"), plank*5, plank*2); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_LEAVES"), 3000, 1200); } catch (Exception ignored) {}
            try { v(Material.valueOf(w + "_SAPLING"), 5000, 2000); } catch (Exception ignored) {}
        }

        // Bamboo speciale
        v(Material.BAMBOO, 3906, 1563);
        v(Material.BAMBOO_BLOCK, 10000, 4000);
        v(Material.STRIPPED_BAMBOO_BLOCK, 9000, 3600);
        v(Material.BAMBOO_MOSAIC, 12000, 4800);
        v(Material.BAMBOO_MOSAIC_STAIRS, 7200, 2880);
        v(Material.BAMBOO_MOSAIC_SLAB, 3600, 1440);

        // ============================================================
        // LANA E COLORI - tutte le varianti
        // ============================================================
        double wool = 7813;
        double carpet = wool * 0.4;
        String[] colors = {"WHITE", "ORANGE", "MAGENTA", "LIGHT_BLUE", "YELLOW", "LIME", "PINK",
                "GRAY", "LIGHT_GRAY", "CYAN", "PURPLE", "BLUE", "BROWN", "GREEN", "RED", "BLACK"};

        for (String color : colors) {
            v(Material.valueOf(color + "_WOOL"), wool, wool*0.4);
            v(Material.valueOf(color + "_CARPET"), carpet, carpet*0.4);
            v(Material.valueOf(color + "_CONCRETE"), 8000, 3200);
            v(Material.valueOf(color + "_CONCRETE_POWDER"), 7000, 2800);
            try { v(Material.valueOf(color + "_TERRACOTTA"), 10938, 4375); } catch (Exception ignored) {}
            try { v(Material.valueOf(color + "_GLAZED_TERRACOTTA"), 13000, 5200); } catch (Exception ignored) {}
            try { v(Material.valueOf(color + "_STAINED_GLASS"), 11000, 4400); } catch (Exception ignored) {}
            try { v(Material.valueOf(color + "_STAINED_GLASS_PANE"), 4000, 1600); } catch (Exception ignored) {}
            try { v(Material.valueOf(color + "_BED"), 15000, 6000); } catch (Exception ignored) {}
            try { v(Material.valueOf(color + "_BANNER"), 20000, 8000); } catch (Exception ignored) {}
            try { v(Material.valueOf(color + "_DYE"), 3000, 1200); } catch (Exception ignored) {}
            try { v(Material.valueOf(color + "_CANDLE"), 5000, 2000); } catch (Exception ignored) {}
            try { v(Material.valueOf(color + "_SHULKER_BOX"), 500000, 200000); } catch (Exception ignored) {}
        }
        v(Material.TERRACOTTA, 10938, 4375);
        v(Material.SHULKER_BOX, 500000, 200000);

        // ============================================================
        // VETRO BASE
        // ============================================================
        v(Material.GLASS, 10938, 4375);
        v(Material.GLASS_PANE, 4000, 1600);
        v(Material.TINTED_GLASS, 15000, 6000);

        // ============================================================
        // GHIACCIO E NEVE
        // ============================================================
        v(Material.ICE, 7813, 3125);
        v(Material.PACKED_ICE, 15625, 6250);
        v(Material.BLUE_ICE, 31250, 12500);
        v(Material.SNOW_BLOCK, 6250, 2500);
        v(Material.SNOW, 2000, 800);
        v(Material.POWDER_SNOW_BUCKET, 8000, 3200);

        // ============================================================
        // OSSIDIANA
        // ============================================================
        v(Material.OBSIDIAN, 78125, 31250);
        v(Material.CRYING_OBSIDIAN, 100000, 40000);

        // ============================================================
        // PRISMARINE
        // ============================================================
        double pris = 15000;
        v(Material.PRISMARINE, pris, pris*0.4);
        v(Material.PRISMARINE_BRICKS, pris*1.3, pris*0.52);
        v(Material.DARK_PRISMARINE, pris*1.6, pris*0.64);
        v(Material.SEA_LANTERN, 30000, 12000);
        v(Material.PRISMARINE_SHARD, 5000, 2000);
        v(Material.PRISMARINE_CRYSTALS, 8000, 3200);
        v(Material.PRISMARINE_STAIRS, pris*0.6, pris*0.24);
        v(Material.PRISMARINE_SLAB, pris*0.3, pris*0.12);
        v(Material.PRISMARINE_BRICK_STAIRS, pris*0.78, pris*0.31);
        v(Material.PRISMARINE_BRICK_SLAB, pris*0.39, pris*0.16);
        v(Material.DARK_PRISMARINE_STAIRS, pris*0.96, pris*0.38);
        v(Material.DARK_PRISMARINE_SLAB, pris*0.48, pris*0.19);

        // ============================================================
        // AMETISTA
        // ============================================================
        v(Material.AMETHYST_SHARD, 62500, 25000);
        v(Material.AMETHYST_BLOCK, 250000, 100000);
        v(Material.BUDDING_AMETHYST, -1, 500000);
        v(Material.AMETHYST_CLUSTER, -1, 80000);
        v(Material.LARGE_AMETHYST_BUD, -1, 50000);
        v(Material.MEDIUM_AMETHYST_BUD, -1, 30000);
        v(Material.SMALL_AMETHYST_BUD, -1, 20000);

        // ============================================================
        // RAME
        // ============================================================
        double copper = 30000;
        v(Material.COPPER_INGOT, copper, copper*0.4);
        v(Material.RAW_COPPER, copper*0.8, copper*0.32);
        v(Material.COPPER_ORE, copper*1.1, copper*0.44);
        v(Material.DEEPSLATE_COPPER_ORE, copper*1.2, copper*0.48);
        v(Material.COPPER_BLOCK, copper*9, copper*3.6);
        v(Material.EXPOSED_COPPER, copper*8, copper*3.2);
        v(Material.WEATHERED_COPPER, copper*7, copper*2.8);
        v(Material.OXIDIZED_COPPER, copper*6, copper*2.4);
        v(Material.WAXED_COPPER_BLOCK, copper*9.5, copper*3.8);
        v(Material.WAXED_EXPOSED_COPPER, copper*8.5, copper*3.4);
        v(Material.WAXED_WEATHERED_COPPER, copper*7.5, copper*3.0);
        v(Material.WAXED_OXIDIZED_COPPER, copper*6.5, copper*2.6);
        v(Material.CUT_COPPER, copper*8, copper*3.2);
        v(Material.EXPOSED_CUT_COPPER, copper*7, copper*2.8);
        v(Material.WEATHERED_CUT_COPPER, copper*6, copper*2.4);
        v(Material.OXIDIZED_CUT_COPPER, copper*5, copper*2.0);
        v(Material.WAXED_CUT_COPPER, copper*8.5, copper*3.4);
        v(Material.WAXED_EXPOSED_CUT_COPPER, copper*7.5, copper*3.0);
        v(Material.WAXED_WEATHERED_CUT_COPPER, copper*6.5, copper*2.6);
        v(Material.WAXED_OXIDIZED_CUT_COPPER, copper*5.5, copper*2.2);
        v(Material.CUT_COPPER_STAIRS, copper*4.8, copper*1.92);
        v(Material.EXPOSED_CUT_COPPER_STAIRS, copper*4.2, copper*1.68);
        v(Material.WEATHERED_CUT_COPPER_STAIRS, copper*3.6, copper*1.44);
        v(Material.OXIDIZED_CUT_COPPER_STAIRS, copper*3.0, copper*1.2);
        v(Material.WAXED_CUT_COPPER_STAIRS, copper*5.1, copper*2.04);
        v(Material.WAXED_EXPOSED_CUT_COPPER_STAIRS, copper*4.5, copper*1.8);
        v(Material.WAXED_WEATHERED_CUT_COPPER_STAIRS, copper*3.9, copper*1.56);
        v(Material.WAXED_OXIDIZED_CUT_COPPER_STAIRS, copper*3.3, copper*1.32);
        v(Material.CUT_COPPER_SLAB, copper*2.4, copper*0.96);
        v(Material.EXPOSED_CUT_COPPER_SLAB, copper*2.1, copper*0.84);
        v(Material.WEATHERED_CUT_COPPER_SLAB, copper*1.8, copper*0.72);
        v(Material.OXIDIZED_CUT_COPPER_SLAB, copper*1.5, copper*0.6);
        v(Material.WAXED_CUT_COPPER_SLAB, copper*2.55, copper*1.02);
        v(Material.WAXED_EXPOSED_CUT_COPPER_SLAB, copper*2.25, copper*0.9);
        v(Material.WAXED_WEATHERED_CUT_COPPER_SLAB, copper*1.95, copper*0.78);
        v(Material.WAXED_OXIDIZED_CUT_COPPER_SLAB, copper*1.65, copper*0.66);
        v(Material.LIGHTNING_ROD, copper*3, copper*1.2);
        v(Material.COPPER_BULB, copper*2, copper*0.8);
        v(Material.EXPOSED_COPPER_BULB, copper*1.8, copper*0.72);
        v(Material.WEATHERED_COPPER_BULB, copper*1.6, copper*0.64);
        v(Material.OXIDIZED_COPPER_BULB, copper*1.4, copper*0.56);
        v(Material.WAXED_COPPER_BULB, copper*2.2, copper*0.88);
        v(Material.WAXED_EXPOSED_COPPER_BULB, copper*2.0, copper*0.8);
        v(Material.WAXED_WEATHERED_COPPER_BULB, copper*1.8, copper*0.72);
        v(Material.WAXED_OXIDIZED_COPPER_BULB, copper*1.6, copper*0.64);
        v(Material.COPPER_DOOR, copper*2, copper*0.8);
        v(Material.EXPOSED_COPPER_DOOR, copper*1.8, copper*0.72);
        v(Material.WEATHERED_COPPER_DOOR, copper*1.6, copper*0.64);
        v(Material.OXIDIZED_COPPER_DOOR, copper*1.4, copper*0.56);
        v(Material.WAXED_COPPER_DOOR, copper*2.2, copper*0.88);
        v(Material.WAXED_EXPOSED_COPPER_DOOR, copper*2.0, copper*0.8);
        v(Material.WAXED_WEATHERED_COPPER_DOOR, copper*1.8, copper*0.72);
        v(Material.WAXED_OXIDIZED_COPPER_DOOR, copper*1.6, copper*0.64);
        v(Material.COPPER_TRAPDOOR, copper*2, copper*0.8);
        v(Material.EXPOSED_COPPER_TRAPDOOR, copper*1.8, copper*0.72);
        v(Material.WEATHERED_COPPER_TRAPDOOR, copper*1.6, copper*0.64);
        v(Material.OXIDIZED_COPPER_TRAPDOOR, copper*1.4, copper*0.56);
        v(Material.WAXED_COPPER_TRAPDOOR, copper*2.2, copper*0.88);
        v(Material.WAXED_EXPOSED_COPPER_TRAPDOOR, copper*2.0, copper*0.8);
        v(Material.WAXED_WEATHERED_COPPER_TRAPDOOR, copper*1.8, copper*0.72);
        v(Material.WAXED_OXIDIZED_COPPER_TRAPDOOR, copper*1.6, copper*0.64);
        v(Material.COPPER_GRATE, copper*1.5, copper*0.6);
        v(Material.EXPOSED_COPPER_GRATE, copper*1.3, copper*0.52);
        v(Material.WEATHERED_COPPER_GRATE, copper*1.1, copper*0.44);
        v(Material.OXIDIZED_COPPER_GRATE, copper*0.9, copper*0.36);
        v(Material.WAXED_COPPER_GRATE, copper*1.6, copper*0.64);
        v(Material.WAXED_EXPOSED_COPPER_GRATE, copper*1.4, copper*0.56);
        v(Material.WAXED_WEATHERED_COPPER_GRATE, copper*1.2, copper*0.48);
        v(Material.WAXED_OXIDIZED_COPPER_GRATE, copper*1.0, copper*0.4);

        // ============================================================
        // MINERALI
        // ============================================================
        v(Material.COAL, 15625, 6250);
        v(Material.COAL_ORE, 18750, 7500);
        v(Material.DEEPSLATE_COAL_ORE, 20000, 8000);
        v(Material.COAL_BLOCK, 140625, 56250);
        v(Material.IRON_INGOT, 78125, 31250);
        v(Material.RAW_IRON, 70000, 28000);
        v(Material.IRON_ORE, 93750, 37500);
        v(Material.DEEPSLATE_IRON_ORE, 100000, 40000);
        v(Material.IRON_BLOCK, 703125, 281250);
        v(Material.IRON_NUGGET, 8681, 3472);
        v(Material.GOLD_INGOT, 156250, 62500);
        v(Material.RAW_GOLD, 140000, 56000);
        v(Material.GOLD_ORE, 187500, 75000);
        v(Material.DEEPSLATE_GOLD_ORE, 200000, 80000);
        v(Material.GOLD_BLOCK, 1406250, 562500);
        v(Material.GOLD_NUGGET, 17361, 6944);
        v(Material.DIAMOND, 781250, 312500);
        v(Material.DIAMOND_ORE, 937500, 375000);
        v(Material.DEEPSLATE_DIAMOND_ORE, 1000000, 400000);
        v(Material.DIAMOND_BLOCK, 7031250, 2812500);
        v(Material.EMERALD, 468750, 187500);
        v(Material.EMERALD_ORE, 562500, 225000);
        v(Material.DEEPSLATE_EMERALD_ORE, 600000, 240000);
        v(Material.EMERALD_BLOCK, 4218750, 1687500);
        v(Material.LAPIS_LAZULI, 46875, 18750);
        v(Material.LAPIS_ORE, 56250, 22500);
        v(Material.DEEPSLATE_LAPIS_ORE, 60000, 24000);
        v(Material.LAPIS_BLOCK, 421875, 168750);
        v(Material.REDSTONE, 31250, 12500);
        v(Material.REDSTONE_ORE, 37500, 15000);
        v(Material.DEEPSLATE_REDSTONE_ORE, 40000, 16000);
        v(Material.REDSTONE_BLOCK, 281250, 112500);
        v(Material.QUARTZ, 39063, 15625);
        v(Material.NETHER_QUARTZ_ORE, 46875, 18750);
        v(Material.QUARTZ_BLOCK, 156250, 62500);
        v(Material.SMOOTH_QUARTZ, 160000, 64000);
        v(Material.CHISELED_QUARTZ_BLOCK, 165000, 66000);
        v(Material.QUARTZ_PILLAR, 165000, 66000);
        v(Material.QUARTZ_STAIRS, 93750, 37500);
        v(Material.QUARTZ_SLAB, 46875, 18750);
        v(Material.SMOOTH_QUARTZ_STAIRS, 96000, 38400);
        v(Material.SMOOTH_QUARTZ_SLAB, 48000, 19200);
        v(Material.NETHERITE_INGOT, -1, 2000000);
        v(Material.NETHERITE_SCRAP, -1, 500000);
        v(Material.ANCIENT_DEBRIS, -1, 1000000);
        v(Material.NETHERITE_BLOCK, -1, 18000000);

        // ============================================================
        // NATURA - PIANTE FIORI FUNGHI
        // ============================================================
        v(Material.SEAGRASS, 3125, 1250);
        v(Material.TALL_SEAGRASS, 5000, 2000);
        v(Material.KELP, 2344, 938);
        v(Material.DRIED_KELP_BLOCK, 18000, 7200);
        v(Material.DRIED_KELP, 2000, 800);
        v(Material.SUGAR_CANE, 4688, 1875);
        v(Material.BAMBOO, 3906, 1563);
        v(Material.CACTUS, 6250, 2500);
        v(Material.PUMPKIN, 7813, 3125);
        v(Material.CARVED_PUMPKIN, 8000, 3200);
        v(Material.JACK_O_LANTERN, 10000, 4000);
        v(Material.MELON, 3000, 1200);
        v(Material.MELON_SLICE, 400, 160);
        v(Material.VINE, 4688, 1875);
        v(Material.LILY_PAD, 7813, 3125);
        v(Material.DANDELION, 3125, 1250);
        v(Material.POPPY, 3125, 1250);
        v(Material.BLUE_ORCHID, 6250, 2500);
        v(Material.ALLIUM, 6250, 2500);
        v(Material.AZURE_BLUET, 5000, 2000);
        v(Material.RED_TULIP, 5000, 2000);
        v(Material.ORANGE_TULIP, 5000, 2000);
        v(Material.WHITE_TULIP, 5000, 2000);
        v(Material.PINK_TULIP, 5000, 2000);
        v(Material.OXEYE_DAISY, 5000, 2000);
        v(Material.CORNFLOWER, 5000, 2000);
        v(Material.LILY_OF_THE_VALLEY, 6250, 2500);
        v(Material.WITHER_ROSE, 50000, 20000);
        v(Material.SUNFLOWER, 7500, 3000);
        v(Material.LILAC, 7500, 3000);
        v(Material.ROSE_BUSH, 7500, 3000);
        v(Material.PEONY, 7500, 3000);
        v(Material.TALL_GRASS, 2000, 800);
        v(Material.LARGE_FERN, 2000, 800);
        v(Material.FERN, 2000, 800);
        v(Material.DEAD_BUSH, 1500, 600);
        v(Material.RED_MUSHROOM, 5000, 2000);
        v(Material.BROWN_MUSHROOM, 5000, 2000);
        v(Material.MUSHROOM_STEM, 4000, 1600);
        v(Material.RED_MUSHROOM_BLOCK, 4000, 1600);
        v(Material.BROWN_MUSHROOM_BLOCK, 4000, 1600);
        v(Material.SPORE_BLOSSOM, 20000, 8000);
        v(Material.AZALEA, 10000, 4000);
        v(Material.FLOWERING_AZALEA, 15000, 6000);
        v(Material.AZALEA_LEAVES, 4000, 1600);
        v(Material.FLOWERING_AZALEA_LEAVES, 5000, 2000);
        v(Material.MOSS_BLOCK, 8000, 3200);
        v(Material.MOSS_CARPET, 3000, 1200);
        v(Material.BIG_DRIPLEAF, 15000, 6000);
        v(Material.SMALL_DRIPLEAF, 10000, 4000);
        v(Material.HANGING_ROOTS, 5000, 2000);
        v(Material.GLOW_LICHEN, 8000, 3200);
        v(Material.SWEET_BERRIES, 5000, 2000);
        v(Material.GLOW_BERRIES, 6000, 2400);
        v(Material.COCOA_BEANS, 8000, 3200);
        v(Material.NETHER_WART, 2000000, 800000);
        v(Material.CHORUS_FRUIT, 46875, 18750);
        v(Material.CHORUS_FLOWER, 78125, 31250);
        v(Material.CHORUS_PLANT, 50000, 20000);

        // SEMI E AGRICOLTURA
        v(Material.WHEAT, 5000, 2000);
        v(Material.WHEAT_SEEDS, 2000, 800);
        v(Material.POTATO, 4000, 1600);
        v(Material.CARROT, 4000, 1600);
        v(Material.BEETROOT, 3000, 1200);
        v(Material.BEETROOT_SEEDS, 2000, 800);
        v(Material.PUMPKIN_SEEDS, 3000, 1200);
        v(Material.MELON_SEEDS, 3000, 1200);
        v(Material.NETHER_WART_BLOCK, 20000, 8000);
        v(Material.WARPED_WART_BLOCK, 20000, 8000);
        v(Material.TORCHFLOWER, 25000, 10000);
        v(Material.TORCHFLOWER_SEEDS, 10000, 4000);
        v(Material.PITCHER_PLANT, 30000, 12000);
        v(Material.PITCHER_POD, 12000, 4800);
        v(Material.PINK_PETALS, 8000, 3200);

        // ============================================================
        // MOB DROP E MATERIALI ORGANICI
        // ============================================================
        v(Material.STRING, 9375, 3750);
        v(Material.FEATHER, 7813, 3125);
        v(Material.LEATHER, 15625, 6250);
        v(Material.INK_SAC, 12500, 5000);
        v(Material.GLOW_INK_SAC, 25000, 10000);
        v(Material.SLIME_BALL, 31250, 12500);
        v(Material.MAGMA_CREAM, 46875, 18750);
        v(Material.SPIDER_EYE, 15000, 6000);
        v(Material.FERMENTED_SPIDER_EYE, 25000, 10000);
        v(Material.GUNPOWDER, 20000, 8000);
        v(Material.BONE, 10000, 4000);
        v(Material.BONE_MEAL, 12500, 5000);
        v(Material.BONE_BLOCK, 90000, 36000);
        v(Material.ROTTEN_FLESH, 2000, 800);
        v(Material.FLINT, 5000, 2000);
        v(Material.ARROW, 5000, 2000);
        v(Material.RABBIT_FOOT, 30000, 12000);
        v(Material.RABBIT_HIDE, 10000, 4000);
        v(Material.TURTLE_SCUTE, 80000, 32000);
        v(Material.NAUTILUS_SHELL, 50000, 20000);
        v(Material.PHANTOM_MEMBRANE, 50000, 20000);
        v(Material.HONEYCOMB, 15000, 6000);
        v(Material.HONEY_BOTTLE, 10000, 4000);
        v(Material.HONEY_BLOCK, 40000, 16000);
        v(Material.HONEYCOMB_BLOCK, 60000, 24000);
        v(Material.EGG, 3000, 1200);
        v(Material.COD, 5000, 2000);
        v(Material.SALMON, 6000, 2400);
        v(Material.TROPICAL_FISH, 8000, 3200);
        v(Material.PUFFERFISH, 10000, 4000);
        v(Material.CLAY_BALL, 2344, 938);
        v(Material.SNOWBALL, 1000, 400);
        v(Material.BREEZE_ROD, 100000, 40000);
        v(Material.WIND_CHARGE, 80000, 32000);
        v(Material.ARMADILLO_SCUTE, 80000, 32000);

        // ============================================================
        // NETHER
        // ============================================================
        v(Material.NETHERRACK, 4688, 1875);
        v(Material.NETHER_BRICK, 12500, 5000);
        double netherbrick_b = 100000;
        v(Material.NETHER_BRICKS, netherbrick_b, netherbrick_b*0.4);
        v(Material.RED_NETHER_BRICKS, netherbrick_b*1.2, netherbrick_b*0.48);
        v(Material.CRACKED_NETHER_BRICKS, netherbrick_b*1.1, netherbrick_b*0.44);
        v(Material.CHISELED_NETHER_BRICKS, netherbrick_b*1.2, netherbrick_b*0.48);
        v(Material.NETHER_BRICK_STAIRS, netherbrick_b*0.6, netherbrick_b*0.24);
        v(Material.NETHER_BRICK_SLAB, netherbrick_b*0.3, netherbrick_b*0.12);
        v(Material.NETHER_BRICK_WALL, netherbrick_b*0.5, netherbrick_b*0.2);
        v(Material.NETHER_BRICK_FENCE, netherbrick_b*0.5, netherbrick_b*0.2);
        v(Material.RED_NETHER_BRICK_STAIRS, netherbrick_b*0.72, netherbrick_b*0.29);
        v(Material.RED_NETHER_BRICK_SLAB, netherbrick_b*0.36, netherbrick_b*0.14);
        v(Material.RED_NETHER_BRICK_WALL, netherbrick_b*0.6, netherbrick_b*0.24);
        v(Material.BLAZE_ROD, 78125, 31250);
        v(Material.BLAZE_POWDER, 46875, 18750);
        v(Material.GHAST_TEAR, 125000, 50000);
        v(Material.SOUL_SAND, 15625, 6250);
        v(Material.SOUL_SOIL, 12000, 4800);
        v(Material.GLOWSTONE_DUST, 31250, 12500);
        v(Material.GLOWSTONE, 62500, 25000);
        v(Material.WITHER_SKELETON_SKULL, 312500, 125000);
        v(Material.CRIMSON_STEM, 7813, 3125);
        v(Material.WARPED_STEM, 7813, 3125);
        v(Material.STRIPPED_CRIMSON_STEM, 7000, 2800);
        v(Material.STRIPPED_WARPED_STEM, 7000, 2800);
        v(Material.CRIMSON_HYPHAE, 7000, 2800);
        v(Material.WARPED_HYPHAE, 7000, 2800);
        v(Material.STRIPPED_CRIMSON_HYPHAE, 6500, 2600);
        v(Material.STRIPPED_WARPED_HYPHAE, 6500, 2600);
        v(Material.CRIMSON_PLANKS, 6000, 2400);
        v(Material.WARPED_PLANKS, 6000, 2400);
        v(Material.CRIMSON_STAIRS, 3600, 1440);
        v(Material.WARPED_STAIRS, 3600, 1440);
        v(Material.CRIMSON_SLAB, 1800, 720);
        v(Material.WARPED_SLAB, 1800, 720);
        v(Material.CRIMSON_FENCE, 3000, 1200);
        v(Material.WARPED_FENCE, 3000, 1200);
        v(Material.CRIMSON_FENCE_GATE, 4800, 1920);
        v(Material.WARPED_FENCE_GATE, 4800, 1920);
        v(Material.CRIMSON_DOOR, 4800, 1920);
        v(Material.WARPED_DOOR, 4800, 1920);
        v(Material.CRIMSON_TRAPDOOR, 4800, 1920);
        v(Material.WARPED_TRAPDOOR, 4800, 1920);
        v(Material.CRIMSON_PRESSURE_PLATE, 2400, 960);
        v(Material.WARPED_PRESSURE_PLATE, 2400, 960);
        v(Material.CRIMSON_BUTTON, 1800, 720);
        v(Material.WARPED_BUTTON, 1800, 720);
        v(Material.CRIMSON_SIGN, 3000, 1200);
        v(Material.WARPED_SIGN, 3000, 1200);
        v(Material.CRIMSON_HANGING_SIGN, 3600, 1440);
        v(Material.WARPED_HANGING_SIGN, 3600, 1440);
        v(Material.CRIMSON_NYLIUM, 8000, 3200);
        v(Material.WARPED_NYLIUM, 8000, 3200);
        v(Material.SHROOMLIGHT, 25000, 10000);
        v(Material.BASALT, 5000, 2000);
        v(Material.SMOOTH_BASALT, 6000, 2400);
        v(Material.POLISHED_BASALT, 6000, 2400);
        v(Material.BLACKSTONE, 6000, 2400);
        v(Material.NETHER_GOLD_ORE, 100000, 40000);
        v(Material.NETHER_QUARTZ_ORE, 46875, 18750);
        v(Material.MAGMA_BLOCK, 15000, 6000);
        v(Material.WEEPING_VINES, 5000, 2000);
        v(Material.TWISTING_VINES, 5000, 2000);
        v(Material.CRIMSON_ROOTS, 3000, 1200);
        v(Material.WARPED_ROOTS, 3000, 1200);
        v(Material.CRIMSON_FUNGUS, 5000, 2000);
        v(Material.WARPED_FUNGUS, 5000, 2000);

        // ============================================================
        // END
        // ============================================================
        double endstone = 31250;
        v(Material.END_STONE, endstone, endstone*0.4);
        v(Material.END_STONE_BRICKS, endstone*1.1, endstone*0.44);
        v(Material.END_STONE_BRICK_STAIRS, endstone*0.66, endstone*0.26);
        v(Material.END_STONE_BRICK_SLAB, endstone*0.33, endstone*0.13);
        v(Material.END_STONE_BRICK_WALL, endstone*0.55, endstone*0.22);
        v(Material.PURPUR_BLOCK, 46875, 18750);
        v(Material.PURPUR_PILLAR, 48000, 19200);
        v(Material.PURPUR_STAIRS, 28125, 11250);
        v(Material.PURPUR_SLAB, 14063, 5625);
        v(Material.ENDER_PEARL, 78125, 31250);
        v(Material.ENDER_EYE, 156250, 62500);
        v(Material.DRAGON_BREATH, 234375, 93750);
        v(Material.SHULKER_SHELL, 312500, 125000);
        v(Material.END_ROD, 20000, 8000);
        v(Material.END_CRYSTAL, -1, 500000);
        v(Material.DRAGON_EGG, -1, 50000000);
        v(Material.CHORUS_FRUIT, 46875, 18750);
        v(Material.POPPED_CHORUS_FRUIT, 60000, 24000);

        // ============================================================
        // SCULK
        // ============================================================
        v(Material.SCULK, 20000, 8000);
        v(Material.SCULK_SENSOR, 100000, 40000);
        v(Material.CALIBRATED_SCULK_SENSOR, 200000, 80000);
        v(Material.SCULK_CATALYST, 200000, 80000);
        v(Material.SCULK_SHRIEKER, 300000, 120000);
        v(Material.SCULK_VEIN, 15000, 6000);

        // ============================================================
        // LUCE E ILLUMINAZIONE
        // ============================================================
        v(Material.TORCH, 2000, 800);
        v(Material.SOUL_TORCH, 3000, 1200);
        v(Material.LANTERN, 5000, 2000);
        v(Material.SOUL_LANTERN, 6000, 2400);
        v(Material.GLOWSTONE, 62500, 25000);
        v(Material.SEA_LANTERN, 30000, 12000);
        v(Material.SHROOMLIGHT, 25000, 10000);
        v(Material.OCHRE_FROGLIGHT, 50000, 20000);
        v(Material.VERDANT_FROGLIGHT, 50000, 20000);
        v(Material.PEARLESCENT_FROGLIGHT, 50000, 20000);
        v(Material.GLOW_ITEM_FRAME, 10000, 4000);

        // CANDELE
        for (String color : colors) {
            try { v(Material.valueOf(color + "_CANDLE"), 5000, 2000); } catch (Exception ignored) {}
        }
        v(Material.CANDLE, 5000, 2000);

        // ============================================================
        // RAGNATELE E VARI
        // ============================================================
        v(Material.COBWEB, 8000, 3200);
        v(Material.SPONGE, -1, 50000);
        v(Material.WET_SPONGE, -1, 40000);

        // ============================================================
        // CONTENITORI E BLOCCHI FUNZIONALI
        // ============================================================
        v(Material.CHEST, 15000, 6000);
        v(Material.TRAPPED_CHEST, 18000, 7200);
        v(Material.BARREL, 15000, 6000);
        v(Material.CRAFTING_TABLE, 10000, 4000);
        v(Material.FURNACE, 12000, 4800);
        v(Material.SMOKER, 20000, 8000);
        v(Material.BLAST_FURNACE, 25000, 10000);
        v(Material.ANVIL, -1, 150000);
        v(Material.CHIPPED_ANVIL, -1, 100000);
        v(Material.DAMAGED_ANVIL, -1, 50000);
        v(Material.ENCHANTING_TABLE, -1, 300000);
        v(Material.BREWING_STAND, -1, 50000);
        v(Material.CAULDRON, -1, 20000);
        v(Material.DROPPER, 10000, 4000);
        v(Material.DISPENSER, 15000, 6000);
        v(Material.HOPPER, 40000, 16000);
        v(Material.PISTON, 20000, 8000);
        v(Material.STICKY_PISTON, 30000, 12000);
        v(Material.OBSERVER, 20000, 8000);
        v(Material.COMPARATOR, 15000, 6000);
        v(Material.REPEATER, 10000, 4000);
        v(Material.LEVER, 3000, 1200);
        v(Material.DAYLIGHT_DETECTOR, 15000, 6000);
        v(Material.TRIPWIRE_HOOK, 5000, 2000);
        v(Material.JUKEBOX, 100000, 40000);
        v(Material.NOTE_BLOCK, 10000, 4000);
        v(Material.BELL, -1, 200000);
        v(Material.BEACON, -1, 5000000);
        v(Material.CONDUIT, -1, 2000000);
        v(Material.GRINDSTONE, 30000, 12000);
        v(Material.CARTOGRAPHY_TABLE, 15000, 6000);
        v(Material.FLETCHING_TABLE, 15000, 6000);
        v(Material.SMITHING_TABLE, 15000, 6000);
        v(Material.STONECUTTER, 15000, 6000);
        v(Material.LOOM, 15000, 6000);
        v(Material.COMPOSTER, 10000, 4000);
        v(Material.LECTERN, 20000, 8000);
        v(Material.TARGET, 10000, 4000);
        v(Material.RESPAWN_ANCHOR, 200000, 80000);
        v(Material.LODESTONE, 100000, 40000);
        v(Material.SOUL_CAMPFIRE, 15000, 6000);
        v(Material.CAMPFIRE, 10000, 4000);
        v(Material.FLOWER_POT, 3000, 1200);
        v(Material.ITEM_FRAME, 5000, 2000);
        v(Material.PAINTING, 5000, 2000);
        v(Material.ARMOR_STAND, 20000, 8000);
        v(Material.END_PORTAL_FRAME, -1, 10000000);
        v(Material.ENDER_CHEST, 500000, 200000);
        v(Material.DECORATED_POT, 10000, 4000);
        v(Material.CHISELED_BOOKSHELF, 30000, 12000);
        v(Material.BOOKSHELF, 20000, 8000);

        // ============================================================
        // REDSTONE
        // ============================================================
        v(Material.REDSTONE, 31250, 12500);
        v(Material.REDSTONE_BLOCK, 281250, 112500);
        v(Material.REDSTONE_LAMP, 20000, 8000);
        v(Material.REDSTONE_TORCH, 5000, 2000);
        v(Material.PISTON, 20000, 8000);
        v(Material.STICKY_PISTON, 30000, 12000);

        // ============================================================
        // OGGETTI VARI
        // ============================================================
        v(Material.PAPER, 3000, 1200);
        v(Material.BOOK, 10000, 4000);
        v(Material.WRITTEN_BOOK, -1, 5000);
        v(Material.WRITABLE_BOOK, 8000, 3200);
        v(Material.COMPASS, 20000, 8000);
        v(Material.CLOCK, 20000, 8000);
        v(Material.MAP, 5000, 2000);
        v(Material.SPYGLASS, 50000, 20000);
        v(Material.BUCKET, 30000, 12000);
        v(Material.WATER_BUCKET, 35000, 14000);
        v(Material.LAVA_BUCKET, 50000, 20000);
        v(Material.MILK_BUCKET, 20000, 8000);
        v(Material.POWDER_SNOW_BUCKET, 8000, 3200);
        v(Material.SADDLE, 100000, 40000);
        v(Material.LEAD, 15000, 6000);
        v(Material.NAME_TAG, 50000, 20000);
        v(Material.FIREWORK_ROCKET, 5000, 2000);
        v(Material.FIREWORK_STAR, 8000, 3200);
        v(Material.TNT, -1, 50000);
        v(Material.FIRE_CHARGE, -1, 10000);
        v(Material.FLINT, 5000, 2000);
        v(Material.FLINT_AND_STEEL, -1, 15000);
        v(Material.SHEARS, -1, 30000);
        v(Material.FISHING_ROD, -1, 20000);
        v(Material.BOW, -1, 40000);
        v(Material.CROSSBOW, -1, 60000);
        v(Material.SHIELD, -1, 80000);
        v(Material.ARROW, 5000, 2000);
        v(Material.SPECTRAL_ARROW, 8000, 3200);
        v(Material.TIPPED_ARROW, -1, 10000);
        v(Material.RECOVERY_COMPASS, -1, 300000);
        v(Material.ECHO_SHARD, -1, 500000);
        v(Material.DISC_FRAGMENT_5, -1, 200000);
        v(Material.BUNDLE, -1, 30000);

        // STRUMENTI - solo vendita
        String[] toolMats = {"WOODEN", "STONE", "IRON", "GOLDEN", "DIAMOND", "NETHERITE"};
        double[] toolVals = {2000, 5000, 50000, 30000, 500000, 2000000};
        String[] toolTypes = {"SWORD", "PICKAXE", "AXE", "SHOVEL", "HOE"};
        for (int i = 0; i < toolMats.length; i++) {
            for (String type : toolTypes) {
                try { v(Material.valueOf(toolMats[i] + "_" + type), -1, toolVals[i]); } catch (Exception ignored) {}
            }
        }

        // ARMATURE - solo vendita
        String[] armorMats = {"LEATHER", "IRON", "GOLDEN", "CHAINMAIL", "DIAMOND", "NETHERITE"};
        double[] armorVals = {5000, 60000, 40000, 45000, 600000, 2500000};
        String[] armorPieces = {"HELMET", "CHESTPLATE", "LEGGINGS", "BOOTS"};
        double[] armorMult = {1.0, 1.5, 1.4, 1.0};
        for (int i = 0; i < armorMats.length; i++) {
            for (int j = 0; j < armorPieces.length; j++) {
                try { v(Material.valueOf(armorMats[i] + "_" + armorPieces[j]), -1, (long)(armorVals[i] * armorMult[j])); } catch (Exception ignored) {}
            }
        }
        v(Material.TURTLE_HELMET, -1, 200000);
        v(Material.WOLF_ARMOR, -1, 300000);

        // CIBO - solo vendita
        v(Material.BREAD, -1, 3000);
        v(Material.BEEF, -1, 2000);
        v(Material.COOKED_BEEF, -1, 5000);
        v(Material.CHICKEN, -1, 1500);
        v(Material.COOKED_CHICKEN, -1, 4000);
        v(Material.PORKCHOP, -1, 2000);
        v(Material.COOKED_PORKCHOP, -1, 5000);
        v(Material.MUTTON, -1, 1800);
        v(Material.COOKED_MUTTON, -1, 4500);
        v(Material.RABBIT, -1, 1600);
        v(Material.COOKED_RABBIT, -1, 4000);
        v(Material.COOKED_COD, -1, 3500);
        v(Material.COOKED_SALMON, -1, 4000);
        v(Material.GOLDEN_APPLE, -1, 100000);
        v(Material.ENCHANTED_GOLDEN_APPLE, -1, 5000000);
        v(Material.GOLDEN_CARROT, -1, 50000);
        v(Material.PUMPKIN_PIE, -1, 8000);
        v(Material.CAKE, -1, 15000);
        v(Material.COOKIE, -1, 2000);
        v(Material.BAKED_POTATO, -1, 3000);
        v(Material.MUSHROOM_STEW, -1, 8000);
        v(Material.RABBIT_STEW, -1, 10000);
        v(Material.BEETROOT_SOUP, -1, 8000);
        v(Material.SUSPICIOUS_STEW, -1, 10000);
        v(Material.APPLE, 8000, 3200);
        v(Material.HONEY_BOTTLE, 10000, 4000);
        v(Material.MILK_BUCKET, 20000, 8000);
        v(Material.TROPICAL_FISH, 8000, 3200);

        // POZIONI - solo vendita
        v(Material.POTION, -1, 20000);
        v(Material.SPLASH_POTION, -1, 25000);
        v(Material.LINGERING_POTION, -1, 35000);
        v(Material.EXPERIENCE_BOTTLE, -1, 50000);

        // LIBRI - solo vendita
        v(Material.ENCHANTED_BOOK, -1, 100000);
        v(Material.KNOWLEDGE_BOOK, -1, 500000);

        // MUSICA - solo vendita
        v(Material.DISC_FRAGMENT_5, -1, 200000);
        v(Material.MUSIC_DISC_5, -1, 1000000);
        v(Material.MUSIC_DISC_11, -1, 500000);
        v(Material.MUSIC_DISC_13, -1, 300000);
        v(Material.MUSIC_DISC_BLOCKS, -1, 300000);
        v(Material.MUSIC_DISC_CAT, -1, 300000);
        v(Material.MUSIC_DISC_CHIRP, -1, 300000);
        v(Material.MUSIC_DISC_FAR, -1, 300000);
        v(Material.MUSIC_DISC_MALL, -1, 300000);
        v(Material.MUSIC_DISC_MELLOHI, -1, 300000);
        v(Material.MUSIC_DISC_OTHERSIDE, -1, 500000);
        v(Material.MUSIC_DISC_PIGSTEP, -1, 800000);
        v(Material.MUSIC_DISC_RELIC, -1, 500000);
        v(Material.MUSIC_DISC_STAL, -1, 300000);
        v(Material.MUSIC_DISC_STRAD, -1, 300000);
        v(Material.MUSIC_DISC_WAIT, -1, 300000);
        v(Material.MUSIC_DISC_WARD, -1, 300000);
        v(Material.MUSIC_DISC_CREATOR, -1, 600000);
        v(Material.MUSIC_DISC_CREATOR_MUSIC_BOX, -1, 400000);
        v(Material.MUSIC_DISC_PRECIPICE, -1, 600000);

        // RARI
        v(Material.TOTEM_OF_UNDYING, 7812500, 3125000);
        v(Material.ELYTRA, 15625000, 6250000);
        v(Material.HEART_OF_THE_SEA, 3125000, 1250000);
        v(Material.NETHER_STAR, 4687500, 1875000);
        v(Material.TRIDENT, 2343750, 937500);
        v(Material.BEACON, -1, 5000000);
        v(Material.CONDUIT, -1, 2000000);
        v(Material.END_CRYSTAL, -1, 500000);
        v(Material.DRAGON_EGG, -1, 50000000);

        // TESCHI E TROFEI
        v(Material.SKELETON_SKULL, 50000, 20000);
        v(Material.WITHER_SKELETON_SKULL, 312500, 125000);
        v(Material.ZOMBIE_HEAD, 30000, 12000);
        v(Material.CREEPER_HEAD, 30000, 12000);
        v(Material.PIGLIN_HEAD, 30000, 12000);
        v(Material.PLAYER_HEAD, 20000, 8000);

        // PORTALI E SPECIALI
        v(Material.NETHER_PORTAL, -1, 0);
        v(Material.END_PORTAL_FRAME, -1, 10000000);
        v(Material.STRUCTURE_VOID, -1, 0);
    }

    private static void v(Material mat, double buy, double sell) {
        // Prezzo vendita = 60% del prezzo acquisto se acquistabile, altrimenti usa sell diretto
        double actualSell = buy > 0 ? buy * 0.6 : sell;
        VALUES.put(mat, new double[]{buy, actualSell});
    }

    public static double getBuyPrice(Material mat) {
        double[] v = VALUES.get(mat);
        return v != null ? v[0] : -1;
    }

    public static double getSellPrice(Material mat) {
        double[] v = VALUES.get(mat);
        return v != null ? v[1] : -1;
    }

    public static boolean hasValue(Material mat) {
        return VALUES.containsKey(mat);
    }
}
