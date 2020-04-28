package madpillow.tacticsMiners.game.skill.spy

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.skill.Skill
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.Bukkit

class SpyInventory(val gamePlayer: GamePlayer, skill: Skill) {
    private val inventory = Bukkit.createInventory(null, 9, SkillType.SPY.getName())

    init {
        TacticsMiners.gameManager.gameTeamList.filter { it != this.gamePlayer.gameTeam }
                .forEach {
                    val targetItemStack = it.getAttachSkillColoredWool(skill)
                    inventory.addItem(targetItemStack)
                }
    }

    fun openInventory() {
        gamePlayer.player.openInventory(inventory)
    }
}