package madpillow.tacticsMiners.game.team

import madpillow.tacticsMiners.config.MissionConfig
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.mission.Mission
import madpillow.tacticsMiners.game.mission.MissionListInventory
import madpillow.tacticsMiners.game.skill.Skill
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GameTeam(val teamColor: TeamColor) {
    val players = mutableListOf<GamePlayer>()
    val missionList = MissionConfig.getMissionList(this)
    val missionListInventory = MissionListInventory(this)
    var soldier: GamePlayer? = null


    fun getMission(missionName: String): Mission? {
        return missionList.firstOrNull { mission -> mission.title.contentEquals(missionName) }
    }

    fun joinTeam(gamePlayer: GamePlayer): Boolean {
        if (containPlayer(gamePlayer)) {
            return false
        }

        return players.add(gamePlayer)
    }

    fun containPlayer(gamePlayer: GamePlayer): Boolean {
        val hasPlayer = players.firstOrNull {
            it == gamePlayer || it.player == gamePlayer.player
        }
        return hasPlayer != null
    }

    fun containPlayer(player: Player): Boolean {
        val hasPlayer = players.firstOrNull {
            it.player == player
        }
        return hasPlayer != null
    }


    fun getColoredWool(): ItemStack {
        val itemStack = when (teamColor) {
            TeamColor.RED -> ItemStack(Material.RED_WOOL)
            TeamColor.BLUE -> ItemStack(Material.BLUE_WOOL)
            TeamColor.GREEN -> ItemStack(Material.GREEN_WOOL)
            TeamColor.YELLOW -> ItemStack(Material.YELLOW_WOOL)
            TeamColor.PURPLE -> ItemStack(Material.PURPLE_WOOL)
            TeamColor.AQUA -> ItemStack(Material.LIGHT_BLUE_WOOL)
            TeamColor.GOLD -> ItemStack(Material.ORANGE_WOOL)
            TeamColor.PINK -> ItemStack(Material.PINK_WOOL)
        }
        val itemMeta = itemStack.itemMeta!!
        itemMeta.setDisplayName("${teamColor.getChatColor()}${this}")
        itemStack.itemMeta = itemMeta

        return itemStack
    }

    fun getAttachSkillColoredWool(skill: Skill): ItemStack {
        val itemStack = this.getColoredWool()
        val itemMeta = itemStack.itemMeta!!
        val (key, type, value) = skill.getPersistentData()
        itemMeta.persistentDataContainer.set(key, type, value)
        itemStack.itemMeta = itemMeta

        return itemStack
    }

    fun getDisplayName(): String {
        return "${this.teamColor.getChatColor()}[${this.teamColor}]${ChatColor.RESET}"
    }

    override fun toString(): String {
        return teamColor.toString()
    }
}