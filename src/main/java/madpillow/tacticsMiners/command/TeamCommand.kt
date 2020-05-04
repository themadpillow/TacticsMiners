package madpillow.tacticsMiners.command

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.team.GameTeam
import madpillow.tacticsMiners.game.team.TeamColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class TeamCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (TacticsMiners.gameManager.isGaming) {
            return false
        }

        var teamSize = TeamColor.values().size
        if (args.isNotEmpty()) {
            for (arg in args) {
                if (arg == "help") {
                    return false
                }
                if (arg.matches("TEAM_SIZE=[0-9]+".toRegex())) {
                    val argTeamSize = arg.substring(10, args[0].length).toIntOrNull()
                    if (argTeamSize == null || teamSize < argTeamSize || argTeamSize < 1) {
                        return false
                    } else {
                        teamSize = argTeamSize
                    }
                }
            }
        }

        TacticsMiners.gameManager.gameTeamList.clear()
        for (iterator in 0 until teamSize) {
            TacticsMiners.gameManager.gameTeamList.add(GameTeam(TeamColor.values()[iterator]))
        }

        TacticsMiners.gameManager.gamePlayerList.clear()
        Bukkit.getOnlinePlayers().forEach {
            TacticsMiners.gameManager.gamePlayerList.add(GamePlayer(it))
        }

        TacticsMiners.gameManager.divideTeam()
        sender.sendMessage("チーム振り分けを行いました")
        return true
    }
}