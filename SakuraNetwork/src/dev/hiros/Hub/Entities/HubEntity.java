package dev.hiros.Hub.Entities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import de.inventivegames.hologram.Hologram;

public class HubEntity {
	private Location loc;
	private String type;
	private Entity entity;
	private Hologram hologram;
	
	public HubEntity(Location loc, String type, Entity entity, Hologram hologram) {
		this.loc = loc;
		this.type = type;
		this.entity = entity;
		this.hologram = hologram;
	}
	
	public Hologram getHologram() {
		return this.hologram;
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public String getType() {
		return this.type;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
}
