package net.crystalyx.bukkit.simplyperms.preventions.projectile;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Potion extends SimplyPrevents {

	public Potion(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void potion(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().getItemInHand().getTypeId() == Material.POTION.getId()
					|| event.getPlayer().getItemInHand().getType() == Material.EXP_BOTTLE) {
				prevent(event, event.getPlayer(), "potion,projectile");
			}
		}
	}

}
