package me.captain.portal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import me.captain.lock.CaptainLock;
import me.captain.warp.CaptainWarp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * @author captainawesome7
 * 
 * Main Plugin class
 */
public class CaptainPortal extends JavaPlugin {

    public CaptainLock zones;
    public CaptainWarp warps;
    public PortalHandler ph;
    public PortalListener listener;
    
    public int totalportals;
    public boolean createreadme;
    
    /**
     * Configuration file
     */
    public FileConfiguration config;

    @Override
    public void onEnable() {
        this.getCommand("portal").setExecutor(new PortalCmd(this));
        PluginManager pluginm = getServer().getPluginManager();
        zones = null;
        warps = null;
        try {
            zones = (CaptainLock) this.getServer().getPluginManager().getPlugin("CaptainLock");
            warps = (CaptainWarp) this.getServer().getPluginManager().getPlugin("CaptainWarp");
        } catch(Exception e) {
            this.getServer().getConsoleSender().sendMessage("[CaptainPortal] CaptainLock/CaptainWarp hook failed.");
        }
        ph = new PortalHandler(this);
        ph.loadPortals();
        listener = new PortalListener(this);
        pluginm.registerEvents(listener, this);
        loadConfig();
        if(createreadme) {
            String created = createReadMeFile();
            if(!created.equals("")) {
                System.out.println(created);
            }
        }
    }

    @Override
    public void onDisable() {
        ph.savePortals();
    }
    
    /**
     * Loads configuration from file
     */
    public void loadConfig() {
        config = getConfig();
        //set defaults
        config.addDefault("total_portals_allowed", 100);
        config.addDefault("create_read_me", true);
        config.options().copyDefaults(true);
        this.saveConfig();
        totalportals = config.getInt("total_portals_allowed");
        createreadme = config.getBoolean("create_read_me");
    }
    
    /**
     * Creates readme if none found
     *
     * @return
     */
    public String createReadMeFile() {
        File readMe = new File(this.getDataFolder().getAbsolutePath() + File.separator + "readme.txt");
        if (!readMe.exists()) {
            try {
                InputStream inStream = this.getClass().getResourceAsStream("readme.txt");
                FileOutputStream outStream = new FileOutputStream(readMe);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inStream.read(buffer)) > 0) {
                    outStream.write(buffer, 0, length);
                }
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (Exception e) {
                return "[CaptainPortal] Error creating readme file.";
            }
            return "[CaptainPortal] ReadMe file created at /plugins/CaptainPortal/readme.txt";
        } else {
            return "";
        }
    }
}
