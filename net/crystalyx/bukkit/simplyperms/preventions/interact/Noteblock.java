package net.crystalyx.bukkit.simplyperms.preventions.interact;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Noteblock extends SimplyPrevents {

	public Noteblock(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void noteblock(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.LEFT_CLICK_BLOCK
				|| action == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
				prevent(event, event.getPlayer(), "noteblock,interact");
			}
		}
	}

}
