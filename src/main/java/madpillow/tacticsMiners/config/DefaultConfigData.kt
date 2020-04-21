package madpillow.tacticsMiners.config

enum class DefaultConfigData {
    WorldBorderSize;

    fun getDefaultData(): Any {
        return when (this) {
            WorldBorderSize -> 100.0
        }
    }
}