package dev.hiros.Minigames.FFA;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.hiros.GameMethods.GameType;
import dev.hiros.Libs.Instance;
import dev.hiros.Libs.Menu.Menu;
import dev.hiros.Libs.Menu.MenuItem;
import dev.hiros.Variables.Chat;

public class FFAVoteMenu extends Menu {
	public FFAVoteMenu() {
	}
	
	@Override
	public void setupDefaults() {
		this.setName("Voting Menu");
		this.setSize(9);
		
		for(String map : ((FFAManager)Instance.getManager(GameType.FFA)).getMaps()) {
			this.addItem(new MenuItem(new ItemStack(Material.IRON_BLOCK, 1), ChatColor.GOLD+""+ChatColor.BOLD+""+map, new MenuItem.ItemFunction() {
				@Override
				public void runFunction(Player player) {
					boolean voted = ((FFAManager)Instance.getManager(GameType.FFA)).getVoteManager().vote(map, player);
					if(voted) {
						player.sendMessage(Chat.CommandSuccessTag+"You have voted for the map "+map+"!");
					} else {
						player.sendMessage(Chat.CommandErrorTag+"You have already voted!");
					}
				}
			}, ChatColor.GRAY+"Click to cast your vote!"));
		}
	}
}
