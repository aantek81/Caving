package me.antek81.caving.messages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Messages {
    private static final Component notFound = Component.text("<not found>");
    private static final MiniMessage mm = MiniMessage.miniMessage();

    private final FileConfiguration config;
    private Component prefix = Component.empty();

    public Messages(File file) {
        this.config = YamlConfiguration.loadConfiguration(file);
        this.config.options().setHeader(List.of(
                "This configuration file supports MiniMessage formatting.",
                "Learn more about MiniMessages at:",
                "https://docs.papermc.io/adventure/minimessage/format/",
                "",
                "You can preview your MiniMessage at:",
                "https://webui.advntr.dev/"
        ));

        final String prefix = this.config.getString("Prefix");

        if (prefix != null)
            this.prefix = mm.deserialize(prefix.trim());

        try {
            this.config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Component getMessage(MessageType messageType) {
        return getMessage(messageType, TagResolver.empty());
    }

    public Component getMessage(MessageType messageType, TagResolver... placeholders) {
        // Load the message from the config
        final  String messageString = this.config.getString(messageType.getPath());

        if (messageString == null) {
            return notFound;
        }

        return prefix.appendSpace().append(mm.deserialize(messageString, TagResolver.resolver(placeholders)));
    }

    public Component getMessageNoPrefix(MessageType messageType, TagResolver... placeholders) {
        final  String messageString = this.config.getString(messageType.getPath());

        if (messageString == null) {
            return notFound;
        }

        return mm.deserialize(messageString, TagResolver.resolver(placeholders));
    }
}
