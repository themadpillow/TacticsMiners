package madpillow.tacticsMiners.game.enchant

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

enum class EnchantLevel(val level: Int) {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    companion object{
        private fun getPersistentKeyAndData(): Pair<NamespacedKey, PersistentDataType<String, String>> {
            return NamespacedKey(TacticsMiners.plugin, "ENCHANT_LEVEL") to PersistentDataType.STRING
        }

        fun getPersistentValue(itemMeta: ItemMeta): String? {
            val (key, data) = getPersistentKeyAndData()
            return itemMeta.persistentDataContainer.get(key, data)
        }
    }

    private fun getEnchantments(): MutableMap<Enchantment, Int> {
        val map = mutableMapOf<Enchantment, Int>()
        when (this) {
            ONE -> {
                map[Enchantment.DURABILITY] = 1
            }
            TWO -> {
                map[Enchantment.DURABILITY] = 1
            }
            THREE -> {
                map[Enchantment.DURABILITY] = 1
            }
            FOUR -> {
                map[Enchantment.DURABILITY] = 1
            }
            FIVE -> {
                map[Enchantment.DURABILITY] = 1
            }
        }

        return map
    }

    fun getEnchantmentsItem(): ItemStack {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta!!
        meta.setDisplayName("以下からランダムで付与されます")
        val (key, data) = getPersistentKeyAndData()
        meta.persistentDataContainer.set(key, data, this.name)
        item.itemMeta = meta
        this.getEnchantments().forEach { (enchant, level) ->
            item.addUnsafeEnchantment(enchant, level)
        }

        return item
    }
}