package madpillow.tacticsMiners.config

import org.bukkit.ChatColor

enum class ScoreboardConfigType {
    SIDEBAR_TITLE, EMPTY, TEAM_POINT, TEAM_SOLDIER, OWN_SOLDIER;

    fun getDefaultData(): String {
        return when (this) {
            SIDEBAR_TITLE -> "${ChatColor.UNDERLINE}Tactics Miners"
            EMPTY -> "なし"
            TEAM_POINT -> "チームのスコア >> [POINT]"
            TEAM_SOLDIER -> "チームの騎士 >> [SOLDIER]"
            OWN_SOLDIER -> "あなたの騎士 >> [SOLDIER]"
        }
    }
}