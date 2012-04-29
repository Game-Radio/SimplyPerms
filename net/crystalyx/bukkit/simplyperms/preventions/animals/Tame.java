package net.crystalyx.bukkit.simplyperms.preventions.animals;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTameEvent;

public class Tame extends SimplyPrevents {

	public Tame(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void tame(EntityTameEvent event) {
		if (event.getOwner() instanceof Player) {
			prevent(event, (Player) event.getOwner(), "tame,animals,interact");
		}
	}
	
}
