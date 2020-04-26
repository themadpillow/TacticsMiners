package madpillow.tacticsMiners.game.shop

import org.bukkit.Bukkit

class ShopListInventory {
    companion object {
        val inventoryName = "ショップ一覧"
        val inventory = Bukkit.createInventory(null, 9, inventoryName)

        init {
            ShopInventoryType.values().forEach {
                inventory.setItem(it.getPos(), it.getItemStack())
            }
        }
    }
}