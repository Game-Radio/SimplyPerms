package net.crystalyx.bukkit.simplyperms.preventions.build;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Breakblock extends SimplyPrevents {

	public Breakblock(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void breakblock(BlockBreakEvent event) {
		prevent(event, event.getPlayer(), "breakblock." + event.getBlock().getTypeId() + ",build." + event.getBlock().getTypeId());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void paintingBreakblock(PaintingBreakByEntityEvent event) {
		Entity remover = event.getRemover();
		if (remover instanceof Player) {
			prevent(event, (Player) remover, "breakblock." + Material.PAINTING.getId() + ",build." + Material.PAINTING.getId());
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void fireBreakblock(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (event.getClickedBlock().getRelative(BlockFace.UP).getType() == Material.FIRE) {
				if (prevent(event, event.getPlayer(), "breakblock." + Material.FIRE.getId() + ",build." + Material.FIRE.getId())) {
					event.setUseInteractedBlock(Result.DENY);
					event.setUseItemInHand(Result.DENY);
				}
			}
		}
	}

}
