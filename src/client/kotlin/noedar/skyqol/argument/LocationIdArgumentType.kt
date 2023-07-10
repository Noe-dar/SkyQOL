package noedar.skyqol.argument

import net.minecraft.command.argument.EnumArgumentType
import net.minecraft.util.StringIdentifiable

enum class LocationId(private val inner: String) : StringIdentifiable {
    HOME("home"),
    HUB("hub");

    override fun asString(): String {
        return inner
    }

    companion object {
        val CODEC = StringIdentifiable.createCodec { values() }!!
    }
}

object LocationIdArgumentType : EnumArgumentType<LocationId>(LocationId.CODEC,  { LocationId.values() })