package dev.hiros.Hub.Menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.hiros.Managers;
import dev.hiros.Hub.Hub;
import dev.hiros.Libs.Menu.MenuItem;
import dev.hiros.Libs.Menu.PlayerMenu;
import dev.hiros.Variables.Chat;

public class LobbyMenu extends PlayerMenu {
	public LobbyMenu(Player player) {
		super(player);
	}
	
	@Override
	public void setupDefaults() {
		this.setName(ChatColor.GREEN+""+ChatColor.BOLD+"Hub Selection Menu");
		this.setSize(9);
		
		for(final Hub hub : Managers.hubManager.getHubs()) {
			if(hub.containsPlayer(this.getPlayer()) == true) {
				this.addItem(new MenuItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 4), ChatColor.YELLOW+"Hub Server #"+hub.getId(), new MenuItem.ItemFunction() {
					@Override
					public void runFunction(Player player) {
						player.sendMessage(Chat.CommandErrorTag+"You are already in this server!");
					}
				},ChatColor.GREEN+""+hub.getCurrentPlayers()+"/"+hub.getMaxPlayers()+" Players Online", ChatColor.GRAY+"(You are in this server!)"));
			}
			
			else if(hub.getCurrentPlayers() >= hub.getMaxPlayers()) {
				this.addItem(new MenuItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 14), ChatColor.YELLOW+"Hub Server #"+hub.getId(), new MenuItem.ItemFunction() {
					@Override
					public void runFunction(Player player) {
						
					}
				},ChatColor.RED+"This Server is Full"));
			}
			
			else if(hub.getCurrentPlayers() < hub.getMaxPlayers()) {
				this.addItem(new MenuItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 5), ChatColor.YELLOW+"Hub Server #"+hub.getId(), new MenuItem.ItemFunction() {
					@Override
					public void runFunction(Player player) {
						Managers.hubManager.switchHubs(player, hub.getId());
					}
				}, ChatColor.GREEN+""+hub.getCurrentPlayers()+"/"+hub.getMaxPlayers()+" Players Online"));
			}
		}
	}
}