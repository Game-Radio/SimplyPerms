package net.crystalyx.bukkit.simplyperms.preventions.build;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;

public class Placeblock extends SimplyPrevents {

	public Placeblock(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void placeblock(BlockPlaceEvent event) {
		prevent(event, event.getPlayer(), "placeblock." + event.getBlockPlaced().getTypeId() + ",build." + event.getBlockPlaced().getTypeId());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void paintingPlaceblock(PaintingPlaceEvent event) {
		prevent(event, event.getPlayer(), "placeblock." + Material.PAINTING.getId() + ",build." + Material.PAINTING.getId());
	}

}
