package madpillow.tacticsMiners.config

import org.bukkit.ChatColor

enum class TextConfigType {
    START_INTERVAL_TITLE, START_INTERVAL_SUBTITLE,
    SUCCESS_STEAL_TARGET, SUCCESS_STEAL_SOURCE, FAILED_STEAL_TARGET,
    FAILED_STEAL_SOURCE, SUCCESS_SOLDIER, REWARD, SUCCESS_ASSASSIN_TARGET,
    SUCCESS_ASSASSIN_SOURCE, FAILED_ASSASSIN_TARGET, FAILED_ASSASSIN_SOURCE,
    SUCCESS_CURSE_TARGET, SUCCESS_CURSE_SOURCE, CAN_NOT_USE_SKILL,
    SUCCESS_MUTE_TARGET, SUCCESS_MUTE_SOURCE, CAN_NOT_CHAT,
    SUCCESS_SHUFFLE_TARGET, SUCCESS_SHUFFLE_SOURCE, SUCCESS_DELIVERY_TARGET,
    SUCCESS_DELIVERY_SOURCE, HALF_TIME, REMAINING_TIME, REMOVE_BOSSBAR;

    fun getDefaultData(): String {
        return when (this) {
            START_INTERVAL_TITLE -> "${ChatColor.RED}Tactics Miners"
            START_INTERVAL_SUBTITLE -> "開始まで...[AMOUNT]秒"
            SUCCESS_STEAL_TARGET -> "[SOURCE]により[ORE]を[AMOUNT]個盗まれました(ミッション:[CONTENT])"
            SUCCESS_STEAL_SOURCE -> "[TARGET]の[ORE]を[AMOUNT]個盗みました"
            FAILED_STEAL_TARGET -> "[SOURCE]による強奪カードから防衛しました"
            FAILED_STEAL_SOURCE -> "[TARGET]への強奪は防衛されました"
            SUCCESS_SOLDIER -> "§l防衛成功§r [SOURCE] █▒░┇ [TARGET]"
            REWARD -> "  報酬 [ORE]：[AMOUNT]個"
            SUCCESS_ASSASSIN_TARGET -> "[TARGET]は[SOURCE]による暗殺カードで死亡した"
            SUCCESS_ASSASSIN_SOURCE -> "[TARGET]の暗殺に成功し、[ORE]を[AMOUNT]個を奪いました"
            FAILED_ASSASSIN_TARGET -> "[SOURCE]による暗殺カードから防衛しました"
            FAILED_ASSASSIN_SOURCE -> "[TARGET]の暗殺に失敗しました"
            SUCCESS_CURSE_TARGET -> "[SOURCE]により[CONTENT]の呪いがかかりました"
            SUCCESS_CURSE_SOURCE -> "[TARGET]へ[CONTENT]の呪いをかけました"
            CAN_NOT_USE_SKILL -> "呪いにより現在スキルは使用できません（残り<AMOUNT>秒）"
            SUCCESS_MUTE_TARGET -> "[SOURCE]により[CONTENT]の呪いがかかりました"
            SUCCESS_MUTE_SOURCE -> "[TARGET]へ[CONTENT]の呪いをかけました"
            CAN_NOT_CHAT -> "妨害スキルによりチャット使用できません（残り<えAMOUNT>秒）"
            SUCCESS_SHUFFLE_TARGET -> "[SOURCE]によりインベントリがシャッフルされました"
            SUCCESS_SHUFFLE_SOURCE -> "[TARGET]のインベントリをシャッフルしました"
            SUCCESS_DELIVERY_TARGET -> "[SOURCE]によりアイテムが送られてきました"
            SUCCESS_DELIVERY_SOURCE -> "[TARGET]へアイテムを送りました"
            HALF_TIME -> "残り時間が半分となりました"
            REMAINING_TIME -> "残り時間[AMOUNT]分です"
            REMOVE_BOSSBAR -> "残り時間5分となったため、ポイントが非表示になりました"
        }
    }
}