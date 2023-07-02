import java.util.*;
import java.io.*;
import java.net.*;

public class ClientREST {

    //Iniciar scanner para receber resposta do cliente
    private static Scanner receiveInput = new Scanner(System.in);

    public static void main(String args[]) {
        //List where we keep options
        ArrayList<Integer> optionList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        boolean userAuthenticated = false;
        String username = "";

        try {
            //Iniciar loop cliente, que só irá parar depois de cliente selecionar opção 0 de sair
            while (true) {
                //Mostrar menu ao cliente e receber resposta correta
                showMenu(username);
                int answer = -1;

                //Check if answer is in correct format and if it is part of the options
                while (true) {
                    System.out.print("Answer: ");
                    String userAnswer = receiveInput.nextLine();
                    boolean foundOption = false;

                    //Check if answer received is number
                    try {
                        answer = Integer.parseInt(userAnswer);

                        //Check if answer received is one of the options available
                        for (Integer option: optionList) {
                            if (answer == option) {
                                foundOption = true;
                            }
                        }
                        if (foundOption == true) {
                            break;
                        }

                        //Answer was not one of the options
                        System.out.println("Answer was not one of the options.");
                    } catch (NumberFormatException e) {
                        System.out.println("Answer was not one of the options.");
                    }
                }
                //Check if user wants to exit program
                if (answer == 0) {
                    break;
                }

                //Option of listing available rooms
                if (answer == 1) {
                    listRoomsHotel();
                }

                //Option of listing rooms currently reserved
                if (answer == 2) {
                    //Check if user is authenticated
                    if (userAuthenticated == false) {
                        System.out.println("You need to log in to choose that option.");
                        continue;
                    }

                    listRoomsReservedHotel(username);
                }

                //Option of reserving a room
                if (answer == 3) {
                    //Check if user is authenticated
                    if (userAuthenticated == false) {
                        System.out.println("You need to log in to choose that option.");
                        continue;
                    }

                    //Reserve room available in hotel
                    reserveRoomHotel(username);
                }

                //Option of canceling a reservation
                if (answer == 4) {
                    //Check if user is authenticated
                    if (userAuthenticated == false) {
                        System.out.println("You need to log in to choose that option.");
                        continue;
                    }

                    //List rooms user has reserved
                    listRoomsReservedHotel(username);

                    //Cancel reservation made from previous list
                    cancelReservationHotel(username);
                }

                //Option of logging in to account (get username and password)
                //Or logging out if already logged in
                if (answer == 5) {
                    //Logout of account as we are already in an account
                    if (!username.equals("")) {
                        System.out.println("Successfully logged out.");
                        userAuthenticated = false;
                        username = "";
                        continue;
                    }

                    //Login to account
                    String usernameReceived = loginClientHotel();

                    //Login worked out
                    if (usernameReceived != null) {
                        userAuthenticated = true;
                        username = usernameReceived;
                    }
                }

                //Option of registering account (setup username and password)
                if (answer == 6) {
                    String usernameReceived = registerClientHotel();

                    if (usernameReceived != null) {
                        userAuthenticated = true;
                        username = usernameReceived;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception error -> " + e);
        }

        //Send message when user leaves client and server
        System.out.println("Thank you for choosing our hotel!");
    }

    //Show menu which will be used for the hotel
    public static void showMenu(String username) {
        System.out.println("------------Hotel Lisboa-------------");
        System.out.println("Bem vindo ao nosso Hotel!");
        System.out.println("1. Listar Quartos Disponiveis.");
        System.out.println("2. Listar Quartos Reservados.");
        System.out.println("3. Reservar um Quarto.");
        System.out.println("4. Cancelar uma Reserva.");
        if (username.equals("")) {
            System.out.println("5. Login.");
        } else { //Make sure user can logout of account if he so wishes
            System.out.println("5. Logout.");
        }
        System.out.println("6. Registar.");
        System.out.println("0. Sair.");
        System.out.println("-------------------------------------");
    }

    //Go to web service and list rooms of hotel
    public static void listRoomsHotel() throws Exception {
        //Setup connection to REST web service
        URL url = new URL("http://localhost:8090/HotelREST/rest/listRooms");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        //Connect to web service and setup response
        Scanner serverResponse;
        System.out.println("Rooms available:");

        //Check if connection was successful and prepare message
        if (conn.getResponseCode() != 200) {
            serverResponse = new Scanner(conn.getErrorStream());
        } else {
            serverResponse = new Scanner(conn.getInputStream());
        }

        //Get answer from scanner which will give us our response
        serverResponse.useDelimiter("\\Z");
        System.out.println(serverResponse.next());

        //Disconnect web service connector and close scanner
        serverResponse.close();
        conn.disconnect();
    }

    //Go to web service and list rooms that user has reserved
    public static void listRoomsReservedHotel(String username) throws Exception {
        //Setup connection to REST web service
        URL url = new URL("http://localhost:8090/HotelREST/rest/listUserReservedRooms");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        //Input string to send to server with info
        String input =  "{\"Client\":{\"userInfo\":\"" + username + " " + "null\"}}";

        //Send info to server
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        //Connect to web service and setup response
        Scanner serverResponse;

        //Check if connection was successful and prepare message depending
        if (conn.getResponseCode() != 200) {
            serverResponse = new Scanner(conn.getErrorStream());

            //Get error from server and leave
            serverResponse.useDelimiter("\\Z");

            System.out.println(serverResponse.next());
            System.out.println("Error in response, exiting...");
            return;
        } else {
            serverResponse = new Scanner(conn.getInputStream());
        }

        //Get answer from web service which will give us our response
        serverResponse.useDelimiter("\\Z");
        String listAnswer = serverResponse.next();

        //Print out answer with rooms reserved
        System.out.println("Rooms currently reserved by " + username + ": ");
        System.out.println(listAnswer);

        //Disconnect web service connector and close scanner
        serverResponse.close();
        conn.disconnect();
    }

    //Go to web service and reserve a room that is available on that date
    public static void reserveRoomHotel(String username) throws Exception {
        //Define format for reservations
        System.out.println("Reservations have a maximum amount of time of 1 month.");

        //First we get the date for the reservation
        String[] dates = getDateFromUser();
        if (dates == null) {
            return;
        }
        String initialDateChosen = dates[0];
        String finalDateChosen = dates[1];

        //Setup connection to REST web service (check rooms at date)
        URL urlCheck = new URL("http://localhost:8090/HotelREST/rest/checkRoomsAtDate");
        HttpURLConnection connCheck = (HttpURLConnection) urlCheck.openConnection();
        connCheck.setDoOutput(true);
        connCheck.setRequestMethod("POST");
        connCheck.setRequestProperty("Content-Type", "application/json");

        //Input string to send to server with info (check rooms at date)
        String inputCheck =  "{\"Reservation\":{\"reservationInfo\":\"" + "null" + " " + initialDateChosen + " " + finalDateChosen + " " + "null" + "\"}}";

        //Send info to server (check rooms at date)
        OutputStream osCheck = connCheck.getOutputStream();
        osCheck.write(inputCheck.getBytes());
        osCheck.flush();

        //Connect to web service and setup response (check rooms at date)
        Scanner serverResponseCheck;

        //Check if connection was successful and prepare message depending (check rooms at date)
        if (connCheck.getResponseCode() != 200) {
            serverResponseCheck = new Scanner(connCheck.getErrorStream());

            //Get error from server and leave
            serverResponseCheck.useDelimiter("\\Z");

            System.out.println(serverResponseCheck.next());
            System.out.println("Error in response, exiting...");
            return;
        } else {
            serverResponseCheck = new Scanner(connCheck.getInputStream());
        }

        //Get answer from web service which will give us our response (check rooms at date)
        serverResponseCheck.useDelimiter("\\Z");
        String checkAnswer = serverResponseCheck.next();

        //Disconnect web service connector and close scanner (check rooms at date)
        serverResponseCheck.close();
        connCheck.disconnect();

        //Get rooms currently available from date given
        System.out.println("Rooms available during that date: " + checkAnswer);

        //Get the reservation from that list
        System.out.println("What room would you like to reserve?");
        String roomChosen = receiveInput.nextLine();

        //Check if the option chosen was in list
        String roomArray[] = checkAnswer.split(" ");
        boolean foundRoom = false;
        for (String room: roomArray) {
            if (room.equals(roomChosen)) {
                foundRoom = true;
                break;
            }
        }

        //Check if room has enough capacity for client
        boolean capacityAnswer = checkRoomCapacity(roomChosen, foundRoom);
        if (capacityAnswer == false) {
            System.out.println("Room " + roomChosen + " did not have the desired capacity, please try a room from another floor.");
            return;
        }

        //Check if room was found in rooms available
        if (foundRoom == false) {
            System.out.println("Room " + roomChosen + " was not found or is currently not available.");
            return;
        }

        //Setup connection to REST web service (reserve room)
        URL urlReserve = new URL("http://localhost:8090/HotelREST/rest/reserveRoom");
        HttpURLConnection connReserve = (HttpURLConnection) urlReserve.openConnection();
        connReserve.setDoOutput(true);
        connReserve.setRequestMethod("POST");
        connReserve.setRequestProperty("Content-Type", "application/json");

        //Input string to send to server with info (reserve room)
        String inputReserve =  "{\"Reservation\":{\"reservationInfo\":\"" + roomChosen + " " + initialDateChosen + " " + finalDateChosen + " " + username + "\"}}";

        //Send info to server (reserve room)
        OutputStream osReserve = connReserve.getOutputStream();
        osReserve.write(inputReserve.getBytes());
        osReserve.flush();

        //Connect to web service and setup response (reserve room)
        Scanner serverResponseReserve;

        //Check if connection was successful and prepare message depending (reserve room)
        if (connReserve.getResponseCode() != 200) {
            serverResponseReserve = new Scanner(connReserve.getErrorStream());

            //Get error from server and leave
            serverResponseReserve.useDelimiter("\\Z");

            System.out.println(serverResponseReserve.next());
            System.out.println("Error in response, exiting...");
            return;
        } else {
            serverResponseReserve = new Scanner(connReserve.getInputStream());
        }

        //Get answer from web service which will give us our response (reserve room)
        serverResponseReserve.useDelimiter("\\Z");
        String reservationAnswer = serverResponseReserve.next();

        //Disconnect web service connector and close scanner (reserve room)
        serverResponseReserve.close();
        connReserve.disconnect();

        //Check if room was reserved
        if (reservationAnswer.equals("true")) {
            System.out.println("Room " + roomChosen + " has been reserved, thank you very much!");
        } else {
        	System.out.println("Error creating " + roomChosen + "'s reservation. Please try again.");
        }
    }

    //Go to web service and remove the reservation of a room the user reserved
    public static void cancelReservationHotel(String username) throws Exception {
        //List rooms the client has reserved and cancel the reservation of one of them
        System.out.println("Hello user, what room would you like to cancel the reservation of?");
        String roomChosen = receiveInput.nextLine();

        //Get date reservation was made in
        String[] dates = getDateFromUser();
        if (dates == null) {
            return;
        }
        String initialDateChosen = dates[0];
        String finalDateChosen = dates[1];

        //Setup connection to REST web service
        URL url = new URL("http://localhost:8090/HotelREST/rest/removeReservation");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        //Input string to send to server with info
        String input =  "{\"Reservation\":{\"reservationInfo\":\"" + roomChosen + " " + initialDateChosen + " " + finalDateChosen + " " + username + "\"}}";

        //Send info to server
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        //Connect to web service and setup response
        Scanner serverResponse;

        //Check if connection was successful and prepare message depending
        if (conn.getResponseCode() != 200) {
            serverResponse = new Scanner(conn.getErrorStream());

            //Get error from server and leave
            serverResponse.useDelimiter("\\Z");

            System.out.println(serverResponse.next());
            System.out.println("Error in response, exiting...");
            return;
        } else {
            serverResponse = new Scanner(conn.getInputStream());
        }

        //Get answer from web service which will give us our response
        serverResponse.useDelimiter("\\Z");
        String cancelAnswer = serverResponse.next();

        //Disconnect web service connector and close scanner
        serverResponse.close();
        conn.disconnect();

        //Check if reservation was made and if we can remove it
        if (cancelAnswer.equals("true")) {
            System.out.println("Reservation for room " + roomChosen + " was canceled. Thank you very much!");
            return;
        }

        //Reservation was not found in file so cancellation was not made
        System.out.println("Reservation was not found, please try again!");
    }

    //Go to web service and login to an account
    public static String loginClientHotel() throws Exception {
        //Get account information from user
        System.out.print("Username: ");
        String usernameChosen = receiveInput.nextLine();
        System.out.print("Password: ");
        String passwordChosen = receiveInput.nextLine();

        //Setup connection to REST web service
        URL url = new URL("http://localhost:8090/HotelREST/rest/loginAccount");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        //Input string to send to server with info
        String input =  "{\"Client\":{\"userInfo\":\"" + usernameChosen + " " + passwordChosen + "\"}}";

        //Send info to server
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        //Connect to web service and setup response
        Scanner serverResponse;

        //Check if connection was successful and prepare message depending
        if (conn.getResponseCode() != 200) {
            serverResponse = new Scanner(conn.getErrorStream());

            //Get error from server and leave
            serverResponse.useDelimiter("\\Z");

            System.out.println(serverResponse.next());
            System.out.println("Error in response, exiting...");
            return null;
        } else {
            serverResponse = new Scanner(conn.getInputStream());
        }

        //Get answer from web service which will give us our response
        serverResponse.useDelimiter("\\Z");
        String loginAnswer = serverResponse.next();

        //Disconnect web service connector and close scanner
        serverResponse.close();
        conn.disconnect();

        //Check if account exists on server (receive int for multiple return answers)
        if (loginAnswer.equals("0")) {
            System.out.println("Successfully logged in to " + usernameChosen + ".");
            return usernameChosen;
        } else if (loginAnswer.equals("-1")) {
            System.out.println("Username was not found.");
        } else {
            System.out.println("Password for username was incorrect.");
        }

        //Account did not exist or password was wrong
        return null;
    }

    //Go to web service and register an account
    public static String registerClientHotel() throws Exception {
        //Define format required for username and password
        System.out.println("You can only use letters and numbers for your username and password.");

        //Setup account with username and password
        System.out.print("Setup Username: ");
        String usernameChosen = receiveInput.nextLine();
        System.out.print("Setup Password: ");
        String passwordChosen = receiveInput.nextLine();

        //Check if only letters and numbers were used
        boolean checkContent = checkUserFormat(usernameChosen, passwordChosen);
        if (!checkContent) {
            System.out.println("Error creating account, please try again.");
        }

        //Setup connection to REST web service
        URL url = new URL("http://localhost:8090/HotelREST/rest/setupAccount");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        //Input string to send to server with info
        String input =  "{\"Client\":{\"userInfo\":\"" + usernameChosen + " " + passwordChosen + "\"}}";

        //Send info to server
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        //Connect to web service and setup response
        Scanner serverResponse;

        //Check if connection was successful and prepare message depending
        if (conn.getResponseCode() != 200) {
            serverResponse = new Scanner(conn.getErrorStream());

            //Get error from server and leave
            serverResponse.useDelimiter("\\Z");

            System.out.println(serverResponse.next());
            System.out.println("Error in response, exiting...");
            return null;
        } else {
            serverResponse = new Scanner(conn.getInputStream());
        }

        //Get answer from web service which will give us our response
        serverResponse.useDelimiter("\\Z");
        String registerAnswer = serverResponse.next();

        //Disconnect web service connector and close scanner
        serverResponse.close();
        conn.disconnect();

        //Check if account was accepted
        if (registerAnswer.equals("true")) {
            System.out.println("Account created successfully. You are now logged in to this account.");
            return usernameChosen;
        }

        System.out.println("Error creating account, please try again.");
        return null;
    }

    //Get initial and final dates from user
    public static String[] getDateFromUser() {
        //Ask user when reservation wants to be made
        System.out.println("Insert dates: (Format: DD/MM)");

        //Get dates for reservaton from user
        System.out.print("Initial Date: ");
        String initialDateChosen = receiveInput.nextLine();
        System.out.print("Final Date: ");
        String finalDateChosen = receiveInput.nextLine();

        //Check if dates are in correct format
        boolean checkFormat = checkDatesFormat(initialDateChosen, finalDateChosen);
        if (checkFormat == false) {
            System.out.println("Date was in the wrong format, please try again.");
            return null;
        }

        //Put dates into string and return to function
        String[] dates = new String[2];
        dates[0] = initialDateChosen;
        dates[1] = finalDateChosen;
        return dates;
    }

    //Check if answers are in required format
    public static boolean checkDatesFormat(String initialDate, String finalDate) {
        String splitInitialDate[] = initialDate.split("/");
        String splitFinalDate[] = finalDate.split("/");

        //Check length
        if (splitInitialDate.length != 2 || splitFinalDate.length != 2 ) {
            return false;
        }
        if (splitInitialDate[0].length() > 2 || splitInitialDate[1].length() > 2) {
            return false;
        }
        if (splitFinalDate[0].length() > 2 || splitFinalDate[1].length() > 2) {
            return false;
        }

        //Check if they are numbers and between possible days/month
        try {
            //Convert into int
            int initialDay = Integer.parseInt(splitInitialDate[0]);
            int initialMonth = Integer.parseInt(splitInitialDate[1]);
            int finalDay = Integer.parseInt(splitFinalDate[0]);
            int finalMonth = Integer.parseInt(splitFinalDate[1]);

            //Check if they are valid numbers (day and month)
            if ((initialDay < 1 || initialDay > 31) || (finalDay < 1 || finalDay > 31)) {
                return false;
            }
            if ((initialMonth < 1 || initialMonth > 12) || (finalMonth < 1 || finalMonth > 12)) {
                return false;
            }

            //Make sure month is okay for reservation (and check for new year exception)
            if (initialMonth == 12 && finalMonth == 1) {
                return true;
            } else {
                //Only allow reservations that are less, or equal, than 1 month (month)
                //Also only allows reserving months forward
                if (initialMonth - finalMonth == 0 || initialMonth - finalMonth == -1) {
                    //Make sure we are not reserving backwards in terms of days
                    if (initialMonth - finalMonth == 0) {
                        if (initialDay > finalDay) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    //Go through username and password and check if letters othen name chars and numbers were used
    public static boolean checkUserFormat(String username, String password) {
        //Go through username
        for (int pos = 0; pos < username.length() - 1; pos++) {
            char letter = username.charAt(pos);

            //Check if it is number or letter
            if (Character.isDigit(letter)) {
                //Check if it is actually number
                if (letter < 48 || letter > 57) {
                    return false;
                }
            } else {
                //Check if letter is upperscore or underscore
                if (Character.isUpperCase(letter)) {
                    //Check if it is actually letter
                    if (letter < 65 || letter > 90) {
                        return false;
                    }
                } else {
                    //Check if it is actually letter
                    if (letter < 97 || letter > 122) {
                        return false;
                    }
                }
            }
        }

        //Go through password
        for (int pos = 0; pos < password.length() - 1; pos++) {
            char letter = password.charAt(pos);

            //Check if it is number or letter
            if (Character.isDigit(letter)) {
                //Check if it is actually number
                if (letter < 48 || letter > 57) {
                    return false;
                }
            } else {
                //Check if letter is upperscore or underscore
                if (Character.isUpperCase(letter)) {
                    //Check if it is actually letter
                    if (letter < 65 || letter > 90) {
                        return false;
                    }
                } else {
                    //Check if it is actually letter
                    if (letter < 97 || letter > 122) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    //Give answer based on room chosen by user for capacity
    public static boolean checkRoomCapacity(String room, boolean foundRoom) {
        //Room was not found so let rest of function deal with it
        if (foundRoom == false) {
            return true;
        }

        //Check first letter of room
        char firstLetter = room.charAt(0);
        if (firstLetter == '1') {
            System.out.println("This room has the capacity of 2 people.");
        } else if (firstLetter == '2') {
            System.out.println("This room has the capacity of 3 people.");
        } else if (firstLetter == '3') {
            System.out.println("This room has the capacity of 6 people.");
        } else {
            return false;
        }

        //Ask user if they are ok with current room capacity
        System.out.println("Are you ok with this capacity? (Y/N)");
        String capacityAnswer = receiveInput.nextLine();
        if (capacityAnswer.charAt(0) == 'Y' || capacityAnswer.charAt(0) == 'y') {
            return true;
        }

        //User was not okay with room capacity
        return false;
    }
}