package net.crystalyx.bukkit.simplyperms.preventions.loot;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerDropItemEvent;

public class Drop extends SimplyPrevents {

	public Drop(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void drop(PlayerDropItemEvent event) {
		prevent(event, event.getPlayer(), "drop." + event.getItemDrop().getItemStack().getTypeId() + ",loot." + event.getItemDrop().getItemStack().getTypeId());
	}

}
