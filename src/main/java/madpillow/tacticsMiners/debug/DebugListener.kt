package madpillow.tacticsMiners.debug

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import java.util.logging.Logger

class DebugListener : Listener {
    @EventHandler
    fun openInv(e : InventoryOpenEvent){
        println(e.inventory.type)
    }
}