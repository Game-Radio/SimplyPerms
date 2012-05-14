package net.crystalyx.bukkit.simplyperms;

import java.util.List;
import java.util.Map;

import net.crystalyx.bukkit.simplyperms.io.PermsConfig;

public class SimplyAPI implements PermsConfig {

	private SimplyPlugin plugin;

	public SimplyAPI(SimplyPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void removePlayer(String player) {
		plugin.config.removePlayer(player);
		refreshPermissions();
	}

	@Override
	public void removePlayerGroups(String player) {
		plugin.config.removePlayerGroups(player);
		refreshPermissions();
	}

	@Override
	public void removePlayerGroup(String player, String group) {
		plugin.config.removePlayerGroup(player, group);
		refreshPermissions();
	}

	@Override
	public void setPlayerGroup(String player, String group) {
		plugin.config.setPlayerGroup(player, group);
		refreshPermissions();
	}

	@Override
	public void addPlayerGroup(String player, String group) {
		plugin.config.addPlayerGroup(player, group);
		refreshPermissions();
	}

	@Override
	public void addPlayerPermission(String player, String permission, boolean value) {
		plugin.config.addPlayerPermission(player, permission, value);
		refreshPermissions();
	}

	@Override
	public void addPlayerPermission(String player, String world, String permission, boolean value) {
		plugin.config.addPlayerPermission(player, world, permission, value);
		refreshPermissions();
	}

	@Override
	public void removePlayerPermissions(String player) {
		plugin.config.removePlayerPermissions(player);
		refreshPermissions();
	}

	@Override
	public void removePlayerPermission(String player, String permission) {
		plugin.config.removePlayerPermission(player, permission);
		refreshPermissions();
	}

	@Override
	public void removePlayerPermission(String player, String world, String permission) {
		plugin.config.removePlayerPermission(player, world, permission);
		refreshPermissions();
	}

	@Override
	public List<String> getPlayers(String group) {
		return plugin.config.getPlayers(group);
	}

	@Override
	public List<String> getPlayerGroups(String player) {
		return plugin.config.getPlayerGroups(player);
	}

	@Override
	public Map<String, Boolean> getPlayerPermissions(String player) {
		return plugin.config.getPlayerPermissions(player);
	}

	@Override
	public Map<String, Boolean> getPlayerPermissions(String player, String world) {
		return plugin.config.getPlayerPermissions(player, world);
	}

	@Override
	public boolean isPlayerInDB(String player) {
		return plugin.config.isPlayerInDB(player);
	}

	@Override
	public List<String> getPlayerWorlds(String player) {
		return plugin.config.getPlayerWorlds(player);
	}

	@Override
	public List<String> getAllPlayers() {
		return plugin.config.getAllPlayers();
	}

	@Override
	public List<String> getAllGroups() {
		return plugin.config.getAllGroups();
	}

	@Override
	public List<String> getGroupWorlds(String group) {
		return plugin.config.getGroupWorlds(group);
	}

	@Override
	public List<String> getGroupInheritance(String group) {
		return plugin.config.getGroupInheritance(group);
	}

	@Override
	public void addGroupInheritance(String group, String inherit) {
		plugin.config.addGroupInheritance(group, inherit);
		refreshPermissions();
	}

	@Override
	public void removeGroupInheritance(String group, String inherit) {
		plugin.config.removeGroupInheritance(group, inherit);
		refreshPermissions();
	}

	@Override
	public void removeGroupInheritances(String group) {
		plugin.config.removeGroupInheritances(group);
		refreshPermissions();
	}

	@Override
	public Map<String, Boolean> getGroupPermissions(String group, String world) {
		return plugin.config.getGroupPermissions(group, world);
	}

	@Override
	public Map<String, Boolean> getGroupPermissions(String group) {
		return plugin.config.getGroupPermissions(group);
	}

	@Override
	public void addGroupPermission(String group, String world, String permission, boolean value) {
		plugin.config.addGroupPermission(group, world, permission, value);
		refreshPermissions();
	}

	@Override
	public void addGroupPermission(String group, String permission, boolean value) {
		plugin.config.addGroupPermission(group, permission, value);
		refreshPermissions();
	}

	@Override
	public void removeGroupPermission(String group, String world, String permission) {
		plugin.config.removeGroupPermission(group, world, permission);
		refreshPermissions();
	}

	@Override
	public void removeGroupPermission(String group, String permission) {
		plugin.config.removeGroupPermission(group, permission);
		refreshPermissions();
	}

	@Override
	public void removeGroupPermissions(String group) {
		plugin.config.removeGroupPermissions(group);
		refreshPermissions();
	}

	@Override
	public void removeGroup(String group) {
		plugin.config.removeGroup(group);
		refreshPermissions();
	}

	@Override
	public Map<String, Object> getMessages() {
		return plugin.config.getMessages();
	}

	@Override
	public String getMessage(String key) {
		return plugin.config.getMessage(key);
	}

	@Override
	public void addMessage(String key, String message) {
		plugin.config.addMessage(key, message);
		refreshPermissions();
	}

	@Override
	public void removeMessage(String key) {
		plugin.config.removeMessage(key);
		refreshPermissions();
	}

	@Override
	public String getDefaultGroup() {
		return plugin.config.getDefaultGroup();
	}

	@Override
	public void setDefaultGroup(String group) {
		plugin.config.setDefaultGroup(group);
		refreshPermissions();
	}

	@Override
	public boolean getDebug() {
		return plugin.config.getDebug();
	}

	@Override
	public void setDebug(boolean debug) {
		plugin.config.setDebug(debug);
		refreshPermissions();
	}

	public void refreshPermissions() {
		plugin.refreshPermissions();
	}

}
