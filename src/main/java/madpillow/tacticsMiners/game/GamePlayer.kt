package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.game.skill.SkillInventory
import madpillow.tacticsMiners.game.skill.soldier.SoldierInventory
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.entity.Player

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
}