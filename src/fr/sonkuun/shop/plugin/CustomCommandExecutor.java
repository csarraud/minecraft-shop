package fr.sonkuun.shop.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.sonkuun.shop.Shop;
import fr.sonkuun.shop.Shop.ShopType;
import fr.sonkuun.shop.log.Log;

public class CustomCommandExecutor implements CommandExecutor {

	private ShopPlugin plugin;
	
	private Shop shop;

	public CustomCommandExecutor(ShopPlugin plugin) {
		this.plugin = plugin;
		this.shop = new Shop(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {

		if(sender instanceof Player) {
			Player player = (Player) sender;
			double coins;
			
			if(command.getName().equalsIgnoreCase("shop")) {
				player.openInventory(shop.createShopInventory(player, plugin, ShopType.HOMEPAGE));
				return true;
			}
			else if(command.getName().equalsIgnoreCase(plugin.getCoinsName().toLowerCase())) {
				coins = plugin.getConfig().getDouble("Users." + player.getUniqueId().toString() + ".Shop.Coins");
				Log.i(player, coins + " " + plugin.getCoinsName().toLowerCase());
				return true;
			}
			else if(command.getName().equalsIgnoreCase("givecoins")) {
				if(args.length == 1) {
					try {
						coins = Double.parseDouble(args[0]);
						ShopPlugin.addCoins(player, coins);
					}
					catch (NumberFormatException e) {
						return false;
					}

					return true;
				}
				return false;
			}
			else if(command.getName().equalsIgnoreCase("removecoins")) {
				if(args.length == 1) {
					try {
						coins = Double.parseDouble(args[0]);
						ShopPlugin.removeCoins(player, coins);
					}
					catch (NumberFormatException e) {
						return false;
					}

					return true;
				}
				return false;
			}
			else if(command.getName().equalsIgnoreCase("setcoins")) {
				if(args.length == 1) {
					try {
						coins = Double.parseDouble(args[0]);
						ShopPlugin.setCoins(player, coins);
					}
					catch (NumberFormatException e) {
						return false;
					}

					return true;
				}
				return false;
			}
		}

		return false;
	}
}
