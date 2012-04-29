package net.crystalyx.bukkit.simplyperms.preventions;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Item extends SimplyPrevents {

	public Item(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void item(PlayerInteractEvent event) {
		if (event.getAction() != Action.PHYSICAL) {
			ItemStack itemInHand = event.getItem();
			if (itemInHand != null) {
				if (prevent(event, event.getPlayer(), "item." + itemInHand.getTypeId())) {
					event.setUseInteractedBlock(Result.DENY);
					event.setUseItemInHand(Result.DENY);
				}
			}
		}
	}
	
}
