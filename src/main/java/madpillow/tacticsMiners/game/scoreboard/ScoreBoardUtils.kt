package madpillow.tacticsMiners.game.scoreboard

import madpillow.tacticsMiners.config.ScoreboardConfig
import madpillow.tacticsMiners.config.ScoreboardConfigType
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Bukkit
import org.bukkit.scoreboard.DisplaySlot

class ScoreBoardUtils {
    companion object {
        fun createScoreBoard(gamePlayer: GamePlayer) {
            val scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard
            gamePlayer.player.scoreboard = scoreboard

            val objective = scoreboard.getObjective(DisplaySlot.SIDEBAR)
                    ?: scoreboard.registerNewObjective("TacticsMiners", "dummy", ScoreboardConfig.getData(ScoreboardConfigType.SIDEBAR_TITLE))
            objective.displaySlot = DisplaySlot.SIDEBAR

            objective.getScore(ScoreboardConfig.getData(ScoreboardConfigType.TEAM_POINT, point = gamePlayer.gameTeam!!.point.toString())).score = 2
            objective.getScore(ScoreboardConfig.getData(ScoreboardConfigType.TEAM_SOLDIER, soldier = gamePlayer.gameTeam!!.getSoldier()?.player?.name
                    ?: ScoreboardConfig.getData(ScoreboardConfigType.EMPTY))).score = 1
            objective.getScore(ScoreboardConfig.getData(ScoreboardConfigType.OWN_SOLDIER, soldier = gamePlayer.getSoldier()?.player?.name
                    ?: ScoreboardConfig.getData(ScoreboardConfigType.EMPTY))).score = 0
        }

        fun replaceTeamSoldier(gameTeam: GameTeam, oldSoldier: GamePlayer?, soldier: GamePlayer?) {
            gameTeam.players.filter { it.player.isOnline }.forEach {
                val board = it.player.scoreboard
                val sidebar = board.getObjective(DisplaySlot.SIDEBAR)!!

                val oldSoldierName = oldSoldier?.player?.name
                        ?: ScoreboardConfig.getData(ScoreboardConfigType.EMPTY)
                val newSoldierName = soldier?.player?.name
                        ?: ScoreboardConfig.getData(ScoreboardConfigType.EMPTY)

                board.resetScores(ScoreboardConfig.getData(ScoreboardConfigType.TEAM_SOLDIER, soldier = oldSoldierName))
                sidebar.getScore(ScoreboardConfig.getData(ScoreboardConfigType.TEAM_SOLDIER, soldier = newSoldierName)).score = 1
            }
        }

        fun replacePlayerSoldier(gamePlayer: GamePlayer, oldSoldier: GamePlayer?, soldier: GamePlayer?) {
            val board = gamePlayer.player.scoreboard
            val sidebar = board.getObjective(DisplaySlot.SIDEBAR)!!

            val oldSoldierName = oldSoldier?.player?.name
                    ?: ScoreboardConfig.getData(ScoreboardConfigType.EMPTY)
            val newSoldierName = soldier?.player?.name
                    ?: ScoreboardConfig.getData(ScoreboardConfigType.EMPTY)

            board.resetScores(ScoreboardConfig.getData(ScoreboardConfigType.OWN_SOLDIER, soldier = oldSoldierName))
            sidebar.getScore(ScoreboardConfig.getData(ScoreboardConfigType.OWN_SOLDIER, soldier = newSoldierName)).score = 0

        }

        fun reloadScore(gameTeam: GameTeam, oldPoint: Int, point: Int) {
            gameTeam.players.filter { it.player.isOnline }.forEach {
                val board = it.player.scoreboard
                val sidebar = board.getObjective(DisplaySlot.SIDEBAR)!!

                board.resetScores(ScoreboardConfig.getData(ScoreboardConfigType.TEAM_POINT, point = oldPoint.toString()))
                sidebar.getScore(ScoreboardConfig.getData(ScoreboardConfigType.TEAM_POINT, point = point.toString())).score = 3
            }
        }
    }
}