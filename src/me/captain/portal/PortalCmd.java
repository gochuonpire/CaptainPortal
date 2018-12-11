package me.captain.portal;

import java.util.ArrayList;
import me.captain.warp.Warp;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author captainawesome7
 * 
 * Command handler for /portal
 * Manage all aspects of portals here
 */
public class PortalCmd implements CommandExecutor {
    public CaptainPortal plugin;

    public PortalCmd(CaptainPortal instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if ((cs instanceof Player)) {
            Player player = (Player) cs;
            if (!player.hasPermission("captainportal.*")) {
                return false;
            }
            switch (args.length) {
                case 0:
                    player.sendMessage(ChatColor.GRAY + "=== " + ChatColor.GREEN + "Captain Portal" + ChatColor.GRAY + " ===");
                    player.sendMessage(ChatColor.GRAY + "/portal cancel - Cancels creation of a portal");
                    player.sendMessage(ChatColor.GRAY + "/portal pause <name> - Pause all/one portal(s)");
                    player.sendMessage(ChatColor.GRAY + "/portal resume <name> - Resume all/one portal(s)");
                    player.sendMessage(ChatColor.GRAY + "/portal list - Show all portals");
                    player.sendMessage(ChatColor.GRAY + "/portal remove <name> - Remove a portal");
                    player.sendMessage(ChatColor.GRAY + "/portal info <name> - Get portal info");
                    player.sendMessage(ChatColor.GRAY + "/portal add <name> <warp> <offsetY> - Add a new portal.");
                case 1:
                    switch (args[0]) {
                        case "cancel":
                            player.sendMessage(ChatColor.GRAY + "Your in progress portal has been cleared.");
                            break;
                        case "pause":
                            for (Portal p : plugin.ph.portals) {
                                p.setStatus(false);
                            }
                            player.sendMessage(ChatColor.GRAY + "All portals have been paused.");
                            break;
                        case "resume":
                            for (Portal p : plugin.ph.portals) {
                                p.setStatus(true);
                            }
                            player.sendMessage(ChatColor.GRAY + "All portals have been resumed.");
                            break;
                        case "list":
                            ArrayList<String> portals = new ArrayList<>();
                            for(Portal p : plugin.ph.portals) {
                                String s = ChatColor.GREEN + p.getName() + ChatColor.GRAY;
                                portals.add(s);
                            }
                            String msg = ChatColor.GRAY + "Portals: " + portals.toString();
                            player.sendMessage(msg);
                            break;
                        default:
                            break;
                    }
                    return true;
                case 2:
                    switch (args[0]) {
                        case "remove": {
                            Portal p = plugin.ph.getPortal(args[1]);
                            if (p != null) {
                                String status = plugin.ph.removePortal(p);
                                player.sendMessage(ChatColor.GRAY + status);
                            }
                            break;
                        }
                        case "info": {
                            Portal p = plugin.ph.getPortal(args[1]);
                            if (p != null) {
                                player.sendMessage(ChatColor.GRAY + "Name: " + p.getName());
                                player.sendMessage(ChatColor.GRAY + "Entrance: " + p.getEntrance().getNode());
                                player.sendMessage(ChatColor.GRAY + "Exit: " + p.getExit().getName());
                            }
                            break;
                        }
                        case "pause": {
                            Portal p = plugin.ph.getPortal(args[1]);
                            if (p != null) {
                                p.setStatus(false);
                            }
                            player.sendMessage(ChatColor.GRAY + "Portal " + args[1] + " paused.");
                            break;
                        }
                        case "resume": {
                            Portal p = plugin.ph.getPortal(args[1]);
                            if (p != null) {
                                p.setStatus(true);
                            }
                            player.sendMessage(ChatColor.GRAY + "Portal " + args[1] + " resumed.");
                            break;
                        }
                        default:
                            break;
                    }
                    return true;
                case 3:
                    if (args[0].equals("air")) {
                        String name = args[1];
                        String warp = args[2];
                        Warp w = plugin.warps.wh.getWarp(warp);
                        if (w != null) {
                            CreatingPortal cp = new CreatingPortal(name, w, true);
                            plugin.ph.newportals.put(player, cp);
                            player.sendMessage(ChatColor.GRAY + "Creating portal " + name + " to warp " + w.getName() + ". Set location one now.");
                        } else {
                            player.sendMessage(ChatColor.GRAY + "Warp " + args[2] + " not found.");
                        }
                    }
                case 4:
                    if (args[0].equals("add")) {
                        if (plugin.ph.newportals.containsKey(player)) {
                            player.sendMessage(ChatColor.GRAY + "You are already creating a portal! Do /portal cancel to clear portal creation!");
                            return true;
                        }
                        String name = args[1];
                        String warp = args[2];
                        Integer offset = Integer.parseInt(args[3]);
                        Warp w = plugin.warps.wh.getWarp(warp);
                        if(w!=null) {
                            CreatingPortal cp = new CreatingPortal(name, w, offset);
                            plugin.ph.newportals.put(player, cp);
                            player.sendMessage(ChatColor.GRAY + "Creating portal " + name + " with offsetY " + offset + " to warp " + w.getName() + ". Set location one now.");
                        } else {
                            player.sendMessage(ChatColor.GRAY + "Warp " + args[2] + " not found.");
                        }
                    }
            }
        }
        return true;
    }
}
