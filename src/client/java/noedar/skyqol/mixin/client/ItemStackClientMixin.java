package noedar.skyqol.mixin.client;

import net.minecraft.item.ItemStack;
import noedar.skyqol.mixins.PickonimbusHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackClientMixin {
    @Inject(method = "getDamage()I", at = @At("HEAD"), cancellable = true)
    private void getDamage(CallbackInfoReturnable<Integer> cir) {
        if(PickonimbusHook.INSTANCE.isPickonimbus((ItemStack) (Object) this)) {
            cir.setReturnValue(PickonimbusHook.INSTANCE.getDamage((ItemStack) (Object) this));
        }
    }
}
