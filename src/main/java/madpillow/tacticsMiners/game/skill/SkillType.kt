package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.InventoryUtils
import madpillow.tacticsMiners.config.SkillConfig
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.mission.Mission
import madpillow.tacticsMiners.game.skill.assasin.AssassinInventory
import madpillow.tacticsMiners.game.skill.curse.CurseInventory
import madpillow.tacticsMiners.game.skill.curse.CurseType
import madpillow.tacticsMiners.game.skill.delivery.DeliverySelectTargetInventory
import madpillow.tacticsMiners.game.skill.mute.MuteInventory
import madpillow.tacticsMiners.game.skill.ore.OreType
import madpillow.tacticsMiners.game.skill.shuffle.ShuffleInventory
import madpillow.tacticsMiners.game.skill.soldier.SoldierSelectTargetInventory
import madpillow.tacticsMiners.game.skill.spy.SpyInventory
import madpillow.tacticsMiners.game.skill.steal.StealInventory
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

enum class SkillType(val maxLevel: Int) {
    FAST_DIGGING(3), SPEED(3), ENCOURAGE(2), STEAL(1), SOLDIER(1), SPY(1), ASSASSIN(1), CURSE(1), MUTE(1), ORE(3), SHUFFLE(1), DELIVERY(1);

    fun getName(level: Int = 1): String {
        return when (this) {
            FAST_DIGGING -> "採掘カード$level"
            SPEED -> "速攻カード$level"
            ENCOURAGE -> "激励カード$level"
            STEAL -> "強奪カード"
            SOLDIER -> "騎士カード"
            SPY -> "スパイカード"
            ASSASSIN -> "暗殺カード"
            CURSE -> "呪縛カード"
            MUTE -> "口外禁止令カード"
            ORE -> "鉱石カード（${OreType.valueOf(level).getDisplayName()})"
            SHUFFLE -> "シャッフルカード"
            DELIVERY -> "配達カード"
        }
    }

    fun getDefaultData(level: Int = 1): Any {
        return when (this) {
            FAST_DIGGING,
            SPEED,
            ENCOURAGE,
            ORE
            -> level * 10
            CURSE -> {
                val map = mutableMapOf<String, Int>()
                CurseType.values().filter { it != CurseType.JAMMING_SKILL }
                        .forEach { map[it.getPotionEffectType().name] = 60 }

                return map
            }
            MUTE -> 60
            else -> throw RuntimeException("DefaultDataの取得に失敗")
        }
    }

    private fun getPotionEffect(level: Int): PotionEffect {
        return when (this) {
            FAST_DIGGING -> PotionEffect(PotionEffectType.FAST_DIGGING, 20 * SkillConfig.getData(this, level) as Int, level)
            SPEED,
            ENCOURAGE -> PotionEffect(PotionEffectType.SPEED, 20 * SkillConfig.getData(this, level) as Int, level)
            else -> throw RuntimeException("PotionEffectの取得に失敗")
        }
    }

    fun getLore(level: Int = 1): MutableList<String> {
        return when (this) {
            FAST_DIGGING -> mutableListOf(
                    "採掘速度上昇${SkillConfig.getData(this, level)}秒間"
            )
            SPEED -> mutableListOf(
                    "移動速度上昇${SkillConfig.getData(this, level)}秒間"
            )
            ENCOURAGE -> mutableListOf(
                    "チーム全員に移動速度上昇${SkillConfig.getData(this, level)}秒間"
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
            CURSE -> mutableListOf(
                    "指定した相手に呪いを数分間掛ける",
                    "鈍足、採掘速度低下、空腹、スキルカード使用禁止など"
            )
            MUTE -> mutableListOf(
                    "指定した相手のチャットを${SkillConfig.getData(this, level)}秒間禁止にする"
            )
            ORE -> mutableListOf(
                    "${OreType.valueOf(level).getMaterial()}を${SkillConfig.getData(this, level)}個もらえる"
            )
            SHUFFLE -> mutableListOf(
                    "指定した相手のインベントリをごちゃごちゃにシャッフルする"
            )
            DELIVERY -> mutableListOf(
                    "指定した相手に任意のブロックを相手に送ることが出来る"
            )
        }
    }

    fun click(gamePlayer: GamePlayer, skill: Skill) {
        when (this) {
            FAST_DIGGING,
            SPEED,
            ENCOURAGE,
            ORE -> perform(gamePlayer, skill)
            STEAL -> StealInventory(gamePlayer, skill).openInventory()
            SOLDIER -> SoldierSelectTargetInventory(gamePlayer, skill).openSelectInventory()
            SPY -> SpyInventory(gamePlayer, skill).openInventory()
            ASSASSIN -> AssassinInventory(gamePlayer, skill).openInventory()
            CURSE -> CurseInventory(gamePlayer, skill).openInventory()
            MUTE -> MuteInventory(gamePlayer, skill).openInventory()
            SHUFFLE -> ShuffleInventory(gamePlayer, skill).openInventory()
            DELIVERY -> DeliverySelectTargetInventory(gamePlayer, skill).openInventory()
        }
    }

    fun perform(performer: GamePlayer, skill: Skill, target: Any? = null, content: MutableList<ItemStack>? = null) {
        performer.player.closeInventory()
        performer.skillInventory.removeSkill(skill)
        when (this) {
            FAST_DIGGING,
            SPEED -> performer.player.addPotionEffect(getPotionEffect(skill.level))
            ENCOURAGE -> performer.gameTeam!!.players.forEach { it.player.addPotionEffect(getPotionEffect(skill.level)) }
            STEAL -> {
                (target as Mission).steal(performer)
            }
            SOLDIER -> {
                if (target is GameTeam) {
                    target.setSoldier(performer)
                } else { // target is GamePlayer
                    (target as GamePlayer).setSoldier(performer)
                }
            }
            SPY -> {
                val gameTeam = target as GameTeam
                val inventory = Bukkit.createInventory(performer.player, InventoryUtils.getInventorySize(gameTeam.missionList.size, 1),
                        "${SPY.getName()}(${gameTeam.teamColor.getChatColor()}$gameTeam${ChatColor.RESET}のミッション)")
                gameTeam.missionList.forEach {
                    inventory.addItem(it.getItemStack())
                }

                performer.player.openInventory(inventory)
            }
            ASSASSIN -> {
                target as GamePlayer
                target.assassinated(performer)
            }
            CURSE -> {
                target as GamePlayer
                target.beCursed(performer)
            }
            MUTE -> {
                target as GamePlayer
                target.muteChat(performer)
            }
            ORE -> {
                performer.player.inventory.addItem(OreType.valueOf(skill.level).getItemStack())
            }
            SHUFFLE -> {
                target as GamePlayer
                target.shuffleInventory(performer)
            }
            DELIVERY -> {
                target as GamePlayer
                target.delivery(performer, content!!)
            }
        }
    }
}