package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.WorldUtils
import madpillow.tacticsMiners.game.mission.MissionInventoryListener
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class GameManager {
    val worldName = "Game"
    var gameWorld: World? = null
    val gameTeamList = mutableListOf<GameTeam>()
    val gamePlayerList = mutableListOf<GamePlayer>()
    var isGaming = false

    fun start() {
        isGaming = true
        // TODO anything

        Bukkit.getPluginManager().registerEvents(MissionInventoryListener(), TacticsMiners.plugin)
    }

    fun stop() {
        //TODO anything

        isGaming = false
    }

    fun reCreateGameWorld() {
        object : BukkitRunnable() {
            override fun run() {
                gameWorld = WorldUtils.reCreateWorld(worldName)
            }
        }.runTaskLater(TacticsMiners.plugin, 0L)
    }

    fun divideTeam(gamePlayerList: MutableList<GamePlayer>, teamSize: Int?) {
        var iterator = 0
        gamePlayerList.forEach { gamePlayer ->
            TacticsMiners.gameManager.gameTeamList[iterator].joinTeam(gamePlayer)
            if (iterator < teamSize ?: TacticsMiners.gameManager.gameTeamList.size) {
                iterator++
            } else {
                iterator = 0
            }
        }
    }

    fun getGamePlayerAtPlayer(player: Player): GamePlayer? {
        return gamePlayerList.firstOrNull { it.player == player }
    }

    fun getGameTeamAtList(player: Player): GameTeam? {
        val gamePlayer = getGamePlayerAtPlayer(player)
        return gamePlayer?.gameTeam
    }
}