package net.crystalyx.bukkit.simplyperms.preventions.fire;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FlintAndSteel extends SimplyPrevents {

	public FlintAndSteel(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void flint(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().getItemInHand().getType() == Material.FLINT_AND_STEEL) {
				prevent(event, event.getPlayer(), "flintandsteel,fire,interact");
			}
		}
	}
	
}
