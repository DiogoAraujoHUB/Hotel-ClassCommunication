import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Reservation")
public class Reservation {
	  private String reservationInfo;

	  public String getReservationInfo() {
		  return reservationInfo;
	  }
		
	  public void setReservationInfo(String reservationInfo) {
		  this.reservationInfo = reservationInfo;
	  }
}