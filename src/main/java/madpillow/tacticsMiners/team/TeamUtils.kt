package madpillow.tacticsMiners.team

import madpillow.tacticsMiners.GamePlayer
import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team

class TeamUtils {
    companion object {
        fun initCreateTeam(): List<GameTeam> {
            val teamList = ArrayList<GameTeam>()
            TeamColor.values().forEach { teamColor ->
                val scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard
                val team = scoreboard.registerNewTeam(teamColor.toString())
                team.color = teamColor.getChatColor()
                team.prefix = "" + teamColor.getChatColor()
                team.setAllowFriendlyFire(false)

                teamList.add(GameTeam(team))
            }

            return teamList
        }

        fun divideTeam(gamePlayerList: List<GamePlayer>, teamSize: Int?) {
            var iterator = 0
            gamePlayerList.forEach { gamePlayer ->
                joinTeam(TacticsMiners.gameManager.gameTeamList[iterator], gamePlayer)
                if (iterator < teamSize ?: TacticsMiners.gameManager.gameTeamList.size) {
                    iterator++
                } else {
                    iterator = 0
                }
            }
        }

        fun removeAllEntryAtAllTeam() {
            TacticsMiners.gameManager.gameTeamList.forEach { removeAllEntry(it.team) }
        }

        private fun removeAllEntry(team: Team) {
            team.entries.forEach { entity -> team.removeEntry(entity) }
        }

        fun getGamePlayerAtList(player: Player): GamePlayer? {
            return TacticsMiners.gameManager.gamePlayerList.firstOrNull { gamePlayer -> gamePlayer.player == player }
        }

        fun joinTeam(gameTeam: GameTeam, gamePlayer: GamePlayer) {
            gameTeam.team.addEntry(gamePlayer.player.name)
            gamePlayer.gameTeam = gameTeam
        }
    }
}