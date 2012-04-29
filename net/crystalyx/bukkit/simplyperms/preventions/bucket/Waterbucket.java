package net.crystalyx.bukkit.simplyperms.preventions.bucket;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class Waterbucket extends SimplyPrevents {

	public Waterbucket(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void waterbucketEmpty(PlayerBucketEmptyEvent event) {
		if (event.getBucket() == Material.WATER_BUCKET) {
			prevent(event, event.getPlayer(), "waterbucket,bucket,interact");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void waterbucketFill(PlayerBucketFillEvent event) {
		if (event.getItemStack().getType() == Material.WATER_BUCKET) {
			prevent(event, event.getPlayer(), "waterbucket,bucket,interact");
		}
	}
	
}
