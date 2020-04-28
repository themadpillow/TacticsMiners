package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.config.TextConfig
import madpillow.tacticsMiners.config.TextConfigType
import madpillow.tacticsMiners.game.skill.SkillInventory
import madpillow.tacticsMiners.game.skill.soldier.SoldierInventory
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GamePlayer(val player: Player) {
    var gameTeam: GameTeam? = null
    val skillInventory = SkillInventory(this)
    var soldierInventory: SoldierInventory? = null
    var soldier: GamePlayer? = null

    fun openMissionInventory() {
        gameTeam!!.missionListInventory.openInventory(this)
    }

    fun openSkillInventory() {
        skillInventory.openInventory()
    }

    fun openSoldierPlayerInventory() {
        soldierInventory?.openPlayerInventory()
    }

    fun assassinated(assassin: GamePlayer) {
        val oreList = mutableListOf<ItemStack>()
        this.player.inventory
                .filter { item ->
                    item != null
                            && Ore.values().firstOrNull { it.getMaterial() == item.type } != null
                }
                .forEach { oreList.add(it) }

        val sourceString = "${assassin.gameTeam?.teamColor?.getChatColor()}[${assassin.gameTeam?.teamColor}]${assassin.player.name}${ChatColor.RESET}"
        val targetString = "${gameTeam!!.teamColor.getChatColor()}[${gameTeam!!.teamColor}]${ChatColor.RESET}"
        if (this.soldier != null) {
            this.player.sendMessage(TextConfig.getMessage(TextConfigType.FAILED_ASSASSIN_TARGET, source = sourceString))
            assassin.player.sendMessage(TextConfig.getMessage(TextConfigType.FAILED_ASSASSIN_TARGET, target = targetString))

            if (soldier != this) {
                soldier!!.player.sendMessage(TextConfig.getMessage(TextConfigType.SUCCESS_SOLDIER, target = sourceString, source = sourceString))
                oreList.forEach {
                    it.amount = (it.amount + 1) / 2
                    soldier!!.player.sendMessage(TextConfig.getMessage(TextConfigType.REWARD, ore = it.type.toString(), amount = it.amount.toString()))
                    soldier!!.player.inventory.addItem(it)
                }
            }

            soldier = null
        } else {
            this.player.damage(Double.MAX_VALUE, assassin.player)

            this.player.sendMessage(TextConfig.getMessage(TextConfigType.SUCCESS_ASSASSIN_TARGET, source = sourceString))
            assassin.player.sendMessage(TextConfig.getMessage(TextConfigType.SUCCESS_ASSASSIN_TARGET, target = targetString))
            oreList.forEach {
                this.player.inventory.remove(it)
                assassin.player.inventory.addItem(it)
                assassin.player.sendMessage(TextConfig.getMessage(TextConfigType.REWARD, ore = it.type.toString(), amount = it.amount.toString()))
            }
        }
    }
}