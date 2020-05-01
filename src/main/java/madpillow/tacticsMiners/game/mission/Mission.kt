package madpillow.tacticsMiners.game.mission

import madpillow.tacticsMiners.InventoryUtils
import madpillow.tacticsMiners.config.TextConfig
import madpillow.tacticsMiners.config.TextConfigType
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class Mission(var holderTeam: GameTeam?, val title: String, val lore: MutableList<String>, val material: Material, val needAmount: Int) {
    companion object {
        const val inventoryNamePrefix = "MISSION:"
    }

    val inventory = Bukkit.createInventory(null, InventoryUtils.getInventorySize(needAmount, material.maxStackSize), inventoryNamePrefix + title)
    var nowAmount = 0
    var isSuccess = false

    init {
        val needPos = needAmount / material.maxStackSize
        for (i in needPos until inventory.size)
            inventory.setItem(i, ItemStack(Material.BARRIER))
    }

    fun delivery(itemStack: ItemStack): ItemStack {
        if (isSuccess
                || this.material != itemStack.type) {
            return itemStack
        }

        val remain = needAmount - nowAmount
        return if (remain >= itemStack.amount) {
            inventory.addItem(itemStack)
            nowAmount += itemStack.amount

            ItemStack(Material.AIR)
        } else {
            inventory.addItem(ItemStack(material, remain))
            nowAmount = needAmount
            this.isSuccess = true

            itemStack.amount -= remain
            itemStack
        }
    }

    fun steal(source: GamePlayer) {
        val stealAmount = Random.nextInt(nowAmount) + 1
        if (this.holderTeam!!.soldier != null) {
            val sourceString = "${source.gameTeam?.teamColor?.getChatColor()}[${source.gameTeam?.teamColor}]${source.player.name}${ChatColor.RESET}"
            holderTeam!!.players.forEach { it.player.sendMessage(TextConfig.getMessage(TextConfigType.FAILED_STEAL_TARGET, source = sourceString)) }

            val targetString = "${holderTeam!!.teamColor.getChatColor()}[${holderTeam!!.teamColor}]${ChatColor.RESET}"
            source.player.sendMessage(TextConfig.getMessage(TextConfigType.FAILED_STEAL_SOURCE, target = targetString))

            val defenceAmount = (stealAmount + 1) / 2
            holderTeam!!.soldier!!.player.inventory.addItem(ItemStack(material, defenceAmount))
            holderTeam!!.soldier!!.player.sendMessage(TextConfig.getMessage(TextConfigType.SUCCESS_SOLDIER, source = sourceString))
            holderTeam!!.soldier!!.player.sendMessage(TextConfig.getMessage(TextConfigType.REWARD, ore = material.toString(), amount = defenceAmount.toString()))
            holderTeam!!.soldier = null
            return
        }



        nowAmount -= stealAmount
        val stealList = reduceInventoryItemStack(stealAmount)

        isSuccess = false
        val sourceString = "${source.gameTeam?.teamColor?.getChatColor()}[${source.gameTeam?.teamColor}]${source.player.name}${ChatColor.RESET}"
        holderTeam!!.players.forEach {
            it.player.sendMessage(
                    TextConfig.getMessage(
                            TextConfigType.SUCCESS_STEAL_TARGET,
                            source = sourceString,
                            ore = material.toString(),
                            amount = stealAmount.toString(),
                            content = title
                    )
            )
        }
        val targetString = "${holderTeam!!.teamColor.getChatColor()}[${holderTeam!!.teamColor}]${ChatColor.RESET}"
        source.player.sendMessage(
                TextConfig.getMessage(
                        TextConfigType.SUCCESS_STEAL_SOURCE,
                        target = targetString,
                        ore = material.toString(),
                        amount = stealAmount.toString()
                )
        )

        stealList.forEach { source.player.inventory.addItem(it) }
    }

    private fun reduceInventoryItemStack(amount: Int): MutableList<ItemStack> {
        var reduceAmount = amount
        if (reduceAmount > nowAmount) {
            throw RuntimeException("現在量以上のSTEALが行われました")
        }

        val reduceList = mutableListOf<ItemStack>()
        for (itemStack in inventory) {
            if (itemStack.amount < reduceAmount) {
                reduceAmount -= itemStack.amount
                reduceList.add(itemStack)
                itemStack.amount = 0
                itemStack.type = Material.AIR
            } else {
                itemStack.amount -= reduceAmount
                reduceList.add(ItemStack(material, reduceAmount))
                break
            }
        }
        nowAmount -= reduceAmount

        return reduceList
    }

    fun getItemStack(): ItemStack {
        val itemStack = ItemStack(Material.PAPER)
        val itemMeta = itemStack.itemMeta!!
        itemMeta.setDisplayName(this.title + "($nowAmount/$needAmount)")
        itemMeta.lore = this.lore
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        itemStack.itemMeta = itemMeta
        if (this.isSuccess) {
            itemStack.addUnsafeEnchantment(Enchantment.LUCK, 1)
        }

        return itemStack
    }
}