package net.crystalyx.bukkit.simplyperms.imports;

import java.util.Map.Entry;

import net.crystalyx.bukkit.simplyperms.SimplyAPI;
import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.io.ConfigFile;
import net.crystalyx.bukkit.simplyperms.io.ConfigSQL;

public class ImportFile extends SimplyAPI implements ImportManager {

	private SimplyPlugin plugin;

	public ImportFile(SimplyPlugin plugin) {
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

			for (String player : importFile.getAllPlayers()) {
				for (String group : importFile.getPlayerGroups(player)) {
					importSQL.addPlayerGroup(player, group);
				}

				for (Entry<String, Boolean> permission : importFile.getPlayerPermissions(player).entrySet()) {
					importSQL.addPlayerPermission(player, permission.getKey(), permission.getValue());
				}

				for (String world : importFile.getPlayerWorlds(player)) {
					for (Entry<String, Boolean> permission : importFile.getPlayerPermissions(player, world).entrySet()) {
						importSQL.addPlayerPermission(player, world, permission.getKey(), permission.getValue());
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
