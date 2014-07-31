package in.parapengu.craftbot.protocol.stream;

import in.parapengu.craftbot.bot.BotHandler;
import in.parapengu.craftbot.event.EventManager;
import in.parapengu.craftbot.event.packet.ReceivePacketEvent;
import in.parapengu.craftbot.event.packet.SendPacketEvent;
import in.parapengu.craftbot.logging.Logger;
import in.parapengu.craftbot.protocol.Packet;
import in.parapengu.craftbot.protocol.State;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public abstract class PacketStream {

	private Map<State, Map<Integer, Class<? extends Packet>>> packets;
	private Socket socket;
	private PacketOutputStream output;
	private PacketInputStream input;

	public PacketStream(Map<State, Map<Integer, Class<? extends Packet>>> packets, Socket socket, PacketOutputStream output, PacketInputStream input) {
		this.packets = packets;
		this.socket = socket;
		this.output = output;
		this.input = input;
	}

	public abstract State getState();

	public Socket getSocket() {
		return socket;
	}

	public PacketOutputStream getOutput() {
		return output;
	}

	public void setOutput(PacketOutputStream output) {
		this.output = output;
	}

	public PacketInputStream getInput() {
		return input;
	}

	public void setInput(PacketInputStream input) {
		this.input = input;
	}

	public void sendPacket(PacketOutputStream packet) throws IOException {
		output.sendPacket(packet);
		getLogger().debug("Sent Packet: " + packet.toString());
	}

	public void sendPacket(Packet packet) {
		SendPacketEvent event = getManager().call(new SendPacketEvent(packet));
		if(event.isCancelled()) {
			return;
		}

		try {
			output.sendPacket(packet);
			getLogger().debug("Sent Packet: " + packet.toString());
		} catch(IOException ex) {
			BotHandler.getHandler().getLogger().log("Could not send Packet (" + packet.getClass().getSimpleName() + "):  ", ex);
		}
	}

	public abstract Logger getLogger();

	public abstract EventManager getManager();

	public PacketStream start() {
		while(input != null && socket.isConnected()) {
			try {
				int length = input.readVarInt();
				getLogger().debug("Read length: " + length);
				int id = input.readVarInt();
				getLogger().debug("Read id: " + id);

				Class<? extends Packet> clazz = packets.get(getState()).get(id);
				Packet packet = clazz.newInstance();
				packet.build(input);
				handle(packet);
			} catch(Exception ex) {
				getLogger().log("Packet error! ", ex);
				try {
					socket.close();
					setInput(null);
				} catch(Exception ex2) {
					getLogger().log("Packet error! ", ex2);
				}
			}
		}

		return this;
	}

	public void handle(Packet packet) {
		getManager().call(new ReceivePacketEvent(packet));
	}

	public void close() {
		try {
			output.close();
			input.close();
			socket.close();
		} catch(IOException ex) {
			getLogger().log("Could not close connection to " + socket.getInetAddress().getHostAddress() + ": ", ex);
		}
		setInput(null);
	}

}
