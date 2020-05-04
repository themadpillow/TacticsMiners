package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.enchant.EnchantLevel
import madpillow.tacticsMiners.game.shop.ShopInventoryType
import madpillow.tacticsMiners.game.skill.Skill
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack

class ShopConfig {
    companion object {
        private val config = CustomConfig(TacticsMiners.plugin, "ShopList.yml")
        private val shopMap = mutableMapOf<ShopInventoryType, MutableMap<ItemStack, MutableList<ItemStack>>>()


        @Suppress("UNCHECKED_CAST")
        fun getData(shopInventoryType: ShopInventoryType): MutableMap<ItemStack, MutableList<ItemStack>> {
            return shopMap[shopInventoryType]!!
        }

        fun init() {
            ShopInventoryType.values().forEach { shopInventoryType ->
                val recipes = mutableMapOf<ItemStack, MutableList<ItemStack>>()
                val section = config.getConfig().getConfigurationSection(shopInventoryType.toString())
                        ?: createSample(shopInventoryType)

                section.getKeys(false).forEach { resultMaterialString ->
                    val resultSection = section.getConfigurationSection(resultMaterialString)!!

                    if (shopInventoryType == ShopInventoryType.SKILL) {
                        val skillType = SkillType.valueOf(resultMaterialString)
                        for (level in 1..skillType.maxLevel) {
                            val resultItem = Skill(SkillType.valueOf(resultMaterialString), level).itemStack
                            val resultMeta = resultItem.itemMeta!!
                            val (typeKey, typeData) = ShopInventoryType.getSkillTypeKeyAndPersistentData()
                            val (levelKey, levelData) = ShopInventoryType.getSkillLevelKeyAndPersistentData()
                            resultMeta.persistentDataContainer.set(typeKey, typeData, skillType.name)
                            resultMeta.persistentDataContainer.set(levelKey, levelData, level)
                            resultItem.itemMeta = resultMeta
                            resultItem.amount = resultSection.getInt("Level${level}.Amount")

                            val needSection = resultSection.getConfigurationSection("Level${level}.Need")!!
                            val needList = mutableListOf<ItemStack>()
                            needSection.getKeys(false).forEach { needKey ->
                                val needMaterial = Material.valueOf(needKey)
                                val needAmount = needSection.getInt(needKey)
                                needList.add(ItemStack(needMaterial, needAmount))
                            }

                            recipes[resultItem] = needList
                        }
                    } else {
                        val resultAmount = resultSection.getInt("Amount")
                        val resultItem = when (shopInventoryType) {
                            ShopInventoryType.ENCHANTMENT -> {
                                EnchantLevel.valueOf(resultMaterialString).getEnchantmentsItem()
                            }
                            else -> {
                                ItemStack(Material.valueOf(resultMaterialString))
                            }
                        }
                        resultItem.amount = resultAmount

                        val needSection = resultSection.getConfigurationSection("Need")!!
                        val needList = mutableListOf<ItemStack>()
                        needSection.getKeys(false).forEach {
                            val needMaterial = Material.valueOf(it)
                            val needAmount = needSection.getInt(it)
                            needList.add(ItemStack(needMaterial, needAmount))
                        }

                        recipes[resultItem] = needList
                    }
                }

                shopMap[shopInventoryType] = recipes
            }
        }

        private fun createSample(shopInventoryType: ShopInventoryType): ConfigurationSection {
            val section = config.getConfig().createSection(shopInventoryType.toString())
            val defaultRecipes = shopInventoryType.getDefaultRecipes()
            defaultRecipes.forEach { (resultItem, needList) ->
                if (resultItem is Skill) {
                    val resultSection = section.getConfigurationSection(resultItem.skillType.toString())
                            ?: section.createSection(resultItem.skillType.toString())
                    if (!resultSection.isSet("Level${resultItem.level}")) {
                        resultSection.createSection("Level${resultItem.level}")
                    }
                    resultSection.set("Level${resultItem.level}.Amount", 1)

                    val needSection = resultSection.createSection("Level${resultItem.level}.Need")
                    needList.forEach { needSection.set(it.name, 1) }
                } else {
                    val resultSection = when (resultItem) {
                        is EnchantLevel -> {
                            section.createSection(resultItem.name)
                        }
                        else -> {
                            resultItem as Material
                            section.createSection(resultItem.name)
                        }
                    }
                    resultSection.set("Amount", 1)

                    val needSection = resultSection.createSection("Need")
                    needList.forEach { needSection.set(it.name, 1) }
                }
            }
            config.saveConfig()

            return section
        }
    }
}