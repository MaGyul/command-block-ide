package arm32x.minecraft.commandblockide.payloads;

import arm32x.minecraft.commandblockide.Packets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record UpdateFunctionCommandPayload(int index, String line) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, UpdateFunctionCommandPayload> CODEC = PacketCodec.of(UpdateFunctionCommandPayload::send, UpdateFunctionCommandPayload::new);

    public UpdateFunctionCommandPayload(PacketByteBuf buf) {
        this(buf.readVarInt(), buf.readString());
    }

    private void send(PacketByteBuf buf) {
        buf.writeVarInt(index);
        buf.writeString(line); // TODO: Make sure this doesn’t exceed size limits.
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return Packets.UPDATE_FUNCTION_COMMAND;
    }
}
