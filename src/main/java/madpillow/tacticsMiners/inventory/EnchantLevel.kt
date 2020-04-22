package madpillow.tacticsMiners.inventory

import org.bukkit.enchantments.Enchantment

enum class EnchantLevel {
    ONE, TWO, THREE, FOUR, FIVE;

    fun getEnchantments(): MutableMap<Enchantment, Int> {
        val map = mutableMapOf<Enchantment, Int>()
        when (this) {
            ONE -> {
                map[Enchantment.DURABILITY] = 1
                map[Enchantment.DIG_SPEED] = 1
                map[Enchantment.LUCK] = 1
            }
            else -> throw RuntimeException("不明なEnchantLevel")
        }

        return map
    }
}