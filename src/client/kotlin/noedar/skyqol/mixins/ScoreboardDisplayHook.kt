package noedar.skyqol.mixins

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import com.mojang.brigadier.tree.RootCommandNode
import net.minecraft.command.CommandSource
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

object ScoreboardDisplayHook {
    private val commands = mutableListOf<LiteralArgumentBuilder<CommandSource>>()
    var isRegistered = false
    fun registerCommands(commandDispatcher: CommandDispatcher<CommandSource>) {
        isRegistered = true
        for(command in commands) {
            commandDispatcher.root.remove(command.literal)
            commandDispatcher.register(command)
        }
    }

    fun register(command: LiteralArgumentBuilder<CommandSource>) {
        commands.add(command)
    }
}

private fun <S> RootCommandNode<S>.remove(command: String) {
    val literalField = CommandNode::class.declaredMemberProperties.first { it.name == "literals" }
    val childrenField = CommandNode::class.declaredMemberProperties.first { it.name == "children" }
    val argumentsField = CommandNode::class.declaredMemberProperties.first { it.name == "arguments" }

    literalField.isAccessible = true
    childrenField.isAccessible = true
    argumentsField.isAccessible = true

    val child = this.getChild(command)
    if(child != null) {
        val mutableMap = when(child) {
            is LiteralCommandNode<*> -> literalField.get(this)
            is ArgumentCommandNode<*, *> -> argumentsField.get(this)
            else -> throw Exception("reached unreachable code")
        } as MutableMap<*, *>

        mutableMap.remove(command, child)

        (childrenField.get(this) as MutableMap<*, *>).remove(command, child)
    }
}
