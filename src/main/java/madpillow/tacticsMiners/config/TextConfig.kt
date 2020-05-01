package madpillow.tacticsMiners.config

import madpillow.tacticsMiners.TacticsMiners
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

class TextConfig {
    companion object {
        private val dataMap = mutableMapOf<TextConfigType, String>()

        fun init() {
            val config = CustomConfig(TacticsMiners.plugin, "TextConfig.yml")
            val configuration = config.getConfig()

            TextConfigType.values().forEach {
                configuration.getString(it.toString())?.also { data ->
                    dataMap[it] = data
                } ?: run {
                    configuration.set(it.toString(), it.getDefaultData())
                    config.saveConfig()
                    dataMap[it] = it.getDefaultData()
                }
            }

            addConfigRuleDescribe()
        }

        private fun addConfigRuleDescribe() {
            val configFile = File(TacticsMiners.plugin.dataFolder, "TextConfig.yml")
            if (!configFile.exists()) {
                configFile.createNewFile()
            }
            val lines = configFile.readLines()

            if (lines.isEmpty() || !lines[0].startsWith("#")) {
                val fileWriter = FileWriter(configFile.path)
                val printWriter = PrintWriter(BufferedWriter(fileWriter))

                mutableListOf(
                        "#----------CONFIGルール----------#",
                        "# [TARGET] -> スキル対象者など",
                        "# [SOURCE] -> スキル発動者など",
                        "# [ORE]    -> 鉱石",
                        "# [AMOUNT] -> 鉱石数",
                        "# [CONTENT]-> ミッション名や呪い名など",
                        "# [REWARD] -> 報酬内容",
                        "#-------------------------------#",
                        ""
                ).forEach { printWriter.println(it) }
                lines.forEach { printWriter.println(it) }

                printWriter.close()
                fileWriter.close()
            }
        }


        fun getMessage(textConfigType: TextConfigType,
                       target: String = "[TARGET]",
                       source: String = "[SOURCE]",
                       ore: String = "[ORE]",
                       amount: String = "[AMOUNT]",
                       content: String = "[CONTENT]"): String {
            return dataMap[textConfigType]!!
                    .replace("[REWARD]", dataMap[TextConfigType.REWARD]!!)
                    .replace("[TARGET]", target)
                    .replace("[SOURCE]", source)
                    .replace("[ORE]", ore)
                    .replace("[AMOUNT]", amount)
                    .replace("[CONTENT]", content)
        }
    }
}