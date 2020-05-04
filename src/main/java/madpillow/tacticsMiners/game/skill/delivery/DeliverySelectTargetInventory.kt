package madpillow.tacticsMiners.game.skill.delivery

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.skill.Skill
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class DeliverySelectTargetInventory(val gamePlayer: GamePlayer, skill: Skill) {
    private val inventory = Bukkit.createInventory(null, 54, DeliveryInventoryType.SELECT_TARGET.getInventoryTitle())

    init {
        var column = 0
        TacticsMiners.gameManager.gameTeamList.filter { it != this.gamePlayer.gameTeam }.forEach { team ->
            inventory.setItem(column, team.getColoredWool())
            var row = 1
            val (key, data, value) = skill.getPersistentData()
            team.players.forEach { player ->
                val skullItem = ItemStack(Material.PLAYER_HEAD)
                val skullMeta = skullItem.itemMeta as SkullMeta
                skullMeta.owningPlayer = player.player
                skullMeta.setDisplayName("${team.teamColor.getChatColor()}${player.player.name}に適用する")
                skullMeta.persistentDataContainer.set(key, data, value)
                skullItem.itemMeta = skullMeta
                inventory.setItem((row * 9) + column, skullItem)

                row++
            }
            column++
        }
    }

    fun openInventory() {
        gamePlayer.player.openInventory(inventory)
    }
}