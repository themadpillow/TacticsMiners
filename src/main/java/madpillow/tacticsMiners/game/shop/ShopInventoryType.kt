package madpillow.tacticsMiners.game.shop

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.config.ShopConfig
import madpillow.tacticsMiners.game.enchant.EnchantLevel
import madpillow.tacticsMiners.game.skill.Skill
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

enum class ShopInventoryType {
    FOOD, TOOL, ENCHANTMENT, SKILL;

    companion object {
        fun getSkillTypeKeyAndPersistentData(): Pair<NamespacedKey, PersistentDataType<String, String>> {
            return NamespacedKey(TacticsMiners.plugin, "SKILL_TYPE") to PersistentDataType.STRING
        }

        fun getSkillLevelKeyAndPersistentData(): Pair<NamespacedKey, PersistentDataType<Int, Int>> {
            return NamespacedKey(TacticsMiners.plugin, "LEVEL") to PersistentDataType.INTEGER
        }

        fun getSkillTypePersistentValue(itemMeta: ItemMeta): SkillType? {
            val (key, data) = getSkillTypeKeyAndPersistentData()
            return itemMeta.persistentDataContainer.get(key, data)?.let { SkillType.valueOf(it) }
        }

        fun getLevelPersistentValue(itemMeta: ItemMeta): Int? {
            val (key, data) = getSkillLevelKeyAndPersistentData()
            return itemMeta.persistentDataContainer.get(key, data)
        }
    }

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

    private fun getMaterial(): Material {
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

    fun getDefaultRecipes(): MutableMap<*, MutableList<Material>> {
        return when (this) {
            FOOD -> mutableMapOf(Material.APPLE to mutableListOf(Material.COAL, Material.DIAMOND),
                    Material.BREAD to mutableListOf(Material.COAL, Material.DIAMOND))
            TOOL -> mutableMapOf(Material.TORCH to mutableListOf(Material.COAL, Material.DIAMOND))
            ENCHANTMENT -> {
                val recipes = mutableMapOf<EnchantLevel, MutableList<Material>>()
                EnchantLevel.values().forEach {
                    recipes[it] = mutableListOf(Material.COAL, Material.DIAMOND)
                }
                return recipes
            }
            SKILL -> {
                val recipes = mutableMapOf<Skill, MutableList<Material>>()
                SkillType.values().forEach {
                    for (level in 1..it.maxLevel) {
                        val skill = Skill(it, level)
                        recipes[skill] = mutableListOf(Material.COAL, Material.DIAMOND)
                    }
                }
                return recipes
            }
        }
    }

    fun getRecipes(): MutableMap<ItemStack, MutableList<ItemStack>> {
        return ShopConfig.getData(this)
    }
}