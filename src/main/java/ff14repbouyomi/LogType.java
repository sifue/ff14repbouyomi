package ff14repbouyomi;

/**
 * ログの種類
 */
public enum LogType {
    Say,
    Emote,
    Yell,
    Shout,
    LinkShell,
    FreeCompany,
    Party,
    Tell,
    Other;

    public static LogType getLogType(String logType) {
        if(logType.startsWith("Say")) return Say;
        if(logType.startsWith("Emote")) return Emote;
        if(logType.startsWith("Yell")) return Yell;
        if(logType.startsWith("Shout")) return Shout;
        if(logType.startsWith("LS")) return LinkShell;
        if(logType.startsWith("FCompany")) return FreeCompany;
        if(logType.startsWith("Party")) return Party;
        if(logType.startsWith("Tell")) return Tell;
        return Other;
    }

}
