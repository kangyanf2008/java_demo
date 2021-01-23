package kyf.server_socket_channel;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface ServerHandlerBase {
    void handleAccept(SelectionKey selectionKey) throws IOException;
    String handleRead(SelectionKey selectionKey) throws IOException;
    void handleWrite() throws IOException;
}