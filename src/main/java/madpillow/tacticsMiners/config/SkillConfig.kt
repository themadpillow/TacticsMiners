package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners

class SkillConfig {
    companion object {
        private val config = CustomConfig(TacticsMiners.plugin, "SkillList.yml")
        val digSkillTimeMap = mutableMapOf<Int, Int>()

        fun reloadConfig() {
            val configuration = config.getConfig()
            if (configuration.getKeys(false).isEmpty()) {
                createSample()
            }

            configuration.getKeys(false).forEach { configKey ->
                val section = configuration.getConfigurationSection(configKey)!!
                section.getKeys(false).forEach { sectionKey ->
                    if (sectionKey == "DIG_SPEED") {
                        digSkillTimeMap[Integer.valueOf(sectionKey)] = section.getInt(sectionKey)
                    }
                }
            }
        }

        private fun createSample() {
            val configuration = config.getConfig()
            val section = configuration.createSection("DIG_SPEED")
            section.set("1", 10)
            section.set("2", 20)
            section.set("3", 30)

            config.saveConfig()
        }
    }
}
