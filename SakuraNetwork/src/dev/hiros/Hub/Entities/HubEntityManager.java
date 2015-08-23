package dev.hiros.Hub.Entities;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.HologramAPI;
import dev.hiros.Config.Config;
import dev.hiros.Hub.NMS.VillagerMerchant;
import dev.hiros.Libs.ConfigLocation;
import net.minecraft.server.v1_8_R3.World;

public class HubEntityManager {
	private ArrayList<HubEntity> entities = new ArrayList<HubEntity>();
	
	public void loadEntities() {
		Config config = Config.get().getFile("/Hub/hub_entities.yml");
		
		if(config.getConfig().getBoolean("setup") == false) {
			return;
		}
		
		//Load Sales Particles Merchants
		for(String id : config.getConfig().getConfigurationSection("entities").getKeys(false)) {
			Location loc = ConfigLocation.get().getLocation("/Hub/hub_entities.yml", "entities."+id+"");
			this.spawnEntity(loc, config.getConfig().getString("entities."+id+".type"));
		}
	}
	
	public ArrayList<HubEntity> getEntities() {
		return this.entities;
	}
	
	public int getCurrentEntities() {
		return this.entities.size();
	}
	
	public void spawnEntity(Location loc, String type) {
		if(type.equals("sales_particles")) {
			World mcWorld = ((CraftWorld)Bukkit.getWorld(loc.getWorld().getName())).getHandle();
			VillagerMerchant ent = new VillagerMerchant(mcWorld);
			ent.setProfession(1);
			
			CraftEntity craftEnt = ent.getBukkitEntity();
			
			LivingEntity livingEnt = (LivingEntity) craftEnt;
			livingEnt.setRemoveWhenFarAway(false);
			
			ent.setPosition(loc.getX(), loc.getY(), loc.getZ());
			mcWorld.addEntity(ent);
			
			Location l = livingEnt.getEyeLocation().add(0,0.75,0);
			Hologram tag = HologramAPI.createHologram(l, ChatColor.AQUA+""+ChatColor.BOLD+"Particles");
			tag.spawn();
			
			entities.add(new HubEntity(loc, type, craftEnt, tag));
		}
		
		if(type.equals("sales_hats")) {
			World mcWorld = ((CraftWorld)Bukkit.getWorld(loc.getWorld().getName())).getHandle();
			VillagerMerchant ent = new VillagerMerchant(mcWorld);
			ent.setProfession(2);
			
			CraftEntity craftEnt = ent.getBukkitEntity();
			
			LivingEntity livingEnt = (LivingEntity) craftEnt;
			livingEnt.setRemoveWhenFarAway(false);
			
			ent.setPosition(loc.getX(), loc.getY(), loc.getZ());
			mcWorld.addEntity(ent);
			
			Hologram tag = HologramAPI.createHologram(livingEnt.getEyeLocation().add(0,0.75,0), ChatColor.YELLOW+""+ChatColor.BOLD+"Hats");
			tag.spawn();
			
			entities.add(new HubEntity(loc, type, craftEnt, tag));
			
		}
		
		if(type.equals("sales_gear")) {
			World mcWorld = ((CraftWorld)Bukkit.getWorld(loc.getWorld().getName())).getHandle();
			VillagerMerchant ent = new VillagerMerchant(mcWorld);
			ent.setProfession(3);
			
			CraftEntity craftEnt = ent.getBukkitEntity();
			
			LivingEntity livingEnt = (LivingEntity) craftEnt;
			livingEnt.setRemoveWhenFarAway(false);
			
			ent.setPosition(loc.getX(), loc.getY(), loc.getZ());
			mcWorld.addEntity(ent);
			
			Hologram tag = HologramAPI.createHologram(livingEnt.getEyeLocation().add(0,0.75,0), ChatColor.GREEN+""+ChatColor.BOLD+"Gear");
			tag.spawn();
			
			entities.add(new HubEntity(loc, type, craftEnt, tag));
		}
	}
	
	public void removeEntities() {
		for(HubEntity ent : entities) {
			ent.getEntity().remove();
			ent.getHologram().despawn();
		}
		for(Entity ent : Bukkit.getWorld("world").getEntities()) {
			if(ent instanceof Player) {
				return;
			}
			else {
				ent.remove();
			}
		}
	}
}
