package cookiedragon.luchadora.mixin.mixins.netty;

import cookiedragon.luchadora.util.ChatUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import net.minecraft.network.NettyCompressionDecoder;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * @author cookiedragon234 22/Dec/2019
 */
@Mixin(NettyCompressionDecoder.class)
public class MixinNettyCompressionDecoder
{
	@Final
	@Shadow
	private Inflater inflater;
	@Shadow
	private int threshold;
	
	/**
	 * @author cookiedragon234
	 */
	@Overwrite
	protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws DataFormatException, Exception
	{
		if (p_decode_2_.readableBytes() != 0)
		{
			PacketBuffer packetbuffer = new PacketBuffer(p_decode_2_);
			int i = packetbuffer.readVarInt();
			
			if (i == 0)
			{
				p_decode_3_.add(packetbuffer.readBytes(packetbuffer.readableBytes()));
			}
			else
			{
				if (i < this.threshold)
				{
					//throw new DecoderException("Badly compressed packet - size of " + i + " is below server threshold of " + this.threshold);
					ChatUtils.sendMessage("Badly compressed packet - size of " + i + " is below server threshold of " + this.threshold);
				}
				
				if (i > 2097152)
				{
					//throw new DecoderException("Badly compressed packet - size of " + i + " is larger than protocol maximum of " + 2097152);
					ChatUtils.sendMessage("Badly compressed packet - size of " + i + " is larger than protocol maximum of " + 2097152);
				}
				
				byte[] abyte = new byte[packetbuffer.readableBytes()];
				packetbuffer.readBytes(abyte);
				this.inflater.setInput(abyte);
				byte[] abyte1 = new byte[i];
				this.inflater.inflate(abyte1);
				p_decode_3_.add(Unpooled.wrappedBuffer(abyte1));
				this.inflater.reset();
			}
		}
	}
}
