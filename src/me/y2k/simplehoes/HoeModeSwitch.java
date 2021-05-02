package me.y2k.simplehoes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class HoeModeSwitch implements Listener {
	
	@EventHandler
	public void itemInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(p.getItemInHand() == null) {return;}
		if(p.getItemInHand().getItemMeta() == null) {return;}
		if(p.getItemInHand().getItemMeta().getDisplayName() == null) {return;}
		ItemMeta meta = p.getItemInHand().getItemMeta();
		if(e.getAction() == Action.RIGHT_CLICK_AIR) {
			String display = p.getItemInHand().getItemMeta().getDisplayName();
			if(display.toString().contains("Collection") && meta.getLore().toString().contains("crops")) {
				String CtoA = display.toString().replaceAll(Utils.clr("&eCollection"), Utils.clr("&aAutoSell"));
				meta.setDisplayName(Utils.clr(CtoA));
				p.getItemInHand().setItemMeta(meta);
			}else if(display.contains("AutoSell") && meta.getLore().toString().contains("crops")) {
				String AtoC = display.replaceAll(Utils.clr("&aAutoSell"), Utils.clr("&eCollection"));
				meta.setDisplayName(Utils.clr(AtoC));
				p.getItemInHand().setItemMeta(meta);
			}
	}
}
}
