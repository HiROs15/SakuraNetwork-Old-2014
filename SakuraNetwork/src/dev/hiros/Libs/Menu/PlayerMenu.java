package dev.hiros.Libs.Menu;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import dev.hiros.SakuraNetwork;

public abstract class PlayerMenu {
	private ArrayList<MenuItem> items = new ArrayList<MenuItem>();
	  @SuppressWarnings("unused")
	private int page = 0;
	  private ItemMenu window;
	  private int size;
	  private String name;
	  private Player player;
	  
	  public abstract void setupDefaults();
	  
	  public PlayerMenu(Player player) {
		  this.player = player;
	    setupDefaults();
	    setupWindow();
	  }
	  
	  public Player getPlayer() {
		  return this.player;
	  }
	  
	  public int getSize() {
	    return this.size;
	  }
	  
	  public void setSize(int size) {
	    this.size = size;
	  }
	  
	  public ItemMenu getWindow() {
	    return this.window;
	  }
	  
	  public String getName() {
	    return this.name;
	  }
	  
	  public void setName(String name) {
	    this.name = name;
	  }
	  
	  public void addItem(MenuItem item) {
	    this.items.add(item);
	  }
	  
	  private void setupWindow() {
	    this.window = new ItemMenu(getSize(), getName(), new ItemMenu.OptionClickEventHandler() {
	      public void onOptionClick(ItemMenu.OptionClickEvent event) {
	        event.setWillDestroy(true);
	        for (MenuItem item : PlayerMenu.this.items) {
	          if (item.getItem().getItemMeta().getDisplayName().equals(event.getName())) {
	            item.runFunction(event.getPlayer());
	          }
	        }
	      }
	    }, SakuraNetwork.plugin);
	    
	    int step = 0;
	    for (MenuItem item : this.items) {
	      this.window.setOption(step, item.getItem(), item.getName(), item.getLore());
	      step++;
	    }
	  }
	  
	  public void openMenu(Player player) {
	    this.window.open(player);
	  }
	  
	  public void closeMenu(Player player) {
	    this.window.destroy();
	  }
}
