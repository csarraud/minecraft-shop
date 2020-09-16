package fr.sonkuun.shop.plugin;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.sonkuun.shop.event.ShopListener;
import fr.sonkuun.shop.log.Log;

public class ShopPlugin extends JavaPlugin {

	private String TAG = this.getClass().getSimpleName().toString();
	public static ShopPlugin plugin;

	public ShopListener shopListener;

	private CustomCommandExecutor commandExecutor;
	private String coinsName;

	@Override
	public void onEnable() {
		plugin = this;
		
		loadConfig();

		coinsName = plugin.getConfig().getString("Coins.Name");
		
		commandExecutor = new CustomCommandExecutor(this);

		this.getCommand("shop").setExecutor(commandExecutor);
		this.getCommand(coinsName).setExecutor(commandExecutor);
		this.getCommand("givecoins").setExecutor(commandExecutor);
		this.getCommand("removecoins").setExecutor(commandExecutor);
		this.getCommand("setcoins").setExecutor(commandExecutor);

		shopListener = new ShopListener(this);

		this.getServer().getPluginManager().registerEvents(shopListener, this);

		System.out.println(TAG + " properly enabled !");
	}

	@Override
	public void onDisable() {

	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public static double getCoins(Player player) {
		
		while(plugin == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		while(!plugin.isEnabled()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		double coins = plugin.getConfig().getDouble("Users." + player.getUniqueId().toString() + ".Shop.Coins", 0.0);
		
		return coins;
	}

	public static void addCoins(Player player, double coins) {

		while(plugin == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		while(!plugin.isEnabled()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		coins = (double) Math.round(coins * 100) / 100;

		String uuidToString = player.getUniqueId().toString();
		double baseCoins = plugin.getConfig().getDouble("Users." + uuidToString + ".Shop.Coins", 0.0);
		double totalCoins = (double) Math.round((baseCoins + coins) * 100) / 100;

		plugin.getConfig().set("Users." + uuidToString + ".Shop.Coins", totalCoins);
		plugin.saveConfig();

		Log.i(player, "+" + coins + " " + plugin.getCoinsName() + ".");
	}

	public static void removeCoins(Player player, double coins) {

		while(plugin == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		while(!plugin.isEnabled()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		coins = (double) Math.round(coins * 100) / 100;

		String uuidToString = player.getUniqueId().toString();
		double baseCoins = plugin.getConfig().getDouble("Users." + uuidToString + ".Shop.Coins", 0.0);
		double totalCoins = (double) Math.round((baseCoins - coins) * 100) / 100;
		
		if(totalCoins < 0)
			totalCoins = 0;
		
		plugin.getConfig().set("Users." + uuidToString + ".Shop.Coins", totalCoins);
		plugin.saveConfig();

		Log.i(player, "-" + coins + " " + plugin.getCoinsName() + ".");
	}
	
	public static void setCoins(Player player, double coins) {

		while(plugin == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		while(!plugin.isEnabled()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		coins = (double) Math.round(coins * 100) / 100;

		String uuidToString = player.getUniqueId().toString();
		plugin.getConfig().set("Users." + uuidToString + ".Shop.Coins", coins);
		plugin.saveConfig();

		Log.d(player, plugin.getCoinsName() + " set to " + coins);
	}
	
	public String getCoinsName() {
		return coinsName;
	}

	public void setCoinsName(String coinsName) {
		this.coinsName = coinsName;
	}
}
