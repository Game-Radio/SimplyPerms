package net.crystalyx.bukkit.simplyperms.preventions.vehicle;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

public class Destroy extends SimplyPrevents {

	public Destroy(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicleDestroy(VehicleDestroyEvent event) {
		Entity attacker = event.getAttacker();
		if (attacker instanceof Player) {
			prevent(event, (Player) attacker, "vehicle.destroy");
		}
	}

}
