package madpillow.tacticsMiners.command

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CheckCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            sender.teleport(TacticsMiners.gameManager.gameWorld.spawnLocation)
            return true
        }

        return false
    }
}