package net.crystalyx.bukkit.simplyperms.preventions.interact;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;

public class Fish extends SimplyPrevents {

	public Fish(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void fish(PlayerFishEvent event) {
		prevent(event, event.getPlayer(), "fish,interact");
	}

}
