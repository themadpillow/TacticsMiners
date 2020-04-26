package madpillow.tacticsMiners.game.skill.soldier

import madpillow.tacticsMiners.InventoryUtils
import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class SoldierInventory(private val holder: GamePlayer) {
    private val selectInventory = Bukkit.createInventory(holder.player, 9, SoldierInventoryType.SELECT.getInventoryTitle())
    private val playerInventory = Bukkit.createInventory(holder.player, InventoryUtils.getInventorySize(holder.gameTeam!!.players.size, 1), SoldierInventoryType.PLAYER.getInventoryTitle())

    init {
        val playerDefenceItem = ItemStack(Material.PLAYER_HEAD)
        val playerDefenceMeta = playerDefenceItem.itemMeta as SkullMeta
        playerDefenceMeta.owningPlayer = holder.player
        playerDefenceItem.itemMeta = playerDefenceMeta

        selectInventory.setItem(6, playerDefenceItem)

        holder.gameTeam!!.players.filter { it != holder }.filter { !it.hasSoldier }.forEach {
            val skullItem = ItemStack(Material.PLAYER_HEAD)
            val skullMeta = skullItem.itemMeta as SkullMeta
            playerDefenceMeta.owningPlayer = it.player
            skullMeta.setDisplayName("${it.player.name}を防衛する")
            skullItem.itemMeta = skullMeta

            playerInventory.addItem()
        }
    }

    private fun openSelectInventory() {
        val gameTeam = holder.gameTeam!!
        val teamDefenceItem = if (gameTeam.hasSoldier) {
            val teamItem = gameTeam.teamColor.getColoredWool()
            val teamItemMeta = teamItem.itemMeta!!
            teamItemMeta.lore = mutableListOf("強奪カードからチームを守る")
            teamItem.itemMeta = teamItemMeta

            teamItem
        } else {
            val alreadyDefenceItem = ItemStack(Material.BARRIER)
            val meta = alreadyDefenceItem.itemMeta!!
            meta.setDisplayName("既に騎士カードが使用されています")
            alreadyDefenceItem.itemMeta = meta

            alreadyDefenceItem
        }
        selectInventory.setItem(2, teamDefenceItem)

        holder.player.openInventory(selectInventory)
    }

    private fun openPlayerInventory() {
        holder.player.openInventory(playerInventory)
    }

    fun openInventory(soldierInventoryType: SoldierInventoryType) {
        when (soldierInventoryType) {
            SoldierInventoryType.SELECT -> openSelectInventory()
            SoldierInventoryType.PLAYER -> openPlayerInventory()
        }
    }
}