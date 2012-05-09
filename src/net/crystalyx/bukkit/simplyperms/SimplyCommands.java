package net.crystalyx.bukkit.simplyperms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import net.crystalyx.bukkit.simplyperms.imports.ImportDB;
import net.crystalyx.bukkit.simplyperms.imports.ImportFile;
import net.crystalyx.bukkit.simplyperms.imports.ImportManager;
import net.crystalyx.bukkit.simplyperms.imports.ImportPermBukkit;
import net.crystalyx.bukkit.simplyperms.imports.ImportPermEx;
import net.crystalyx.bukkit.simplyperms.imports.ImportPrivileges;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class SimplyCommands implements CommandExecutor {

	private SimplyPlugin plugin;

	public SimplyCommands(SimplyPlugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		if (split.length < 1) {
			return !checkPerm(sender, "help") || usage(sender, command);
		}

		String subcommand = split[0];
		if (subcommand.equals("reload")) {
			if (!checkPerm(sender, "reload")) return true;
			plugin.reloadConfig();
			plugin.refreshPermissions();
			sender.sendMessage(ChatColor.GREEN + "Configuration reloaded.");
			return true;
		} if (subcommand.equals("check")) {
			if (!checkPerm(sender, "check")) return true;
			if (split.length != 2 && split.length != 3) return usage(sender, command, subcommand);

			String node = split[1];
			Permissible permissible;
			if (split.length == 2) {
				permissible = sender;
			} else {
				permissible = plugin.getServer().getPlayer(split[2]);
			}

			String name = (permissible instanceof Player) ? ((Player) permissible).getName() : (permissible instanceof ConsoleCommandSender) ? "Console" : "Unknown";

			if (permissible == null) {
				sender.sendMessage(ChatColor.RED + "Player " + ChatColor.WHITE + split[2] + ChatColor.RED + " not found.");
			} else {
				boolean set = permissible.isPermissionSet(node), has = permissible.hasPermission(node);
				String sets = set ? " sets " : " defaults ";
				String perm = has ? " true" : " false";
				sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + name + ChatColor.GREEN + sets + ChatColor.WHITE + node + ChatColor.GREEN + " to " + ChatColor.WHITE + perm + ChatColor.GREEN + ".");
			}
			return true;
		} else if (subcommand.equals("info")) {
			if (!checkPerm(sender, "info")) return true;
			if (split.length != 2) return usage(sender, command, subcommand);

			String node = split[1];
			Permission perm = plugin.getServer().getPluginManager().getPermission(node);

			if (perm == null) {
				sender.sendMessage(ChatColor.RED + "Permission " + ChatColor.WHITE + node + ChatColor.RED + " not found.");
			} else {
				sender.sendMessage(ChatColor.GREEN + "Info on permission " + ChatColor.WHITE + perm.getName() + ChatColor.GREEN + ":");
				sender.sendMessage(ChatColor.GREEN + "Default: " + ChatColor.WHITE + perm.getDefault());
				if (perm.getDescription() != null && perm.getDescription().length() > 0) {
					sender.sendMessage(ChatColor.GREEN + "Description: " + ChatColor.WHITE + perm.getDescription());
				}
				if (perm.getChildren() != null && perm.getChildren().size() > 0) {
					sender.sendMessage(ChatColor.GREEN + "Children: " + ChatColor.WHITE + perm.getChildren().size());
				}
			}
			return true;
		} else if (subcommand.equals("dump")) {
			if (!checkPerm(sender, "dump")) return true;
			if (split.length < 1 || split.length > 3) return usage(sender, command, subcommand);

			int page;
			Permissible permissible;
			if (split.length == 1) {
				permissible = sender;
				page = 1;
			} else if (split.length == 2) {
				try {
					permissible = sender;
					page = Integer.parseInt(split[1]);
				}
				catch (NumberFormatException ex) {
					permissible = plugin.getServer().getPlayer(split[1]);
					page = 1;
				}
			} else {
				permissible = plugin.getServer().getPlayer(split[1]);
				try {
					page = Integer.parseInt(split[2]);
				}
				catch (NumberFormatException ex) {
					page = 1;
				}
			}

			if (permissible == null) {
				sender.sendMessage(ChatColor.RED + "Player " + ChatColor.WHITE + split[1] + ChatColor.RED + " not found.");
			} else {
				ArrayList<PermissionAttachmentInfo> dump = new ArrayList<PermissionAttachmentInfo>(permissible.getEffectivePermissions());
				Collections.sort(dump, new Comparator<PermissionAttachmentInfo>() {
					public int compare(PermissionAttachmentInfo a, PermissionAttachmentInfo b) {
						return a.getPermission().compareTo(b.getPermission());
					}
				});

				int numpages = 1 + (dump.size() - 1) / 8;
				if (page > numpages) {
					page = numpages;
				} else if (page < 1) {
					page = 1;
				}

				ChatColor g = ChatColor.GREEN, w = ChatColor.WHITE, r = ChatColor.RED;

				int start = 8 * (page - 1);
				sender.sendMessage(ChatColor.RED + "[==== " + ChatColor.GREEN + "Page " + page + " of " + numpages + ChatColor.RED + " ====]");
				for (int i = start; i < start + 8 && i < dump.size(); ++i) {
					PermissionAttachmentInfo info = dump.get(i);

					if (info.getAttachment() == null) {
						sender.sendMessage(g + "Node " + w + info.getPermission() + g + "=" + w + info.getValue() + g + " (" + r + "default" + g + ")");
					} else {
						sender.sendMessage(g + "Node " + w + info.getPermission() + g + "=" + w + info.getValue() + g + " (" + w + info.getAttachment().getPlugin().getDescription().getName() + g + ")");
					}
				}
			}
			return true;
		} else if (subcommand.equals("group")) {
			if (split.length < 2) {
				return !checkPerm(sender, "group.help") || usage(sender, command, subcommand);
			}
			groupCommand(sender, command, split);
			return true;
		} else if (subcommand.equals("player")) {
			if (split.length < 2) {
				return !checkPerm(sender, "player.help") || usage(sender, command, subcommand);
			}
			playerCommand(sender, command, split);
			return true;
		} else if (subcommand.equals("import")) {
			if (!checkPerm(sender, "import")) return true;
			if (split.length != 2) return usage(sender, command, subcommand);
			String pluginName = split[1].toLowerCase();
			ImportManager manager;

			if (pluginName.equals("permissionsbukkit")) {
				manager = new ImportPermBukkit(plugin);
			} else if (pluginName.equals("privileges")) {
				manager = new ImportPrivileges(plugin);
			} else if (pluginName.equals("permissionsex")) {
				manager = new ImportPermEx(plugin);
			} else if (pluginName.equals("file")) {
				manager = new ImportFile(plugin);
			} else if (plugin.getConfig().getString("db/type") != null
					&& pluginName.equals(plugin.getConfig().getString("db/type").toLowerCase())) {
				manager = new ImportDB(plugin);
			} else {
				sender.sendMessage(ChatColor.RED + "Unknown import type !");
				return true;
			}

			try {
				manager.run();
				plugin.refreshPermissions();
				sender.sendMessage(ChatColor.GREEN + "Operation success !");
			} catch (Exception e) {
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.RED + "An error occurred ! Please check server log...");
				e.printStackTrace();
			}
			return true;
		} else {
			return !checkPerm(sender, "help") || usage(sender, command);
		}
	}

	private boolean groupCommand(CommandSender sender, Command command, String[] split) {
		String subcommand = split[1];

		if (subcommand.equals("list")) {
			if (!checkPerm(sender, "group.list")) return true;
			if (split.length != 2) return usage(sender, command, "group list");

			String result = "", sep = "";
			for (String key : plugin.getNode("groups").getKeys(false)) {
				result += sep + key;
				sep = ", ";
			}
			sender.sendMessage(ChatColor.GREEN + "Groups: " + ChatColor.WHITE + result);
			return true;
		} else if (subcommand.equals("players")) {
			if (!checkPerm(sender, "group.players")) return true;
			if (split.length != 3) return usage(sender, command, "group players");
			String group = split[2];

			if (plugin.getNode("groups/" + group) == null) {
				sender.sendMessage(ChatColor.RED + "No such group " + ChatColor.WHITE + group + ChatColor.RED + ".");
				return true;
			}

			int count = 0;
			String text = "", sep = "";
			for (String user : plugin.config.getPlayers(group)) {
				++count;
				text += sep + user;
				sep = ", ";
			}
			sender.sendMessage(ChatColor.GREEN + "Users in " + ChatColor.WHITE + group + ChatColor.GREEN + " (" + ChatColor.WHITE + count + ChatColor.GREEN + "): " + ChatColor.WHITE + text);
			return true;
		} else if (subcommand.equals("setperm")) {
			if (!checkPerm(sender, "group.setperm")) return true;
			if (split.length != 4 && split.length != 5) return usage(sender, command, "group setperm");
			if (!checkPerm(sender, "group.setperm", Arrays.asList(split[3].split(",")))) return true;
			String group = split[2];
			String perm = split[3];
			boolean value = (split.length != 5) || Boolean.parseBoolean(split[4]);

			String node = "permissions";
			if (plugin.getNode("groups/" + group) == null) {
				sender.sendMessage(ChatColor.RED + "No such group " + ChatColor.WHITE + group + ChatColor.RED + ".");
				return true;
			}

			if (perm.contains(":")) {
				String world = perm.substring(0, perm.indexOf(':'));
				perm = perm.substring(perm.indexOf(':') + 1);
				node = "worlds/" + world;
			}
			if (plugin.getNode("groups/" + group + "/" + node) == null) {
				plugin.getConfig().createSection("groups/" + group + "/" + node);
			}

			plugin.getNode("groups/" + group + "/" + node).set(perm, value);
			plugin.refreshPermissions();

			sender.sendMessage(ChatColor.GREEN + "Group " + ChatColor.WHITE + group + ChatColor.GREEN + " now has " + ChatColor.WHITE + perm + ChatColor.GREEN + " = " + ChatColor.WHITE + value + ChatColor.GREEN + ".");
			return true;
		} else if (subcommand.equals("unsetperm")) {
			if (!checkPerm(sender, "group.unsetperm")) return true;
			if (split.length != 4) return usage(sender, command, "group unsetperm");
			if (!checkPerm(sender, "group.unsetperm", Arrays.asList(split[3].split(",")))) return true;
			String group = split[2].toLowerCase();
			String perm = split[3];

			String node = "permissions";
			if (plugin.getNode("groups/" + group) == null) {
				sender.sendMessage(ChatColor.RED + "No such group " + ChatColor.WHITE + group + ChatColor.RED + ".");
				return true;
			}

			if (perm.contains(":")) {
				String world = perm.substring(0, perm.indexOf(':'));
				perm = perm.substring(perm.indexOf(':') + 1);
				node = "worlds/" + world;
			}
			if (plugin.getNode("groups/" + group + "/" + node) == null) {
				plugin.getConfig().createSection("groups/" + group + "/" + node);
			}

			ConfigurationSection sec = plugin.getNode("groups/" + group + "/" + node);
			if (!sec.contains(perm)) {
				sender.sendMessage(ChatColor.GREEN + "Group " + ChatColor.WHITE + group + ChatColor.GREEN + " did not have " + ChatColor.WHITE + perm + ChatColor.GREEN + " set.");
				return true;
			}
			sec.set(perm, null);
			plugin.refreshPermissions();

			sender.sendMessage(ChatColor.GREEN + "Group " + ChatColor.WHITE + group + ChatColor.GREEN + " no longer has " + ChatColor.WHITE + perm + ChatColor.GREEN + " set.");
			return true;
		} else {
			return !checkPerm(sender, "group.help") || usage(sender, command);
		}
	}

	private boolean playerCommand(CommandSender sender, Command command, String[] split) {
		String subcommand = split[1];

		if (subcommand.equals("groups")) {
			if (!checkPerm(sender, "player.groups")) return true;
			if (split.length != 3) return usage(sender, command, "player groups");
			String player = split[2].toLowerCase();

			if (!plugin.config.isPlayerInDB(player)) {
				sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.RED + " is in the default group.");
				return true;
			}

			int count = 0;
			String text = "", sep = "";
			for (String group : plugin.config.getPlayerGroups(player)) {
				++count;
				text += sep + group;
				sep = ", ";
			}
			sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " is in groups (" + ChatColor.WHITE + count + ChatColor.GREEN + "): " + ChatColor.WHITE + text);
			return true;
		} else if (subcommand.equals("setgroup")) {
			if (!checkPerm(sender, "player.setgroup")) return true;
			if (split.length != 4) return usage(sender, command, "player setgroup");
			if (!checkPerm(sender, "player.setgroup", Arrays.asList(split[3].split(",")))) return true;
			String player = split[2].toLowerCase();
			String[] groups = split[3].split(",");

			plugin.config.removePlayerGroups(player);
			for (String group : Arrays.asList(groups)) {
				plugin.config.addPlayerGroup(player, group);
			}

			plugin.refreshPermissions();

			sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " is now in " + ChatColor.WHITE + split[3] + ChatColor.GREEN + ".");
			return true;
		} else if (subcommand.equals("addgroup")) {
			if (!checkPerm(sender, "player.addgroup")) return true;
			if (split.length != 4) return usage(sender, command, "player addgroup");
			if (!checkPerm(sender, "player.addgroup", Arrays.asList(split[3]))) return true;
			String player = split[2].toLowerCase();
			String group = split[3];

			if (plugin.config.getPlayerGroups(player).contains(group)) {
				sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " was already in " + ChatColor.WHITE + group + ChatColor.GREEN + ".");
				return true;
			}
			plugin.config.addPlayerGroup(player, group);

			plugin.refreshPermissions();

			sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " is now in " + ChatColor.WHITE + group + ChatColor.GREEN + ".");
			return true;
		} else if (subcommand.equals("removegroup")) {
			if (!checkPerm(sender, "player.removegroup")) return true;
			if (split.length != 4) return usage(sender, command, "player removegroup");
			if (!checkPerm(sender, "player.removegroup", Arrays.asList(split[3]))) return true;
			String player = split[2].toLowerCase();
			String group = split[3];

			if (!plugin.config.getPlayerGroups(player).contains(group)) {
				sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " was not in " + ChatColor.WHITE + group + ChatColor.GREEN + ".");
				return true;
			}
			plugin.config.removePlayerGroup(player, group);

			plugin.refreshPermissions();

			sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " is no longer in " + ChatColor.WHITE + group + ChatColor.GREEN + ".");
			return true;
		} else if (subcommand.equals("remove")) {
			if (!checkPerm(sender, "player.remove")) return true;
			if (split.length != 3) return usage(sender, command, "player remove");
			String player = split[2].toLowerCase();

			if (!plugin.config.isPlayerInDB(player)) {
				sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " was not in config file.");
				return true;
			}

			plugin.config.removePlayer(player);
			plugin.refreshPermissions();

			sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " is no longer in config file.");
			return true;
		} else if (subcommand.equals("setperm")) {
			if (!checkPerm(sender, "player.setperm")) return true;
			if (split.length != 4 && split.length != 5) return usage(sender, command, "player setperm");
			if (!checkPerm(sender, "player.setperm", Arrays.asList(split[3].split(",")))) return true;
			String player = split[2].toLowerCase();
			String perm = split[3];
			boolean value = (split.length != 5) || Boolean.parseBoolean(split[4]);

			String world = "";
			if (perm.contains(":")) {
				world = perm.substring(0, perm.indexOf(':'));
				perm = perm.substring(perm.indexOf(':') + 1);
			}

			plugin.config.addPlayerPermission(player, world, perm, value);
			plugin.refreshPermissions();

			sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " now has " + ChatColor.WHITE + perm + ChatColor.GREEN + " = " + ChatColor.WHITE + value + ChatColor.GREEN + ".");
			return true;
		} else if (subcommand.equals("unsetperm")) {
			if (!checkPerm(sender, "player.unsetperm")) return true;
			if (split.length != 4) return usage(sender, command, "player unsetperm");
			if (!checkPerm(sender, "player.unsetperm", Arrays.asList(split[3].split(",")))) return true;
			String player = split[2].toLowerCase();
			String perm = split[3];

			String world = "";
			if (perm.contains(":")) {
				world = perm.substring(0, perm.indexOf(':'));
				perm = perm.substring(perm.indexOf(':') + 1);
			}

			Map<String, Boolean> list = plugin.config.getPlayerPermissions(player, world);
			if (!list.containsKey(perm)) {
				sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " did not have " + ChatColor.WHITE + perm + ChatColor.GREEN + " set.");
				return true;
			}
			plugin.config.removePlayerPermission(player, world, perm);
			plugin.refreshPermissions();

			sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.WHITE + player + ChatColor.GREEN + " no longer has " + ChatColor.WHITE + perm + ChatColor.GREEN + " set.");
			return true;
		} else {
			return !checkPerm(sender, "player.help") || usage(sender, command);
		}
	}

	// -- utilities --

	private boolean checkPerm(CommandSender sender, String subnode) {
		boolean ok = sender.hasPermission("permissions." + subnode);
		if (!ok) {
			sender.sendMessage(ChatColor.RED + "You do not have permissions to do that.");
		}
		return ok;
	}

	private boolean checkPerm(CommandSender sender, String node, List<String> subnodes) {
		boolean ok = sender.hasPermission("permissions." + node + ".*");
		for (String subnode : subnodes) {
			String testnode = "";
			for (String sub : subnode.split(".")) {
				testnode += sub;
				if (sender.isPermissionSet("permissions." + node + "." + testnode)) {
					if (ok && !sender.hasPermission("permissions." + node + "." + testnode)) {
						ok = false;
						break;
					}
					else if (!ok && sender.hasPermission("permissions." + node + "." + testnode)) {
						ok = true;
						break;
					}
				}
				else if (sender.isPermissionSet("permissions." + node + "." + testnode + ".*")) {
					if (ok && !sender.hasPermission("permissions." + node + "." + testnode + ".*")) {
						ok = false;
						break;
					}
					else if (!ok && sender.hasPermission("permissions." + node + "." + testnode + ".*")) {
						ok = true;
						break;
					}
				}
			}
			if (!ok) break;
		}
		if (!ok) {
			sender.sendMessage(ChatColor.RED + "You do not have permissions to do that.");
		}
		return ok;
	}

	private boolean usage(CommandSender sender, Command command) {
		sender.sendMessage(ChatColor.RED + "[====" + ChatColor.GREEN + " /permissons " + ChatColor.RED + "====]");
		for (String line : command.getUsage().split("\\n")) {
			if ((line.startsWith("/<command> group") && !line.startsWith("/<command> group -")) ||
				(line.startsWith("/<command> player") && !line.startsWith("/<command> player -"))) {
				continue;
			}
			sender.sendMessage(formatLine(line));
		}
		return true;
	}

	private boolean usage(CommandSender sender, Command command, String subcommand) {
		sender.sendMessage(ChatColor.RED + "[====" + ChatColor.GREEN + " /permissons " + subcommand + " " + ChatColor.RED + "====]");
		for (String line : command.getUsage().split("\\n")) {
			if (line.startsWith("/<command> " + subcommand)) {
				sender.sendMessage(formatLine(line));
			}
		}
		return true;
	}

	private String formatLine(String line) {
		int i = line.indexOf(" - ");
		String usage = line.substring(0, i);
		String desc = line.substring(i + 3);

		usage = usage.replace("<command>", "permissions");
		usage = usage.replaceAll("\\[[^]:]+\\]", ChatColor.AQUA + "$0" + ChatColor.GREEN);
		usage = usage.replaceAll("\\[[^]]+:\\]", ChatColor.AQUA + "$0" + ChatColor.LIGHT_PURPLE);
		usage = usage.replaceAll("<[^>]+>", ChatColor.LIGHT_PURPLE + "$0" + ChatColor.GREEN);

		return ChatColor.GREEN + usage + " - " + ChatColor.WHITE + desc;
	}

}
