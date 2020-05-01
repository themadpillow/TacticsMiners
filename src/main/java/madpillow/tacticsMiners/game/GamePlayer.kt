package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.PlayerUtils
import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.config.SkillConfig
import madpillow.tacticsMiners.config.TextConfig
import madpillow.tacticsMiners.config.TextConfigType
import madpillow.tacticsMiners.game.skill.SkillInventory
import madpillow.tacticsMiners.game.skill.SkillType
import madpillow.tacticsMiners.game.skill.curse.CurseType
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class GamePlayer(val player: Player) {
    var gameTeam: GameTeam? = null
    val skillInventory = SkillInventory(this)
    var soldier: GamePlayer? = null
    private var jammingSkillTime = 0
    private var jammingChatTime = 0

    fun openMissionInventory() {
        gameTeam!!.missionListInventory.openInventory(this)
    }

    fun canUseSkill(): Boolean {
        return jammingSkillTime == 0
    }

    fun canChat(): Boolean {
        return jammingChatTime == 0
    }

    fun openSkillInventory() {
        if (canUseSkill()) {
            skillInventory.openInventory()
        } else {
            PlayerUtils.sendMessage(this, TextConfig.getMessage(TextConfigType.CAN_NOT_USE_SKILL, amount = jammingSkillTime.toString()))
        }
    }

    fun assassinated(assassin: GamePlayer) {
        val oreList = mutableListOf<ItemStack>()
        this.player.inventory
                .filter { item ->
                    item != null
                            && Ore.values().firstOrNull { it.getMaterial() == item.type } != null
                }
                .forEach { oreList.add(it) }

        if (this.soldier != null) {
            this.player.sendMessage(TextConfig.getMessage(TextConfigType.FAILED_ASSASSIN_TARGET, source = assassin.getDisplayName()))
            assassin.player.sendMessage(TextConfig.getMessage(TextConfigType.FAILED_ASSASSIN_TARGET, target = gameTeam!!.getDisplayName()))

            if (soldier != this) {
                soldier!!.player.sendMessage(TextConfig.getMessage(TextConfigType.SUCCESS_SOLDIER, target = gameTeam!!.getDisplayName(), source = assassin.getDisplayName()))
                oreList.forEach {
                    it.amount = (it.amount + 1) / 2
                    soldier!!.player.sendMessage(TextConfig.getMessage(TextConfigType.REWARD, ore = it.type.toString(), amount = it.amount.toString()))
                    soldier!!.player.inventory.addItem(it)
                }
            }

            soldier = null
        } else {
            this.player.damage(Double.MAX_VALUE, assassin.player)

            this.player.sendMessage(TextConfig.getMessage(TextConfigType.SUCCESS_ASSASSIN_TARGET, source = assassin.getDisplayName()))
            assassin.player.sendMessage(TextConfig.getMessage(TextConfigType.SUCCESS_ASSASSIN_TARGET, target = gameTeam!!.getDisplayName()))
            oreList.forEach {
                this.player.inventory.remove(it)
                assassin.player.inventory.addItem(it)
                assassin.player.sendMessage(TextConfig.getMessage(TextConfigType.REWARD, ore = it.type.toString(), amount = it.amount.toString()))
            }
        }
    }

    fun getDisplayName(): String {
        return "${this.gameTeam?.teamColor?.getChatColor()}[${this.gameTeam?.teamColor}]${this.player.name}${ChatColor.RESET}"
    }

    fun beCursed(curser: GamePlayer) {
        val curseType = CurseType.values().random()

        PlayerUtils.sendMessage(curser, TextConfig.getMessage(TextConfigType.SUCCESS_CURSE_SOURCE, target = this.getDisplayName(), content = curseType.toString()))
        PlayerUtils.sendMessage(this, TextConfig.getMessage(TextConfigType.SUCCESS_CURSE_TARGET, source = curser.getDisplayName(), content = curseType.toString()))


        if (curseType == CurseType.JAMMING_SKILL) {
            if (!canUseSkill()) {
                jammingSkillTime = curseType.getTime()
                return
            }
            jammingSkillTime = curseType.getTime()
            object : BukkitRunnable() {
                override fun run() {
                    jammingSkillTime--
                    if (jammingSkillTime == 0) {
                        this.cancel()
                        return
                    }
                }
            }.runTaskTimer(TacticsMiners.plugin, 20L, 20L)
        } else {
            player.addPotionEffect(curseType.getPotionEffect())
        }
    }

    fun muteChat(muter: GamePlayer) {
        PlayerUtils.sendMessage(muter, TextConfig.getMessage(TextConfigType.SUCCESS_MUTE_SOURCE, target = this.getDisplayName()))
        PlayerUtils.sendMessage(this, TextConfig.getMessage(TextConfigType.SUCCESS_MUTE_TARGET, source = muter.getDisplayName()))
        if (!canChat()) {
            jammingChatTime = SkillConfig.getData(SkillType.MUTE) as Int
            return
        }
        jammingChatTime = SkillConfig.getData(SkillType.MUTE) as Int
        object : BukkitRunnable() {
            override fun run() {
                jammingChatTime--
                if (jammingChatTime == 0) {
                    this.cancel()
                    return
                }
            }
        }.runTaskTimer(TacticsMiners.plugin, 20L, 20L)
    }

    fun sendMutingChatMessage() {
        PlayerUtils.sendMessage(this, TextConfig.getMessage(TextConfigType.CAN_NOT_CHAT, jammingChatTime.toString()))
    }

    fun shuffleInventory(shuffler: GamePlayer) {
        PlayerUtils.sendMessage(shuffler, TextConfig.getMessage(TextConfigType.SUCCESS_SHUFFLE_SOURCE, target = this.getDisplayName()))
        PlayerUtils.sendMessage(this, TextConfig.getMessage(TextConfigType.SUCCESS_SHUFFLE_TARGET, source = shuffler.getDisplayName()))

        this.player.closeInventory()
        this.player.inventory.contents = this.player.inventory.shuffled().toTypedArray()
        this.player.updateInventory()
    }

    fun delivery(performer: GamePlayer, contents: MutableList<ItemStack>) {
        PlayerUtils.sendMessage(performer, TextConfig.getMessage(TextConfigType.SUCCESS_DELIVERY_SOURCE, target = this.getDisplayName()))
        PlayerUtils.sendMessage(this, TextConfig.getMessage(TextConfigType.SUCCESS_DELIVERY_TARGET, source = performer.getDisplayName()))

        this.player.closeInventory()
        this.player.inventory.addItem(*contents.toTypedArray())
        this.player.updateInventory()
    }
}