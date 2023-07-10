package noedar.skyqol

import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.minecraft.command.CommandSource
import noedar.skyqol.argument.LocationIdArgumentType
import noedar.skyqol.mixins.ScoreboardDisplayHook

object SkyQolModClient : ClientModInitializer {
    override fun onInitializeClient() {
        ScoreboardDisplayHook.register(literal<CommandSource>("warp").then(argument("location_id", LocationIdArgumentType)))

        ClientPlayConnectionEvents.JOIN.register { _, _, _ -> run {
            ScoreboardDisplayHook.isRegistered = false
        } }
    }
}