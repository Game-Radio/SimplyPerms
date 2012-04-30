package net.crystalyx.bukkit.simplyperms.preventions;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;

public class Changesign extends SimplyPrevents {

	public Changesign(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void changesign(SignChangeEvent event) {
		prevent(event, event.getPlayer(), "changesign");
	}

}
