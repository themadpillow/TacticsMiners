package madpillow.tacticsMiners.game.menu

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class MenuItem : ItemStack(Material.MOJANG_BANNER_PATTERN) {
    init {
        val meta = this.itemMeta!!
        meta.setDisplayName(MenuInventory.inventoryName)
        this.itemMeta = meta
    }
}