import java.util.*;
import java.rmi.*;
import javax.jws.WebService;
import javax.ws.rs.*;

@Consumes("application/json")
@Produces("application/json")
public class WebServiceRest {	

	//Server RMI to use to connect to method implementation
	private String serverIp = "192.168.132.5";
	private String serverURL = "rmi://" + serverIp + "/HotelServer";
	
	@GET
	@Path("/listRooms")
	public String listRooms() throws Exception {
		//Startup RMI connection
		ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

		String answer = serverIntf.listRooms();
		return answer;
	}

	@POST
	@Path("/listUserReservedRooms")
	public String listUserReservedRooms(Client user) throws Exception {
		//Remove user info from client class
		String[] userInfo = user.getUserInfo().split(" ");
		String username = userInfo[0];
		String password = userInfo[1];
		
		//Startup RMI connection
		ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

		String answer = serverIntf.listUserReservedRooms(username);
		return answer;
	}
	
	@POST
	@Path("/checkRoomsAtDate")
	public String checkRoomsAtDate(Reservation reservation) throws Exception {
		//Remove reservation info from reservation class
		String[] reservationInfo = reservation.getReservationInfo().split(" ");
		String room = reservationInfo[0];
		String initialDateReserved = reservationInfo[1];
		String finalDateReserved = reservationInfo[2];
		String username = reservationInfo[3];
		
		//Startup RMI connection
		ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

		String answer = serverIntf.checkRoomsAtDate(initialDateReserved, finalDateReserved);
		return answer;
	}

	@POST
	@Path("/reserveRoom")
	public String reserveRoom(Reservation reservation) throws Exception {
		//Remove reservation info from reservation class
		String[] reservationInfo = reservation.getReservationInfo().split(" ");
		String room = reservationInfo[0];
		String initialDateReserved = reservationInfo[1];
		String finalDateReserved = reservationInfo[2];
		String username = reservationInfo[3];
		
		//Startup RMI connection
		ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

		//Send to server what room has to be reserved, when and by whom
		boolean answer = serverIntf.reserveRoom(room, initialDateReserved, finalDateReserved, username);
		
		//Convert answer to string
		String answerConverted;
		if (answer == true) {
			answerConverted = "true";
		} else {
			answerConverted = "false";
		}
		
		return answerConverted;
	}

	@POST
	@Path("/removeReservation")
	public String removeReservation(Reservation reservation) throws Exception {
		//Remove reservation info from reservation class
		String[] reservationInfo = reservation.getReservationInfo().split(" ");
		String room = reservationInfo[0];
		String initialDateReserved = reservationInfo[1];
		String finalDateReserved = reservationInfo[2];
		String username = reservationInfo[3];
		
		//Startup RMI connection
		ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

		//Check if reservation was made and if we can remove it
		boolean answer = serverIntf.removeReservation(room, initialDateReserved, finalDateReserved, username);
		
		//Convert answer to string
		String answerConverted;
		if (answer == true) {
			answerConverted = "true";
		} else {
			answerConverted = "false";
		}
		
		return answerConverted;
	}

	@POST
	@Path("/loginAccount")
	public String loginAccount(Client user) throws Exception {
		//Remove user info from client class
		String[] userInfo = user.getUserInfo().split(" ");
		String username = userInfo[0];
		String password = userInfo[1];
		
		//Startup RMI connection
		ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);

		//Check if account exists on server (receive integer for multiple return answers)
		int answer = serverIntf.loginAccount(username, password);
		
		//Convert answer to string
		String answerConverted;
		if (answer == 0) {
			answerConverted = "0";
		} else if (answer == -1) {
			answerConverted = "-1";
		} else {
			answerConverted = "-2";
		}
		
		return answerConverted;
	}

	@POST
	@Path("/setupAccount")
	public String setupAccount(Client user) throws Exception {	
		//Remove user info from client class
		String[] userInfo = user.getUserInfo().split(" ");
		String username = userInfo[0];
		String password = userInfo[1];
		
		//Startup RMI connection
		ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverURL);
		
		//Send account to server
		boolean answer = serverIntf.setupAccount(username, password);
		
		//Convert answer to string
		String answerConverted;
		if (answer == true) {
			answerConverted = "true";
		} else {
			answerConverted = "false";
		}
		
		return answerConverted;
	}
}
