package net.crystalyx.bukkit.simplyperms.imports;

import org.bukkit.configuration.file.YamlConfiguration;

import net.crystalyx.bukkit.simplyperms.SimplyAPI;
import net.crystalyx.bukkit.simplyperms.SimplyPlugin;

public class ImportPermEx extends SimplyAPI implements ImportManager {

	private SimplyPlugin plugin;
	private YamlConfiguration permEx;
	
	public ImportPermEx(SimplyPlugin plugin) {
		super(plugin);
		this.plugin = plugin;
		this.permEx = new YamlConfiguration();
	}

	@Override
	public void run() throws Exception {
		try {
			permEx.load("plugins/PermissionsEx/config.yml");
			plugin.getConfig().set("debug", permEx.getBoolean("permissions.debug"));
			permEx.load("plugins/PermissionsEx/" + permEx.getString("permissions.backends.file.file"));
			
			for (String player : getKeys(permEx, "users")) {
				for (String group : permEx.getStringList("users." + player + ".group")) {
					addPlayerGroup(player, group);
				} 
				
				for (String perm : permEx.getStringList("users." + player + ".permissions")) {
					addPlayerPermission(player, perm.replace("-", ""), !perm.startsWith("-"));
				}

				for (String world : getKeys(permEx, "users." + player + ".permissions.worlds")) {
					for (String worldperm : permEx.getStringList("users." + player + ".permissions.worlds." + world)) {
						addPlayerPermission(player, world, worldperm.replace("-", ""), !worldperm.startsWith("-"));
					}
				}
			}

			for (String group : getKeys(permEx, "groups")) {
				for (String perm : permEx.getStringList("groups." + group + ".permissions")) {
					addGroupPermission(group, perm.replace("-", ""), !perm.startsWith("-"));
				}

				for (String world : getKeys(permEx, "groups." + group + ".permissions.worlds")) {
					for (String worldperm : permEx.getStringList("groups." + group + ".permissions.worlds." + world)) {
						addGroupPermission(group, world, worldperm.replace("-", ""), !worldperm.startsWith("-"));
					}
				}

				for (String inherit : permEx.getStringList("groups." + group + ".inheritance")) {
					addGroupInheritance(group, inherit);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
