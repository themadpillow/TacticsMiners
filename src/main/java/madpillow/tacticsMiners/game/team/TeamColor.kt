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
}