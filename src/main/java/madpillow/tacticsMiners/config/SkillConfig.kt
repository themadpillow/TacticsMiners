package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.configuration.ConfigurationSection

class SkillConfig {
    companion object {
        private val config = CustomConfig(TacticsMiners.plugin, "SkillList.yml")
        val skillEffectTimeMap = mutableMapOf<SkillType, MutableMap<Int, Int>>()

        fun loadConfig() {
            mutableListOf(SkillType.FAST_DIGGING, SkillType.SPEED, SkillType.ENCOURAGE)
                    .forEach { loadEffectTime(it) }
        }

        private fun loadEffectTime(skillType: SkillType) {
            val section = config.getConfig().getConfigurationSection(skillType.toString())
                    ?: createSample(skillType)
            val list = mutableMapOf<Int, Int>()
            for (level in 1..skillType.maxLevel) {
                if (section.isSet(level.toString())) {
                    list[level] = section.getInt(level.toString())
                } else {
                    section.set(level.toString(), level * 10)
                    config.saveConfig()
                    list[level] = level * 10
                }
            }

            skillEffectTimeMap[skillType] = list
        }

        private fun createSample(skillType: SkillType): ConfigurationSection {
            val section = config.getConfig().createSection(skillType.toString())
            val list = mutableMapOf<Int, Int>()
            for (level in 1..skillType.maxLevel) {
                section.set(level.toString(), level * 10)
                list[level] = level * 10
            }

            skillEffectTimeMap[skillType] = list
            config.saveConfig()

            return section
        }
    }
}
