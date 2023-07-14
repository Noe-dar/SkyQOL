package noedar.skyqol.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import noedar.skyqol.SkyQolModClient.MOD_ID

@Config(name = MOD_ID)
class SkyQolConfig : ConfigData {
    @JvmField
    val enderNodeHighlightColor = "#0000AA"
}