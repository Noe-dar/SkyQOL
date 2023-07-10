package noedar.skyqol.argument

import net.minecraft.command.argument.EnumArgumentType
import net.minecraft.util.StringIdentifiable

enum class LocationId(private val inner: String) : StringIdentifiable {
    HOME("home"),
    ISLAND("island"),
    HUB("hub"),
    MUSEUM("museum"),
    SIRIUS_SHACK("da"),
    RUINS("ruins"),
    CRYPTS("crypts"),
    DUNGEON_HUB("dungeon_hub"),
    BARN("barn"),
    DESERT("desert"),
    TRAPPER_DEN("trapper"),
    BIRCH_PARK("park"),
    HOWLING_CAVE("howl"),
    JUNGLE("jungle"),
    GOLD_MINE("gold"),
    DEEP_CAVERNS("deep"),
    DWARVES("dwarves"),
    FORGE("forge"),
    CRYSTAL_HOLLOWS("crystals"),
    CRYSTAL_NUCLEUS("nucleus"),
    SPIDERS_DEN("spider"),
    TOP_OF_NEST("top"),
    ARACHNES_SANCTUARY("arachne"),
    END("end"),
    DRAGONS_NEST("drag"),
    VOID_SEPULTURE("void"),
    CRIMSON_ISLE("isle"),
    FORGOTTEN_SKULL("skull"),
    SMOLDERING_TOMB("tomb"),
    THE_WASTELAND("wasteland"),
    DRAGONTAIL("dragontail"),
    SCARLETON("scarleton"),
    GARDEN("garden"),
    JERRYS_WORKSHOP("jerrys_workshop");

    override fun asString(): String {
        return inner
    }

    companion object {
        val CODEC = StringIdentifiable.createCodec { values() }!!
    }
}

object LocationIdArgumentType : EnumArgumentType<LocationId>(LocationId.CODEC,  { LocationId.values() })