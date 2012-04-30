package net.crystalyx.bukkit.simplyperms.preventions.bucket;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class Lavabucket extends SimplyPrevents {

	public Lavabucket(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lavabucketEmpty(PlayerBucketEmptyEvent event) {
		if (event.getBucket() == Material.LAVA_BUCKET) {
			prevent(event, event.getPlayer(), "lavabucket,bucket,interact");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lavabucketFill(PlayerBucketFillEvent event) {
		if (event.getItemStack().getType() == Material.LAVA_BUCKET) {
			prevent(event, event.getPlayer(), "lavabucket,bucket,interact");
		}
	}

}
