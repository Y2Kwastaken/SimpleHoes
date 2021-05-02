package me.y2k.simplehoes;

import org.bukkit.inventory.ItemStack;

import net.brcdev.shopgui.ShopGuiPlusApi;

public class ShopGuiHook {
		public static double price(ItemStack stack) {
			return ShopGuiPlusApi.getItemStackPriceSell(stack);
		}
}
