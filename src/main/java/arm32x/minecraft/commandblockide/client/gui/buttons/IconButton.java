package arm32x.minecraft.commandblockide.client.gui.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class IconButton extends PressableWidget {
	public IconButton(int x, int y, int width, int height) {
		super(x, y, width, height, LiteralText.EMPTY);
	}

	@Override
	public final Text getMessage() {
		return LiteralText.EMPTY;
	}

	@Override
	protected abstract MutableText getNarrationMessage();

	protected abstract Identifier getTexture();

	/**
	 * Whether the standard Minecraft button background will be rendered. This
	 * does not affect the clickable area or the tooltip.
	 */
	public boolean drawsBackground() {
		return false;
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		boolean drawsBackground = drawsBackground();
		if (drawsBackground) {
			super.renderButton(matrices, mouseX, mouseY, delta);
		}

		RenderSystem.setShaderTexture(0, getTexture());

		if (drawsBackground) {
			float brightness = active ? 1.0f : (float)0xA0 / 0xFF;
			RenderSystem.setShaderColor(brightness / 4, brightness / 4, brightness / 4, alpha);
			drawTexture(matrices, x + 1, y + 1, 0, 0, width, height, width, height);
			RenderSystem.setShaderColor(brightness, brightness, brightness, alpha);
			drawTexture(matrices, x, y, 0, 0, width, height, width, height);
		} else {
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			if (active) {
				RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 0.25f * alpha);
				drawTexture(matrices, x + 1, y + 1, 0, 0, width, height, width, height);
			}
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, active ? alpha : 0.5f * alpha);
			drawTexture(matrices, x, y, 0, 0, width, height, width, height);
		}

		if (isHovered()) {
			renderTooltip(matrices, mouseX, mouseY);
		}
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
	}
}
