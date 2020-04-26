package madpillow.tacticsMiners.game.shop

import madpillow.tacticsMiners.game.skill.Skill
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class ShopInventoryType {
    FOOD, TOOL, ENCHANTMENT, SKILL;

    fun getItemStack(): ItemStack {
        val itemStack = ItemStack(getMaterial())
        val itemMeta = itemStack.itemMeta!!
        itemMeta.setDisplayName(getName())
        itemStack.itemMeta = itemMeta

        return itemStack
    }

    fun getPos(): Int {
        return when (this) {
            FOOD -> 1
            TOOL -> 3
            ENCHANTMENT -> 5
            SKILL -> 7
        }
    }

    fun getMaterial(): Material {
        return when (this) {
            FOOD -> Material.BREAD
            TOOL -> Material.TORCH
            ENCHANTMENT -> Material.ENCHANTING_TABLE
            SKILL -> Material.NETHER_STAR
        }
    }

    fun getName(): String {
        return when (this) {
            FOOD -> "${ChatColor.GREEN}食料"
            TOOL -> "${ChatColor.GOLD}便利"
            ENCHANTMENT -> "${ChatColor.GREEN}エンチャント"
            SKILL -> "${ChatColor.LIGHT_PURPLE}スキルカード"
        }
    }

    fun getRecipes(): MutableMap<ItemStack, MutableList<ItemStack>> {
        return when (this) {
            FOOD -> mutableMapOf(
                    Pair(ItemStack(Material.BREAD), mutableListOf(ItemStack(Material.COAL), ItemStack(Material.DIAMOND))))
            SKILL -> {
                val recipe = mutableMapOf<ItemStack, MutableList<ItemStack>>()
                SkillType.values().forEach {
                    for (level in 1 until it.maxLevel) {
                        recipe[Skill(it, level)] = mutableListOf(ItemStack(Material.COAL, level))
                    }
                }

                return recipe
            }
            // TODO set recipe
            else -> throw RuntimeException("Recipesの取得に失敗")
        }
    }
}