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
			String defaultGroup = privileges.getString("default_group");
			plugin.getConfig().set("debug", privileges.getBoolean("debug"));

			privileges.load("plugins/Privileges/users.yml");
			for (String player : getKeys(privileges, "users")) {
				addPlayerGroup(player, privileges.getString("users." + player + ".group"));

				for (String permission : privileges.getStringList("users." + player + ".permissions")) {
					addPlayerPermission(player, permission, !permission.contains("-"));
				}
				
				for (String world : getKeys(privileges, "users." + player + ".worlds")) {
					for (String worldpermission : privileges.getStringList("users." + player + ".worlds." + world)) {
						addPlayerPermission(player, world, worldpermission, !worldpermission.contains("-"));
					}
				}
			}

			privileges.load("plugins/Privileges/groups.yml");
			for (String group : getKeys(privileges, "groups")) {
				String simplyGroup = (group.equals(defaultGroup)) ? "default" : group;
				
				for (String permission : privileges.getStringList("groups." + group + ".permissions")) {
					addGroupPermission(simplyGroup, permission, !permission.contains("-"));
				}
				
				for (String world : getKeys(privileges, "groups." + group + ".worlds")) {
					for (String worldpermission : privileges.getStringList("groups." + group + ".worlds." + world)) {
						addGroupPermission(simplyGroup, world, worldpermission, !worldpermission.contains("-"));
					}
				}
				
				for (String inherit : privileges.getStringList("groups." + group + ".inheritance")) {
					addGroupInheritance(simplyGroup, inherit);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
