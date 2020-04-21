package madpillow.tacticsMiners.command

import madpillow.tacticsMiners.GamePlayer
import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.team.TeamUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class TeamCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (TacticsMiners.gameManager.isGaming) {
            return false
        }
        var teamSize = TacticsMiners.gameManager.gameTeamList.size
        if (args.isNotEmpty()) {
            for (arg in args) {
                if (arg.matches("TEAM_SIZE\\([0-9]+\\)".toRegex())) {
                    val argTeamSize = arg.substring(10, args[0].length - 1).toIntOrNull()
                    if (argTeamSize == null || teamSize < argTeamSize || argTeamSize < 1) {
                        return false
                    } else {
                        teamSize = argTeamSize
                    }
                }
            }
        }
        Bukkit.getOnlinePlayers().forEach {
            TacticsMiners.gameManager.gamePlayerList.add(GamePlayer(it))
        }

        TeamUtils.removeAllEntryAtAllTeam()
        TeamUtils.divideTeam(TacticsMiners.gameManager.gamePlayerList, teamSize)

        return true
    }
}