package net.crystalyx.bukkit.simplyperms.imports;

import java.util.Map.Entry;

import org.bukkit.configuration.file.YamlConfiguration;

import net.crystalyx.bukkit.simplyperms.SimplyAPI;
import net.crystalyx.bukkit.simplyperms.SimplyPlugin;

public class ImportPermBukkit extends SimplyAPI implements ImportManager {

	private SimplyPlugin plugin;
	private YamlConfiguration permBukkit;

	public ImportPermBukkit(SimplyPlugin plugin) {
		super(plugin);
		this.plugin = plugin;
		this.permBukkit = new YamlConfiguration();
		permBukkit.options().pathSeparator('/');
	}

	@Override
	public void run() throws Exception {
		try {
			permBukkit.load("plugins/PermissionsBukkit/config.yml");
			plugin.getConfig().set("debug", permBukkit.getBoolean("debug"));
			addMessage("build", permBukkit.getString("messages/build"));

			for (String player : plugin.getKeys(permBukkit, "users")) {
				for (String group : permBukkit.getStringList("users/" + player + "/groups")) {
					addPlayerGroup(player, group);
				} 

				for (Entry<String, Object> perm : permBukkit.getConfigurationSection("users/" + player + "/permissions").getValues(false).entrySet()) {
					addPlayerPermission(player, perm.getKey(), (Boolean) perm.getValue());
				}

				for (String world : plugin.getKeys(permBukkit, "users/" + player + "/worlds")) {
					for (Entry<String, Object> worldperm : permBukkit.getConfigurationSection("users/" + player + "/worlds/" + world).getValues(false).entrySet()) {
						addPlayerPermission(player, world, worldperm.getKey(), (Boolean) worldperm.getValue());
					}
				}
			}

			for (String group : plugin.getKeys(permBukkit, "groups")) {
				for (Entry<String, Object> perms : permBukkit.getConfigurationSection("groups/" + group + "/permissions").getValues(false).entrySet()) {
					addGroupPermission(group, perms.getKey(), (Boolean) perms.getValue());
				}

				for (String world : plugin.getKeys(permBukkit, "groups/" + group + "/worlds")) {
					for (Entry<String, Object> worldperm : permBukkit.getConfigurationSection("groups/" + group + "/worlds/" + world).getValues(false).entrySet()) {
						addGroupPermission(group, world, worldperm.getKey(), (Boolean) worldperm.getValue());
					}
				}

				for (String inherit : plugin.getConfig().getStringList("groups/" + group + "/inheritance")) {
					addGroupInheritance(group, inherit);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
