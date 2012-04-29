package net.crystalyx.bukkit.simplyperms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.file.YamlConfiguration;

import net.crystalyx.bukkit.simplyperms.io.PermsConfig;

public class SimplyAPI implements PermsConfig {

	private SimplyPlugin plugin;

	public SimplyAPI(SimplyPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void removePlayer(String player) {
		plugin.config.removePlayer(player);
	}

	@Override
	public void removePlayerGroups(String player) {
		plugin.config.removePlayerGroups(player);
	}

	@Override
	public void removePlayerGroup(String player, String group) {
		plugin.config.removePlayerGroup(player, group);
	}

	@Override
	public void addPlayerGroup(String player, String group) {
		plugin.config.addPlayerGroup(player, group);
	}

	@Override
	public void addPlayerPermission(String player, String permission, boolean value) {
		plugin.config.addPlayerPermission(player, permission, value);
	}

	@Override
	public void addPlayerPermission(String player, String world, String permission, boolean value) {
		plugin.config.addPlayerPermission(player, world, permission, value);
	}

	@Override
	public void removePlayerPermissions(String player) {
		plugin.config.removePlayerPermissions(player);
	}

	@Override
	public void removePlayerPermission(String player, String permission) {
		plugin.config.removePlayerPermission(player, permission);
	}

	@Override
	public void removePlayerPermission(String player, String world, String permission) {
		plugin.config.removePlayerPermission(player, world, permission);
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

	public List<String> getAllGroups() {
		if (plugin.getNode("groups") != null) {
			return new ArrayList<String>(plugin.getNode("groups").getKeys(false));
		}
		else {
			return new ArrayList<String>();
		}
	}

	public List<String> getGroupWorlds(String group) {
		if (group.isEmpty()) group = getDefaultGroup();
		if (plugin.getNode("groups/" + group + "/worlds") != null) {
			return new ArrayList<String>(plugin.getNode("groups/" + group + "/worlds").getKeys(false));
		}
		else {
			return new ArrayList<String>();
		}
	}

	public List<String> getGroupInheritance(String group) {
		if (group.isEmpty()) group = getDefaultGroup();
		return plugin.getConfig().getStringList("groups/" + group + "/inheritance");
	}

	public void addGroupInheritance(String group, String inherit) {
		if (group.isEmpty()) group = getDefaultGroup();
		List<String> inheritances = getGroupInheritance(group);
		if (!inheritances.contains(inherit)) inheritances.add(inherit);
		plugin.getConfig().set("groups/" + group + "/inheritance", inheritances);
	}

	public void removeGroupInheritance(String group, String inherit) {
		if (group.isEmpty()) group = getDefaultGroup();
		List<String> inheritances = getGroupInheritance(group);
		inheritances.remove(inherit);
		plugin.getConfig().set("groups/" + group + "/inheritance", inheritances);
	}
	
	public void removeGroupInheritances(String group) {
		if (group.isEmpty()) group = getDefaultGroup();
		plugin.getConfig().set("groups/" + group + "/inheritance", null);
	}

	public Map<String, Boolean> getGroupPermissions(String group, String world) {
		if (group.isEmpty()) group = getDefaultGroup();
		Map<String, Boolean> finalPerms = new HashMap<String, Boolean>();
		String permNode = (!world.isEmpty()) ? "groups/" + group + "/worlds/" + world : "groups/" + group + "/permissions";
		if (plugin.getNode(permNode) != null) {
			for (Entry<String, Object> permGroup : plugin.getNode(permNode).getValues(false).entrySet()) {
				finalPerms.put(permGroup.getKey(), (Boolean) permGroup.getValue());
			}
		}
		return finalPerms;
	}

	public Map<String, Boolean> getGroupPermissions(String group) {
		return getGroupPermissions(group, "");
	}

	public void addGroupPermission(String group, String world, String permission, boolean value) {
		if (group.isEmpty()) group = getDefaultGroup();
		Map<String, Boolean> permissions = getGroupPermissions(group, world);
		if (permissions.containsKey(permission)) permissions.remove(permission);
		permissions.put(permission, value);
		if (!world.isEmpty()) {
			plugin.getConfig().set("groups/" + group + "/worlds/" + world, permissions);
		}
		else {
			plugin.getConfig().set("groups/" + group + "/permissions", permissions);
		}
	}

	public void addGroupPermission(String group, String permission, boolean value) {
		addGroupPermission(group, "", permission, value);
	}

	public void removeGroupPermission(String group, String world, String permission) {
		if (group.isEmpty()) group = getDefaultGroup();
		Map<String, Boolean> permissions = getGroupPermissions(group, world);
		permissions.remove(permission);
		if (!world.isEmpty()) {
			plugin.getConfig().set("groups/" + group + "/worlds/" + world, (permissions.isEmpty()) ? null : permissions);
		}
		else {
			plugin.getConfig().set("groups/" + group + "/permissions", (permissions.isEmpty()) ? null : permissions);
		}
	}

	public void removeGroupPermission(String group, String permission) {
		removeGroupPermission(group, "", permission);
	}

	public void removeGroupPermissions(String group) {
		if (group.isEmpty()) group = getDefaultGroup();
		plugin.getConfig().set("groups/" + group + "/permissions", null);
	}

	public void removeGroup(String group) {
		plugin.getConfig().set("groups/" + group, null);
	}

	public Map<String, Object> getMessages() {
		if (plugin.getNode("messages") != null) {
			return plugin.getNode("messages").getValues(false);
		}
		else {
			return new HashMap<String, Object>();
		}
	}

	public void addMessage(String key, String message) {
		Map<String, Object> messages = getMessages();
		if (!messages.containsKey(key)) messages.put(key, message);
		plugin.getConfig().set("messages", messages);
	}

	public void removeMessage(String key) {
		Map<String, Object> messages = getMessages();
		messages.remove(key);
		plugin.getConfig().set("messages", messages);
	}

	public String getDefaultGroup() {
		return plugin.getConfig().getString("default", "default");
	}

	public void setDefaultGroup(String group) {
		if (group.isEmpty()) group = "default";
		plugin.getConfig().set("default", group);
	}

	public void refreshPermissions() {
		plugin.refreshPermissions();
	}

	protected List<String> getKeys(YamlConfiguration config, String node) {
		if (config.isConfigurationSection(node)) {
			return new ArrayList<String>(config.getConfigurationSection(node).getKeys(false));
		}
		else {
			return new ArrayList<String>();
		}
	}

}
