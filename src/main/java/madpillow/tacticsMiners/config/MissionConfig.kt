package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.mission.Mission
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Material

class MissionConfig {
    companion object {
        private val config = CustomConfig(TacticsMiners.plugin, "MissionList.yml")
        private val missionList = mutableListOf<Mission>()

        fun init() {
            val configuration = config.getConfig()
            if (configuration.getKeys(false).isEmpty()) {
                createSampleMission()
            }

            configuration.getKeys(false).forEach {
                val section = configuration.getConfigurationSection(it)!!
                val mission = Mission(null, it, section.getStringList("Lore"), Material.valueOf(section.getString("Material")!!.toUpperCase()), section.getInt("NeedAmount"))
                missionList.add(mission)
            }
        }

        fun getMissionList(gameTeam: GameTeam): MutableList<Mission> {
            val copyMissionList = missionList.toMutableList()
            copyMissionList.forEach { it.holderTeam = gameTeam }
            return copyMissionList
        }

        private fun createSampleMission() {
            val configuration = config.getConfig()
            val mission = Mission(null, "サンプルMISSION", mutableListOf<String>("説明1", "説明2"), Material.DIAMOND, 64)
            val missionSection = configuration.createSection(mission.title)
            missionSection.set("Lore", mission.lore)
            missionSection.set("Material", mission.material.toString())
            missionSection.set("NeedAmount", mission.needAmount)

            config.saveConfig()
        }
    }
}