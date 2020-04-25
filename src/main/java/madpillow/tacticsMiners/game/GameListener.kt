package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class GameListener : Listener {
    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        val gameTeam = TacticsMiners.gameManager.getGameTeamAtList(e.player) ?: return
        e.isCancelled = true
        gameTeam.players.forEach {
            it.player.sendMessage("<${e.player}> ${e.message}")
        }
    }
}