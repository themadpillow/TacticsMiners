package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.game.skill.SkillInventory
import madpillow.tacticsMiners.game.skill.soldier.SoldierInventory
import madpillow.tacticsMiners.game.skill.soldier.SoldierInventoryType
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.entity.Player

class GamePlayer(val player: Player) {
    var gameTeam: GameTeam? = null
    val skillInventory = SkillInventory(this)
    private val soldierInventory = SoldierInventory(this)
    var hasSoldier = false

    fun openMissionInventory() {
        if (gameTeam != null) {
            player.openInventory(gameTeam!!.missionListInventory.inventory)
        }
    }

    fun openSkillInventory() {
        skillInventory.openInventory()
    }

    fun openSoldierInventory(soldierInventoryType: SoldierInventoryType) {
        soldierInventory.openInventory(soldierInventoryType)
    }
}