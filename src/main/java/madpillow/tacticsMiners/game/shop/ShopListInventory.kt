package madpillow.tacticsMiners.game.shop

import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.Bukkit

class ShopListInventory {
    companion object {
        const val inventoryName = "ショップ一覧"
        val inventory = Bukkit.createInventory(null, 9, inventoryName)

        init {
            ShopInventoryType.values().forEach {
                inventory.setItem(it.getPos(), it.getItemStack())
            }
        }

        fun openInventory(gamePlayer: GamePlayer) {
            gamePlayer.player.openInventory(inventory)
        }
    }
}