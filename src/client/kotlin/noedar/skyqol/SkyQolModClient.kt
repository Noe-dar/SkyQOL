package noedar.skyqol

import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.minecraft.client.gui.DrawContext
import net.minecraft.command.CommandSource
import net.minecraft.item.Items
import noedar.skyqol.argument.LocationIdArgumentType
import noedar.skyqol.mixins.ScoreboardDisplayHook

object SkyQolModClient : ClientModInitializer {
    override fun onInitializeClient() {
        ScoreboardDisplayHook.register(literal<CommandSource>("warp").then(argument("location_id", LocationIdArgumentType)))
        ScoreboardDisplayHook.registerEmptyCommands(
            arrayListOf(
                "claimdarewards",
                "evacuate",
                "deathcount",
                "playtime",
                "pt",
                "togglemusic",
                "togglerds",
                "wiki",
                "wikiinhand",
                "wikihand",
                "wikithis",
                "acceptinvite",
                "sbkickall",
                "setguestlocation",
                "setguestspawn",
                "guestlocation",
                "setspawnlocation",
                "spawnlocation",
                "setspawn",
                "sethome",
                "showextrastats",
                "barnskinsmenu",
                "barnskins",
                "cropmilestonesmenu",
                "cropmilestones",
                "cropupgradesmenu",
                "cropupgrades",
                "deskmenu",
                "desk",
                "gardenlevelsmenu",
                "gardenlevels",
                "visitormilestonesmenu",
                "visitormilestones",
                "clearstash",
                "pickupstash",
                "undograndarchitect",
                "undozap",
                "viewstash",
                "enterthecrystalhollows",
                "hub",
                "garry",
                "is",
                "savethejerrys",
                "warpforge",
                "enchantmentsguide",
                "enchantments",
                "eg",
                "seacreatureguide",
                "fishingbosses",
                "seacreature",
                "scg",
                "activeeffects",
                "effects",
                "potions",
                "bestiary",
                "be",
                "calendar",
                "events",
                "craftedgenerators",
                "craftingtable",
                "craft",
                "equipment",
                "islandsettings",
                "profiles",
                "sacks",
                "sax",
                "sbbingo",
                "bingo",
                "skyblocklevels",
                "skyblocklevel",
                "sblevels",
                "sblevel",
                "levels",
                "level",
                "storage",
                "st",
                "er",
                "trades",
                "viewcollectionmenu",
                "collectionlog",
                "collections",
                "collection",
                "viewcommissionmilestones",
                "viewcraftingtable",
                "craftingtable",
                "craftingmenu",
                "craft",
                "viewenderchest",
                "enderchest",
                "echest",
                "ec",
                "vieweventrewards",
                "viewhotm",
                "hotm",
                "viewmuseumrewards",
                "viewpetsmenu",
                "viewpets",
                "petsmenu",
                "petmenu",
                "pets",
                "pet",
                "viewprofilemenu",
                "viewquestlog",
                "questlog",
                "quests",
                "viewrecipebook",
                "recipemenu",
                "recipebook",
                "recipes",
                "viewsbmenu",
                "sbmenu",
                "viewsettings",
                "opengamesettings",
                "viewskillmenu",
                "skillsmenu",
                "skillmenu",
                "skills",
                "wardrobe",
                "wd",
                "accessorybag",
                "accessories",
                "accessory",
                "accs",
                "abag",
                "ab",
                "anvilmenu",
                "anvil",
                "av",
                "arrowsbag",
                "arrowbag",
                "quiver",
                "arrows",
                "quiv",
                "enchantmenttablemenu",
                "enchantingtablemenu",
                "enchantmenttable",
                "enchantingtable",
                "etable",
                "et",
                "fishingbag",
                "fishbag",
                "fishingb",
                "fbag",
                "fb",
                "potionsbag",
                "potionbag",
                "potbag",
                "pbag",
                "pots",
                "pb",
                "thehex",
                "hecks",
                "hex",
                "coopcheck",
                "coopmanage",
                "coopid",
                "coopparty",
                "togglecoopchat",
                "co"
            )
        )

        ClientPlayConnectionEvents.JOIN.register { _, _, _ ->
            run {
                ScoreboardDisplayHook.isRegistered = false
            }
        }

    }
}