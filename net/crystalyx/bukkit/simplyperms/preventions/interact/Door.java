package net.crystalyx.bukkit.simplyperms.preventions.interact;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Door extends SimplyPrevents {
	
	public Door(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void door(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.LEFT_CLICK_BLOCK
				|| action == Action.RIGHT_CLICK_BLOCK) {
			Material material = event.getClickedBlock().getType();
			if (material == Material.WOODEN_DOOR
					|| material == Material.IRON_DOOR
					|| material == Material.IRON_DOOR_BLOCK
					|| material == Material.TRAP_DOOR
					|| material == Material.FENCE_GATE) {
				prevent(event, event.getPlayer(), "door,interact");
			}
		}
	}
	
}
