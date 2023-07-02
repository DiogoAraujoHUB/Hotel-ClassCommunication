
package project.soap.server;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the project.soap.server package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CheckRoomsAtDate_QNAME = new QName("http://server.soap.project/", "checkRoomsAtDate");
    private final static QName _CheckRoomsAtDateResponse_QNAME = new QName("http://server.soap.project/", "checkRoomsAtDateResponse");
    private final static QName _ListRooms_QNAME = new QName("http://server.soap.project/", "listRooms");
    private final static QName _ListRoomsResponse_QNAME = new QName("http://server.soap.project/", "listRoomsResponse");
    private final static QName _ListUserReservedRooms_QNAME = new QName("http://server.soap.project/", "listUserReservedRooms");
    private final static QName _ListUserReservedRoomsResponse_QNAME = new QName("http://server.soap.project/", "listUserReservedRoomsResponse");
    private final static QName _LoginAccount_QNAME = new QName("http://server.soap.project/", "loginAccount");
    private final static QName _LoginAccountResponse_QNAME = new QName("http://server.soap.project/", "loginAccountResponse");
    private final static QName _RemoveReservation_QNAME = new QName("http://server.soap.project/", "removeReservation");
    private final static QName _RemoveReservationResponse_QNAME = new QName("http://server.soap.project/", "removeReservationResponse");
    private final static QName _ReserveRoom_QNAME = new QName("http://server.soap.project/", "reserveRoom");
    private final static QName _ReserveRoomResponse_QNAME = new QName("http://server.soap.project/", "reserveRoomResponse");
    private final static QName _SetupAccount_QNAME = new QName("http://server.soap.project/", "setupAccount");
    private final static QName _SetupAccountResponse_QNAME = new QName("http://server.soap.project/", "setupAccountResponse");
    private final static QName _Exception_QNAME = new QName("http://server.soap.project/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: project.soap.server
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CheckRoomsAtDate }
     * 
     */
    public CheckRoomsAtDate createCheckRoomsAtDate() {
        return new CheckRoomsAtDate();
    }

    /**
     * Create an instance of {@link CheckRoomsAtDateResponse }
     * 
     */
    public CheckRoomsAtDateResponse createCheckRoomsAtDateResponse() {
        return new CheckRoomsAtDateResponse();
    }

    /**
     * Create an instance of {@link ListRooms }
     * 
     */
    public ListRooms createListRooms() {
        return new ListRooms();
    }

    /**
     * Create an instance of {@link ListRoomsResponse }
     * 
     */
    public ListRoomsResponse createListRoomsResponse() {
        return new ListRoomsResponse();
    }

    /**
     * Create an instance of {@link ListUserReservedRooms }
     * 
     */
    public ListUserReservedRooms createListUserReservedRooms() {
        return new ListUserReservedRooms();
    }

    /**
     * Create an instance of {@link ListUserReservedRoomsResponse }
     * 
     */
    public ListUserReservedRoomsResponse createListUserReservedRoomsResponse() {
        return new ListUserReservedRoomsResponse();
    }

    /**
     * Create an instance of {@link LoginAccount }
     * 
     */
    public LoginAccount createLoginAccount() {
        return new LoginAccount();
    }

    /**
     * Create an instance of {@link LoginAccountResponse }
     * 
     */
    public LoginAccountResponse createLoginAccountResponse() {
        return new LoginAccountResponse();
    }

    /**
     * Create an instance of {@link RemoveReservation }
     * 
     */
    public RemoveReservation createRemoveReservation() {
        return new RemoveReservation();
    }

    /**
     * Create an instance of {@link RemoveReservationResponse }
     * 
     */
    public RemoveReservationResponse createRemoveReservationResponse() {
        return new RemoveReservationResponse();
    }

    /**
     * Create an instance of {@link ReserveRoom }
     * 
     */
    public ReserveRoom createReserveRoom() {
        return new ReserveRoom();
    }

    /**
     * Create an instance of {@link ReserveRoomResponse }
     * 
     */
    public ReserveRoomResponse createReserveRoomResponse() {
        return new ReserveRoomResponse();
    }

    /**
     * Create an instance of {@link SetupAccount }
     * 
     */
    public SetupAccount createSetupAccount() {
        return new SetupAccount();
    }

    /**
     * Create an instance of {@link SetupAccountResponse }
     * 
     */
    public SetupAccountResponse createSetupAccountResponse() {
        return new SetupAccountResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckRoomsAtDate }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CheckRoomsAtDate }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "checkRoomsAtDate")
    public JAXBElement<CheckRoomsAtDate> createCheckRoomsAtDate(CheckRoomsAtDate value) {
        return new JAXBElement<CheckRoomsAtDate>(_CheckRoomsAtDate_QNAME, CheckRoomsAtDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckRoomsAtDateResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CheckRoomsAtDateResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "checkRoomsAtDateResponse")
    public JAXBElement<CheckRoomsAtDateResponse> createCheckRoomsAtDateResponse(CheckRoomsAtDateResponse value) {
        return new JAXBElement<CheckRoomsAtDateResponse>(_CheckRoomsAtDateResponse_QNAME, CheckRoomsAtDateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListRooms }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListRooms }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "listRooms")
    public JAXBElement<ListRooms> createListRooms(ListRooms value) {
        return new JAXBElement<ListRooms>(_ListRooms_QNAME, ListRooms.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListRoomsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListRoomsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "listRoomsResponse")
    public JAXBElement<ListRoomsResponse> createListRoomsResponse(ListRoomsResponse value) {
        return new JAXBElement<ListRoomsResponse>(_ListRoomsResponse_QNAME, ListRoomsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListUserReservedRooms }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListUserReservedRooms }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "listUserReservedRooms")
    public JAXBElement<ListUserReservedRooms> createListUserReservedRooms(ListUserReservedRooms value) {
        return new JAXBElement<ListUserReservedRooms>(_ListUserReservedRooms_QNAME, ListUserReservedRooms.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListUserReservedRoomsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListUserReservedRoomsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "listUserReservedRoomsResponse")
    public JAXBElement<ListUserReservedRoomsResponse> createListUserReservedRoomsResponse(ListUserReservedRoomsResponse value) {
        return new JAXBElement<ListUserReservedRoomsResponse>(_ListUserReservedRoomsResponse_QNAME, ListUserReservedRoomsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginAccount }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LoginAccount }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "loginAccount")
    public JAXBElement<LoginAccount> createLoginAccount(LoginAccount value) {
        return new JAXBElement<LoginAccount>(_LoginAccount_QNAME, LoginAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginAccountResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LoginAccountResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "loginAccountResponse")
    public JAXBElement<LoginAccountResponse> createLoginAccountResponse(LoginAccountResponse value) {
        return new JAXBElement<LoginAccountResponse>(_LoginAccountResponse_QNAME, LoginAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveReservation }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RemoveReservation }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "removeReservation")
    public JAXBElement<RemoveReservation> createRemoveReservation(RemoveReservation value) {
        return new JAXBElement<RemoveReservation>(_RemoveReservation_QNAME, RemoveReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveReservationResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RemoveReservationResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "removeReservationResponse")
    public JAXBElement<RemoveReservationResponse> createRemoveReservationResponse(RemoveReservationResponse value) {
        return new JAXBElement<RemoveReservationResponse>(_RemoveReservationResponse_QNAME, RemoveReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveRoom }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReserveRoom }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "reserveRoom")
    public JAXBElement<ReserveRoom> createReserveRoom(ReserveRoom value) {
        return new JAXBElement<ReserveRoom>(_ReserveRoom_QNAME, ReserveRoom.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveRoomResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReserveRoomResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "reserveRoomResponse")
    public JAXBElement<ReserveRoomResponse> createReserveRoomResponse(ReserveRoomResponse value) {
        return new JAXBElement<ReserveRoomResponse>(_ReserveRoomResponse_QNAME, ReserveRoomResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetupAccount }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SetupAccount }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "setupAccount")
    public JAXBElement<SetupAccount> createSetupAccount(SetupAccount value) {
        return new JAXBElement<SetupAccount>(_SetupAccount_QNAME, SetupAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetupAccountResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SetupAccountResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "setupAccountResponse")
    public JAXBElement<SetupAccountResponse> createSetupAccountResponse(SetupAccountResponse value) {
        return new JAXBElement<SetupAccountResponse>(_SetupAccountResponse_QNAME, SetupAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.soap.project/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
