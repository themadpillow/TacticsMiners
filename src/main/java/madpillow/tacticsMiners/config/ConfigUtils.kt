package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners

class ConfigUtils {
    companion object {
        private val configuration = TacticsMiners.plugin.config
        private val dataMap = HashMap<String, Any>()

        fun init() {
            DefaultConfig.values().forEach {
                dataMap[it.toString()] = (configuration.get(it.toString())
                        ?: configuration.set(it.toString(), it.getDefaultData()))
            }
        }

        fun getWorldBorderSize(): Double {
            return dataMap.get(DefaultConfig.WorldBorderSize) as Double
        }
    }
}