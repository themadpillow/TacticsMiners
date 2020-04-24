package madpillow.tacticsMiners.game

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class GameListener : Listener {
    @EventHandler
    fun onDeathEvent(e : PlayerDeathEvent){
    }
}