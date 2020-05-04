package madpillow.tacticsMiners.game.mission

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class MissionInventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        if (!e.view.title.startsWith(Mission.inventoryNamePrefix)) {
            return
        }

        if (e.currentItem?.type == Material.BARRIER) {
            e.isCancelled = true
            return
        }
        val gamePlayer: GamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player)
                ?: return

        val mission = gamePlayer.gameTeam?.getMission(e.view.title.substring(Mission.inventoryNamePrefix.length))
                ?: return

        object : BukkitRunnable() {
            var amount = 0
            override fun run() {
                mission.inventory.filterNotNull().filter { it.type != Material.BARRIER }.forEach {
                    if (it.type != mission.material) {
                        mission.inventory.remove(it)
                        when {
                            e.cursor == null -> {
                                e.whoClicked.openInventory.cursor = it
                            }
                            e.cursor!!.type == it.type -> {
                                if (e.cursor!!.amount + it.amount <= it.maxStackSize) {
                                    e.cursor!!.amount += it.amount
                                } else {
                                    e.whoClicked.inventory.addItem(it)
                                }
                            }
                            else -> {
                                e.whoClicked.inventory.addItem(it)
                            }
                        }
                        (e.whoClicked as Player).updateInventory()
                    } else {
                        amount += it.amount
                    }
                }
                if (amount < mission.needAmount) {
                    mission.nowAmount = amount
                } else {
                    mission.success()
                    val returnAmount = amount - mission.needAmount
                    e.whoClicked.inventory.addItem(ItemStack(mission.material, returnAmount))
                }
            }
        }.runTaskLater(TacticsMiners.plugin, 0L)

    }
}