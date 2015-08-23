package dev.hiros.Libs;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
	public static Item get() {
		return new Item();
	}
	
	public ItemStack createItem(ItemStack item, String name, String... lore) {
		ItemStack i = item;
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(name);
		
		ArrayList<String> l = new ArrayList<String>();
		for(String a : lore) {
			l.add(a);
		}
		
		meta.setLore(l);
		
		item.setItemMeta(meta);
		
		return item;
	}
}
