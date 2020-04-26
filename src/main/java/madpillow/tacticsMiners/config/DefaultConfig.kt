package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners

class DefaultConfig {
    companion object {
        private val dataMap = HashMap<DefaultConfigType, Any>()

        fun init() {
            val config = TacticsMiners.plugin.config
            DefaultConfigType.values().forEach {
                dataMap[it] = config.get(it.toString())
                        ?: run {
                            config.set(it.toString(), it.getDefaultData())
                            dataMap[it] = it.getDefaultData()

                            TacticsMiners.plugin.saveConfig()
                        }
            }
        }

        fun getData(defaultConfigType: DefaultConfigType): Any {
            return dataMap[defaultConfigType]!!
        }
    }
}