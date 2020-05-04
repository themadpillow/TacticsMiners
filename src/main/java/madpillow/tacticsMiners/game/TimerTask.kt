package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.PlayerUtils
import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.config.DefaultConfig
import madpillow.tacticsMiners.config.DefaultConfigType
import madpillow.tacticsMiners.config.TextConfig
import madpillow.tacticsMiners.config.TextConfigType
import madpillow.tacticsMiners.game.bossbar.BossBarUtils
import org.bukkit.scheduler.BukkitRunnable

class TimerTask {
    companion object{
        var isInterval = true
    }
    fun init() {
        object : BukkitRunnable() {
            var time = 10
            override fun run() {
                if (time == 0) {
                    cancel()
                    start()
                    return
                }
                TacticsMiners.gameManager.gamePlayerList.forEach {
                    PlayerUtils.sendTitle(it, TextConfig.getMessage(TextConfigType.START_INTERVAL_TITLE), TextConfig.getMessage(TextConfigType.START_INTERVAL_SUBTITLE, amount = time.toString()))
                }
                time--
            }
        }.runTaskTimer(TacticsMiners.plugin, 0L, 20L)
    }

    private fun start() {
        isInterval = false
        object : BukkitRunnable() {
            val origin = DefaultConfig.getData(DefaultConfigType.GAME_TIME) as Int
            var time = origin
            override fun run() {
                when (time) {
                    0 -> {
                        TacticsMiners.gameManager.stop()
                        cancel()
                        return
                    }
                    60,
                    300 -> {
                        BossBarUtils.destroy()
                        TacticsMiners.gameManager.gamePlayerList.forEach {
                            PlayerUtils.sendMessage(it, TextConfig.getMessage(TextConfigType.REMAINING_TIME, amount = (time/60).toString()))
                            PlayerUtils.sendMessage(it, TextConfig.getMessage(TextConfigType.REMOVE_BOSSBAR))
                        }
                    }
                    origin / 2 -> {
                        TacticsMiners.gameManager.gamePlayerList.forEach {
                            PlayerUtils.sendMessage(it, TextConfig.getMessage(TextConfigType.HALF_TIME))
                        }
                    }
                }

                BossBarUtils.setProgress(time)
                time--
            }
        }.runTaskTimer(TacticsMiners.plugin, 0L, 20L)
    }
}