package madpillow.tacticsMiners.config

enum class DefaultConfigType {
    WorldBorderSize;

    fun getDefaultData(): Any {
        return when (this) {
            WorldBorderSize -> 100.0
        }
    }
}