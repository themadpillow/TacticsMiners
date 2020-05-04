package madpillow.tacticsMiners.game.skill.soldier

import madpillow.tacticsMiners.InventoryUtils
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.skill.Skill
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class SoldierSelectPlayerInventory(val holder: GamePlayer, skill: Skill) {
    private val inventory = Bukkit.createInventory(holder.player, InventoryUtils.getInventorySize(holder.gameTeam!!.players.size, 1), SoldierInventoryType.SELECT_PLAYER.getInventoryTitle())

    init {
        val (key, data, value) = skill.getPersistentData()
        holder.gameTeam!!.players.filter { it != holder }.filterNot { it.hasSoldier() }.forEach {
            val skullItem = ItemStack(Material.PLAYER_HEAD)
            val skullMeta = skullItem.itemMeta as SkullMeta
            skullMeta.owningPlayer = it.player
            skullMeta.setDisplayName("${it.player.name}を防衛する")
            skullMeta.persistentDataContainer.set(key, data, value)
            skullItem.itemMeta = skullMeta

            inventory.addItem()
        }
    }

    fun openInventory() {
        holder.player.openInventory(inventory)
    }
}