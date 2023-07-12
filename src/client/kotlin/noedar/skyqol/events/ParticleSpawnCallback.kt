package noedar.skyqol.events

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket

fun interface ParticleSpawnCallback {
    companion object {
        val EVENT = EventFactory.createArrayBacked(ParticleSpawnCallback::class.java) { listeners ->
            ParticleSpawnCallback { packet ->
                for (listener in listeners) {
                    listener.onParticleSpawn(packet)
                }
            }
        }!!
    }

    fun onParticleSpawn(packet: ParticleS2CPacket)
}