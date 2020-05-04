package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners

class ScoreboardConfig {
    companion object {
        private val dataMap = mutableMapOf<ScoreboardConfigType, String>()

        fun init() {
            val config = CustomConfig(TacticsMiners.plugin, "ScoreboardConfig.yml")
            val configuration = config.getConfig()

            ScoreboardConfigType.values().forEach {
                configuration.getString(it.toString())?.also { data ->
                    dataMap[it] = data
                } ?: run {
                    configuration.set(it.toString(), it.getDefaultData())
                    config.saveConfig()
                    dataMap[it] = it.getDefaultData()
                }
            }
        }

        fun getData(scoreboardConfigType: ScoreboardConfigType,
                    point: String = "[POINT]",
                    soldier: String = "[SOLDIER]"): String {
            return dataMap[scoreboardConfigType]!!
                    .replace("[POINT]", point)
                    .replace("[SOLDIER]", soldier)
        }
    }
}