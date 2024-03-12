import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public Client(){
        try{
            System.out.println("sending request to server");
            socket=new Socket("192.168.77.32",7778);
            System.out.println("connection done");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void startReading(){
        //thread -read karke  deta rahenga
        Runnable r1=()->{

            System.out.println("reader started..");
            try{
            while(true){

                    String msg=br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Server terminated the chat");
                        socket.close();
                       break;
                    }

                    System.out.println("Server :"+msg);
                }
            }catch (Exception ex){
              //  ex.printStackTrace();
                System.out.println("connection closed");
            }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
        //thread  - data user lega and then send karenga clint tak
        Runnable r2=()->{
            System.out.println("writer started..");
            try{
                 while( !socket.isClosed()){

                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();

                    out.println(content);
                    out.flush();

                if(content.equals("exit")){
                    socket.close();
                    break;
                }

                }
                System.out.println("connection is closed");
            }catch( Exception ex){
                //  ex.printStackTrace();

            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is client ready to start...");
        new Client();
    }
}
