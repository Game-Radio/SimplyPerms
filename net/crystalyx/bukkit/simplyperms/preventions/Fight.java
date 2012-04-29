package net.crystalyx.bukkit.simplyperms.preventions;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Fight extends SimplyPrevents {

	public Fight(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void fight(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (damager instanceof Player) {
			prevent(event, (Player) damager, "fight");
		} else if (damager instanceof Projectile) {
			LivingEntity shooter = ((Projectile) damager).getShooter();
			if (shooter instanceof Player) {
				prevent(event, (Player) shooter, "fight");
			}
		}
	}
	
}
