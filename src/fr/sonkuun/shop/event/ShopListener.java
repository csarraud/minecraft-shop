package fr.sonkuun.shop.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.sonkuun.shop.Shop;
import fr.sonkuun.shop.Shop.ShopType;
import fr.sonkuun.shop.log.Log;
import fr.sonkuun.shop.plugin.ShopPlugin;

public class ShopListener implements Listener{

	private ShopPlugin plugin;

	private Shop shop;
	
	public ShopListener(ShopPlugin plugin) {
		this.plugin = plugin;
		this.shop = new Shop(plugin);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		String inventory_name = event.getView().getTitle();

		if(inventory_name.equalsIgnoreCase("Shop") && event.getRawSlot() >= 0 && event.getRawSlot() < event.getInventory().getSize()) {
			event.setCancelled(true);

			ItemStack clicked_item = event.getCurrentItem();
			ItemMeta meta = clicked_item.getItemMeta();

			if(clicked_item.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
				return;
			}

			ShopType shopType = ShopType.fromString(meta.getDisplayName());

			if(shopType == null) {
				player.closeInventory();
				return;
			}

			player.openInventory(shop.createShopInventory(player, plugin, shopType));
		}

		if(ShopType.fromString(inventory_name) != null && event.getRawSlot() < event.getInventory().getSize()) {
			event.setCancelled(true);

			ItemStack clicked_item = event.getCurrentItem();

			/*
			 * In case of click out of the inventory
			 */
			if(clicked_item == null)
				return;

			if(clicked_item.hasItemMeta()) {
				ItemMeta meta = clicked_item.getItemMeta();

				if(meta.getDisplayName().equalsIgnoreCase("Go back")) {
					player.openInventory(shop.createShopInventory(player, plugin, ShopType.HOMEPAGE));
				}
				else if(meta.getDisplayName().equalsIgnoreCase("Next page")) {
					player.openInventory(shop.createShopInventory(player, plugin, ShopType.fromString(inventory_name), 2));
				}
				else if(meta.getDisplayName().equalsIgnoreCase("Previous page")) {
					player.openInventory(shop.createShopInventory(player, plugin, ShopType.fromString(inventory_name)));
				}
				else if(clicked_item.getType().equals(Material.BLACK_STAINED_GLASS)) {
					return;
				}
			}

			int buyPrice, sellPrice;

			try {
				buyPrice = priceFromMeta(clicked_item.getItemMeta(), "Buy price");
				sellPrice = priceFromMeta(clicked_item.getItemMeta(), "Sell price");
			} catch (NumberFormatException e) {
				Log.e(player, "An error occur during price conversion");
				return;
			}

			if(clicked_item.getType().equals(Material.SPAWNER)) {
				String name = clicked_item.getItemMeta().getDisplayName();
				clicked_item = new ItemStack(clicked_item.getType(), clicked_item.getAmount());
				ItemMeta meta = clicked_item.getItemMeta();
				meta.setDisplayName(name);
				
				ChatColor white = ChatColor.WHITE;
				List<String> lore = new ArrayList<String>();
				lore.add("");
				lore.add(white + "Mobs per wave : " + ChatColor.YELLOW + "2");
				lore.add(white + "Wave delay : " + ChatColor.YELLOW + "25" + white + " seconds");
				lore.add(white + "Player range needed : " + ChatColor.YELLOW + "16" + white + " blocks");
				
				meta.setLore(lore);
				clicked_item.setItemMeta(meta);
			}
			else {
				clicked_item = new ItemStack(clicked_item.getType(), clicked_item.getAmount());
			}

			ClickType click = event.getClick();
			int slot;
			int amount = clicked_item.getAmount();

			switch (click) {
			/*
			 * Buy item
			 */
			case LEFT:
				/*
				 * The clicked item isn't purchasable
				 */
				if(buyPrice == 0)
					return;

				/*
				 * Check if the player has enough coins to buy this item
				 */
				double coins = ShopPlugin.getCoins(player);
				if(coins < buyPrice) {
					Log.w(player, "No enought money to buy this item.");
					return;
				}

				ShopPlugin.removeCoins(player, buyPrice);

				/*
				 * While all the items wasn't given, loop and search where to give items
				 */
				while(amount != 0) {
					clicked_item.setAmount(amount);

					boolean HAS_FREE_SLOT = false;
					boolean HAS_A_STACK_NOT_FULL = false;

					/*
					 * Check if the player inventory has a similar item
					 * and if the stack isn't full
					 */
					HashMap<Integer, ? extends ItemStack> slotsMap = player.getInventory().all(clicked_item.getType());
					List<Integer> slots = new ArrayList<Integer>();
					for(ItemStack item : slotsMap.values()) {
						if(item.getAmount() < item.getMaxStackSize() && item.getItemMeta().getDisplayName().equalsIgnoreCase(clicked_item.getItemMeta().getDisplayName())) {
							slots.add(player.getInventory().first(item));
						}
					}

					if(slots.size() > 0)
						slot = slots.get(0);
					else 
						slot = -1;

					if(slot != -1 && player.getInventory().getItem(slot).getAmount() < player.getInventory().getItem(slot).getMaxStackSize()) {
						HAS_A_STACK_NOT_FULL = true;
					}
					/*
					 * Check if the player inventory has a free slot
					 */
					else if(player.getInventory().firstEmpty() != -1) {
						HAS_FREE_SLOT = true;
					}

					/*
					 * If player inventory has a stack of this item not full,
					 * fill it with the clicked item
					 */
					if(HAS_A_STACK_NOT_FULL) {
						ItemStack itemStackNotFull = player.getInventory().getItem(slot);
						int amountOfTheSlotStack = itemStackNotFull.getAmount();
						int maxAmountToAdd = itemStackNotFull.getMaxStackSize() - amountOfTheSlotStack;

						/*
						 * If there is more item to add than the actual place,
						 * fill the actual place to max
						 */
						if(amount >= maxAmountToAdd) {
							itemStackNotFull.setAmount(itemStackNotFull.getMaxStackSize());
							player.getInventory().setItem(slot, itemStackNotFull);
							amount -= maxAmountToAdd;
						}
						/*
						 * If there is less item to add than the actual place,
						 * fill the actual place with the item to add
						 */
						else if(amount < maxAmountToAdd) {
							itemStackNotFull.setAmount(amountOfTheSlotStack + amount);
							player.getInventory().setItem(slot, itemStackNotFull);
							amount = 0;
						}
					}
					/*
					 * If player inventory has a free slot,
					 * fill it with the clicked item
					 */
					else if(HAS_FREE_SLOT) {
						player.getInventory().setItem(player.getInventory().firstEmpty(), clicked_item);
						amount = 0;
					}
					/*
					 * Else the player inventory is full and don't have a stack of this
					 * item not full, then drop the clicked item at the player location
					 */
					else {
						Location loc = player.getLocation().add(0, 0.5, 0);
						player.getWorld().dropItem(loc, clicked_item);
						amount = 0;
					}
				}

				break;

				/*
				 * Sell item
				 */
			case RIGHT:
				/*
				 * The clicked item isn't salable
				 */
				if(sellPrice == 0)
					return;

				/*
				 * If the player inventory doesn't have the clicked item
				 */
				slot = player.getInventory().first(clicked_item.getType());
				if(slot == -1) {
					return;
				}

				/*
				 * Check if the player inventory has a similar item
				 * and check the total amount
				 */
				HashMap<Integer, ? extends ItemStack> slotsMap = player.getInventory().all(clicked_item.getType());
				List<Integer> slots = new ArrayList<Integer>();
				int totalAmount = 0;
				for(ItemStack item : slotsMap.values()) {
					if(item.getItemMeta().getDisplayName().equalsIgnoreCase(clicked_item.getItemMeta().getDisplayName())) {
						totalAmount += item.getAmount();
						slots.add(player.getInventory().first(item));
					}
				}

				amount = clicked_item.getAmount();

				/*
				 * If there aren't enough amount item in the
				 * player inventory, then return
				 */
				if(totalAmount < amount)
					return;

				int amountSell = 0;

				while(amountSell != amount) {

					slotsMap = player.getInventory().all(clicked_item.getType());
					slots = new ArrayList<Integer>();
					for(ItemStack item : slotsMap.values()) {
						if(item.getItemMeta().getDisplayName().equalsIgnoreCase(clicked_item.getItemMeta().getDisplayName())) {
							slots.add(player.getInventory().first(item));
						}
					}

					slot = slots.get(0);

					ItemStack item = player.getInventory().getItem(slot);

					if(item.getAmount() <= (amount - amountSell)) {
						amountSell += item.getAmount();
						item.setAmount(0);
					}
					else {
						item.setAmount(item.getAmount() - (amount - amountSell));
						amountSell = amount;
					}
				}

				ShopPlugin.addCoins(player, sellPrice);

				break;

				/*
				 * Sell all items
				 */
			case MIDDLE:
				/*
				 * The clicked item isn't salable
				 */
				if(sellPrice == 0)
					return;

				int totalPrice = 0;
				totalAmount = 0;
				amountSell = 0;

				slotsMap = player.getInventory().all(clicked_item.getType());
				slots = new ArrayList<Integer>();
				for(ItemStack item : slotsMap.values()) {
					if(item.getItemMeta().getDisplayName().equalsIgnoreCase(clicked_item.getItemMeta().getDisplayName())) {
						totalAmount += item.getAmount();
						slots.add(player.getInventory().first(item));
					}
				}

				int numberItemSelled = totalAmount / amount;
				int residual = totalAmount % amount;

				totalPrice = sellPrice * numberItemSelled;

				/*
				 * The player doesn't have the corresponding item
				 */
				if(totalPrice == 0) {
					return;
				}

				for(Integer _slot : slots) {
					player.getInventory().clear(_slot);
				}

				player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(clicked_item.getType(), residual));

				ShopPlugin.addCoins(player, totalPrice);

				break;
				
			default:
				break;
			}
		}

	}

	public int priceFromMeta(ItemMeta meta, String regex) throws NumberFormatException {
		if(!meta.hasLore())
			return 0;

		List<String> lore = meta.getLore();
		String lineToParse = "";

		for(String line : lore) {
			if(line.contains(regex)) {
				lineToParse = line;
			}
		}

		if(lineToParse != "")
			return Integer.parseInt(lineToParse.split(" ")[3].substring(2));
		else
			return 0;
	}
}
