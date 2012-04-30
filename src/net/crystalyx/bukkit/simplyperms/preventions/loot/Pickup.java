package net.crystalyx.bukkit.simplyperms.preventions.loot;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Pickup extends SimplyPrevents {

	public Pickup(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void pickup(PlayerPickupItemEvent event) {
		prevent(event, event.getPlayer(), "pickup." + event.getItem().getItemStack().getTypeId() + ",loot." + event.getItem().getItemStack().getTypeId());
	}

}
