package net.crystalyx.bukkit.simplyperms.preventions.chat;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

public class Macros extends SimplyPrevents {

	private HashMap<Player, Long> commandsTimestamps = new HashMap<Player, Long>();

	public Macros(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void chat(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (isChatLocked(player)) {
			prevent(event, player, "macros,spam");
		} else {
			setChatLock(player);
		}
	}

	private void setChatLock(Player player) {
		commandsTimestamps.put(player, Long.valueOf(System.currentTimeMillis() + 2000));
	}

	private boolean isChatLocked(Player player) {
		Long nextPossible = commandsTimestamps.get(player);
		if (nextPossible == null) {
			return false;
		}

		long currentTime = System.currentTimeMillis();
		return nextPossible.longValue() >= currentTime;
	}

}
