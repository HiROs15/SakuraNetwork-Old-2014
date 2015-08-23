package dev.hiros.Libs.Menu;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ItemMenu
  implements Listener
{
  private String name;
  private int size;
  private OptionClickEventHandler handler;
  private Plugin plugin;
  private Player player;
  private String[] optionNames;
  private ItemStack[] optionIcons;
  
  public ItemMenu(int size, String name, OptionClickEventHandler handler, Plugin plugin)
  {
    this.name = name;
    this.size = size;
    this.handler = handler;
    this.plugin = plugin;
    this.optionNames = new String[size];
    this.optionIcons = new ItemStack[size];
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
  
  public ItemMenu setOption(int position, ItemStack icon, String name, String... info)
  {
    this.optionNames[position] = name;
    this.optionIcons[position] = setItemNameAndLore(icon, name, info);
    return this;
  }
  
  public void open(Player player)
  {
    this.player = player;
    Inventory inventory = Bukkit.createInventory(player, this.size, this.name);
    for (int i = 0; i < this.optionIcons.length; i++) {
      if (this.optionIcons[i] != null) {
        inventory.setItem(i, this.optionIcons[i]);
      }
    }
    player.openInventory(inventory);
  }
  
  public void destroy()
  {
    HandlerList.unregisterAll(this);
    this.handler = null;
    this.plugin = null;
    this.optionNames = null;
    this.optionIcons = null;
  }
  
  public Player getPlayer()
  {
    return this.player;
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  void onInventoryClick(InventoryClickEvent event)
  {
    if ((event.getInventory().getTitle().equals(this.name)) && 
      (event.getWhoClicked().getName().equals(this.player.getName())))
    {
      event.setCancelled(true);
      int slot = event.getRawSlot();
      if ((slot >= 0) && (slot < this.size) && (this.optionNames[slot] != null))
      {
        Plugin plugin = this.plugin;
        OptionClickEvent e = new OptionClickEvent((Player)event.getWhoClicked(), slot, this.optionNames[slot]);
        this.handler.onOptionClick(e);
        if (e.willClose())
        {
          final Player p = (Player)event.getWhoClicked();
          Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
          {
            public void run()
            {
              p.closeInventory();
            }
          }, 1L);
        }
        if (e.willDestroy()) {
          destroy();
        }
      }
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event)
  {
    if (event.getInventory().getTitle().equals(this.name) && (event.getPlayer().getName().equals(this.player.getName()))) {
      destroy();
    }
  }
  
  public class OptionClickEvent
  {
    private Player player;
    private int position;
    private String name;
    private boolean close;
    private boolean destroy;
    
    public OptionClickEvent(Player player, int position, String name)
    {
      this.player = player;
      this.position = position;
      this.name = name;
      this.close = true;
      this.destroy = false;
    }
    
    public Player getPlayer()
    {
      return this.player;
    }
    
    public int getPosition()
    {
      return this.position;
    }
    
    public String getName()
    {
      return this.name;
    }
    
    public boolean willClose()
    {
      return this.close;
    }
    
    public boolean willDestroy()
    {
      return this.destroy;
    }
    
    public void setWillClose(boolean close)
    {
      this.close = close;
    }
    
    public void setWillDestroy(boolean destroy)
    {
      this.destroy = destroy;
    }
  }
  
  private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore)
  {
    ItemMeta im = item.getItemMeta();
    im.setDisplayName(name);
    im.setLore(Arrays.asList(lore));
    item.setItemMeta(im);
    return item;
  }
  
  public static abstract interface OptionClickEventHandler
  {
    public abstract void onOptionClick(ItemMenu.OptionClickEvent paramOptionClickEvent);
  }
}
