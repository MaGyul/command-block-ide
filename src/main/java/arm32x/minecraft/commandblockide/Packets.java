package arm32x.minecraft.commandblockide;

import arm32x.minecraft.commandblockide.payloads.ApplyFunctionPayload;
import arm32x.minecraft.commandblockide.payloads.EditFunctionPayload;
import arm32x.minecraft.commandblockide.payloads.UpdateFunctionCommandPayload;
import net.minecraft.network.packet.CustomPayload;

import static net.minecraft.network.packet.CustomPayload.Id;

public final class Packets {
	// Namespace
	private static final String NAMESPACE = "commandblockide";

	// Client to Server
	public static final Id<ApplyFunctionPayload> APPLY_FUNCTION = CustomPayload.id(NAMESPACE + ":apply_function");

	// Server to Client
	public static final Id<EditFunctionPayload> EDIT_FUNCTION = CustomPayload.id(NAMESPACE + ":edit_function");
	public static final Id<UpdateFunctionCommandPayload> UPDATE_FUNCTION_COMMAND = CustomPayload.id(NAMESPACE + ":update_function_command");
}
