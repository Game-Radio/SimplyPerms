package net.crystalyx.bukkit.simplyperms.io;

import java.util.List;
import java.util.Map;

public interface PermsConfig {

	public void removePlayer(String player);

	public void removePlayerGroups(String player);

	public void removePlayerGroup(String player, String group);

	public void setPlayerGroup(String player, String group);

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

	public List<String> getAllGroups();

	public List<String> getGroupWorlds(String group);

	public List<String> getGroupInheritance(String group);

	public void addGroupInheritance(String group, String inherit);

	public void removeGroupInheritance(String group, String inherit);

	public void removeGroupInheritances(String group);

	public Map<String, Boolean> getGroupPermissions(String group, String world);

	public Map<String, Boolean> getGroupPermissions(String group);

	public void addGroupPermission(String group, String world, String permission, boolean value);

	public void addGroupPermission(String group, String permission, boolean value);

	public void removeGroupPermission(String group, String world, String permission);

	public void removeGroupPermission(String group, String permission);

	public void removeGroupPermissions(String group);

	public void removeGroup(String group);

	public Map<String, Object> getMessages();

	public String getMessage(String key);

	public void addMessage(String key, String message);

	public void removeMessage(String key);

	public String getDefaultGroup();
	
	public void setDefaultGroup(String group);

	public boolean getDebug();

	public void setDebug(boolean debug);

}
