package noedar.skyqol.mixin.client;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import noedar.skyqol.mixins.PickonimbusHook;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemClientMixin {
    @Mutable
    @Shadow @Final private int maxDamage;

    @Inject(method = "isItemBarVisible(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
    private void isItemBarVisible(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(PickonimbusHook.INSTANCE.isItemBarVisible(stack));
    }

    @Inject(method = "getItemBarStep(Lnet/minecraft/item/ItemStack;)I", at = @At("HEAD"))
    private void getItemBarStep(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        maxDamage = PickonimbusHook.INSTANCE.getMaxDamage(stack, maxDamage);
    }

    @Inject(method = "getItemBarColor(Lnet/minecraft/item/ItemStack;)I", at = @At("HEAD"))
    private void getItemBarColor(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        maxDamage = PickonimbusHook.INSTANCE.getMaxDamage(stack, maxDamage);
    }
}
