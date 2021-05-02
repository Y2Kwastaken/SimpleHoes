package me.y2k.simplehoes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HoeCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 0) {
				p.sendMessage(Utils.clr("&a/harvesterhoe give [target] - gives player harvester hoe"));
				p.sendMessage(Utils.clr("&a/harvesterhoe replace - replaces old harvester hoe"));
				p.sendMessage(Utils.clr("&a/harvesterhoe fix - fixes the harvesterhoe if it breaks in any circumstance"));
				p.sendMessage(Utils.clr("&eCreated by - Y2K_ in 1 and a half hours :)"));
			}
			if(args.length >= 1) {
				switch(args[0]) {
				case "replace":
					if(p.hasPermission("simplehoe.replace")) {
						if(p.getItemInHand() == null) {return false;}
						if(p.getItemInHand().getItemMeta() == null) {return false;}
						ItemMeta inHandMeta = p.getItemInHand().getItemMeta();
						if(inHandMeta.getDisplayName() == null) {return false;}
						if(inHandMeta.getDisplayName().contains(Utils.clr("&aHarvester Hoe")) && inHandMeta.getLore().toString().contains("this hoe is currently")) {
							p.setItemInHand(new ItemStack(Material.AIR));
							p.setItemInHand(HoeStack.hoeStorage.get(0));
						}
					}
					//replace code here
					break;
				case "fix":
					if(p.hasPermission("simplehoe.fix")) {
						if(p.getItemInHand() == null) {return false;}
						if(p.getItemInHand().getItemMeta() == null) {return false;}
						ItemMeta inHandMeta = p.getItemInHand().getItemMeta();
						if(inHandMeta.getDisplayName() == null) {return false;}
						if(inHandMeta.getDisplayName().contains(Utils.clr("&aHarvester Hoe")) && inHandMeta.getLore().toString().contains("crops")) {
							p.setItemInHand(new ItemStack(Material.AIR));
							p.setItemInHand(HoeStack.hoeStorage.get(0));
						}
					}
					break;
				default:
					break;
				}
			}
			if(args.length >= 2) {
				switch(args[0]) {
				case "give":
					Player target = Bukkit.getPlayer(args[1]);
					target.getInventory().addItem(HoeStack.hoeStorage.get(0));
					break;
				default:
					break;
				}
			}
		}
		return true;
	}

}
