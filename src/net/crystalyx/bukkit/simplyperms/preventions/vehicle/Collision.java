package net.crystalyx.bukkit.simplyperms.preventions.vehicle;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

public class Collision extends SimplyPrevents {

	public Collision(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicleCollision(VehicleEntityCollisionEvent event) {
		Entity collider = event.getEntity();
		if (collider instanceof Player) {
			if (prevent(event, (Player) collider, "vehicle.collision")) {
				event.setCollisionCancelled(true);
				event.setPickupCancelled(true);
			}
		}
	}

}
