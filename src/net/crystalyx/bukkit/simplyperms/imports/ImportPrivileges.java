package net.crystalyx.bukkit.simplyperms.imports;

import org.bukkit.configuration.file.YamlConfiguration;

import net.crystalyx.bukkit.simplyperms.SimplyAPI;
import net.crystalyx.bukkit.simplyperms.SimplyPlugin;

public class ImportPrivileges extends SimplyAPI implements ImportManager {

	private SimplyPlugin plugin;
	private YamlConfiguration privileges;

	public ImportPrivileges(SimplyPlugin plugin) {
		super(plugin);
		this.plugin = plugin;
		this.privileges = new YamlConfiguration();
	}

	@Override
	public void run() throws Exception {
		try {
			privileges.load("plugins/Privileges/config.yml");
			setDefaultGroup(privileges.getString("default_group"));
			setDebug(privileges.getBoolean("debug"));

			privileges.load("plugins/Privileges/users.yml");
			for (String player : plugin.getKeys(privileges, "users")) {
				addPlayerGroup(player, privileges.getString("users." + player + ".group"));

				for (String permission : privileges.getStringList("users." + player + ".permissions")) {
					addPlayerPermission(player, permission.replace("-", ""), !permission.startsWith("-"));
				}

				for (String world : plugin.getKeys(privileges, "users." + player + ".worlds")) {
					for (String worldpermission : privileges.getStringList("users." + player + ".worlds." + world)) {
						addPlayerPermission(player, world, worldpermission.replace("-", ""), !worldpermission.startsWith("-"));
					}
				}
			}

			privileges.load("plugins/Privileges/groups.yml");
			for (String group : plugin.getKeys(privileges, "groups")) {
				for (String permission : privileges.getStringList("groups." + group + ".permissions")) {
					addGroupPermission(group, permission.replace("-", ""), !permission.startsWith("-"));
				}

				for (String world : plugin.getKeys(privileges, "groups." + group + ".worlds")) {
					for (String worldpermission : privileges.getStringList("groups." + group + ".worlds." + world)) {
						addGroupPermission(group, world, worldpermission.replace("-", ""), !worldpermission.startsWith("-"));
					}
				}

				for (String inherit : privileges.getStringList("groups." + group + ".inheritance")) {
					addGroupInheritance(group, inherit);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
