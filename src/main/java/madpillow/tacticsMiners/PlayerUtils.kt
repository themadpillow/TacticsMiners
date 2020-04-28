package madpillow.tacticsMiners

import madpillow.tacticsMiners.game.GamePlayer

class PlayerUtils {
    companion object {
        fun sendMessage(gamePlayer: GamePlayer, message: String) {
            gamePlayer.player.sendMessage(message)
        }
    }
}