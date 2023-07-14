package noedar.skyqol.features

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import noedar.skyqol.screen.IrisWarningScreen

object IrisWarningFeature {
    private const val IRIS_MOD_ID = "iris"
    fun init() {
        ClientLifecycleEvents.CLIENT_STARTED.register(::onClientStarted)
    }

    private fun onClientStarted(minecraftClient: MinecraftClient) {
        if(FabricLoader.getInstance().isModLoaded(IRIS_MOD_ID)) {
            minecraftClient.setScreen(IrisWarningScreen)
        }
    }
}