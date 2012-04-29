package net.crystalyx.bukkit.simplyperms.preventions.animals;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class Shear extends SimplyPrevents {

	public Shear(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void shear(PlayerShearEntityEvent event) {
		prevent(event, event.getPlayer(), "shear,animals,interact");
	}

}
