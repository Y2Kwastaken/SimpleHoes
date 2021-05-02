package me.y2k.simplehoes;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class HoeBreakEvent implements Listener {
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(p.getItemInHand() == null) {return;}
		ItemStack inHand = p.getItemInHand();
		if(inHand.getItemMeta() == null) {return;}
		if(inHand.getItemMeta().getDisplayName() == null) {return;}
		if(inHand.getItemMeta().getDisplayName().contains(Utils.clr("&7&l(&eCollection&7&l)")) && inHand.getItemMeta().getLore().toString().contains("crops")) {
			this.hoeFunction(e, "collect");
			return;
		}else if(inHand.getItemMeta().getDisplayName().contains(Utils.clr("&7&l(&aAutoSell&7&l)")) && inHand.getItemMeta().getLore().toString().contains("crops")) {
			this.hoeFunction(e, "sell");
			return;
		}
		//sugar cane workings

	}
	@SuppressWarnings({ "unused", "static-method" })
	private void hoeFunction(BlockBreakEvent e, String function) {
		Random rand = new Random();
		// nextInt as provided by Random is exclusive of the top value so you need to add 1 
		Block b = e.getBlock();
		Player p = e.getPlayer();
		if(function == "collect") {
		    if (b.getType().equals(Material.SUGAR_CANE_BLOCK)) {
		        e.setCancelled(true);
		        int i = 0;
		        Location currLoc;
		        for (currLoc = e.getBlock().getLocation(); currLoc.getBlock().getType() == Material.SUGAR_CANE_BLOCK; currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), (currLoc.getBlockY() + 1), currLoc.getBlockZ()));
		        for (currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), (currLoc.getBlockY() - 1), currLoc.getBlockZ()); currLoc.getBlockY() >= e.getBlock().getY(); currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), (currLoc.getBlockY() - 1), currLoc.getBlockZ())) {
		          currLoc.getBlock().setType(Material.AIR);
		          p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.SUGAR_CANE) });
		          i++;
		        }
		    }
		    if(b.getType().equals(Material.POTATO) || b.getType().equals(Material.CROPS) || b.getType().equals(Material.CARROT) || b.getType().equals(Material.NETHER_WARTS)) {
		    byte data = b.getData();
		 	if(data == (byte)7) {
				int restRand = rand.nextInt((3 - 1) + 1) + 1;
		 		switch(b.getType()) {
				case POTATO:
					int randomNum = rand.nextInt((5 - 1) + 1) + 1;
					e.setCancelled(true);
					b.getLocation().getBlock().setType(Material.AIR);
					p.getInventory().addItem(new ItemStack(Material.POTATO_ITEM, restRand));
					break;
				case CROPS:
					e.setCancelled(true);
					b.getLocation().getBlock().setType(Material.AIR);
					p.getInventory().addItem(new ItemStack(Material.WHEAT, restRand));
					break;
				case CARROT:
					e.setCancelled(true);
					b.getLocation().getBlock().setType(Material.AIR);
					p.getInventory().addItem(new ItemStack(Material.CARROT_ITEM, restRand));
					break;
				default:
					break;
		 		}
		 	}else if(data == (byte)3) {
		 		if(b.getType() == Material.NETHER_WARTS) {
		 			int wartRand = rand.nextInt((5 - 1) + 1) + 1;
					e.setCancelled(true);
					b.getLocation().getBlock().setType(Material.AIR);
		 			p.getInventory().addItem(new ItemStack(Material.NETHER_STALK, wartRand));
		 		}
		 	}
		    	}
		}

		if(function == "sell") {
			OfflinePlayer op = Bukkit.getOfflinePlayer(p.getName());
		    if (b.getType().equals(Material.SUGAR_CANE_BLOCK)) {
		        e.setCancelled(true);
		        int i = 0;
		        Location currLoc;
		        for (currLoc = e.getBlock().getLocation(); currLoc.getBlock().getType() == Material.SUGAR_CANE_BLOCK; currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), (currLoc.getBlockY() + 1), currLoc.getBlockZ()));
		        for (currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), (currLoc.getBlockY() - 1), currLoc.getBlockZ()); currLoc.getBlockY() >= e.getBlock().getY(); currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), (currLoc.getBlockY() - 1), currLoc.getBlockZ())) {
		          currLoc.getBlock().setType(Material.AIR);
	              double baseprice = ShopGuiHook.price(new ItemStack(Material.SUGAR_CANE));
	              SimpleHoes.getEconomy().depositPlayer(op, baseprice);
		          i++;
		        }
		    }
		    if(b.getType().equals(Material.POTATO) || b.getType().equals(Material.CROPS) || b.getType().equals(Material.CARROT) || b.getType().equals(Material.NETHER_WARTS)) {
		    byte data = b.getData();
		 	if(data == (byte)7) {
				int restRand = rand.nextInt((3 - 1) + 1) + 1;
		 		switch(b.getType()) {
				case POTATO:
					e.setCancelled(true);
					b.getLocation().getBlock().setType(Material.AIR);
		            double price1 = ShopGuiHook.price(new ItemStack(Material.POTATO_ITEM, restRand));
		            SimpleHoes.getEconomy().depositPlayer(op, price1);
					break;
				case CROPS:
					e.setCancelled(true);
					b.getLocation().getBlock().setType(Material.AIR);
		            double price2 = ShopGuiHook.price(new ItemStack(Material.WHEAT, restRand));
		            SimpleHoes.getEconomy().depositPlayer(op, price2);
					break;
				case CARROT:
					e.setCancelled(true);
					b.getLocation().getBlock().setType(Material.AIR);
		            double price3 = ShopGuiHook.price(new ItemStack(Material.CARROT_ITEM, restRand));
		            SimpleHoes.getEconomy().depositPlayer(op, price3);
					break;
				default:
					break;
		 		}
		 	}else if(data == (byte)3) {
		 		if(b.getType() == Material.NETHER_WARTS) {
		 			int wartRand = rand.nextInt((5 - 1) + 1) + 1;
					b.getDrops().clear();
					b.getLocation().getBlock().setType(Material.AIR);
		            double price3 = ShopGuiHook.price(new ItemStack(Material.NETHER_STALK, wartRand));
		            SimpleHoes.getEconomy().depositPlayer(op, price3);
		 		}
		 	}
		    	}
		}
	}
}

