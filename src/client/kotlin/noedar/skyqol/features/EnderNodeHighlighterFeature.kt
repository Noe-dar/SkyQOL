package noedar.skyqol.features

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.minecraft.block.Blocks
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.WorldRenderer
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import noedar.skyqol.Color
import noedar.skyqol.SkyQolModClient.CONFIG
import noedar.skyqol.equalWithError
import noedar.skyqol.events.ParticleSpawnCallback
import org.joml.Vector3d
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.abs
import kotlin.math.floor


// https://github.com/Soopyboo32/SoopyV2/blob/master/src/features/events/index.js#L767
// At least it works
object EnderNodeHighlighterFeature {
    enum class ParticleOperation {
        NORMAL, WITH_ERROR;

        fun test(value: Double, comparator: Double): Boolean {
            return when (this) {
                NORMAL -> abs(value % 1) == comparator
                WITH_ERROR -> equalWithError((value - 0.5) % 1, comparator, 0.2)
            }
        }
    }

    data class Operation(
        val x: ParticleOperation, val y: ParticleOperation, val z: ParticleOperation
    )

    data class ParticleSelector(val predicate: Vector3d, val operation: Operation)

    private val PARTICLE_POSITIONS = mapOf(
        Direction.DOWN to ParticleSelector(
            Vector3d(0.0, 0.25, 0.0), Operation(
                ParticleOperation.WITH_ERROR, ParticleOperation.NORMAL, ParticleOperation.WITH_ERROR
            )
        ), Direction.UP to ParticleSelector(
            Vector3d(0.0, 0.75, 0.0), Operation(
                ParticleOperation.WITH_ERROR, ParticleOperation.NORMAL, ParticleOperation.WITH_ERROR
            )
        ), Direction.EAST to ParticleSelector(
            Vector3d(0.25, 0.0, 0.0), Operation(
                ParticleOperation.NORMAL, ParticleOperation.WITH_ERROR, ParticleOperation.WITH_ERROR
            )
        ), Direction.WEST to ParticleSelector(
            Vector3d(0.75, 0.0, 0.0), Operation(
                ParticleOperation.NORMAL, ParticleOperation.WITH_ERROR, ParticleOperation.WITH_ERROR
            )
        ), Direction.SOUTH to ParticleSelector(
            Vector3d(0.0, 0.0, 0.25), Operation(
                ParticleOperation.WITH_ERROR, ParticleOperation.WITH_ERROR, ParticleOperation.NORMAL
            )
        ), Direction.NORTH to ParticleSelector(
            Vector3d(0.0, 0.0, 0.75), Operation(
                ParticleOperation.WITH_ERROR, ParticleOperation.WITH_ERROR, ParticleOperation.NORMAL
            )
        )
    )

    private val ENDER_NODE_BLOCKS = arrayOf(Blocks.OBSIDIAN, Blocks.END_STONE)

    private val enderNodes = ConcurrentHashMap<BlockPos, Long>()

    private val client = MinecraftClient.getInstance()

    fun init() {
        ParticleSpawnCallback.EVENT.register(EnderNodeHighlighterFeature::onParticle)

        WorldRenderEvents.AFTER_TRANSLUCENT.register(EnderNodeHighlighterFeature::onRender)
    }

    private fun onRender(worldRenderContext: WorldRenderContext) {
        val world = client.world!!


        val parsedColor = Integer.parseInt(CONFIG.enderNodeHighlightColor.replaceFirst("#", ""), 16)
        val color = Color(parsedColor)

        for ((enderNode, timestamp) in enderNodes) {
            if ((System.currentTimeMillis() - timestamp) >= 2000) {
                enderNodes.remove(enderNode)
            }

            val cameraPos = worldRenderContext.camera().pos
            val renderLayer = RenderLayer.getLines()
            val filledBoxLayer = RenderLayer.getDebugFilledBox()
            val blockState = world.getBlockState(enderNode)

            val shape = blockState.getOutlineShape(world, enderNode)
            val collisionShape = blockState.getCollisionShape(world, enderNode)
            if (collisionShape.isEmpty) return
            val boundingBox = collisionShape.boundingBox

            WorldRenderer.renderFilledBox(
                worldRenderContext.matrixStack(),
                worldRenderContext.consumers()!!.getBuffer(filledBoxLayer),
                boundingBox.minX + enderNode.x - cameraPos.x,
                boundingBox.minY + enderNode.y - cameraPos.y,
                boundingBox.minZ + enderNode.z - cameraPos.z,
                boundingBox.maxX + enderNode.x - cameraPos.x,
                boundingBox.maxY + enderNode.y - cameraPos.y,
                boundingBox.maxZ + enderNode.z - cameraPos.z,
                color.red,
                color.green,
                color.blue,
                0.4f
            )

            WorldRenderer.drawShapeOutline(
                worldRenderContext.matrixStack(),
                worldRenderContext.consumers()!!.getBuffer(renderLayer),
                shape,
                enderNode.x - cameraPos.x,
                enderNode.y - cameraPos.y,
                enderNode.z - cameraPos.z,
                color.red,
                color.green,
                color.blue,
                1.0f,
                true
            )
        }
    }

    private fun onParticle(particle: ParticleS2CPacket) {
        if (particle.parameters.type == ParticleTypes.PORTAL) {
            val blockDirection = PARTICLE_POSITIONS.filterValues {
                return@filterValues it.operation.x.test(particle.x, it.predicate.x) && it.operation.y.test(particle.y, it.predicate.y) && it.operation.z.test(particle.z, it.predicate.z)
            }.keys.first()

            val blockPos = BlockPos(floor(particle.x).toInt(), floor(particle.y).toInt(), floor(particle.z).toInt()).add(blockDirection.vector)

            if (ENDER_NODE_BLOCKS.contains(client.world!!.getBlockState(blockPos).block)) {
                enderNodes[blockPos] = System.currentTimeMillis()
            }
        }
    }
}