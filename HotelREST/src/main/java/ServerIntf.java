import java.rmi.*;

public interface ServerIntf extends Remote {
    String listRooms() throws Exception;

    String listUserReservedRooms(String username) throws Exception;

    boolean reserveRoom(String roomReserved, String initialDateReserved, String finalDateReserved, String username) throws Exception;

    String checkRoomsAtDate(String initialDateReserved, String finalDateReserved) throws Exception;

    boolean removeReservation(String room, String initialDateReserved, String finalDateReserved, String username) throws Exception;

    int loginAccount(String username, String password) throws Exception;

    boolean setupAccount(String username, String password) throws Exception;
}