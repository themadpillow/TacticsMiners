package madpillow.tacticsMiners.team

import jdk.internal.jline.internal.Nullable
import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.Bukkit
import org.bukkit.scoreboard.Team

class TeamUtils {
    companion object {
        fun initCreateTeam(): List<Team> {
            val teamList = ArrayList<Team>()
            TeamColor.values().forEach { teamColor ->
                val scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard
                val team = scoreboard.registerNewTeam(teamColor.toString())
                team.color = teamColor.getChatColor()
                team.prefix = "" + teamColor.getChatColor()
                team.setAllowFriendlyFire(false)

                teamList.add(team)
            }

            return teamList
        }

        fun divideTeam(teamSize: Int?) {
            var iterator = 0
            Bukkit.getOnlinePlayers().forEach { player ->
                TacticsMiners.gameManager.teamList[iterator].addEntry(player.name)
                if (iterator < teamSize ?: TacticsMiners.gameManager.teamList.size) {
                    iterator++
                } else {
                    iterator = 0
                }
            }
        }

        fun removeAllEntryAtAllTeam() {
            TacticsMiners.gameManager.teamList.forEach { removeAllEntry(it) }
        }

        private fun removeAllEntry(team: Team) {
            team.entries.forEach { entity -> team.removeEntry(entity) }
        }
    }
}