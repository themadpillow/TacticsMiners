package madpillow.tacticsMiners.game.team

import org.bukkit.ChatColor

enum class TeamColor {
    RED, BLUE, GREEN, YELLOW, PURPLE, AQUA, GOLD, PINK;

    fun getChatColor(): ChatColor {
        return when (this) {
            RED -> ChatColor.RED
            BLUE -> ChatColor.BLUE
            GREEN -> ChatColor.GREEN
            YELLOW -> ChatColor.YELLOW
            PURPLE -> ChatColor.DARK_PURPLE
            AQUA -> ChatColor.AQUA
            GOLD -> ChatColor.GOLD
            PINK -> ChatColor.LIGHT_PURPLE
        }
    }

    fun getDisplayName(): String {
        return when (this) {
            RED -> "${ChatColor.RED}赤"
            BLUE -> "${ChatColor.BLUE}青"
            GREEN -> "${ChatColor.GREEN}緑"
            YELLOW -> "${ChatColor.YELLOW}黄"
            PURPLE -> "${ChatColor.DARK_PURPLE}紫"
            AQUA -> "${ChatColor.AQUA}水"
            GOLD -> "${ChatColor.GOLD}橙"
            PINK -> "${ChatColor.LIGHT_PURPLE}桃"
        }
    }
}