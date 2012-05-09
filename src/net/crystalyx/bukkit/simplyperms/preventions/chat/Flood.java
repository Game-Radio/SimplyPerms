package net.crystalyx.bukkit.simplyperms.preventions.chat;

import java.util.HashMap;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatEvent;

public class Flood extends SimplyPrevents {

	private HashMap<Player, Long> chatTimestamps = new HashMap<Player, Long>();

	public Flood(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void chat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		if (isChatLocked(player)) {
			prevent(event, player, "flood,spam");
		} else {
			setChatLock(player);
		}
	}

	private void setChatLock(Player player) {
		chatTimestamps.put(player, Long.valueOf(System.currentTimeMillis() + 2000));
	}

	private boolean isChatLocked(Player player) {
		Long nextPossible = chatTimestamps.get(player);
		if (nextPossible == null) {
			return false;
		}

		long currentTime = System.currentTimeMillis();
		return nextPossible.longValue() >= currentTime;
	}

}
