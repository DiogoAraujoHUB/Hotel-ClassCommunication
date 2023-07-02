import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Client")
public class Client {
	  private String userInfo;

	  public String getUserInfo() {
		  return userInfo;
	  }
		
	  public void setUserInfo(String userInfo) {
		  this.userInfo = userInfo;
	  }
}