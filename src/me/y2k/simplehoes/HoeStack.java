package me.y2k.simplehoes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class HoeStack {
	private SimpleHoes plugin() {
		return SimpleHoes.plugin;
	}
	private FileConfiguration config = plugin().getConfigFile("config.yml");
	public static List<ItemStack> hoeStorage = new ArrayList<ItemStack>();
	public void Stacks() {
		Utils.itemFromConfig(config, false, 0, true, true, "HarvesterHoe", hoeStorage);
	}
}
