package madpillow.tacticsMiners.config

enum class DefaultConfigType {
    GAME_TIME,
    WORLD_BORDER_SIZE;

    fun getDefaultData(): Any {
        return when (this) {
            GAME_TIME -> 360
            WORLD_BORDER_SIZE -> 300.0
        }
    }
}