package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners

class DefaultConfigUtils {
    companion object {
        private val configuration = TacticsMiners.plugin.config
        private val dataMap = HashMap<DefaultConfigData, Any>()

        fun init() {
            DefaultConfigData.values().forEach {
                dataMap[it] = (configuration.get(it.toString())
                        ?: configuration.set(it.toString(), it.getDefaultData()))
            }
        }

        fun getWorldBorderSize(): Double {
            return dataMap[DefaultConfigData.WorldBorderSize].toString().toDouble()
        }
    }
}