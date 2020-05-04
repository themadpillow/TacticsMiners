package madpillow.tacticsMiners.game.delivery

import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.menu.MenuType
import org.bukkit.Bukkit

class DeliveryInventory {
    private val inventory = Bukkit.createInventory(null, 9, MenuType.DELIVERY.getName())

    fun openInventory(gamePlayer: GamePlayer) {
        gamePlayer.player.openInventory(inventory)
    }
}