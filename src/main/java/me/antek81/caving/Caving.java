package me.antek81.caving;

import me.antek81.caving.commands.CaveCommand;
import me.antek81.caving.event.CaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Caving extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        CaveEvent caveEvent = new CaveEvent(this);

        getCommand("cave").setExecutor(new CaveCommand(caveEvent));

        getLogger().info("Caving plugin has been loaded");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
