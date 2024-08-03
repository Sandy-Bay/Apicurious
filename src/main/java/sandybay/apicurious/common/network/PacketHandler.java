package sandybay.apicurious.common.network;

import net.minecraft.client.gui.Gui;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.network.packets.GuiDataPacket;

public class PacketHandler
{
  private static final String PROTOCOL_VERSION = Integer.toString(1);

  public static void init(IEventBus bus) {
    bus.addListener(PacketHandler::registerPackets);
  }

  public static void registerPackets(RegisterPayloadHandlersEvent event) {
    PayloadRegistrar registrar = event.registrar(Apicurious.MODID).versioned(PROTOCOL_VERSION);
    // Serverbound

    // Clientbound
    registrar.playToClient(
            GuiDataPacket.TYPE,
            StreamCodec.of((buf, pkt) -> pkt.write(buf), GuiDataPacket::read),
            new GuiDataPacket.Handler()
    );

    // Bidirectional
  }

  public static void sendToServer(CustomPacketPayload pkt) {
    PacketDistributor.sendToServer(pkt);
  }

  public static void sendTo(CustomPacketPayload pkt, ServerPlayer player) {
    PacketDistributor.sendToPlayer(player, pkt);
  }

}
