package me.captain.portal;

import me.captain.lock.Zone;
import me.captain.warp.Warp;

/**
 * @author captainawesome7
 * 
 * Portal data container
 */
public class Portal {

    private Zone entrance;
    private Warp exit;
    private Boolean active;
    private String name;

    public Portal(String portalname, Zone zone, Warp warp, Boolean status) {
        name = portalname;
        entrance = zone;
        exit = warp;
        active = status;
    }

    public Zone getEntrance() {
        return entrance;
    }

    public void setEntrance(Zone entrance) {
        this.entrance = entrance;
    }

    public Warp getExit() {
        return exit;
    }

    public void setExit(Warp exit) {
        this.exit = exit;
    }

    public void setStatus(Boolean status) {
        active = status;
    }

    public Boolean getStatus() {
        return active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
