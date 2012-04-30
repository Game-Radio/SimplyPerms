package net.crystalyx.bukkit.simplyperms.preventions.interact;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Pressureplate extends SimplyPrevents {

	public Pressureplate(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void pressureplate(PlayerInteractEvent event) {
		if (event.getAction() == Action.PHYSICAL) {
			Material material = event.getClickedBlock().getType();
			if (material == Material.STONE_PLATE
					|| material == Material.WOOD_PLATE) {
				prevent(event, event.getPlayer(), "pressureplate,interact");
			}
		}
	}

}
