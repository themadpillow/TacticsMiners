package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.config.SkillConfig
import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

enum class SkillType(val maxLevel: Int) {
    FAST_DIGGING(3), SPEED(3), ENCOURAGE(2), STEAL(1);

    fun getName(level: Int = 1): String {
        return when (this) {
            FAST_DIGGING -> "採掘カード$level"
            SPEED -> "速攻カード$level"
            ENCOURAGE -> "激励カード$level"
            STEAL -> "強奪カード"
        }
    }

    private fun getPotionEffect(level: Int): PotionEffect {
        if (level > maxLevel) {
            throw RuntimeException("Skillの最大Levelを超えています")
        }
        return when (this) {
            FAST_DIGGING -> PotionEffect(PotionEffectType.FAST_DIGGING, 20 * SkillConfig.skillEffectTimeMap[this]!![level]!!, level)
            SPEED, ENCOURAGE -> PotionEffect(PotionEffectType.SPEED, 20 * SkillConfig.skillEffectTimeMap[this]!![level]!!, level)
            else -> throw RuntimeException("PotionEffectの取得に失敗")
        }
    }

    fun getLore(level: Int = 1): MutableList<String> {
        return when (this) {
            FAST_DIGGING -> mutableListOf(
                    "採掘速度上昇${SkillConfig.skillEffectTimeMap[this]!![level]!!}秒間"
            )
            SPEED -> mutableListOf(
                    "移動速度上昇${SkillConfig.skillEffectTimeMap[this]!![level]!!}秒間"
            )
            ENCOURAGE -> mutableListOf(
                    "チーム全員に移動速度上昇${SkillConfig.skillEffectTimeMap[this]!![level]!!}秒間"
            )
            STEAL -> mutableListOf(
                    "相手から納品済みの鉱石を盗むことが出来る",
                    "盗む納品鉱石や数はランダムで選ばれる"
            )
        }
    }

    fun getSkill(level: Int = 1): Skill {
        if (level > maxLevel) {
            throw RuntimeException("Skillの最大Levelを超えています")
        }
        return Skill(this, level)
    }

    fun perform(gamePlayer: GamePlayer, level: Int = 1) {
        if (level > maxLevel) {
            throw RuntimeException("Skillの最大Levelを超えています")
        }

        when (this) {
            FAST_DIGGING,
            SPEED -> gamePlayer.player.addPotionEffect(getPotionEffect(level))
            ENCOURAGE -> gamePlayer.gameTeam!!.players.forEach { it.player.addPotionEffect(getPotionEffect(level)) }
            STEAL -> {
                // TODO set STEAL perform
            }
        }
    }
}