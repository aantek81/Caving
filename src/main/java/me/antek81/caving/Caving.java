package me.antek81.caving;

import me.antek81.caving.commands.CaveCommand;
import me.antek81.caving.event.CaveEvent;
import me.antek81.caving.listeners.OreDrop;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Caving extends JavaPlugin {

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        HashMap<String, Short> configPoints = new HashMap<>();
        configPoints.put("coal", (short) getConfig().getInt("points.coal"));
        configPoints.put("iron", (short) getConfig().getInt("points.iron"));
        configPoints.put("copper", (short) getConfig().getInt("points.copper"));
        configPoints.put("gold", (short) getConfig().getInt("points.gold"));
        configPoints.put("redstone", (short) getConfig().getInt("points.redstone"));
        configPoints.put("lapis", (short) getConfig().getInt("points.lapis"));
        configPoints.put("diamond", (short) getConfig().getInt("points.diamond"));
        configPoints.put("emerald", (short) getConfig().getInt("points.emerald"));

        CaveEvent caveEvent = new CaveEvent(this, configPoints);

        getCommand("cave").setExecutor(new CaveCommand(caveEvent));
        getServer().getPluginManager().registerEvents(new OreDrop(caveEvent), this);

        getLogger().info("Caving plugin has been loaded");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
