package net.crystalyx.bukkit.simplyperms.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;

public class ConfigFile implements PermsConfig {

	private SimplyPlugin plugin;

	public ConfigFile(SimplyPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void removePlayer(String player) {
		plugin.getConfig().set("users/" + player, null);
	}

	@Override
	public void removePlayerGroups(String player) {
		plugin.getConfig().set("users/" + player + "/groups", null);
	}

	@Override
	public void removePlayerGroup(String player, String group) {
		List<String> groups = getPlayerGroups(player);
		groups.remove(group);
		plugin.getConfig().set("users/" + player + "/groups", (groups.isEmpty()) ? null : groups);
	}

	@Override
	public void addPlayerGroup(String player, String group) {
		if (group.isEmpty() || group.equals("default")) return;
		List<String> groups = getPlayerGroups(player);
		if (!groups.contains(group)) groups.add(group);
		plugin.getConfig().set("users/" + player + "/groups", groups);
	}

	@Override
	public void addPlayerPermission(String player, String permission, boolean value) {
		addPlayerPermission(player, "", permission, value);
	}

	@Override
	public void addPlayerPermission(String player, String world, String permission, boolean value) {
		Map<String, Boolean> permissions = getPlayerPermissions(player, world);
		if (permissions.containsKey(permission)) permissions.remove(permission);
		permissions.put(permission, value);
		if (!world.isEmpty()) {
			plugin.getConfig().set("users/" + player + "/worlds/" + world, permissions);
		}
		else {
			plugin.getConfig().set("users/" + player + "/permissions", permissions);
		}
	}

	@Override
	public void removePlayerPermissions(String player) {
		plugin.getConfig().set("users/" + player + "/permissions", null);
	}

	@Override
	public void removePlayerPermission(String player, String permission) {
		removePlayerPermission(player, "", permission);
	}

	@Override
	public void removePlayerPermission(String player, String world, String permission) {
		Map<String, Boolean> permissions = getPlayerPermissions(player, world);
		permissions.remove(permission);
		if (!world.isEmpty()) {
			plugin.getConfig().set("users/" + player + "/worlds/" + world, (permissions.isEmpty()) ? null : permissions);
		}
		else {
			plugin.getConfig().set("users/" + player + "/permissions", (permissions.isEmpty()) ? null : permissions);
		}
	}

	@Override
	public List<String> getPlayers(String group) {
		List<String> players = new ArrayList<String>();
		for (String player : getAllPlayers()) {
			for (String groupName : getPlayerGroups(player)) {
				if (groupName.equals(group)) {
					players.add(player);
				}
			}
		}
		return players;
	}

	@Override
	public List<String> getPlayerGroups(String player) {
		return plugin.getConfig().getStringList("users/" + player + "/groups");
	}

	@Override
	public Map<String, Boolean> getPlayerPermissions(String player) {
		return getPlayerPermissions(player, "");
	}

	@Override
	public Map<String, Boolean> getPlayerPermissions(String player, String world) {
		Map<String, Boolean> finalPerms = new HashMap<String, Boolean>();
		String permNode = (!world.isEmpty()) ? "users/" + player + "/worlds/" + world : "users/" + player + "/permissions";
		if (plugin.getConfig().getConfigurationSection(permNode) != null) {
			for (Entry<String, Object> permPlayer : plugin.getConfig().getConfigurationSection(permNode).getValues(false).entrySet()) {
				finalPerms.put(permPlayer.getKey(), (Boolean) permPlayer.getValue());
			}
		}
		return finalPerms;
	}

	@Override
	public boolean isPlayerInDB(String player) {
		return plugin.getConfig().getConfigurationSection("users/" + player) != null;
	}

	@Override
	public List<String> getPlayerWorlds(String player) {
		if (plugin.getConfig().getConfigurationSection("users/" + player + "/worlds") != null) {
			return new ArrayList<String>(plugin.getConfig().getConfigurationSection("users/" + player + "/worlds").getKeys(false));
		}
		else {
			return new ArrayList<String>();
		}
	}

	@Override
	public List<String> getAllPlayers() {
		if (plugin.getConfig().getConfigurationSection("users") != null) {
			return new ArrayList<String>(plugin.getConfig().getConfigurationSection("users").getKeys(false));
		}
		else {
			return new ArrayList<String>();
		}
	}

}
