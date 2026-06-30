package me.antek81.caving;

import me.antek81.caving.commands.CaveCommand;
import me.antek81.caving.event.CaveEvent;
import me.antek81.caving.util.DelayedTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class Caving extends JavaPlugin {

    private CaveEvent caveEvent;

    @Override
    public void onEnable() {
        // Plugin startup logic
        caveEvent = new CaveEvent(this);

        getLogger().info("Caving plugin has been loaded");

        getCommand("cave").setExecutor(new CaveCommand(caveEvent));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
