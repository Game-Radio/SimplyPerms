package net.crystalyx.bukkit.simplyperms.preventions;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTargetEvent;

public class Monster extends SimplyPrevents {

	public Monster(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void monster(EntityTargetEvent event) {
		Entity target = event.getTarget();
		if (target instanceof Player) {
			prevent(event, (Player) target, "monster");
		}
	}

}
