package fr.sonkuun.shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.sonkuun.shop.plugin.ShopPlugin;


public class Shop {
	
	private ShopPlugin plugin;

	public enum ShopType {
		HOMEPAGE("Homepage"),
		BLOCKS("Blocks"),
		ORES("Ores"),
		REDSTONE("Redstone"),
		FOOD("Food"),
		DROPS("Drops"),
		SPAWNER("Spawner"),
		AGRICULTURE("Agriculture"),
		DIVERSE("Diverse"),
		FLOWERS("Flowers");

		private String name;

		private ShopType(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

		public static ShopType fromString(String name) {
			for(ShopType type : ShopType.values()) {
				if(name.equalsIgnoreCase(ChatColor.YELLOW + type.toString()) || name.equalsIgnoreCase(type.toString())) {
					return type;
				}
			}
			
			if(name.equalsIgnoreCase("Go back")) {
				return ShopType.HOMEPAGE;
			}
			
			return null;
		}
	}
	
	public Shop(ShopPlugin plugin) {
		this.plugin = plugin;
	}

	public Inventory createShopInventory(Player player, ShopPlugin plugin, ShopType shopType) {
		switch (shopType) {
		case HOMEPAGE:
			return createHomepageInventory(player, plugin);
		case BLOCKS:
			return createBlocksInventory(player, plugin, 1);
		case AGRICULTURE:
			return createAgricultureInventory(player, plugin);
		case DIVERSE:
			return createDiverseInventory(player, plugin);
		case DROPS:
			return createDropsInventory(player, plugin);
		case FLOWERS:
			return createFlowersInventory(player, plugin);
		case FOOD:
			return createFoodInventory(player, plugin);
		case ORES:
			return createOresInventory(player, plugin);
		case REDSTONE:
			return createRedstoneInventory(player, plugin);
		case SPAWNER:
			return createSpawnerInventory(player, plugin);
		default:
			return null;
		}
	}
	
	public Inventory createShopInventory(Player player, ShopPlugin plugin, ShopType shopType, int page) {
		switch (shopType) {
		case HOMEPAGE:
			return createHomepageInventory(player, plugin);
		case BLOCKS:
			return createBlocksInventory(player, plugin, page);
		case AGRICULTURE:
			return createAgricultureInventory(player, plugin);
		case DIVERSE:
			return createDiverseInventory(player, plugin);
		case DROPS:
			return createDropsInventory(player, plugin);
		case FLOWERS:
			return createFlowersInventory(player, plugin);
		case FOOD:
			return createFoodInventory(player, plugin);
		case ORES:
			return createOresInventory(player, plugin);
		case REDSTONE:
			return createRedstoneInventory(player, plugin);
		case SPAWNER:
			return createSpawnerInventory(player, plugin);
		default:
			return null;
		}
	}

	private Inventory createHomepageInventory(Player player, ShopPlugin plugin) {
		Inventory inventory = Bukkit.createInventory(null, 45, "Shop");

		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName("");
		glass.setItemMeta(glassMeta);
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, glass);
		}
		
		inventory.setItem(12, createSelectionShopItem(Material.GRASS_BLOCK, "Blocks"));
		inventory.setItem(13, createSelectionShopItem(Material.REDSTONE, "Redstone"));
		inventory.setItem(14, createSelectionShopItem(Material.BONE, "Drops"));
		
		inventory.setItem(20, createSelectionShopItem(Material.COOKED_CHICKEN, "Food"));
		inventory.setItem(21, createSelectionShopItem(Material.DIAMOND, "Ores"));
		inventory.setItem(22, createSelectionShopItem(Material.SPAWNER, "Spawner"));
		inventory.setItem(23, createSelectionShopItem(Material.WHEAT, "Agriculture"));
		inventory.setItem(24, createSelectionShopItem(Material.ALLIUM, "Flowers"));
		
		inventory.setItem(31, createSelectionShopItem(Material.DRAGON_BREATH, "Diverse"));

		inventory.setItem(40, createQuitItem());

