package madpillow.tacticsMiners.config

enum class TextConfigType {
    SUCCESS_STEAL_TARGET, SUCCESS_STEAL_SOURCE, FAILED_STEAL_TARGET, FAILED_STEAL_SOURCE, SUCCESS_SOLDIER, REWARD,
    SUCCESS_ASSASSIN_TARGET, SUCCESS_ASSASSIN_SOURCE, FAILED_ASSASSIN_TARGET, FAILED_ASSASSIN_SOURCE;

    fun getDefaultData(): String {
        return when (this) {
            SUCCESS_STEAL_TARGET -> "[SOURCE]により[ORE]を[AMOUNT]個盗まれました([MISSION])"
            SUCCESS_STEAL_SOURCE -> "[TARGET]の[ORE]を[AMOUNT]個盗みました"
            FAILED_STEAL_TARGET -> "[SOURCE]による強奪カードから防衛しました"
            FAILED_STEAL_SOURCE -> "[TARGET]への強奪は防衛されました"
            SUCCESS_SOLDIER -> "防衛成功 [SOURCE] >> |✖| [TARGET]"
            REWARD -> "  報酬 [ORE]：[AMOUNT]個"
            SUCCESS_ASSASSIN_TARGET -> "[TARGET]は[SOURCE]による暗殺カードで死亡した"
            SUCCESS_ASSASSIN_SOURCE -> "[TARGET]の暗殺に成功し、[ORE]を[AMOUNT]個を奪いました"
            FAILED_ASSASSIN_TARGET -> "[SOURCE]による暗殺カードから防衛しました"
            FAILED_ASSASSIN_SOURCE -> "[TARGET]の暗殺に失敗しました"
        }
    }
}