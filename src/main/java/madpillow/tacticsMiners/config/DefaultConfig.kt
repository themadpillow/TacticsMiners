package madpillow.tacticsMiners.config

enum class DefaultConfig {
    WorldBorderSize;

    fun getDefaultData(): Any {
        return when (this) {
            WorldBorderSize -> 100.0
        }
    }
}