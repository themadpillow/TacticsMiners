package madpillow.tacticsMiners.game.skill.steal

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.Skill
import madpillow.tacticsMiners.game.skill.SkillType
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class StealInventory(val gameTeam: GameTeam, skill: Skill) {
    val inventory = Bukkit.createInventory(null, 9, SkillType.STEAL.getName())

    init {
        TacticsMiners.gameManager.gameTeamList
                .filter { gameTeam -> gameTeam != gameTeam }
                .forEach {
                    val targetItemStack = ItemStack(it.teamColor.getColoredWool())
                    val itemMeta = targetItemStack.itemMeta!!
                    itemMeta.persistentDataContainer.set(NamespacedKey(TacticsMiners.plugin, "SKILL"), PersistentDataType.INTEGER, skill.hashCode())
                    targetItemStack.itemMeta = itemMeta
                    inventory.addItem(targetItemStack)
                }
    }
}