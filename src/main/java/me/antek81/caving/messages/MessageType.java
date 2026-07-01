package me.antek81.caving.messages;

public enum MessageType {
    COMMAND_NOT_PERMITTED("Command-Not-Permitted"),
    COMMAND_ONLY_FOR_PLAYERS("Command-Only-For-Player"),
    COMMAND_USE_PROPER_SUB("Command-Use-Proper-Sub"),
    COMMAND_PROPER_TIME_FORMAT("Command-Proper-Time-Format"),
    COMMAND_PROPER_ARGS("Command-Proper-Args"),
    EVENT_NAME("Event-Name"),
    EVENT_CREATED("Event-Created"),
    EVENT_JOINED("Event-Joined"),
    EVENT_STARTED("Event-Started"),
    EVENT_STARTED_HOST("Event-Started-Host"),
    EVENT_CANCELED_HOST("Event-Canceled-Host"),
    EVENT_RULES("Event-Rules"),
    EVENT_PLAYER_WINNER("Event-Player-Winner"),
    EVENT_PLAYER("Event-Player"),
    EVENT_TOTAL_ORES("Event-Total-Ores"),
    PLAYER_MINED_COAL("Player-Mined-Coal"),
    PLAYER_MINED_IRON("Player-Mined-Iron"),
    PLAYER_MINED_COPPER("Player-Mined-Copper"),
    PLAYER_MINED_GOLD("Player-Mined-Gold"),
    PLAYER_MINED_REDSTONE("Player-Mined-Redstone"),
    PLAYER_MINED_LAPIS("Player-Mined-Lapis"),
    PLAYER_MINED_DIAMOND("Player-Mined-Diamond"),
    PLAYER_MINED_EMERALD("Player-Mined-Emerald");

    private final String path;

    MessageType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
