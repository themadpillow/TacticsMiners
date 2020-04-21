package madpillow.tacticsMiners.team

import madpillow.tacticsMiners.config.MissionConfig
import madpillow.tacticsMiners.mission.Mission
import madpillow.tacticsMiners.mission.MissionListInventory
import org.bukkit.scoreboard.Team

class GameTeam(val team: Team) {
    val missionList = MissionConfig.getMissionList()
    val missionListInventory: MissionListInventory

    init {
        // TODO createMissionMap
        missionListInventory = MissionListInventory(missionList)
    }

    fun getMission(missionName: String): Mission? {
        return missionList.firstOrNull { mission -> mission.title.contentEquals(missionName) }
    }
}