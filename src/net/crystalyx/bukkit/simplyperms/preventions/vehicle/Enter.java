package net.crystalyx.bukkit.simplyperms.preventions.vehicle;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

public class Enter extends SimplyPrevents {

	public Enter(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicleEnter(VehicleEnterEvent event) {
		Entity entered = event.getEntered();
		if (entered instanceof Player) {
			prevent(event, (Player) entered, "vehicle.enter");
		}
	}

}
