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
                       source: String = "source",
                       target: String = "target",
                       ore: String = "ore",
                       amount: String = "amount",
                       mission: String = "mission"): String {
            return textConfigType.convertMessage(dataMap[textConfigType]!!, source, target, ore, amount, mission)
        }
    }
}