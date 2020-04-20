package madpillow.tacticsMiners

import madpillow.tacticsMiners.team.GameTeam
import org.bukkit.entity.Player

class GamePlayer(val player: Player) {
    var gameTeam: GameTeam? = null

    fun openMissionInventory() {
        gameTeam?.missionListInventory?.inventory?.let { player.openInventory(it) }
    }
}