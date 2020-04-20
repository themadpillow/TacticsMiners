package madpillow.tacticsMiners.team

import madpillow.tacticsMiners.mission.Mission
import madpillow.tacticsMiners.mission.MissionListInventory
import org.bukkit.scoreboard.Team

class GameTeam(val team: Team) {
    val missionMap = HashMap<String, Mission>()
    val missionListInventory : MissionListInventory
    init {
        // TODO createMissionMap
        missionListInventory = MissionListInventory(missionMap)
    }
    fun getMission(missionName: String): Mission? {
        return missionMap[missionName]
    }
}