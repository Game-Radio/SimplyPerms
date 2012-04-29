package net.crystalyx.bukkit.simplyperms.preventions.projectile;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Egg extends SimplyPrevents {

	public Egg(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void egg(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().getItemInHand().getTypeId() == Material.MONSTER_EGG.getId()
					|| event.getPlayer().getItemInHand().getType() == Material.EGG) {
				prevent(event, event.getPlayer(), "egg,projectile");
			}
		}
	}

}
