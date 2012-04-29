package net.crystalyx.bukkit.simplyperms.preventions;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

public class Vehicle extends SimplyPrevents {

	public Vehicle(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicleEnter(VehicleEnterEvent event) {
		Entity entered = event.getEntered();
		if (entered instanceof Player) {
			prevent(event, (Player) entered, "vehicle");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicleDestroy(VehicleDestroyEvent event) {
		Entity attacker = event.getAttacker();
		if (attacker instanceof Player) {
			prevent(event, (Player) attacker, "vehicle");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicleCollision(VehicleEntityCollisionEvent event) {
		Entity collider = event.getEntity();
		if (collider instanceof Player) {
			if (prevent(event, (Player) collider, "vehicle")) {
				event.setCollisionCancelled(true);
				event.setPickupCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehicle(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Material clickedMaterial = event.getClickedBlock().getType();
			Player player = event.getPlayer();
			Material materialInHand = player.getItemInHand().getType();
			if (clickedMaterial == Material.RAILS
					|| clickedMaterial == Material.POWERED_RAIL
					|| clickedMaterial == Material.DETECTOR_RAIL) {
				if (materialInHand == Material.MINECART
						|| materialInHand == Material.POWERED_MINECART
						|| materialInHand == Material.STORAGE_MINECART) {
					prevent(event, player, "vehicle");
				}
			} else if (materialInHand == Material.BOAT) {
				prevent(event, player, "vehicle");
			}
		}
	}

}
