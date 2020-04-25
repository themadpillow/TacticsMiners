package madpillow.tacticsMiners.game.enchant

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class EnchantInventory(enchantLevel: EnchantLevel) {
    companion object {
        val inventoryNamePrefix = "${ChatColor.GREEN}${ChatColor.BOLD}エンチャント}"
        val nullResultItem = ItemStack(Material.BARRIER)

        init {
            val resultMeta = nullResultItem.itemMeta!!
            resultMeta.setDisplayName("左枠にアイテム設置")
            nullResultItem.itemMeta = resultMeta
        }

        fun resultShowItem(itemStack: ItemStack?): ItemStack {
            if (itemStack != null &&
                    CanEnchantType.list.contains(itemStack.type)) {
                val resultItem = ItemStack(itemStack.type)
                val resultMeta = resultItem.itemMeta!!
                resultMeta.setDisplayName("エンチャント結果")
                resultMeta.lore = mutableListOf("§kENCHANT")
                resultMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                resultItem.itemMeta = resultMeta
                resultItem.addUnsafeEnchantment(Enchantment.LUCK, 1)

                return resultItem
            } else {
                return nullResultItem
            }

        }
    }

    val enchantInventory = Bukkit.createInventory(null, InventoryType.FURNACE, inventoryNamePrefix + "(Level:${enchantLevel.name})")

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