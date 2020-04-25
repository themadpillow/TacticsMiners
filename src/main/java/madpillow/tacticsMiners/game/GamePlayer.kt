package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.game.skill.SkillInventory
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.entity.Player

class GamePlayer(val player: Player) {
    var gameTeam: GameTeam? = null
    val skillInventory = SkillInventory(player)

    fun openMissionInventory() {
        if (gameTeam != null) {
            player.openInventory(gameTeam!!.missionListInventory.inventory)
        }
    }

    fun openSkillInventory() {
        skillInventory.openInventory()
    }
}