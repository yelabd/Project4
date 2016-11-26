import com.sun.org.apache.xerces.internal.util.URI;
import com.sun.xml.internal.messaging.saaj.util.JaxmURI;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by youssefelabd on 11/20/16.
 */
public class ServerListener {

    public static HashMap<Integer, Socket> clientSocketStorage = new HashMap<Integer, Socket>();
    public static HashMap<Integer, ClientHandler> handlerStorage = new HashMap<Integer, ClientHandler>();
    public static int userID = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(5000);

        while (true) {
            Socket socket = listener.accept();

            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String clientMessage = in.readLine();

        }


    }

    public static void startClient() throws IOException {
        ServerSocket serverSocket;

        try {
            System.out.println("Creating socket");
            serverSocket = new ServerSocket(5000);

            System.out.println("Listening");
            while (true) {
                clientSocketStorage.put(userID, serverSocket.accept());
                handlerStorage.put(userID,new ClientHandler(clientSocketStorage.get(userID),userID));
                handlerStorage.get(userID).start();
                userID++;

            }
        } catch (URI.MalformedURIException e) {
            System.out.println("Could not conenct: "+ e.getMessage());
            System.exit(0);

        }


    }

}
