package madpillow.tacticsMiners.debug

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import java.util.logging.Logger

class DebugListener : Listener {
    @EventHandler
    fun openInv(e: InventoryOpenEvent) {
        println(e.inventory.type)
    }

    @EventHandler
    fun clickInv(e: InventoryClickEvent) {
        println(e.action)
    }
}