package in.parapengu.craftbot.protocol.stream;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import in.parapengu.commons.utils.OtherUtil;
import in.parapengu.craftbot.bot.BotHandler;
import in.parapengu.craftbot.inventory.ItemStack;
import in.parapengu.craftbot.inventory.nbt.CompressedStreamTools;
import in.parapengu.craftbot.inventory.nbt.NBTTagCompound;
import in.parapengu.craftbot.protocol.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PacketOutputStream extends DataOutputStream {

	private static final Charset UTF8 = Charset.forName("UTF-8");

	public PacketOutputStream(OutputStream out) {
		super(out);
	}

	public void writeString(String string) throws IOException {
		writeVarInt(string.length());
		write(string.getBytes(UTF8));
	}

	public void writeVarInt(int paramInt) throws IOException {
		while(true) {
			if((paramInt & 0xFFFFFF80) == 0) {
				writeByte((byte) paramInt);
				return;
			}

			writeByte((byte) (paramInt & 0x7F | 0x80));
			paramInt >>>= 7;
		}
	}

	public void writeVarInt64(long varInt) throws IOException {
		int length = 10;
		for(int i = 9; i >= 0; i--)
			if(((varInt >> (i * 7)) & (i != 9 ? 0x7F : 0x01)) == 0)
				length--;
		for(int i = 0; i < length; i++)
			writeByte((int) ((i == length - 1 ? 0x00 : 0x80) | ((varInt >> (i * 7)) & (i != 9 ? 0x7F : 0x01))));
	}

	public void writeByteArray(byte[] data) throws IOException {
		writeShort(data.length);
		write(data);
	}

	public void writeItemStack(ItemStack item) throws IOException {
		if(item != null) {
			writeShort(item.getTypeId());
			writeByte(item.getAmount());
			writeShort((short) item.getData().getData());

			writeNBTTagCompound(item.getCompound());
		} else {
			writeShort(-1);
		}
	}

	public void writeNBTTagCompound(NBTTagCompound compound) throws IOException {
		if(compound != null) {
			byte[] data = CompressedStreamTools.compress(compound);
			writeShort((short) data.length);
			write(data);
		} else {
			writeShort(-1);
		}
	}

	public void sendPacket(Packet packet) throws IOException {
		PacketOutputStream data = new PacketOutputStream(new PacketOutputArray());
		data.writeVarInt(packet.getId()); // write the packet id
		packet.send(data); // write the packet data
		sendPacket(data);
	}

	public void sendPacket(PacketOutputStream data) throws IOException {
		PacketOutputStream send = new PacketOutputStream(new PacketOutputArray()); // create a new final byte array
		PacketOutputArray dataBuf = (PacketOutputArray) data.out;
		PacketOutputArray sendBuf = (PacketOutputArray) send.out;

		send.writeVarInt(dataBuf.toByteArray().length); // write the length of the buffer
		for(byte b : dataBuf.toByteArray()) {
			send.write(b); // write the array of bytes to the final byte array
		}
		write(sendBuf.toByteArray()); // write the final array to the data output stream
		flush(); // flush the output and send it on it's way

		List<Byte> list = sendBuf.toByteList();
		BotHandler.getHandler().getLogger().debug("Wrote out bytes for " + data.getClass().getSimpleName() + ": " + OtherUtil.listToEnglishCompound(list, "", ""));
	}

}
