import java.util.*;
import java.rmi.*;
import javax.jws.WebService;

@WebService(targetNamespace = "http://server.soap.project/", portName = "WebServiceSoapPort", serviceName = "WebServiceSoapService")
public class WebServiceSoap {
    //Server RMI to use to connect to method implementation
    private String serverIp = "192.168.132.5";
    private String serverURL = "rmi://" + serverIp + "/HotelServer";

    public String listRooms() throws Exception {
        //Startup RMI connection
        ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

        String answer = serverIntf.listRooms();
        return answer;
    }

    public String listUserReservedRooms(String username) throws Exception {
        //Startup RMI connection
        ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

        String answer = serverIntf.listUserReservedRooms(username);
        return answer;
    }

    public String checkRoomsAtDate(String initialDateReserved, String finalDateReserved) throws Exception {
        //Startup RMI connection
        ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

        String answer = serverIntf.checkRoomsAtDate(initialDateReserved, finalDateReserved);
        return answer;
    }

    public boolean reserveRoom(String roomReserved, String initialDateReserved, String finalDateReserved, String username) throws Exception  {
        //Startup RMI connection
        ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

        //Send to server what room has to be reserved, when and by whom
        boolean answer = serverIntf.reserveRoom(roomReserved, initialDateReserved, finalDateReserved, username);
        return answer;
    }

    public boolean removeReservation(String room, String initialDateReserved, String finalDateReserved, String username) throws Exception {
        //Startup RMI connection
        ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

        //Check if reservation was made and if we can remove it
        boolean answer = serverIntf.removeReservation(room, initialDateReserved, finalDateReserved, username);
        return answer;
    }

    public int loginAccount(String username, String password) throws Exception {
        //Startup RMI connection
        ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

        //Check if account exists on server (receive integer for multiple return answers)
        int answer = serverIntf.loginAccount(username, password);
        return answer;
    }

    public boolean setupAccount(String username, String password) throws Exception {
        //Startup RMI connection
        ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

        //Send account to server
        boolean answer = serverIntf.setupAccount(username, password);
        return answer;
    }
}