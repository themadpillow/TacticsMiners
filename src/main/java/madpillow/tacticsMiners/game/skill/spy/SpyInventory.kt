package madpillow.tacticsMiners.game.skill.spy

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.Skill
import madpillow.tacticsMiners.game.skill.SkillType
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Bukkit

class SpyInventory(val gameTeam: GameTeam, skill: Skill) {
    private val inventory = Bukkit.createInventory(null, 9, SkillType.SPY.getName())

    init {
        TacticsMiners.gameManager.gameTeamList.filter { it != this.gameTeam }
                .forEach {
                    val targetItemStack = it.getAttachSkillColoredWool(skill)
                    inventory.addItem(targetItemStack)
                }
    }
}