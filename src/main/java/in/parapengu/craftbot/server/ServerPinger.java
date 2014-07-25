package in.parapengu.craftbot.server;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Logger;

public class ServerPinger {

	/*
	public Map<String, String> ping(String server, int port) {
		Socket clientSocket = c.clientSocket;
		TorchGUI gui = c.gui;
		Logger netlog = c.netlog;
		try {
			clientSocket = new Socket(server, port);
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			ByteArrayDataOutput buf = ByteStreams.newDataOutput();
			Packet.writeVarInt(buf, 0);
			Packet.writeVarInt(buf, 4);
			Packet.writeString(buf, c.servername);
			buf.writeShort(port);
			Packet.writeVarInt(buf, 1);
			Packet.sendPacket(buf, out);

			buf = ByteStreams.newDataOutput();
			Packet.writeVarInt(buf, 0);
			Packet.sendPacket(buf, out);

			Packet.readVarInt(in);
			int id = Packet.readVarInt(in);

			if(id == 0) {
				String pings = Packet.getString(in);
				System.out.println("Pings: " + pings);
				JSONObject json = (JSONObject) JSONSerializer.toJSON(pings);
				JSONObject version = json.getJSONObject("version");
				String prot = version.getString("name");
				String ver = version.getString("protocol");
				JSONObject players = json.getJSONObject("players");
				String max = players.getString("max");
				String online = players.getString("online");
				String text = json.getString("description");
				if(json.containsKey("favicon")) {
					String images = json.getString("favicon").replace("data:image/png;base64,", "");
					byte[] data = Base64.decode(images);
					InputStream is = new ByteArrayInputStream(data);
					ImageIcon test = new ImageIcon(data);
					gui.favicon.setIcon(test);
					gui.repaint();
				}
				gui.addText("�5Game version: " + ver + "  " + text + " " + online + "/" + max);
			}
			out.close();
			in.close();
			clientSocket.close();

		} catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	*/

}
