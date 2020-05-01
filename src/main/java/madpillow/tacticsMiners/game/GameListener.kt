package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.PlayerUtils
import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class GameListener : Listener {
    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.player)
                ?: return
        if (!gamePlayer.canChat()) {
            gamePlayer.sendMutingChatMessage()
            return
        }

        val gameTeam = gamePlayer.gameTeam ?: return
        e.isCancelled = true
        gameTeam.players.filter { it.canChat() }.forEach {
            PlayerUtils.sendMessage(it, "<${e.player}> ${e.message}")
        }
    }
}