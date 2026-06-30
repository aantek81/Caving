package me.antek81.caving.event;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CaveEvent {
    private final Plugin plugin;

    public CaveEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    Player eventCreator;
    long eventDuration;
    boolean active = false;

    BossBar eventBar = BossBar.bossBar(Component.text("Caving Event").color(NamedTextColor.DARK_GRAY),
            1,
            BossBar.Color.BLUE,
            BossBar.Overlay.PROGRESS);

    // playersPoints(player, points)
    HashMap<Player, Integer> playersPoints = new HashMap<>();
    // players...(player, minedOre)
    HashMap<Player, Integer> playersCoal = new HashMap<>();
    HashMap<Player, Integer> playersIron = new HashMap<>();
    HashMap<Player, Integer> playersCopper = new HashMap<>();
    HashMap<Player, Integer> playersGold = new HashMap<>();
    HashMap<Player, Integer> playersRedstone = new HashMap<>();
    HashMap<Player, Integer> playersLapis = new HashMap<>();
    HashMap<Player, Integer> playersDiamond = new HashMap<>();
    HashMap<Player, Integer> playersEmerald = new HashMap<>();

    public void create(long duration, Player sender) {
        eventCreator = sender;
        eventDuration = duration;
    }

    public void join(Player player) {
        playersPoints.put(player, 0);
        playersCoal.put(player, 0);
        playersIron.put(player, 0);
        playersCopper.put(player, 0);
        playersGold.put(player, 0);
        playersRedstone.put(player, 0);
        playersLapis.put(player, 0);
        playersDiamond.put(player, 0);
        playersEmerald.put(player, 0);
    }

    public void cancel() {
        for (Player player : playersPoints.keySet()) {
            player.hideBossBar(eventBar);
        }

        playersPoints.clear();
        playersCoal.clear();
        playersIron.clear();
        playersCopper.clear();
        playersGold.clear();
        playersRedstone.clear();
        playersLapis.clear();
        playersDiamond.clear();
        playersEmerald.clear();

        active = false;
        eventDuration = -1;
        eventCreator = null;
    }

    public void start() {
        active = true;

        for (Player player : playersPoints.keySet()) {
            eventBar = BossBar.bossBar(Component.text("Caving Event").color(NamedTextColor.DARK_GRAY),
                    1,
                    BossBar.Color.BLUE,
                    BossBar.Overlay.PROGRESS);
            player.showBossBar(eventBar);
        }

        new BukkitRunnable() {

            long timeLeft = eventDuration;

            @Override
            public void run() {

                if (timeLeft <= 0) {
                    ArrayList<Map.Entry<Player, Integer>> listPlayerPoints = new ArrayList<>(playersPoints.entrySet());
                    listPlayerPoints.sort(Collections.reverseOrder());

                    Player winner = Collections.max(playersPoints.entrySet(), Map.Entry.comparingByValue()).getKey();

                    eventBar.progress(0f);

                    for (Player player : playersPoints.keySet()) {
                        player.hideBossBar(eventBar);
                        player.sendMessage("Player " + winner.getName() + "has won the Caving Event with " + playersPoints.get(winner) + "points!");
                        player.sendMessage("You have collected " + playersPoints.get(player) + "points \n" +
                                "and mined: " +
                                "\nCoal Ore - " + playersCoal.get(player) +
                                "\nIron Ore - " + playersIron.get(player) +
                                "\nCopper Ore - " + playersCopper.get(player) +
                                "\nGold Ore - " + playersGold.get(player) +
                                "\nRedstone Ore - " + playersRedstone.get(player) +
                                "\nLapis Ore - " + playersLapis.get(player) +
                                "\nDiamond Ore - " + playersDiamond.get(player) +
                                "\nEmerald Ore - " + playersEmerald.get(player) +
                                ".\nCongratulations!");
                    }

                    int sumCoal = 0, sumIron = 0, sumCopper = 0, sumGold = 0, sumRedstone = 0, sumLapis = 0, sumDiamond = 0, sumEmerald = 0;

                    for (int f : playersCoal.values()) {
                        sumCoal += f;
                    }
                    for (int f : playersIron.values()) {
                        sumIron += f;
                    }
                    for (int f : playersCopper.values()) {
                        sumCopper += f;
                    }
                    for (int f : playersGold.values()) {
                        sumGold += f;
                    }
                    for (int f : playersRedstone.values()) {
                        sumRedstone += f;
                    }
                    for (int f : playersLapis.values()) {
                        sumLapis += f;
                    }
                    for (int f : playersDiamond.values()) {
                        sumDiamond += f;
                    }
                    for (int f : playersEmerald.values()) {
                        sumEmerald += f;
                    }

                    eventCreator.sendMessage("Sum of all ores mined in Caving Event: " +
                            "\nCoal Ore - " + sumCoal +
                            "\nIron Ore - " + sumIron +
                            "\nCopper Ore - " + sumCopper +
                            "\nGold Ore - " + sumGold +
                            "\nRedstone Ore - " + sumRedstone +
                            "\nLapis Ore - " + sumLapis +
                            "\nDiamond Ore - " + sumDiamond +
                            "\nEmerald Ore - " + sumEmerald);

                    playersPoints.clear();
                    playersCoal.clear();
                    playersIron.clear();
                    playersCopper.clear();
                    playersGold.clear();
                    playersRedstone.clear();
                    playersLapis.clear();
                    playersDiamond.clear();
                    playersEmerald.clear();

                    active = false;
                    eventDuration = -1;
                    eventCreator = null;

                    this.cancel();
                }

                eventBar.progress((float) timeLeft / eventDuration);

                for (Player player : playersPoints.keySet()) {
                    player.sendMessage(timeLeft + ", " + (100f * timeLeft / eventDuration));
                }

                timeLeft--;
            }

        }.runTaskTimer(plugin, 0L, 20L);
    }
}
