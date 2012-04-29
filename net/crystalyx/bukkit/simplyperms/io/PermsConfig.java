package net.crystalyx.bukkit.simplyperms.io;

import java.util.List;
import java.util.Map;

public interface PermsConfig {

	public void removePlayer(String player);

	public void removePlayerGroups(String player);

	public void removePlayerGroup(String player, String group);

	public void addPlayerGroup(String player, String group);

	public void addPlayerPermission(String player, String permission, boolean value);

	public void addPlayerPermission(String player, String world, String permission, boolean value);

	public void removePlayerPermissions(String player);

	public void removePlayerPermission(String player, String permission);

	public void removePlayerPermission(String player, String world, String permission);

	public List<String> getPlayers(String group);

	public List<String> getPlayerGroups(String player);

	public Map<String, Boolean> getPlayerPermissions(String player);

	public Map<String, Boolean> getPlayerPermissions(String player, String world);

	public boolean isPlayerInDB(String player);

	public List<String> getPlayerWorlds(String player);

	public List<String> getAllPlayers();

}
