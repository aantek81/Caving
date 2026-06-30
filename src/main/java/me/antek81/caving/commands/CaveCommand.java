package me.antek81.caving.commands;

import me.antek81.caving.event.CaveEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CaveCommand implements TabExecutor {

    private final CaveEvent caveEvent;

    public CaveCommand(CaveEvent caveEvent) {
        this.caveEvent = caveEvent;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage("§cOnly players can execute this command!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cPlease use proper subcommands.");
            return true;
        }

        switch (args[0]) {
            case "create":
                if (sender.hasPermission("caving.cave.admin")) {
                    long seconds = 0;

                    if (args.length == 1)
                        seconds = 3600;
                    else {
                        try {
                            Duration duration = Duration.parse("PT" + args[1]);
                            seconds = duration.getSeconds();
                        } catch (Exception e) {
                            sender.sendMessage("§cPlease adhere to proper time format: §7nnHnnMnnS");
                            return true;
                        }
                    }

                    caveEvent.create(seconds, player);

                    sender.sendMessage("§aYour Caving event has been created and will last §7" + seconds + "§a seconds. §7Players can now join this event using §o/cave join§r");
                } else {
                    sender.sendMessage("§cYou don't have permission to execute this command!");
                }
                return true;

            case "join":
                if (args.length == 1) {
                    caveEvent.join(player);
                    sender.sendMessage("§7You have joined Caving event.");
                } /* else if (sender.hasPermission("caving.cave.admin")) {
                    caveEvent.join(args[1]);
                    sender.sendMessage("§7You forced §8" + args[1] + "§7 into joining Caving event.");
                }
                */
                return true;

            case "start":
                if (sender.hasPermission("caving.cave.admin")) {
                    caveEvent.start();
                    player.sendMessage("§7You started Caving event.");
                } else {
                    sender.sendMessage("§cYou don't have permission to execute this command!");
                }
                return true;

            case "cancel":
                if (sender.hasPermission("caving.cave.admin")) {
                    caveEvent.cancel();
                    sender.sendMessage("§7You canceled Caving event.");
                } else {
                    sender.sendMessage("§cYou don't have permission to execute this command!");
                }
                return true;

            case "rules":
                sender.sendMessage("§7Someday you'll receive rules of Caving event here.");
                return true;

            default:
                if (sender.hasPermission("caving.cave.admin")) {
                    sender.sendMessage("§cPlease use proper arguments. \n§7§o/cave (create|join|start|cancel|rules)");
                } else {
                    sender.sendMessage("§cPlease use proper arguments. §7§o/cave (join|rules)");
                }
                return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                @NotNull String label, @NotNull String @NotNull [] args) {
        final List<String> validArguments = new ArrayList<>();
        
        if (sender.hasPermission("caving.cave.admin")) {
            if (args.length == 1) {
                // /cave (create | join | start | cancel | rules)
                StringUtil.copyPartialMatches(args[0], List.of("create", "join", "start", "cancel", "rules"), validArguments);
                return validArguments;
            } else if (args.length == 2) {
                switch (args[0]) {
                    case "create": return List.of("nnHnnMnnS");
                    case "join": return List.of("<player>");
                }
            }
        } else if (sender.hasPermission("caving.cave")) {
            if (args.length == 1) {
                // /cave (join | rules)
                StringUtil.copyPartialMatches(args[0], List.of("join", "rules"), validArguments);
                return validArguments;
            }
        }

        return List.of();
    }
}
