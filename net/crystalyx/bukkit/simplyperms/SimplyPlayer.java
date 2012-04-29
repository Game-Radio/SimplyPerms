package net.crystalyx.bukkit.simplyperms;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SimplyPlayer implements Listener {

	private SimplyPlugin plugin;
	
	public SimplyPlayer(SimplyPlugin plugin) {
		this.plugin = plugin;
	}
	
	// Keep track of player's world
	
    @EventHandler(priority = EventPriority.LOWEST)
    public void onWorldChange(PlayerChangedWorldEvent event) {
    	plugin.debug("Player " + event.getPlayer().getName() + " changed world, recalculating...");
        plugin.calculateAttachment(event.getPlayer());
    }
	
    // Register players when needed
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerJoinEvent event) {
        plugin.debug("Player " + event.getPlayer().getName() + " joined, registering...");
        plugin.registerPlayer(event.getPlayer());
    }

    // Unregister players when needed
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.isCancelled()) return;
        plugin.debug("Player " + event.getPlayer().getName() + " was kicked, unregistering...");
        plugin.unregisterPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.debug("Player " + event.getPlayer().getName() + " quit, unregistering...");
        plugin.unregisterPlayer(event.getPlayer());
    }

}
