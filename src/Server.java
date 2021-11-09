import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)){
            while (true){
                try(Socket client = serverSocket.accept()){
                    InputStreamReader isr = new InputStreamReader(client.getInputStream());
                    BufferedReader br = new BufferedReader(isr);

                    // String request = br.readLine();
                    // System.out.println(request);

                    // reading complete requests
                    StringBuilder request = new StringBuilder();
                    String line = br.readLine();

                    while (!line.isBlank()){
                        request.append(line).append("\r\n");
                        line = br.readLine();
                    }
                    // System.out.println(request);

                    // routes
                    String resource = request.toString().split("\n")[0].split(" ")[1];

                    if(resource.equals("/") || resource.equals("/home")){
                        OutputStream response = client.getOutputStream();
                        response.write(("HTTP/1.1 200 OK\r\n").getBytes());
                        response.write(("\r\n").getBytes());
                        response.write(("Hello, world!").getBytes());
                        response.flush();
                    }
                    else if(resource.equals("/image")){
                        OutputStream response = client.getOutputStream();
                        response.write(("HTTP/1.1 200 OK\r\n").getBytes());
                        response.write(("\r\n").getBytes());
                        FileInputStream image = new FileInputStream("/home/thusara/Desktop/screenshot.png");
                        response.write(image.readAllBytes());
                        response.flush();
                    }
                    else{
                        OutputStream response = client.getOutputStream();
                        response.write(("HTTP/1.1 200 OK\r\n").getBytes());
                        response.write(("\r\n").getBytes());
                        response.write(("page not found!").getBytes());
                        response.flush();
                    }
                    client.close();
                }


            }
        } catch (Exception e){
            System.err.println(e);
        }

    }
}
