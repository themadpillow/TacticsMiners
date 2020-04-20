package madpillow.tacticsMiners

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryType

class GameListener : Listener {
    @EventHandler
    fun onDeathEvent(e : PlayerDeathEvent){
    }
}