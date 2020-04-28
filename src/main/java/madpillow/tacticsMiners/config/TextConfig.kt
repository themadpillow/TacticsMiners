package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners

class TextConfig {
    companion object {
        private val dataMap = mutableMapOf<TextConfigType, String>()

        fun init() {
            val config = CustomConfig(TacticsMiners.plugin, "TextConfig.yml")
            val configuration = config.getConfig()
            TextConfigType.values().forEach {
                configuration.getString(it.toString())?.also { data ->
                    dataMap[it] = data
                } ?: run {
                    configuration.set(it.toString(), it.getDefaultData())
                    config.saveConfig()
                    dataMap[it] = it.getDefaultData()
                }
            }
        }

        fun getMessage(textConfigType: TextConfigType,
                       target: String = "[TARGET]",
                       source: String = "[SOURCE]",
                       ore: String = "[ORE]",
                       amount: String = "[AMOUNT]",
                       mission: String = "[MISSION]"): String {
            return dataMap[textConfigType]!!
                    .replace("[REWARD]", dataMap[TextConfigType.REWARD]!!)
                    .replace("[TARGET]", target)
                    .replace("[SOURCE]", source)
                    .replace("[ORE]", ore)
                    .replace("[AMOUNT]", amount)
                    .replace("[MISSION]", mission)
        }
    }
}