package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.configuration.ConfigurationSection

class SkillConfig {
    companion object {
        private val config = CustomConfig(TacticsMiners.plugin, "SkillTimeList.yml")
        private val skillEffectTimeMap = mutableMapOf<SkillType, MutableMap<Int, Any>>()

        fun init() {
            mutableListOf(
                    SkillType.FAST_DIGGING,
                    SkillType.SPEED,
                    SkillType.ENCOURAGE,
                    SkillType.CURSE,
                    SkillType.MUTE,
                    SkillType.ORE
            ).forEach { loadEffectTime(it) }
        }

        fun getData(skillType: SkillType, level: Int = 1): Any {
            return skillEffectTimeMap[skillType]!![level]!!
        }

        private fun loadEffectTime(skillType: SkillType) {
            val section = config.getConfig().getConfigurationSection(skillType.toString())
                    ?: createSample(skillType)
            val map = mutableMapOf<Int, Any>()
            for (level in 1..skillType.maxLevel) {
                if (section.isSet("Level$level")) {
                    map[level] = section.getInt("Level$level")
                } else {
                    section.set("Level$level", skillType.getDefaultData(level))
                    config.saveConfig()
                    map[level] = skillType.getDefaultData(level)
                }
            }

            skillEffectTimeMap[skillType] = map
        }

        private fun createSample(skillType: SkillType): ConfigurationSection {
            val section = config.getConfig().createSection(skillType.toString())
            val map = mutableMapOf<Int, Any>()
            for (level in 1..skillType.maxLevel) {
                section.set("Level$level", skillType.getDefaultData(level))
                map[level] = skillType.getDefaultData(level)
            }

            skillEffectTimeMap[skillType] = map
            config.saveConfig()

            return section
        }
    }
}
