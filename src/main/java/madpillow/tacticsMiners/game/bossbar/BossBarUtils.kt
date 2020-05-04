package madpillow.tacticsMiners.game.bossbar

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.config.DefaultConfig
import madpillow.tacticsMiners.config.DefaultConfigType
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player

class BossBarUtils {
    companion object {
        private var firstBossBar: BossBar? = null
        private var secondBossBar: BossBar? = null
        fun createBossBar() {
            val titlePair = getTitle()
            firstBossBar = Bukkit.createBossBar(titlePair.first, BarColor.GREEN, BarStyle.SEGMENTED_10)
            if (titlePair.second != null) {
                secondBossBar = Bukkit.createBossBar(titlePair.second, BarColor.GREEN, BarStyle.SEGMENTED_10)
            }

            TacticsMiners.gameManager.gamePlayerList.forEach {
                setBossBar(it.player)
            }
        }

        fun reloadTitle() {
            firstBossBar ?: return

            val titlePair = getTitle()
            firstBossBar!!.setTitle(titlePair.first)
            secondBossBar?.setTitle(titlePair.second)
        }

        fun setProgress(now: Int) {
            firstBossBar ?: return

            val progress = (now / DefaultConfig.getData(DefaultConfigType.GAME_TIME) as Int).toDouble()
            firstBossBar!!.progress = progress
            secondBossBar?.progress = progress
        }

        fun destroy() {
            firstBossBar ?: return

            firstBossBar?.removeAll()
            secondBossBar?.removeAll()
            firstBossBar = null
            secondBossBar = null
        }

        private fun getTitle(): Pair<String, String?> {
            val stringList = mutableListOf<String>()
            TacticsMiners.gameManager.gameTeamList.forEach {
                stringList.add("${it.getDisplayName()} >> ${it.point}pt")
            }

            val firstString = stringList.subList(0, if (stringList.size > 4) 4 else stringList.size).joinToString(" || ")
            var secondString: String? = null
            if (stringList.size > 4) {
                secondString = stringList.subList(4, stringList.size).joinToString(" || ")
            }

            return firstString to secondString
        }

        fun setBossBar(player: Player) {
            firstBossBar?.addPlayer(player)
            secondBossBar?.addPlayer(player)
        }
    }
}