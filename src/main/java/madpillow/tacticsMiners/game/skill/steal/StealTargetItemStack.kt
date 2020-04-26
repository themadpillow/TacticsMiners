package madpillow.tacticsMiners.game.skill.steal

import madpillow.tacticsMiners.game.skill.Skill
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.inventory.ItemStack

class StealTargetItemStack(val skill: Skill, gameTeam: GameTeam) : ItemStack() {
    init {
        this.type = gameTeam.teamColor.getColoredWool().type
        this.itemMeta = gameTeam.teamColor.getColoredWool().itemMeta
    }
}