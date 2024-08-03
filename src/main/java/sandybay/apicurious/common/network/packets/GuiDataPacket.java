package sandybay.apicurious.common.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.ApiaryError;
import sandybay.apicurious.common.menu.ApiaryMenu;

import java.util.List;

public class GuiDataPacket implements CustomPacketPayload
{
  public static final Type<GuiDataPacket> TYPE = new Type<>(Apicurious.createResourceLocation("gui_data"));

  private final List<ApiaryError> errors;

  public GuiDataPacket(List<ApiaryError> errors)
  {
    this.errors = errors;
  }

  public static GuiDataPacket read(FriendlyByteBuf buf)
  {
    int count = buf.readInt();
    List<ApiaryError> errors = Lists.newArrayList();
    for (int i = 0; i < count; i++)
    {
      int rawError = buf.readInt();
      ApiaryError error = ApiaryError.values()[rawError];
      errors.add(error);
    }
    return new GuiDataPacket(errors);
  }

  @OnlyIn(Dist.CLIENT)
  private static void handleClientSide(GuiDataPacket pkt, IPayloadContext ctx)
  {
    Player player = Minecraft.getInstance().player;
    if (player != null && player.containerMenu instanceof ApiaryMenu apiaryMenu)
    {
      apiaryMenu.receiveGuiData(pkt.errors);
    }
  }

  public void write(FriendlyByteBuf buf)
  {
    buf.writeInt(this.errors.size());
    for (ApiaryError error : this.errors)
    {
      buf.writeInt(error.ordinal());
    }
  }

  @Override
  public @NotNull Type<? extends CustomPacketPayload> type()
  {
    return TYPE;
  }

  public static class Handler implements IPayloadHandler<GuiDataPacket>
  {

    @Override
    public void handle(GuiDataPacket payload, IPayloadContext context)
    {
      if (context.flow() != PacketFlow.CLIENTBOUND) return;
      context.enqueueWork(() -> handleClientSide(payload, context));
    }
  }
}
