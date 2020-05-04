package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.Ore

class OreConfig {
    companion object {
        private val dataMap = mutableMapOf<Ore, Int>()

        fun init() {
            val config = CustomConfig(TacticsMiners.plugin, "OreConfig.yml")
            val configuration = config.getConfig()

            Ore.values().forEach {
                configuration.get(it.name)?.also { data ->
                    dataMap[it] = data as Int
                } ?: run {
                    configuration.set(it.name, it.getDefaultPoint())
                    config.saveConfig()
                    dataMap[it] = it.getDefaultPoint()
                }
            }
        }

        fun getData(ore: Ore): Int {
            return dataMap[ore]!!
        }
    }
}