package net.crystalyx.bukkit.simplyperms.imports;

import java.util.Map.Entry;

import net.crystalyx.bukkit.simplyperms.SimplyAPI;
import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.io.ConfigFile;
import net.crystalyx.bukkit.simplyperms.io.ConfigSQL;

public class ImportDB extends SimplyAPI implements ImportManager {

	private SimplyPlugin plugin;

	public ImportDB(SimplyPlugin plugin) {
		super(plugin);
		this.plugin = plugin;
	}

	@Override
	public void run() throws Exception {
		try {
			ConfigFile importFile = new ConfigFile(plugin);
			ConfigSQL importSQL = new ConfigSQL(plugin);
			if (!importSQL.checkDatabase()) {
				throw new Exception("Could not connect to database !");
			}

			for (String player : importSQL.getAllPlayers()) {
				for (String group : importSQL.getPlayerGroups(player)) {
					importFile.addPlayerGroup(player, group);
				}

				for (Entry<String, Boolean> permission : importSQL.getPlayerPermissions(player).entrySet()) {
					importFile.addPlayerPermission(player, permission.getKey(), permission.getValue());
				}

				for (String world : importSQL.getPlayerWorlds(player)) {
					for (Entry<String, Boolean> permission : importSQL.getPlayerPermissions(player, world).entrySet()) {
						importFile.addPlayerPermission(player, world, permission.getKey(), permission.getValue());
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
