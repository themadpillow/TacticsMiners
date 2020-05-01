package madpillow.tacticsMiners.game.skill.curse

import madpillow.tacticsMiners.config.SkillConfig
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

enum class CurseType {
    SLOW, SLOW_DIGGING, HUNGER, JAMMING_SKILL;

    fun getPotionEffect(): PotionEffect {
        if (this == JAMMING_SKILL) {
            throw RuntimeException("PotionEffectの取得に失敗")
        }

        return PotionEffect(getPotionEffectType(),
                getTime(),
                1)
    }

    fun getPotionEffectType(): PotionEffectType {
        if (this == JAMMING_SKILL) {
            throw RuntimeException("PotionEffectTypeの取得に失敗")
        }

        return PotionEffectType.getByName(this.name)!!
    }

    fun getTime(): Int {
        return (SkillConfig.getData(SkillType.CURSE) as MutableMap<*, *>)[getPotionEffectType().name] as Int
    }
}