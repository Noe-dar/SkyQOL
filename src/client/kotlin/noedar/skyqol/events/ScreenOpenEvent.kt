package noedar.skyqol.events

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.item.ItemStack
import net.minecraft.text.Text


fun interface ScreenOpenEvent {
    companion object {
        val EVENT = EventFactory.createArrayBacked(ScreenOpenEvent::class.java) { listeners ->
            ScreenOpenEvent { title, contents ->
                for(listener in listeners) {
                    listener.onScreenOpen(title, contents)
                }
            }
        }!!
    }

    fun onScreenOpen(title: Text, contents: List<ItemStack>)
}