		return inventory;
	}

	private Inventory createBlocksInventory(Player player, ShopPlugin plugin, int page) {
		Inventory inventory = Bukkit.createInventory(null, 54, "Blocks");

		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, glass);
		}
		
		if(page == 1) {
			
			inventory.setItem(10, createShopItem(Material.GRASS_BLOCK, 64));
			inventory.setItem(11, createShopItem(Material.DIRT, 64));
			inventory.setItem(12, createShopItem(Material.STONE, 64));
			inventory.setItem(13, createShopItem(Material.GRANITE, 64));
			inventory.setItem(14, createShopItem(Material.DIORITE, 64));
			inventory.setItem(15, createShopItem(Material.ANDESITE, 64));
			inventory.setItem(16, createShopItem(Material.GRAVEL, 8));
			inventory.setItem(19, createShopItem(Material.BLACKSTONE, 64));
			inventory.setItem(20, createShopItem(Material.NETHERRACK, 64));
			inventory.setItem(21, createShopItem(Material.NETHER_BRICKS, 64));
			inventory.setItem(22, createShopItem(Material.OAK_LOG, 8));
			inventory.setItem(23, createShopItem(Material.SPRUCE_LOG, 8));
			inventory.setItem(24, createShopItem(Material.BIRCH_LOG, 8));
			inventory.setItem(25, createShopItem(Material.JUNGLE_LOG, 8));
			inventory.setItem(28, createShopItem(Material.ACACIA_LOG, 8));
			inventory.setItem(29, createShopItem(Material.DARK_OAK_LOG, 8));
			inventory.setItem(30, createShopItem(Material.SNOW_BLOCK, 8));
			inventory.setItem(31, createShopItem(Material.PACKED_ICE, 8));
			inventory.setItem(32, createShopItem(Material.BLUE_ICE, 8));
			inventory.setItem(33, createShopItem(Material.SPONGE, 1));
			inventory.setItem(34, createShopItem(Material.OBSIDIAN, 1));
			
			ItemStack nextPage = new ItemStack(Material.PAPER);
			ItemMeta meta = nextPage.getItemMeta();
			meta.setDisplayName("Next page");
			nextPage.setItemMeta(meta);
			inventory.setItem(50, nextPage);
		}
		else if(page == 2) {
			
			inventory.setItem(10, createShopItem(Material.GLOWSTONE, 64));
			inventory.setItem(11, createShopItem(Material.END_STONE, 64));
			inventory.setItem(12, createShopItem(Material.PRISMARINE, 8));
			inventory.setItem(13, createShopItem(Material.DARK_PRISMARINE, 8));
			inventory.setItem(14, createShopItem(Material.OAK_LEAVES, 16));
			inventory.setItem(15, createShopItem(Material.SPRUCE_LEAVES, 16));
			inventory.setItem(16, createShopItem(Material.BIRCH_LEAVES, 16));
			inventory.setItem(19, createShopItem(Material.JUNGLE_LEAVES, 16));
			inventory.setItem(20, createShopItem(Material.ACACIA_LEAVES, 16));
			inventory.setItem(21, createShopItem(Material.QUARTZ_BLOCK, 8));
			
			ItemStack previousPage = new ItemStack(Material.PAPER);
			ItemMeta meta = previousPage.getItemMeta();
			meta.setDisplayName("Previous page");
			previousPage.setItemMeta(meta);
			inventory.setItem(48, previousPage);
		}
		

		inventory.setItem(49, createGoBackItem());

		return inventory;
	}

	private Inventory createAgricultureInventory(Player player, ShopPlugin plugin) {
		Inventory inventory = Bukkit.createInventory(null, 54, "Agriculture");

		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, glass);
		}
		
		inventory.setItem(10, createShopItem(Material.WHEAT_SEEDS, 64));
		inventory.setItem(11, createShopItem(Material.PUMPKIN_SEEDS, 64));
		inventory.setItem(12, createShopItem(Material.MELON_SEEDS, 64));
		inventory.setItem(13, createShopItem(Material.COCOA_BEANS, 64));
		inventory.setItem(14, createShopItem(Material.NETHER_WART, 64));
		inventory.setItem(15, createShopItem(Material.SUGAR_CANE, 64));
		inventory.setItem(16, createShopItem(Material.WHEAT, 64));
		inventory.setItem(19, createShopItem(Material.CARROT, 16));
		inventory.setItem(20, createShopItem(Material.POTATO, 16));
		inventory.setItem(21, createShopItem(Material.CARVED_PUMPKIN, 64));
		inventory.setItem(22, createShopItem(Material.LILY_PAD, 16));
		inventory.setItem(23, createShopItem(Material.CACTUS, 64));
		inventory.setItem(24, createShopItem(Material.OAK_SAPLING, 1));
		inventory.setItem(25, createShopItem(Material.SPRUCE_SAPLING, 1));
		inventory.setItem(28, createShopItem(Material.BIRCH_SAPLING, 1));
		inventory.setItem(29, createShopItem(Material.JUNGLE_SAPLING, 1));
		inventory.setItem(30, createShopItem(Material.ACACIA_SAPLING, 1));
		inventory.setItem(31, createShopItem(Material.DARK_OAK_SAPLING, 1));
		inventory.setItem(31, createShopItem(Material.BROWN_MUSHROOM, 1));
		inventory.setItem(31, createShopItem(Material.RED_MUSHROOM, 1));
		inventory.setItem(31, createShopItem(Material.DEAD_BUSH, 16));

		inventory.setItem(49, createGoBackItem());

		return inventory;
	}

	private Inventory createSpawnerInventory(Player player, ShopPlugin plugin) {
		Inventory inventory = Bukkit.createInventory(null, 54, "Spawner");

		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, glass);
		}
		
		inventory.setItem(10, createSpawnerItem(EntityType.CHICKEN, 1));
		inventory.setItem(11, createSpawnerItem(EntityType.SHEEP, 1));
		inventory.setItem(12, createSpawnerItem(EntityType.PIG, 1));
		inventory.setItem(13, createSpawnerItem(EntityType.COW, 1));
		inventory.setItem(14, createSpawnerItem(EntityType.RABBIT, 1));
		inventory.setItem(15, createSpawnerItem(EntityType.ZOMBIE, 1));
		inventory.setItem(16, createSpawnerItem(EntityType.CAVE_SPIDER, 1));
		inventory.setItem(20, createSpawnerItem(EntityType.SPIDER, 1));
		inventory.setItem(21, createSpawnerItem(EntityType.CREEPER, 1));
		inventory.setItem(22, createSpawnerItem(EntityType.SKELETON, 1));
		inventory.setItem(23, createSpawnerItem(EntityType.ZOMBIFIED_PIGLIN, 1));
		inventory.setItem(24, createSpawnerItem(EntityType.WITCH, 1));
		inventory.setItem(30, createSpawnerItem(EntityType.SLIME, 1));
		inventory.setItem(31, createSpawnerItem(EntityType.MAGMA_CUBE, 1));
		inventory.setItem(32, createSpawnerItem(EntityType.BLAZE, 1));

		inventory.setItem(49, createGoBackItem());

		return inventory;
	}

	private Inventory createRedstoneInventory(Player player, ShopPlugin plugin) {
		Inventory inventory = Bukkit.createInventory(null, 45, "Redstone");

		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, glass);
		}
		
		// TODO

		

		inventory.setItem(44, createGoBackItem());

		return inventory;
	}

	private Inventory createOresInventory(Player player, ShopPlugin plugin) {
		Inventory inventory = Bukkit.createInventory(null, 45, "Ores");

		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, glass);
		}
		
		inventory.setItem(10, createShopItem(Material.COAL, 64));
		inventory.setItem(11, createShopItem(Material.IRON_INGOT, 64));
		inventory.setItem(12, createShopItem(Material.GOLD_INGOT, 64));
		inventory.setItem(13, createShopItem(Material.LAPIS_LAZULI, 64));
		inventory.setItem(14, createShopItem(Material.DIAMOND, 64));
		inventory.setItem(15, createShopItem(Material.EMERALD, 64));
		inventory.setItem(16, createShopItem(Material.IRON_ORE, 64));
		inventory.setItem(19, createShopItem(Material.COAL_BLOCK, 64));
		inventory.setItem(20, createShopItem(Material.IRON_BLOCK, 64));
		inventory.setItem(21, createShopItem(Material.GOLD_BLOCK, 64));
		inventory.setItem(22, createShopItem(Material.LAPIS_BLOCK, 64));
		inventory.setItem(23, createShopItem(Material.DIAMOND_BLOCK, 64));
		inventory.setItem(24, createShopItem(Material.EMERALD_BLOCK, 64));
		inventory.setItem(25, createShopItem(Material.GOLD_ORE, 64));
		inventory.setItem(28, createShopItem(Material.QUARTZ, 64));

		inventory.setItem(40, createGoBackItem());

		return inventory;
	}

	private Inventory createFoodInventory(Player player, ShopPlugin plugin) {
		Inventory inventory = Bukkit.createInventory(null, 54, "Food");

		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, glass);
		}
		
		inventory.setItem(10, createShopItem(Material.MELON_SLICE, 16));
		inventory.setItem(11, createShopItem(Material.APPLE, 16));
		inventory.setItem(12, createShopItem(Material.GOLDEN_APPLE, 16));
		inventory.setItem(13, createShopItem(Material.CAKE, 1));
		inventory.setItem(14, createShopItem(Material.PUMPKIN_PIE, 16));
		inventory.setItem(15, createShopItem(Material.BREAD, 16));
		inventory.setItem(16, createShopItem(Material.COOKED_CHICKEN, 16));
		inventory.setItem(19, createShopItem(Material.BAKED_POTATO, 16));
		inventory.setItem(20, createShopItem(Material.MUSHROOM_STEW, 1));
		inventory.setItem(21, createShopItem(Material.COOKED_COD, 16));
		inventory.setItem(22, createShopItem(Material.COOKED_SALMON, 16));
		inventory.setItem(23, createShopItem(Material.COOKED_RABBIT, 16));
		inventory.setItem(24, createShopItem(Material.RABBIT_STEW, 1));
		inventory.setItem(25, createShopItem(Material.COOKED_PORKCHOP, 16));
		inventory.setItem(28, createShopItem(Material.COOKED_BEEF, 16));
		inventory.setItem(29, createShopItem(Material.COOKED_MUTTON, 16));
		inventory.setItem(30, createShopItem(Material.COOKIE, 16));
		inventory.setItem(31, createShopItem(Material.GOLDEN_CARROT, 8));
		
		inventory.setItem(49, createGoBackItem());

		return inventory;
	}

	private Inventory createFlowersInventory(Player player, ShopPlugin plugin) {
		Inventory inventory = Bukkit.createInventory(null, 45, "Flowers");

		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, glass);
		}
		
		inventory.setItem(10, createShopItem(Material.RED_TULIP, 1));
		inventory.setItem(11, createShopItem(Material.ORANGE_TULIP, 1));
		inventory.setItem(12, createShopItem(Material.PINK_TULIP, 1));
		inventory.setItem(13, createShopItem(Material.WHITE_TULIP, 1));
		inventory.setItem(14, createShopItem(Material.TALL_GRASS, 1));
		inventory.setItem(15, createShopItem(Material.LARGE_FERN, 1));
		inventory.setItem(16, createShopItem(Material.LILAC, 1));
		inventory.setItem(19, createShopItem(Material.ROSE_BUSH, 1));
		inventory.setItem(20, createShopItem(Material.SUNFLOWER, 1));
		inventory.setItem(21, createShopItem(Material.POPPY, 1));
		inventory.setItem(22, createShopItem(Material.DANDELION, 1));
		inventory.setItem(23, createShopItem(Material.OXEYE_DAISY, 1));
		inventory.setItem(24, createShopItem(Material.ALLIUM, 1));
		inventory.setItem(25, createShopItem(Material.AZURE_BLUET, 1));
		inventory.setItem(28, createShopItem(Material.BLUE_ORCHID, 1));

		inventory.setItem(40, createGoBackItem());

		return inventory;
	}

	private Inventory createDropsInventory(Player player, ShopPlugin plugin) {
		Inventory inventory = Bukkit.createInventory(null, 54, "Drops");

		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(" ");
		glass.setItemMeta(glassMeta);
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, glass);
		}
		
		inventory.setItem(10, createShopItem(Material.ROTTEN_FLESH, 64));
		inventory.setItem(11, createShopItem(Material.BONE, 64));
		inventory.setItem(12, createShopItem(Material.GUNPOWDER, 64));
		inventory.setItem(13, createShopItem(Material.STRING, 64));
		inventory.setItem(14, createShopItem(Material.SPIDER_EYE, 64));
		inventory.setItem(15, createShopItem(Material.ENDER_PEARL, 16));
		inventory.setItem(16, createShopItem(Material.SLIME_BALL, 64));
		inventory.setItem(19, createShopItem(Material.PRISMARINE_CRYSTALS, 64));
		inventory.setItem(20, createShopItem(Material.PRISMARINE_SHARD, 64));
		inventory.setItem(21, createShopItem(Material.BLAZE_ROD, 64));
		inventory.setItem(22, createShopItem(Material.MAGMA_CREAM, 64));
		inventory.setItem(23, createShopItem(Material.LEATHER, 64));
		inventory.setItem(24, createShopItem(Material.RABBIT_HIDE, 64));
		inventory.setItem(25, createShopItem(Material.RABBIT_FOOT, 16));
		inventory.setItem(28, createShopItem(Material.INK_SAC, 16));
		inventory.setItem(29, createShopItem(Material.FEATHER, 64));
		inventory.setItem(30, createShopItem(Material.EGG, 16));
		inventory.setItem(31, createShopItem(Material.GHAST_TEAR, 8));
		inventory.setItem(32, createShopItem(Material.ARROW, 64));

		inventory.setItem(49, createGoBackItem());

		return inventory;
	}

	private Inventory createDiverseInventory(Player player, ShopPlugin plugin) {
		Inventory inventory = Bukkit.createInventory(null, 45, "Diverse");

		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName("");
		glass.setItemMeta(glassMeta);
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, glass);
		}
		
		inventory.setItem(10, createShopItem(Material.BUCKET, 1));
		inventory.setItem(11, createShopItem(Material.WATER_BUCKET, 1));
		inventory.setItem(12, createShopItem(Material.LAVA_BUCKET, 1));
		inventory.setItem(13, createShopItem(Material.CAULDRON, 1));
		inventory.setItem(14, createShopItem(Material.BREWING_STAND, 1));
		inventory.setItem(15, createShopItem(Material.NAME_TAG, 1));
		inventory.setItem(16, createShopItem(Material.LEAD, 1));
		inventory.setItem(19, createShopItem(Material.SADDLE, 1));
		inventory.setItem(20, createShopItem(Material.GLASS_BOTTLE, 1));
		inventory.setItem(21, createShopItem(Material.VILLAGER_SPAWN_EGG, 1));
		inventory.setItem(22, createShopItem(Material.OCELOT_SPAWN_EGG, 1));
		inventory.setItem(23, createShopItem(Material.WOLF_SPAWN_EGG, 1));
		inventory.setItem(24, createShopItem(Material.PARROT_SPAWN_EGG, 1));
		inventory.setItem(25, createShopItem(Material.POLAR_BEAR_SPAWN_EGG, 1));
		
		inventory.setItem(40, createGoBackItem());

		return inventory;
	}

	private ItemStack createSelectionShopItem(Material mat, String name) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + name);

		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack createShopItem(Material mat, int nbr) {
		ItemStack item = new ItemStack(mat, nbr);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();

		int buyPrice = ShopPlugin.plugin.getConfig().getInt("Price." + mat.toString() + ".Buy");
		int sellPrice = ShopPlugin.plugin.getConfig().getInt("Price." + mat.toString() + ".Sell");
		
		if(buyPrice != 0)
			lore.add(ChatColor.GRAY + "Buy price : " + ChatColor.RED + buyPrice + " " + plugin.getCoinsName());
		
		if(sellPrice != 0)
			lore.add(ChatColor.GRAY + "Sell price : " + ChatColor.GREEN + sellPrice + " " + plugin.getCoinsName());
		
		lore.add("");
		lore.add(ChatColor.GRAY + "Left click to buy, right click to sell.");
		lore.add(ChatColor.GRAY + "Middle click to sell all.");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack createSpawnerItem(EntityType type, int nbr) {
		Material mat = Material.SPAWNER;
		ItemStack item = new ItemStack(mat, nbr);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		String name = "";
		for(String word : type.toString().split("_")) {
			word = word.charAt(0) + word.substring(1).toLowerCase();
			name += word + " ";
		}
		
		meta.setDisplayName(name + "spawner");
		
		int buyPrice = ShopPlugin.plugin.getConfig().getInt("Price." + mat.toString() + "." + type.toString() + ".Buy");
		int sellPrice = ShopPlugin.plugin.getConfig().getInt("Price." + mat.toString() + "." + type.toString() + ".Sell");
		
		if(buyPrice != 0)
			lore.add(ChatColor.GRAY + "Buy price : " + ChatColor.RED + buyPrice + " " + plugin.getCoinsName());
		
		if(sellPrice != 0)
			lore.add(ChatColor.GRAY + "Sell price : " + ChatColor.GREEN + sellPrice + " " + plugin.getCoinsName());
		
		lore.add("");
		lore.add(ChatColor.GRAY + "Left click to buy, right click to sell.");
		lore.add(ChatColor.GRAY + "Middle click to sell all.");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}

	private ItemStack createGoBackItem() {
		ItemStack go_back_item = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = go_back_item.getItemMeta();
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		meta.setDisplayName("Go back");
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		go_back_item.setItemMeta(meta);

		return go_back_item;
	}
	
	private ItemStack createQuitItem() {
		ItemStack quit_item = new ItemStack(Material.BARRIER);
		ItemMeta meta = quit_item.getItemMeta();

		meta.setDisplayName("Quit");
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		quit_item.setItemMeta(meta);

		return quit_item;
	}
}
