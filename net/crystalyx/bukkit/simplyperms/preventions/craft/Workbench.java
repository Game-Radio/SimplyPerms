package net.crystalyx.bukkit.simplyperms.preventions.craft;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class Workbench extends SimplyPrevents {

	public Workbench(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void workbench(InventoryOpenEvent event) {
		if (event.getInventory().getType() == InventoryType.WORKBENCH) {
			if (event.getPlayer() instanceof Player) {
				prevent(event, (Player) event.getPlayer(), "workbench,craft");
			}
		}
	}
	
}
