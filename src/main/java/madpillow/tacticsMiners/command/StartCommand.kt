package madpillow.tacticsMiners.command

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StartCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (TacticsMiners.gameManager.isGaming) {
            sender.sendMessage("既にゲーム中です")
            return true
        } else if (TacticsMiners.gameManager.gameTeamList.isEmpty()) {
            sender.sendMessage("先にチームを設定してください")
            sender.sendMessage("/team <Options...>")
            return true
        }

        TacticsMiners.plugin.checkConfig(sender)
        TacticsMiners.gameManager.start()
        return true
    }
}