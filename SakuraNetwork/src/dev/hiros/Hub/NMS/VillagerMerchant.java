package dev.hiros.Hub.NMS;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;

public class VillagerMerchant extends EntityVillager {
	public VillagerMerchant(World world) {
		super(world);
		
		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			
			bField.setAccessible(true);
			cField.setAccessible(true);
			
			bField.set(this.goalSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(this.goalSelector, new UnsafeList<PathfinderGoalSelector>());
		}catch(Exception e) {}
		this.goalSelector.a(1, new PathfinderGoalRandomLookaround(this));
		this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 16.0F));
		System.out.println("Spawn Entity");
	}
	
	@Override
	public void g(double x, double y, double z) {
		return;
	}
	
	@Override
	public boolean damageEntity(DamageSource source, float f) {
		return false;
	}
	
	@Override
	protected String z() {
		return null;
		
	}
}
