package madpillow.tacticsMiners.game.skill.steal

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.skill.Skill
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.Bukkit

class StealInventory(val gamePlayer: GamePlayer, skill: Skill) {
    val inventory = Bukkit.createInventory(null, 9, SkillType.STEAL.getName())

    init {
        TacticsMiners.gameManager.gameTeamList
                .filter { it != this.gamePlayer.gameTeam }
                .forEach {
                    val targetItemStack = it.getAttachSkillColoredWool(skill)
                    inventory.addItem(targetItemStack)
                }
    }

    fun openInventory() {
        gamePlayer.player.openInventory(inventory)
    }
}