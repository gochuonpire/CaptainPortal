package me.captain.portal;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import me.captain.lock.Zone;
import me.captain.warp.Warp;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * @author captainawesome7
 * 
 * Utils
 */
public class PortalHandler {

    public CaptainPortal plugin;

    public ArrayList<Portal> portals;

    public HashMap<Player, CreatingPortal> newportals;

    public PortalHandler(CaptainPortal instance) {
        plugin = instance;
        newportals = new HashMap<>();
    }

    public Boolean addPortal(Portal p, Zone z) {
        if(portals.contains(p)) {
            return false;
        } else {
            portals.add(p);
            plugin.zones.zh.addZone(z);
            return true;
        }
    }

    public String removePortal(Portal p) {
        if(portals.contains(p)) {
            portals.remove(p);
            ArrayList<Integer> removed =  plugin.zones.zh.removeZone(p.getEntrance().getId().toString());
            if(removed.isEmpty()) {
                return "There was a problem removing the zone!";
            } else {
                return p.getName() + " removed.";
            }
        } else {
            return "There was a problem removing portal " + p.getName();
        }
    }

    public void loadPortals() {
        portals = new ArrayList<>();
        try {
            File f = new File(plugin.getDataFolder(), "portals.yml");
            YamlConfiguration zonef = new YamlConfiguration();
            zonef.load(f);
            for (String s : zonef.getKeys(false)) {
                String warpname = zonef.getString(s + ".warp");
                Integer zoneid = zonef.getInt(s + ".zone");
                String name = zonef.getString(s + ".name");
                Warp w = plugin.warps.wh.getWarp(warpname);
                Zone z = plugin.zones.zh.getZone(zoneid);
                Boolean active = zonef.getBoolean(s + ".active");
                Portal p = new Portal(name, z, w, active);
                portals.add(p);
            }
        } catch (Exception e) {
            System.out.println("[CaptainPortal] Error while loading portals.yml");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void savePortals() {
        try {
            File f = new File(plugin.getDataFolder(), "portals.yml");
            YamlConfiguration zonef = new YamlConfiguration();
            for (Portal p : portals) {
                zonef.set(p.getEntrance().getId() + p.getExit().getName() + ".name", p.getName());
                zonef.set(p.getEntrance().getId() + p.getExit().getName() + ".zone", p.getEntrance().getId());
                zonef.set(p.getEntrance().getId() + p.getExit().getName() + ".warp", p.getExit().getName());
                zonef.set(p.getEntrance().getId() + p.getExit().getName() + ".active", p.getStatus());
            }
            zonef.save(f);
            System.out.println("[CaptainPortal] Portals saved.");
        } catch (Exception e) {
            System.out.println("[CaptainPortal] Error while saving portals.yml");
        }
    }

    public Portal getPortal(int zoneid) {
        for(Portal p : portals) {
            if (p.getEntrance().getId() == zoneid) {
                return p;
            }
        }
        return null;
    }

    public Portal getPortal(String name) {
        for(Portal p : portals) {
            if(p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
}
