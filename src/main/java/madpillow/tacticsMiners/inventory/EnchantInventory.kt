package madpillow.tacticsMiners.inventory

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

class EnchantInventory(private val enchantLevel: EnchantLevel) {
    companion object {
        val nullResultItem = ItemStack(Material.BARRIER)

        init {
            val resultMeta = nullResultItem.itemMeta!!
            resultMeta.setDisplayName("左枠にアイテム設置")
            nullResultItem.itemMeta = resultMeta
        }
    }

    val enchantInventory = Bukkit.createInventory(null, InventoryType.FURNACE, "${ChatColor.GREEN}§lエンチャント(Level:${enchantLevel.name})")

    init {
        val bottomItem = ItemStack(Material.PAPER)
        val bottomMeta = bottomItem.itemMeta!!
        bottomMeta.setDisplayName("以下からランダムで付与されます")
        enchantLevel.getEnchantments().forEach { (enchant, level) ->
            bottomMeta.addEnchant(enchant, level, true)
        }
        bottomItem.itemMeta = bottomMeta
        enchantInventory.setItem(1, bottomItem)
        enchantInventory.setItem(2, nullResultItem)
    }
}