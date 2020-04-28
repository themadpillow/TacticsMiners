package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.InventoryUtils
import madpillow.tacticsMiners.config.SkillConfig
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.mission.Mission
import madpillow.tacticsMiners.game.skill.assasin.AssassinInventory
import madpillow.tacticsMiners.game.skill.soldier.SoldierInventory
import madpillow.tacticsMiners.game.skill.spy.SpyInventory
import madpillow.tacticsMiners.game.skill.steal.StealInventory
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

enum class SkillType(val maxLevel: Int) {
    FAST_DIGGING(3), SPEED(3), ENCOURAGE(2), STEAL(1), SOLDIER(1), SPY(1), ASSASSIN(1);

    fun getName(level: Int = 1): String {
        return when (this) {
            FAST_DIGGING -> "採掘カード$level"
            SPEED -> "速攻カード$level"
            ENCOURAGE -> "激励カード$level"
            STEAL -> "強奪カード"
            SOLDIER -> "騎士カード"
            SPY -> "スパイカード"
            ASSASSIN -> "暗殺カード"
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
            SPY -> mutableListOf(
                    "相手の納品済み鉱石や納品済みのミッションを確認することが出来る"
            )
            ASSASSIN -> mutableListOf(
                    "指定した相手を暗殺することが出来る",
                    "暗殺成功した場合は納品していないインベントリに入っている鉱石を貰える",
                    "暗殺に失敗した場合は何かしらのペナルティを受ける"
            )
        }
    }

    fun click(gamePlayer: GamePlayer, skill: Skill) {
        when (this) {
            FAST_DIGGING,
            SPEED,
            ENCOURAGE -> {
                perform(gamePlayer, skill)
            }
            STEAL -> StealInventory(gamePlayer, skill).openInventory()
            SOLDIER -> SoldierInventory(gamePlayer, skill).openSelectInventory()
            SPY -> SpyInventory(gamePlayer, skill).openInventory()
            ASSASSIN -> AssassinInventory(gamePlayer, skill).openInventory()
        }
    }

    fun perform(gamePlayer: GamePlayer, skill: Skill, target: Any? = null) {
        gamePlayer.player.closeInventory()
        gamePlayer.skillInventory.removeSkill(skill)
        when (this) {
            FAST_DIGGING,
            SPEED -> gamePlayer.player.addPotionEffect(getPotionEffect(skill.level))
            ENCOURAGE -> gamePlayer.gameTeam!!.players.forEach { it.player.addPotionEffect(getPotionEffect(skill.level)) }
            STEAL -> {
                (target as Mission).steal(gamePlayer)
            }
            SOLDIER -> {
                if (target is GameTeam) {
                    target.soldier = gamePlayer
                } else { // target is GamePlayer
                    (target as GamePlayer).soldier = gamePlayer
                }
            }
            SPY -> {
                val gameTeam = target as GameTeam
                val inventory = Bukkit.createInventory(gamePlayer.player, InventoryUtils.getInventorySize(gameTeam.missionList.size, 1),
                        "${SPY.getName()}(${gameTeam.teamColor.getChatColor()}$gameTeam${ChatColor.RESET}のミッション)")
                gameTeam.missionList.forEach {
                    inventory.addItem(it.getItemStack())
                }

                gamePlayer.player.openInventory(inventory)
            }
            ASSASSIN -> {
                target as GamePlayer
                target.assassinated(gamePlayer)
            }
        }
    }
}