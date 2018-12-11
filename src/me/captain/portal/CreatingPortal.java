package me.captain.portal;

import me.captain.warp.Warp;
import org.bukkit.Location;

/**
 * @author captainawesome7
 * 
 * Portal data container for a currently incomplete portal
 */
public class CreatingPortal {

    public String portalName;
    public Warp warp;
    public int offset;
    public int zoneId;
    public Location loc1;
    public Location loc2;
    public boolean air;

    public CreatingPortal(String name, Warp w, int offset) {
        portalName = name;
        warp = w;
        this.offset = offset;
    }
    public CreatingPortal(String name, Warp w, boolean aironly) {
        portalName = name;
        warp = w;
        air = true;
        offset = 0;
    }
}
