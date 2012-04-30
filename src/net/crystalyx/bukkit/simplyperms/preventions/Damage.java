package net.crystalyx.bukkit.simplyperms.preventions;

import java.util.Collection;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;

public class Damage extends SimplyPrevents {

	public Damage(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void damage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (prevent(event, player, "damage")) {
				player.setFireTicks(0);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void damagePotion(PotionSplashEvent event) {
		Collection<LivingEntity> affectedEntities = event.getAffectedEntities();

		for (LivingEntity entity : affectedEntities) {
			if (entity instanceof Player) {
				Player affectedPlayer = (Player) entity;
				if (prevent(event, affectedPlayer, "damage")) {
					affectedEntities.remove(entity);
				}
			}
		}
	}

}
