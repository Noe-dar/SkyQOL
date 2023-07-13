package noedar.skyqol.mixins

import net.minecraft.item.ItemStack

object PickonimbusHook {
    private const val MAX_DAMAGE = 5000
    private fun getPickonimbusDamage(itemStack: ItemStack): Int? {
        val durability = itemStack.nbt?.getCompound("ExtraAttributes")?.getInt("pickonimbus_durability")
        return if (durability != null) MAX_DAMAGE - durability else null
    }

    fun isPickonimbus(itemStack: ItemStack): Boolean {
        return itemStack.nbt?.getCompound("ExtraAttributes")?.getString("id") == "PICKONIMBUS"
    }

    fun isItemBarVisible(itemStack: ItemStack): Boolean {
        return isPickonimbus(itemStack)
    }

    fun getDamage(itemStack: ItemStack): Int? {
        return getPickonimbusDamage(itemStack)
    }

    fun getMaxDamage(itemStack: ItemStack, prevMaxDamage: Int): Int {
        if (!isPickonimbus(itemStack)) return prevMaxDamage
        return MAX_DAMAGE
    }
}