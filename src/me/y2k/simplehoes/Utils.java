package me.y2k.simplehoes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Utils {
	public static void cSend(CommandSender sender, String string) {
		sender.sendMessage(clr(string));
	}
	public static String clr(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	public static void loadMessage() {		
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		cSend(sender, "&a:S Simple Hoes Loaded");	}
	public static  int stringToInt(String value){return Integer.parseInt(value);}
	public static  String intToString(int value){return Integer.toString(value);}
	public static String doubleToString(double value) {return Double.toString(value);}
	
	public static void fillSlots(Inventory inv, ItemStack stack) {
		for(int i = 0; i < inv.getSize(); i++) {
			if(inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
				inv.setItem(i, stack);	
			}
		}
	}
	public static void delayTask(JavaPlugin plugin,Runnable method, int time) {
		Bukkit.getScheduler().runTaskLater(plugin, method, time);
	}
	public static String randomDouble(double chance) {
		double rand = Math.random();
		String proc = "proc";
		if(chance > rand) {
			return proc;	
		}
		return null;
	}
    public static String randomInt(int chance) {
		double rand = Math.random();
		String proc = "proc";
		if(chance > rand) {
			return proc;
		}
		return null;
	}
    public static byte idColor(String path) {
    	int npath = Utils.stringToInt(path.replaceAll(".*(?<=%clr%)", ""));  //replaces everything but the integer after the keyword %clr%
    	return (byte) npath; //Returns a byte for the glass
    }
    /*
     * Assumes multiple things
     * it assumes the config is set up like below
     * ConfigSection:
     *  HEADER:
     *   item:
     *   #COULD BE LIST ???
     *    -
     *    -
     *   name:
     *   lore:
     *    -
     *    -
     *    -
     */
    public static void itemFromConfig(FileConfiguration cfg, boolean itemlist,int index, boolean name, boolean lore, String section, List<ItemStack> storage) {
    	for(String itemSect: cfg.getConfigurationSection(section).getKeys(false)) {
    		// Create Material to Use ahead
    		Material mat;

    		if(itemlist == true) {
    			//gets the config point configsection.ItemSection.item as List
    			/* Note taht ItemSection is infinitly stackable with this method
    			 * There could be ItemSection2. 3. etc...
    			 * 
    			 * ConfigSection
    			 *  ItemSection:
    			 *   item:
    			 *    - DIRT
    			 *    - STONE
    			 *    - GRAVEL
    			 */
    			List<String> materialList = cfg.getStringList(section+"."+itemSect+".item");
    			mat = Material.valueOf(materialList.get(index));
    			
    		}else {
    			//gets the config point configsection.ItemSection.item
    			mat = Material.valueOf(cfg.getString(section+"."+itemSect+".item"));
    		}
    		// Creates the item stack from the Material options above
    		ItemStack stack = new ItemStack(mat);
    		
    		ItemMeta stackmeta = stack.getItemMeta();
    		
    		// checks if name is true
    		// gets the config point configsection.ItemSection.name
    		if(name == true) {
    			stackmeta.setDisplayName(Utils.clr(cfg.getString(section+"."+itemSect+".name")));
    		}
    		
    		
    		// checks if lore is true
    		// gets the config point configsection.ItemSection.lore
			List<String> loreList = new ArrayList<String>();
    		if(lore == true) {
    			//For Loop adds color to every line thats in the Config for Lore
    			for(String line: cfg.getStringList(section+"."+itemSect+".lore")){
    				loreList.add(Utils.clr(line));
    			}
    		}
			stackmeta.setLore(loreList);    		
    		stack.setItemMeta(stackmeta);
    		//adds it to the storage point of your choice to be dumped or stored for future use
    		storage.add(stack);
    	}
    }
    @SuppressWarnings("boxing")
    /*
     *add item position to list from config sing 
     */
	/* Note taht ItemSection is infinitly stackable with this method
	 * There could be ItemSection2. 3. etc...
	 * 
	 * ConfigSection
	 *  ItemSection:
	 *   item:
	 *    - DIRT
	 *    - STONE
	 *    - GRAVEL
	 *   position: 10
	 */
	public static void itemPositionFromConfig(FileConfiguration cfg,String section, List<Integer> storage) {
    	//gets config sections
    	for(String itemSect: cfg.getConfigurationSection(section).getKeys(false)) {
    		//gets keys storage adds position to seperate storage do not add to same storage as item stacks make sure you initilize
    		// at the same time
    		storage.add(cfg.getInt(section+"."+itemSect+".position"));
    	}
    }
    
}

