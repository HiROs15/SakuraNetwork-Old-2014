package dev.hiros.Minigames.FFA.Menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.hiros.GameMethods.GameType;
import dev.hiros.Libs.Instance;
import dev.hiros.Libs.Menu.Menu;
import dev.hiros.Libs.Menu.MenuItem;
import dev.hiros.Minigames.FFA.FFAManager;
import dev.hiros.Minigames.FFA.FFAPlayer;

public class KitSelector extends Menu {
	public KitSelector() {
		
	}
	
	@Override
	public void setupDefaults() {
		this.setSize(9);
		this.setName("Kit Selector");
		
		this.addItem(new MenuItem(new ItemStack(Material.STONE_SWORD, 1), ChatColor.BLUE+""+ChatColor.BOLD+"Basic Kit", new MenuItem.ItemFunction() {
			@Override
			public void runFunction(Player player) {
				((FFAPlayer)((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player).getPlayer(player)).setKit("Basic");
				player.sendMessage(ChatColor.BLUE+"Kit> "+ChatColor.GRAY+"You have selected the Basic kit!");
			}
		}, ChatColor.YELLOW+"This kit contains: ", ChatColor.GRAY+"Stone Sword", ChatColor.GRAY+"Fishing Rod", ChatColor.GRAY+"Default Armor"));
		
		this.addItem(new MenuItem(new ItemStack(Material.DIAMOND_SWORD, 1), ChatColor.BLUE+""+ChatColor.BOLD+"Assult", new MenuItem.ItemFunction() {
			@Override
			public void runFunction(Player player) {
				((FFAPlayer)((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player).getPlayer(player)).setKit("Assult");
				player.sendMessage(ChatColor.BLUE+"Kit> "+ChatColor.GRAY+"You have selected the Assult kit!");
			}
		}, ChatColor.YELLOW+"This kit contains:", ChatColor.GRAY+"Diamond Sword", ChatColor.GRAY+"NO Armor", ChatColor.GRAY+"Speed Boost"));
	}
}
