package dev.hiros.Libs.Menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItem
{
  private String name;
  private ItemStack item;
  private ItemFunction function;
  private String[] lore;
  
  public MenuItem(ItemStack item, String name, ItemFunction function, String... lore)
  {
	  ItemStack i = item;
    ItemMeta meta = i.getItemMeta();
    meta.setDisplayName(name);
    i.setItemMeta(meta);
    
    this.item = i;
    this.name = name;
    this.function = function;
    this.lore = lore;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String[] getLore()
  {
    return this.lore;
  }
  
  public ItemStack getItem()
  {
    return this.item;
  }
  
  public void runFunction(Player player)
  {
    this.function.runFunction(player);
  }
  
  public interface ItemFunction
  {
    public void runFunction(Player paramPlayer);
  }
}

