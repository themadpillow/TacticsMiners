package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.mission.Mission
import org.bukkit.Material

class MissionConfig {
    companion object {
        private val config = CustomConfig(TacticsMiners.plugin, "MissionList.yml")
        private val missionList = mutableListOf<Mission>()

        fun getMissionList(): MutableList<Mission> {
            if (missionList.isEmpty()) {
                reloadConfig()
            }

            return missionList
        }

        fun reloadConfig() {
            val configuration = config.getConfig()
            if (configuration.getKeys(false).isEmpty()) {
                createSampleMission()
            }

            configuration.getKeys(false).forEach {
                val section = configuration.getConfigurationSection(it)!!
                val mission = Mission(it, section.getStringList("Lore"), Material.valueOf(section.getString("Material")!!.toUpperCase()), section.getInt("NeedAmount"))
                missionList.add(mission)
            }
        }

        private fun createSampleMission() {
            val configuration = config.getConfig()
            val mission = Mission("サンプルMISSION", mutableListOf<String>("説明1", "説明2"), Material.DIAMOND, 64)
            val missionSection = configuration.createSection(mission.title)
            missionSection.set("Lore", mission.lore)
            missionSection.set("Material", mission.material.toString())
            missionSection.set("NeedAmount", mission.needAmount)

            config.saveConfig()
        }
    }
}