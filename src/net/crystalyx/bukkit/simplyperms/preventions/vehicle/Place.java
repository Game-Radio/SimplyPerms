package net.crystalyx.bukkit.simplyperms.preventions.vehicle;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

public class Place extends SimplyPrevents {

	public Place(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void vehiclePlace(PlayerInteractEvent event) {
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
					prevent(event, player, "vehicle.place");
				}
			} else if (materialInHand == Material.BOAT) {
				prevent(event, player, "vehicle.place");
			}
		}
	}

}
