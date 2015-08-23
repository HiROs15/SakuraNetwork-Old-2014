package dev.hiros.Minigames.FFA;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.hiros.Minigames.Engine.MinigamePlayer;

public class FFAPlayer extends MinigamePlayer {
	private int kills = 0;
	private int deaths = 0;
	private PlayerState state = PlayerState.ALIVE;
	private String kit = "None Selected";
	private Location spawn;
	
	public FFAPlayer(Player player, Location spawn) {
		super(player);
		this.spawn = spawn;
	}
	
	public String getKit() {
		return this.kit;
	}
	
	public Location getSpawn() {
		return this.spawn;
	}
	
	public void setSpawn(Location loc) {
		this.spawn = loc;
	}
	
	public void setKit(String kit) {
		this.kit = kit;
		
		this.updateKitInventory();
	}
	
	public PlayerState getState() {
		return this.state;
	}
	
	public int getKills() {
		return this.kills;
	}
	
	public int getDeaths() {
		return this.deaths;
	}
	
	public void addDeath() {
		this.deaths += 1;
	}
	
	public void addKill() {
		this.kills += 1;
	}
	
	private void updateKitInventory() {
		Player player = this.getPlayer();
		this.getPlayer().getInventory().clear();
		this.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR, 1));
	    this.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR, 1));
	    this.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR, 1));
	    this.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR, 1));
	    
	    if(this.getKit().equals("Basic")) {
	    	player.getInventory().setHelmet(createItem(Material.GOLD_HELMET, 1, "Helmet"));
	    	player.getInventory().setChestplate(createItem(Material.IRON_CHESTPLATE, 1, "Chestplate"));
	    	player.getInventory().setLeggings(createItem(Material.CHAINMAIL_LEGGINGS, 1, "Leggings"));
	    	player.getInventory().setBoots(createItem(Material.IRON_BOOTS, 1, "Boots"));
	    	player.getInventory().setItem(0, createItem(Material.STONE_SWORD,  1, "Sword"));
	    	player.getInventory().setItem(1, createItem(Material.FISHING_ROD, 1, "Fishing Rod"));
	    }
	    
	    if(this.getKit().equals("Assult")) {
	    	player.getInventory().setItem(0, createItem(Material.DIAMOND_SWORD, 1, "Sword"));
	    }
	}
	
	private ItemStack createItem(Material mat, int num, String name) {
		ItemStack item = new ItemStack(mat, num);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		
		return item;
	}
}
