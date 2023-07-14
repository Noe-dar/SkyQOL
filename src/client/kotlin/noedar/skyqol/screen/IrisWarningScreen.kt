package noedar.skyqol.screen

import net.minecraft.client.font.MultilineText
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text
import net.minecraft.util.math.MathHelper

object IrisWarningScreen : Screen(Text.literal("⚠ Warning ⚠")) {
    private const val warningText = "You have Iris installed, this may cause graphical artifacts in the highlight of the ender node"
    private const val closeButtonText = "Close"

    private lateinit var multilineWarningText: MultilineText
    private lateinit var closeButton: ButtonWidget

    override fun init() {
        multilineWarningText = MultilineText.create(
            textRenderer, Text.literal(warningText), width - 50
        );
        closeButton = ButtonWidget.builder(Text.literal(closeButtonText)) { close() }.dimensions(width / 2 - (150 / 2), getTitleY() + 50, 150, 20).build()

        addDrawableChild(closeButton)
    }

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(context)

        context?.drawCenteredTextWithShadow(textRenderer, title, width / 2, getTitleY(), 0xFFFFFF)
        multilineWarningText.drawCenterWithShadow(context, width / 2, getTitleY() + 20)

        super.render(context, mouseX, mouseY, delta)
    }

    private fun getTitleY(): Int {
        val i = (height - this.multilineWarningText.count() * textRenderer.fontHeight) / 2
        return MathHelper.clamp((i - 20 - textRenderer.fontHeight), 10, 80)
    }
}