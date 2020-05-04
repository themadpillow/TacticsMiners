package madpillow.tacticsMiners.command

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AllCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            return false
        }

        val prefix: String = "[${TacticsMiners.gameManager.getGameTeamAtList(sender as Player)?.getDisplayName() ?: run { "未所属" }}" +
                "]<${sender.name}> ${ChatColor.RESET}"
        Bukkit.broadcastMessage("$prefix${args.joinToString(separator = " ")}")

        return true
    }
}