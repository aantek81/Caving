package me.antek81.caving.listeners;

import me.antek81.caving.event.CaveEvent;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

public class OreDrop implements Listener {

    private final CaveEvent caveEvent;

    public OreDrop(CaveEvent caveEvent) {
        this.caveEvent = caveEvent;
    }

    @EventHandler
    public void onOreDrop(BlockDropItemEvent event) {
        if (caveEvent.isActive()) {
            final Player player = event.getPlayer();
            final BlockState block = event.getBlockState();

            if (caveEvent.isParticipant(player)) {
                switch (block.getType()) {
                    case COAL_ORE, DEEPSLATE_COAL_ORE:
                        caveEvent.addCoal(player);
                        event.setCancelled(true);
                        break;
                    case IRON_ORE, DEEPSLATE_IRON_ORE:
                        caveEvent.addIron(player);
                        event.setCancelled(true);
                        break;
                    case COPPER_ORE, DEEPSLATE_COPPER_ORE:
                        caveEvent.addCopper(player);
                        event.setCancelled(true);
                        break;
                    case GOLD_ORE, DEEPSLATE_GOLD_ORE:
                        caveEvent.addGold(player);
                        event.setCancelled(true);
                        break;
                    case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE:
                        caveEvent.addRedstone(player);
                        event.setCancelled(true);
                        break;
                    case LAPIS_ORE, DEEPSLATE_LAPIS_ORE:
                        caveEvent.addLapis(player);
                        event.setCancelled(true);
                        break;
                    case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE:
                        caveEvent.addDiamond(player);
                        event.setCancelled(true);
                        break;
                    case EMERALD_ORE, DEEPSLATE_EMERALD_ORE:
                        caveEvent.addEmerald(player);
                        event.setCancelled(true);
                        break;
                }
            }
        }
    }
}
