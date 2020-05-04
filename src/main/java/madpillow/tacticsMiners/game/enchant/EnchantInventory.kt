package madpillow.tacticsMiners.game.enchant

import madpillow.tacticsMiners.game.GamePlayer
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
            return if (itemStack != null &&
                    CanEnchantType.list.contains(itemStack.type)) {
                val resultItem = ItemStack(itemStack.type)
                val resultMeta = resultItem.itemMeta!!
                resultMeta.setDisplayName("エンチャント結果")
                resultMeta.lore = mutableListOf("§kENCHANT")
                resultMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                resultItem.itemMeta = resultMeta
                resultItem.addUnsafeEnchantment(Enchantment.LUCK, 1)

                resultItem
            } else {
                nullResultItem
            }

        }
    }

    private val inventory = Bukkit.createInventory(null, InventoryType.FURNACE, inventoryNamePrefix + "(Level:${enchantLevel.name})")

    init {
        val bottomItem = enchantLevel.getEnchantmentsItem()
        inventory.setItem(1, bottomItem)
        inventory.setItem(2, nullResultItem)
    }

    fun openInventory(gamePlayer: GamePlayer) {
        gamePlayer.player.openInventory(inventory)
    }
}