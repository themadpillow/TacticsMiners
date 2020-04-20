package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners

class ConfigUtils {
    companion object {
        private val configuration = TacticsMiners.plugin.config
        private val dataMap = HashMap<DefaultConfig, Any>()

        fun init() {
            DefaultConfig.values().forEach {
                dataMap[it] = (configuration.get(it.toString())
                        ?: configuration.set(it.toString(), it.getDefaultData()))
            }
        }

        fun getWorldBorderSize(): Double {
            return dataMap[DefaultConfig.WorldBorderSize].toString().toDouble()
        }
    }
}