package net.crystalyx.bukkit.simplyperms;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Listener;

public abstract class SimplyPrevents implements Listener {

	protected SimplyPlugin plugin;
	private HashMap<Player, Long> throttleTimestamps = new HashMap<Player, Long>();
	public static String[] preventions = {
		"Bow",
		"Changesign",
		"Command",
		"Damage",
		"Fight",
		"Hunger",
		"Item",
		"Monster",
		"Sneak",
		"animals.Shear",
		"animals.Tame",
		"bucket.Lavabucket",
		"bucket.Milking",
		"bucket.Waterbucket",
		"build.Breakblock",
		"build.Placeblock",
		"chat.Chat",
		"chat.CapsLock",
		"chat.Flood",
		"craft.Brew",
		"craft.Chest",
		"craft.Dispenser",
		"craft.Enchant",
		"craft.Furnace",
		"craft.Workbench",
		"fire.Fireball",
		"fire.FlintAndSteel",
		"interact.Bed",
		"interact.Button",
		"interact.Cake",
		"interact.Door",
		"interact.Fish",
		"interact.Jukebox",
		"interact.Lever",
		"interact.Noteblock",
		"interact.Pressureplate",
		"interact.Repeater",
		"loot.Drop",
		"loot.Pickup",
		"projectile.Egg",
		"projectile.EnderPearl",
		"projectile.Potion",
		"projectile.Snowball",
		"vehicle.Collision",
		"vehicle.Destroy",
		"vehicle.Enter",
		"vehicle.Place"
	};

	public SimplyPrevents(SimplyPlugin plugin) {
		this.plugin = plugin;
	}

	public void sendMessage(Player player, String node) {
		Long next = throttleTimestamps.get(player);
		next = Long.valueOf(next == null ? 0 : next.longValue());
		long current = System.currentTimeMillis();

		if (next.longValue() < current) {
			player.sendMessage(plugin.config.getMessage(node));
			plugin.debug("Event '" + node + "' cancelled for " + player.getName());
			throttleTimestamps.put(player, Long.valueOf(current + 3000));
		}
	}

	protected boolean prevent(Cancellable event, Player player, String node) {
		if (node.contains(",")) {
			for (String subNode : node.split(",")) {
				if (!prevent(event, player, subNode)) {
					return false;
				}
			}
		} else if (node.contains(".")) {
			if (player.isPermissionSet("permissions.allow." + node)) {
				if (!player.hasPermission("permissions.allow." + node)) {
					event.setCancelled(true);
					sendMessage(player, node);
					return true;
				}
			} else if (prevent(event, player, node.substring(0, node.indexOf('.')))) {
				return true;
			}
		} else if (player.isPermissionSet("permissions.allow." + node)) {
			if (!player.hasPermission("permissions.allow." + node)) {
				event.setCancelled(true);
				sendMessage(player, node);
				return true;
			}
		} else if (player.isPermissionSet("permissions.allow.*")
				&& !player.hasPermission("permissions.allow.*")) {
			event.setCancelled(true);
			sendMessage(player, node);
			return true;
		}
		return false;
	}

}
