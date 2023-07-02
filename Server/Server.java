import java.net.*;
import java.rmi.*;
import java.io.*;

//Text files are resetting, could be because we start new serverImpl, maybe we check on this class?
public class Server {
    //Setup files
    private static File reservationFile = new File("reservations.txt");
    private static File accountFile = new File("accounts.txt");

    public static void main(String args[]) {
        try {
            //Setup reservation and account files
            try {
                //System.out.println("Started setting up files.");

                //Creates files if files dont exist
                if (!reservationFile.exists()) {
                    reservationFile.createNewFile();
                }
                if (!accountFile.exists()) {
                    accountFile.createNewFile();
                }

                //System.out.println("Finished setting up files.");
            } catch (IOException e) {
                System.out.println("Error setting up files: " + e);
            }

            //Setup server implementation
            ServerImpl serverImpl = new ServerImpl();
            Naming.rebind("HotelServer", serverImpl);
        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}