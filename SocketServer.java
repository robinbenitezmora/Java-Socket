import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {
 ServerSocket server;
 Socket socket;
 InetAddress address;

 ArrayList<ServerThread> list = new ArrayList<ServerThread>();

 public SocketServer() {
  try {
   address = InetAddress.getByName("127.0.0.1");
   server = new ServerSocket(1234, 50, address);
   System.out.println("Waiting for Client connection");
   SocketCustomer.main(null);

   while (true) {
    socket = server.accept();
    System.out.println("Connection Established");
    ServerThread thread = new ServerThread(this);
    addThread(thread);
    thread.start();
   }
  } catch (IOException e) {
   System.out.println(e + "-> ServerSocket failed");
  }
 }

 public void addThread(ServerThread thread) {
  list.add(thread);
 }

 public void removeThread(ServerThread thread) {
  list.remove(thread);
 }

 public void broadcast(String message) {
  for (ServerThread thread : list) {
   thread.sendMessage(message);
  }
 }

 public static void main(String[] args) {
  new SocketServer();
 }
}

class ServerThread extends Thread {
 SocketServer server;
 PrintWriter pw;
 String name;

 public ServerThread(SocketServer server) {
  this.server = server;
 }

 public void sendMessage(String message) {

 }

 @Override
 public void run() {
  try {
   BufferedReader br = new BufferedReader(new InputStreamReader(server.socket.getInputStream()));
   pw = new PrintWriter(server.socket.getOutputStream(), true);
   name = br.readLine();
   server.broadcast(name + " has joined the chat");
   while (true) {
    String message = br.readLine();
    server.broadcast(name + ": " + message);
   }
  } catch (IOException e) {
   System.out.println(e + "-> ServerThread failed");
  }
 }
}
