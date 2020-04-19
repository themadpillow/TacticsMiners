package madpillow.tacticsMiners

import madpillow.tacticsMiners.team.TeamUtils
import org.bukkit.World

class GameManager {
    val worldName = "Game"
    var gameWorld = reCreateGameWorld()
    var teamList = TeamUtils.initCreateTeam()
    var isGaming = false

    fun start() {
        isGaming = true
        // TODO anything


    }

    fun stop() {
        //TODO anything

        isGaming = false
    }

    fun reCreateGameWorld(): World {
        gameWorld = WorldUtils.reCreateWorld(worldName)
        return gameWorld
    }
}