package madpillow.tacticsMiners.game.skill.soldier

import madpillow.tacticsMiners.InventoryUtils
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.skill.Skill
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class SoldierInventory(private val holder: GamePlayer, val skill: Skill) {
    private val selectInventory = Bukkit.createInventory(holder.player, 9, SoldierInventoryType.SELECT.getInventoryTitle())
    private val playerInventory = Bukkit.createInventory(holder.player, InventoryUtils.getInventorySize(holder.gameTeam!!.players.size, 1), SoldierInventoryType.PLAYER.getInventoryTitle())

    init {
        val gameTeam = holder.gameTeam!!
        val (key, data, value) = skill.getPersistentData()
        val teamDefenceItem = if (gameTeam.soldier == null) {
            val teamItem = gameTeam.getColoredWool()
            val teamItemMeta = teamItem.itemMeta!!
            teamItemMeta.setDisplayName("強奪カードからチームを守る")
            teamItemMeta.persistentDataContainer.set(key, data, value)
            teamItem.itemMeta = teamItemMeta

            teamItem
        } else {
            val alreadyDefenceItem = ItemStack(Material.BARRIER)
            val meta = alreadyDefenceItem.itemMeta!!
            meta.setDisplayName("既に騎士カードが使用されています")
            meta.persistentDataContainer.set(key, data, value)
            alreadyDefenceItem.itemMeta = meta

            alreadyDefenceItem
        }

        val playerDefenceItem = ItemStack(Material.PLAYER_HEAD)
        val playerDefenceMeta = playerDefenceItem.itemMeta as SkullMeta
        playerDefenceMeta.owningPlayer = holder.player
        playerDefenceMeta.setDisplayName("暗殺カードからプレイヤーを守る")
        playerDefenceMeta.persistentDataContainer.set(key, data, value)
        playerDefenceItem.itemMeta = playerDefenceMeta


        selectInventory.setItem(2, teamDefenceItem)
        selectInventory.setItem(6, playerDefenceItem)

        holder.soldierInventory = this
    }

    fun openSelectInventory() {
        holder.player.openInventory(selectInventory)
    }

    fun openPlayerInventory() {
        val (key, data, value) = skill.getPersistentData()
        holder.gameTeam!!.players.filter { it != holder }.filter { it.soldier != null }.forEach {
            val skullItem = ItemStack(Material.PLAYER_HEAD)
            val skullMeta = skullItem.itemMeta as SkullMeta
            skullMeta.owningPlayer = it.player
            skullMeta.setDisplayName("${it.player.name}を防衛する")
            skullMeta.persistentDataContainer.set(key, data, value)
            skullItem.itemMeta = skullMeta

            playerInventory.addItem()
        }

        holder.player.openInventory(playerInventory)
    }
}