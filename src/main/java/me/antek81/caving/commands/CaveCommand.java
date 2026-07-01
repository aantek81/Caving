package me.antek81.caving.commands;

import me.antek81.caving.event.CaveEvent;
import me.antek81.caving.messages.MessageType;
import me.antek81.caving.messages.Messages;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
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
    private final Messages messages;

    public CaveCommand(CaveEvent caveEvent, Messages messages) {
        this.caveEvent = caveEvent;
        this.messages = messages;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage(messages.getMessage(MessageType.COMMAND_ONLY_FOR_PLAYERS));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(messages.getMessage(MessageType.COMMAND_USE_PROPER_SUB));
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
                            sender.sendMessage(messages.getMessage(MessageType.COMMAND_PROPER_TIME_FORMAT));
                            return true;
                        }
                    }

                    caveEvent.create(seconds, player);

                    sender.sendMessage(messages.getMessage(MessageType.EVENT_CREATED, Placeholder.unparsed("seconds", String.valueOf(seconds))));
                } else {
                    sender.sendMessage(messages.getMessage(MessageType.COMMAND_NOT_PERMITTED));
                }
                return true;

            case "join":
                if (args.length == 1) {
                    caveEvent.join(player);
                    sender.sendMessage(messages.getMessage(MessageType.EVENT_JOINED));
                } /* else if (sender.hasPermission("caving.cave.admin")) {
                    caveEvent.join(args[1]);
                    sender.sendMessage("§7You forced §8" + args[1] + "§7 into joining Caving event.");
                }
                */
                return true;

            case "start":
                if (sender.hasPermission("caving.cave.admin")) {
                    caveEvent.start();
                    player.sendMessage(messages.getMessage(MessageType.EVENT_STARTED_HOST));
                } else {
                    sender.sendMessage(messages.getMessage(MessageType.COMMAND_NOT_PERMITTED));
                }
                return true;

            case "cancel":
                if (sender.hasPermission("caving.cave.admin")) {
                    caveEvent.cancel();
                    sender.sendMessage(messages.getMessage(MessageType.EVENT_CANCELED_HOST));
                } else {
                    sender.sendMessage(messages.getMessage(MessageType.COMMAND_NOT_PERMITTED));
                }
                return true;

            case "rules":
                sender.sendMessage(messages.getMessage(MessageType.EVENT_RULES));
                return true;

            default:
                if (sender.hasPermission("caving.cave.admin")) {
                    sender.sendMessage(messages.getMessage(MessageType.COMMAND_PROPER_ARGS, Placeholder.unparsed("command", "/cave (create|join|start|cancel|rules)")));
                } else {
                    sender.sendMessage(messages.getMessage(MessageType.COMMAND_PROPER_ARGS, Placeholder.unparsed("command", "/cave (join|rules)")));
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
