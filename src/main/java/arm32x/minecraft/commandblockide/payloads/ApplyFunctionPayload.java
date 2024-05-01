package arm32x.minecraft.commandblockide.payloads;

import arm32x.minecraft.commandblockide.Packets;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record ApplyFunctionPayload(ByteBuf bytes) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, ApplyFunctionPayload> CODEC = PacketCodec.of(ApplyFunctionPayload::send, ApplyFunctionPayload::new);

    public ApplyFunctionPayload(PacketByteBuf buf) {
        this(buf.readBytes(buf.readableBytes()));
    }

    private void send(PacketByteBuf buf) {
        buf.writeBytes(bytes, bytes.readableBytes());
    }

    public PacketByteBuf toBuf() {
        return new PacketByteBuf(bytes);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return Packets.APPLY_FUNCTION;
    }
}
