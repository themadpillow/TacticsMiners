package madpillow.tacticsMiners.config

enum class TextConfigType {
    STEAL_MESSAGE_TARGET, STEAL_MESSAGE_SOURCE;

    fun getDefaultData(): String {
        return when (this) {
            STEAL_MESSAGE_TARGET -> "[SOURCE]により[ORE]が[AMOUNT]盗まれました([MISSION])"
            STEAL_MESSAGE_SOURCE -> "[TARGET]の[ORE]を[AMOUNT]盗みました"
        }
    }

    fun convertMessage(sourceText: String,
                       source: String = "source",
                       target: String = "target",
                       ore: String = "ore",
                       amount: String = "amount",
                       mission: String = "mission"): String {
        return when (this) {
            STEAL_MESSAGE_TARGET -> sourceText
                    .replace("[SOURCE]", source)
                    .replace("[ORE]", ore)
                    .replace("[AMOUNT]", amount)
                    .replace("[MISSION]", mission)
            STEAL_MESSAGE_SOURCE -> sourceText
                    .replace("[TARGET]", target)
                    .replace("[ORE]", ore)
                    .replace("[AMOUNT]", amount)
        }
    }
}