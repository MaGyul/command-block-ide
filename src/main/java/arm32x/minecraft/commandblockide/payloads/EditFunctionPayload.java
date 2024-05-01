package arm32x.minecraft.commandblockide.payloads;

import arm32x.minecraft.commandblockide.Packets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record EditFunctionPayload(Identifier id, int lineCount) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, EditFunctionPayload> CODEC = PacketCodec.of(EditFunctionPayload::send, EditFunctionPayload::new);

    public EditFunctionPayload(PacketByteBuf buf) {
        this(buf.readIdentifier(), buf.readVarInt());
    }

    private void send(PacketByteBuf buf) {
        buf.writeIdentifier(id);
        buf.writeVarInt(lineCount);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return Packets.EDIT_FUNCTION;
    }
}
