package madpillow.tacticsMiners.game.shop

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.Skill
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class SkillShopInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != ShopInventoryType.SKILL.getName()) {
            return
        }

        if (e.clickedInventory != e.view.topInventory) {
            return
        }

        if (e.slot != 2) {
            return
        }

        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player)
                ?: return
        val currentItem = e.currentItem ?: return
        val meta = currentItem.itemMeta ?: return
        val skillType = ShopInventoryType.getSkillTypePersistentValue(meta)
                ?: return
        val level = ShopInventoryType.getLevelPersistentValue(meta) ?: return
        object : BukkitRunnable() {
            override fun run() {
                if (e.cursor?.itemMeta?.displayName == meta.displayName) {
                    e.whoClicked.openInventory.cursor = ItemStack(Material.AIR)
                    val skill = Skill(skillType, level)
                    gamePlayer.skillInventory.addSkill(skill)
                } else {
                    val itemPos = e.whoClicked.inventory.filterNotNull().indexOfFirst { it.itemMeta?.displayName == meta.displayName }
                    val amount = e.whoClicked.inventory.getItem(itemPos)!!.amount
                    for (i in 0 until amount) {
                        val skill = Skill(skillType, level)
                        gamePlayer.skillInventory.addSkill(skill)
                    }
                    e.whoClicked.inventory.setItem(itemPos, ItemStack(Material.AIR))
                }

                (e.whoClicked as Player).updateInventory()
                (e.whoClicked as Player).playSound(e.whoClicked.location, Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F)
            }
        }.runTaskLater(TacticsMiners.plugin, 0L)
    }
}