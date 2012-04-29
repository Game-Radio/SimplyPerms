package net.crystalyx.bukkit.simplyperms.preventions.interact;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBedEnterEvent;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

public class Bed extends SimplyPrevents {

	public Bed(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void bed(PlayerBedEnterEvent event) {
		prevent(event, event.getPlayer(), "bed,interact");
	}

}
