package madpillow.tacticsMiners.game.skill.soldier

enum class SoldierInventoryType {
    SELECT, PLAYER;

    fun getInventoryTitle():String{
        return when(this){
            SELECT -> "防衛先指定"
            PLAYER -> "防衛先指定（未防衛プレイヤー）"
        }
    }
}