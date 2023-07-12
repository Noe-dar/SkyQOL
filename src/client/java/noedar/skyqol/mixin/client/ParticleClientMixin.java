package noedar.skyqol.mixin.client;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import noedar.skyqol.events.ParticleSpawnCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ParticleClientMixin {
    @Inject(method = "onParticle(Lnet/minecraft/network/packet/s2c/play/ParticleS2CPacket;)V", at = @At("RETURN"))
    private void onParticle(ParticleS2CPacket packet, CallbackInfo ci) {
        ParticleSpawnCallback.Companion.getEVENT().invoker().onParticleSpawn(packet);
    }
}
