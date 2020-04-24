package madpillow.tacticsMiners

import madpillow.tacticsMiners.mission.MissionInventoryListener
import madpillow.tacticsMiners.team.GameTeam
import madpillow.tacticsMiners.team.TeamUtils
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class GameManager {
    val worldName = "Game"
    var gameWorld: World? = null
    val gameTeamList = TeamUtils.initCreateTeam()
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

    fun getGamePlayerAtPlayer(player: Player): GamePlayer? {
        return gamePlayerList.firstOrNull { it.player == player }
    }

    fun getGameTeamAtList(player: Player): GameTeam? {
        val gamePlayer = getGamePlayerAtPlayer(player)
        return gamePlayer?.gameTeam
    }
}