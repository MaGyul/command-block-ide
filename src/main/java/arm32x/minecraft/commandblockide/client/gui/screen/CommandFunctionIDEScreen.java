package arm32x.minecraft.commandblockide.client.gui.screen;

import arm32x.minecraft.commandblockide.client.gui.editor.CommandEditor;
import arm32x.minecraft.commandblockide.client.gui.editor.CommandFunctionEditor;
import arm32x.minecraft.commandblockide.payloads.ApplyFunctionPayload;
import arm32x.minecraft.commandblockide.util.PacketSplitter;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public final class CommandFunctionIDEScreen extends CommandIDEScreen<CommandFunctionEditor> {
	private final Identifier functionId;
	private final int startingLineCount;

	public CommandFunctionIDEScreen(Identifier functionId, int lineCount) {
		this.functionId = functionId;
		this.startingLineCount = lineCount;
	}

	@Override
	protected void firstInit() {
		for (int index = 0; index < startingLineCount; index++) {
			CommandFunctionEditor editor = new CommandFunctionEditor(this, textRenderer, 8, 20 * index + 8, width - 16, 16, index);
			if (index == 0) {
				setFocusedEditor(editor);
			}
			addEditor(editor);
		}

		statusText = Text.literal(functionId.toString()).formatted(Formatting.GRAY).asOrderedText();

		super.firstInit();
	}

	public void update(int index, String command) {
		var editor = editors.get(index);
		editor.update(functionId, command);
		setLoaded(true);
		if (getFocused() == editor) {
			setFocusedEditor(editor);
		}
	}

	@Override
	public void save() {
		PacketByteBuf buf = PacketByteBufs.create();
		PacketSplitter.writeHeader(buf);
		buf.writeIdentifier(functionId);
		buf.writeVarInt(editors.size());
		for (CommandEditor editor : editors) {
			buf.writeString(editor.getSingleLineCommand(), Integer.MAX_VALUE >> 2);
			if (editor instanceof CommandFunctionEditor functionEditor) {
				functionEditor.saveMultilineCommand(functionId);
			}
		}
		PacketSplitter.updateChunkCount(buf);

		PacketSplitter splitter = new PacketSplitter(buf);
		for (ByteBuf splitBuf : splitter) {
			ClientPlayNetworking.send(new ApplyFunctionPayload(splitBuf));
		}

		super.save();
	}
}
