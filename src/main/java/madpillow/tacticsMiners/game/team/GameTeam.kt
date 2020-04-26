package madpillow.tacticsMiners.game.team

import madpillow.tacticsMiners.config.MissionConfig
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.mission.Mission
import madpillow.tacticsMiners.game.mission.MissionListInventory
import org.bukkit.entity.Player

class GameTeam(val teamColor: TeamColor) {
    val players = mutableListOf<GamePlayer>()
    val missionList = MissionConfig.getMissionList(this)
    val missionListInventory: MissionListInventory
    var hasSoldier = false

    init {
        // TODO createMissionMap
        missionListInventory = MissionListInventory(missionList)
    }

    fun getMission(missionName: String): Mission? {
        return missionList.firstOrNull { mission -> mission.title.contentEquals(missionName) }
    }

    fun joinTeam(gamePlayer: GamePlayer): Boolean {
        if (containPlayer(gamePlayer)) {
            return false
        }

        return players.add(gamePlayer)
    }

    fun containPlayer(gamePlayer: GamePlayer): Boolean {
        val hasPlayer = players.firstOrNull {
            it == gamePlayer || it.player == gamePlayer.player
        }
        return hasPlayer != null
    }

    fun containPlayer(player: Player): Boolean {
        val hasPlayer = players.firstOrNull {
            it.player == player
        }
        return hasPlayer != null
    }
}