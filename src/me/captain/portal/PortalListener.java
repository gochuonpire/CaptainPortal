package me.captain.portal;

import java.util.ArrayList;
import java.util.List;
import me.captain.lock.Zone;
import me.captain.warp.Warp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author captainawesome7
 * 
 * Portal player listener
 */
public class PortalListener implements Listener {

    public CaptainPortal plugin;

    public PortalListener(CaptainPortal instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = event.getPlayer().getLocation();
        Zone z = plugin.zones.zh.isInside(loc);
        if (z != null) {
            if (player.hasPermission("captainportal." + z.getNode())) {
                Portal p = plugin.ph.getPortal(z.getId());
                if (p != null) {
                    if (p.getStatus()) {
                        player.teleport(p.getExit().getLoc());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == Action.LEFT_CLICK_BLOCK && player.getInventory().getItemInMainHand().getType() == Material.DIAMOND && player.hasPermission("captainportal.*")) {
            if(plugin.ph.newportals.containsKey(player)) {
                CreatingPortal cp = plugin.ph.newportals.get(player);
                Location loc = event.getClickedBlock().getLocation();
                if(cp.loc1 == null) {
                    cp.loc1 = loc;
                    player.sendMessage(ChatColor.GRAY + "First location set. Set next location.");
                } else if(cp.loc2 == null) {
                    cp.loc2 = loc;
                    Warp w = cp.warp;
                    Zone z = new Zone(plugin.zones.zh.nextId(), player.getName(), w.getNode(), cp.loc1, cp.loc2, cp.offset);
                    Portal p = new Portal(cp.portalName, z, w, true);
                    plugin.ph.addPortal(p, z);
                    plugin.ph.newportals.remove(player);
                    if(cp.air) {
                        List<Block> blocks = blocksFromTwoPoints(cp.loc1, cp.loc2);
                        for(Block b : blocks) {
                            if(b.getType() == Material.AIR) {
                                b.setType(Material.WEB);
                            }
                        }
                    }
                    player.sendMessage(ChatColor.GRAY + "Second location set. Your portal is now active.");
                }

            }
        }
    }

    public List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }
        return blocks;
    }
}
