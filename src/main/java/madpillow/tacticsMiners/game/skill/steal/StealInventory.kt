package madpillow.tacticsMiners.game.skill.steal

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.Skill
import madpillow.tacticsMiners.game.skill.SkillType
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Bukkit

class StealInventory(val gameTeam: GameTeam, skill: Skill) {
    val inventory = Bukkit.createInventory(null, 9, SkillType.STEAL.getName())

    init {
        TacticsMiners.gameManager.gameTeamList
                .filter { gameTeam -> gameTeam != gameTeam }
                .forEach {
                    val targetItemStack = StealTargetItemStack(skill, it)
                    inventory.addItem(targetItemStack)
                }
    }
}