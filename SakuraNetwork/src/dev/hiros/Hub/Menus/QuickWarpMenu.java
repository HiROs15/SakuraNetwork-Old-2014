package dev.hiros.Hub.Menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.hiros.Managers;
import dev.hiros.GameMethods.GameType;
import dev.hiros.Libs.Menu.Menu;
import dev.hiros.Libs.Menu.MenuItem;

public class QuickWarpMenu extends Menu {
	public QuickWarpMenu() {
	}
	
	@Override
	public void setupDefaults() {
		this.setName("Quick Warp");
		this.setSize(9);
		this.addItem(new MenuItem(new ItemStack(Material.DIAMOND_SWORD, 1), ChatColor.GREEN+"Free for All "+ChatColor.RED+""+ChatColor.BOLD+"BETA", new MenuItem.ItemFunction() {
			@Override
			public void runFunction(Player player) {
				Managers.lobbyManager.getOpenLobby(GameType.FFA, player);
			}
		},ChatColor.GRAY+"A simple Free for All battle with your friends."));
	}
}
