package madpillow.tacticsMiners

import madpillow.tacticsMiners.game.GamePlayer

class PlayerUtils {
    companion object {
        fun sendMessage(gamePlayer: GamePlayer, message: String) {
            gamePlayer.player.sendMessage(message)
        }

        fun sendTitle(gamePlayer: GamePlayer, title: String? = null, subTitle: String? = null, fadeIn: Int = 10, stay: Int = 70, fadeOut: Int = 20) {
            gamePlayer.player.sendTitle(title, subTitle, fadeIn, stay, fadeOut)
        }
    }
}