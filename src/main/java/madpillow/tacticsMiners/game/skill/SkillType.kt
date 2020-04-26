package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.config.SkillConfig
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.mission.Mission
import madpillow.tacticsMiners.game.skill.soldier.SoldierInventoryType
import madpillow.tacticsMiners.game.skill.steal.StealInventory
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

enum class SkillType(val maxLevel: Int) {
    FAST_DIGGING(3), SPEED(3), ENCOURAGE(2), STEAL(1), SOLDIER(1);

    fun getName(level: Int = 1): String {
        return when (this) {
            FAST_DIGGING -> "採掘カード$level"
            SPEED -> "速攻カード$level"
            ENCOURAGE -> "激励カード$level"
            STEAL -> "強奪カード"
            SOLDIER -> "騎士カード"
        }
    }

    private fun getPotionEffect(level: Int): PotionEffect {
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
            SOLDIER -> mutableListOf(
                    "強奪・暗殺カードからチームやプレイヤーを守ることが出来る",
                    "他プレイヤーの防衛に成功した場合、報酬を受け取ることが出来る",
                    "(報酬は盗まれる予定だった量の半分）"
            )
        }
    }

    fun getSkill(level: Int = 1): Skill {
        return Skill(this, level)
    }

    fun click(gamePlayer: GamePlayer, skill: Skill) {
        when (this) {
            FAST_DIGGING,
            SPEED,
            ENCOURAGE -> {
                perform(gamePlayer, skill)
            }
            STEAL -> gamePlayer.player.openInventory(StealInventory(gamePlayer.gameTeam!!, skill).inventory)
            SOLDIER -> gamePlayer.openSoldierInventory(SoldierInventoryType.SELECT)
        }
    }

    fun perform(gamePlayer: GamePlayer, skill: Skill, target: Any? = null) {
        gamePlayer.player.closeInventory()
        gamePlayer.skillInventory.removeSkill(skill)
        when (this) {
            FAST_DIGGING,
            SPEED -> gamePlayer.player.addPotionEffect(getPotionEffect(skill.level))
            ENCOURAGE -> gamePlayer.gameTeam!!.players.forEach { it.player.addPotionEffect(getPotionEffect(skill.level)) }
            STEAL -> (target as Mission).steal(gamePlayer)
            SOLDIER -> {
                if (target is GameTeam) {
                    target.hasSoldier = true
                } else { // target is GamePlayer
                    (target as GamePlayer).hasSoldier = true
                }
            }
        }
    }
}