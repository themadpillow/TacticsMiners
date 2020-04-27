package madpillow.tacticsMiners.config

enum class TextConfigType {
    SUCCESS_STEAL_TARGET, SUCCESS_STEAL_SOURCE, FAILED_STEAL_SOURCE, FAILED_STEAL_TARGET,
    SUCCESS_SOLDIER;

    fun getDefaultData(): String {
        return when (this) {
            SUCCESS_STEAL_TARGET -> "[SOURCE]により[ORE]を[AMOUNT]個盗まれました([MISSION])"
            SUCCESS_STEAL_SOURCE -> "[TARGET]の[ORE]を[AMOUNT]個盗みました"
            FAILED_STEAL_TARGET -> "[SOURCE]による強奪カードから防衛しました"
            FAILED_STEAL_SOURCE -> "[TARGET]への強奪は防衛されました"
            SUCCESS_SOLDIER -> "[TARGET]からの防衛に成功し、報酬([ORE]:[AMOUNT]個)を受け取りました"
        }
    }

    fun convertMessage(sourceText: String,
                       source: String = "source",
                       target: String = "target",
                       ore: String = "ore",
                       amount: String = "amount",
                       mission: String = "mission"): String {
        return when (this) {
            SUCCESS_STEAL_TARGET -> sourceText
                    .replace("[SOURCE]", source)
                    .replace("[ORE]", ore)
                    .replace("[AMOUNT]", amount)
                    .replace("[MISSION]", mission)
            SUCCESS_STEAL_SOURCE -> sourceText
                    .replace("[TARGET]", target)
                    .replace("[ORE]", ore)
                    .replace("[AMOUNT]", amount)
            FAILED_STEAL_TARGET -> sourceText
                    .replace("[SOURCE]", source)
            FAILED_STEAL_SOURCE -> sourceText
                    .replace("[TARGET]", target)
            SUCCESS_SOLDIER -> sourceText
                    .replace("[TARGET]", target)
                    .replace("[ORE]", ore)
                    .replace("[AMOUNT]", amount)
        }
    }
}