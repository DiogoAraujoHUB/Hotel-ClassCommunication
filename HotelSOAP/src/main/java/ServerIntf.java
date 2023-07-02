import java.rmi.*;

public interface ServerIntf extends Remote {
    String listRooms() throws RemoteException;

    String listUserReservedRooms(String username) throws RemoteException;

    boolean reserveRoom(String roomReserved, String initialDateReserved, String finalDateReserved, String username) throws RemoteException;

    String checkRoomsAtDate(String initialDateReserved, String finalDateReserved) throws RemoteException;

    boolean removeReservation(String room, String initialDateReserved, String finalDateReserved, String username) throws RemoteException;

    int loginAccount(String username, String password) throws RemoteException;

    boolean setupAccount(String username, String password) throws RemoteException;
}