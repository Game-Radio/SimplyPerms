package net.crystalyx.bukkit.simplyperms.preventions.bucket;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class Milking extends SimplyPrevents {

	public Milking(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void milking(PlayerBucketFillEvent event) {
		if (event.getItemStack().getType() == Material.MILK_BUCKET) {
			prevent(event, event.getPlayer(), "milking,bucket,interact");
		}
	}
	
}
