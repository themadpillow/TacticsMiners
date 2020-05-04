package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.ListenerManager
import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.config.DefaultConfig
import madpillow.tacticsMiners.config.DefaultConfigType
import madpillow.tacticsMiners.game.bossbar.BossBarUtils
import madpillow.tacticsMiners.game.menu.MenuItem
import madpillow.tacticsMiners.game.scoreboard.ScoreBoardUtils
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import kotlin.random.Random

class GameManager {
    val gameWorld = Bukkit.getWorlds()[0]!!
    val gameTeamList = mutableListOf<GameTeam>()
    val gamePlayerList = mutableListOf<GamePlayer>()
    var isGaming = false
    private lateinit var listenerManager: ListenerManager

    init {
        loadGameWorld()
    }

    fun start() {
        isGaming = true
        gamePlayerList.forEach {
            it.player.teleport(gameWorld.spawnLocation)
            it.player.inventory.setItem(8, MenuItem())

            ScoreBoardUtils.createScoreBoard(it)
        }
        gameWorld.worldBorder.size = DefaultConfig.getData(DefaultConfigType.WORLD_BORDER_SIZE) as Double
        BossBarUtils.createBossBar()
        TimerTask().init()

        listenerManager = ListenerManager()
    }

    fun stop() {
        isGaming = false
        BossBarUtils.destroy()
        gamePlayerList.forEach {
            it.player.closeInventory()
            it.player.teleport(gameWorld.spawnLocation)
        }

        Bukkit.broadcastMessage("[DEBUG]")
        gameTeamList.forEach {
            Bukkit.broadcastMessage("${it.getDisplayName()} >> ${it.point}pt")
        }

        TacticsMiners.plugin.reload()
    }

    private fun loadGameWorld() {
        gameWorld.isAutoSave = false
        gameWorld.pvp = false
        resetSpawnLocation()
    }

    fun resetSpawnLocation() {
        gameWorld.loadedChunks.forEach { gameWorld.unloadChunk(it) }
        var location = Location(gameWorld, Random.nextDouble(10000.0), 100.0, Random.nextDouble(10000.0))
        while (location.block.type != Material.AIR) {
            location = location.add(0.0, -1.0, 0.0)
        }

        gameWorld.spawnLocation = location.add(0.0, 1.0, 0.0)
    }

    fun divideTeam() {
        var iterator = 0

        gamePlayerList.shuffled().forEach { gamePlayer ->
            TacticsMiners.gameManager.gameTeamList[iterator].joinTeam(gamePlayer)
            if (iterator < TacticsMiners.gameManager.gameTeamList.size) {
                iterator++
            } else {
                iterator = 0
            }
        }

        TacticsMiners.gameManager.gameTeamList.filter { it.players.isEmpty() }.forEach {
            TacticsMiners.gameManager.gameTeamList.remove(it)
        }
    }

    fun getGamePlayerAtPlayer(player: Player): GamePlayer? {
        return gamePlayerList.firstOrNull { it.player == player }
    }

    fun getHardGamePlayerAtPlayer(player: Player): GamePlayer? {
        return gamePlayerList.firstOrNull { it.player.name == player.name }
    }

    fun getGameTeamAtList(player: Player): GameTeam? {
        val gamePlayer = getGamePlayerAtPlayer(player)
        return gamePlayer?.gameTeam
    }
}