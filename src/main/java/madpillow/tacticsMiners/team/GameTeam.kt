package madpillow.tacticsMiners.team

import madpillow.tacticsMiners.GamePlayer
import madpillow.tacticsMiners.config.MissionConfig
import madpillow.tacticsMiners.mission.Mission
import madpillow.tacticsMiners.mission.MissionListInventory
import org.bukkit.entity.Player

class GameTeam {
    val players = mutableListOf<GamePlayer>()
    val missionList = MissionConfig.getMissionList()
    val missionListInventory: MissionListInventory

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