package net.crystalyx.bukkit.simplyperms.preventions.interact;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Lever extends SimplyPrevents {

	public Lever(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lever(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.LEFT_CLICK_BLOCK
				|| action == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.LEVER) {
				prevent(event, event.getPlayer(), "lever,interact");
			}
		}
	}
	
}
