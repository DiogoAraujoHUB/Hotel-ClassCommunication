import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.io.*;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf {
    private ArrayList<String> roomList = new ArrayList<>();

    //Setup files
    private static File reservationFile = new File("reservations.txt");
    private static File accountFile = new File("accounts.txt");

    //Setup list of rooms currently available
    public ServerImpl() throws RemoteException {
        String floor1[] = new String[] {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110"};
        String floor2[] = new String[] {"201", "202", "203", "204", "205", "206", "207", "208", "209", "210"};
        String floor3[] = new String[] {"301", "302", "303", "304"};

        //Todos os quartos no piso 1 só aceitam, no máximo, 2 pessoas
        roomList.addAll(Arrays.asList(floor1));

        //Todos os quartos no piso 2 só aceitam, no máximo, 3 pessoas
        roomList.addAll(Arrays.asList(floor2));

        //Todos os quartos no piso 3 só aceitam, no máximo, 6 pessoas
        roomList.addAll(Arrays.asList(floor3));
    }

    //List all rooms available in hotel
    public String listRooms() throws RemoteException {
        //System.out.println("Received command to list rooms in server.");

        //Convert into string for user
        String answer = "101 - 102 - 103 - 104 - 105 - 106 - 107 - 108 - 109 - 110\n";
        answer += "201 - 202 - 203 - 204 - 205 - 206 - 207 - 208 - 209 - 210\n";
        answer += "301 - 302 - 303 - 304";

        //System.out.println("Finished listing rooms.");
        return answer;
    }

    //Show rooms user has currently reserved
    public String listUserReservedRooms(String username) throws RemoteException {
        //Get rooms currently coinciding with initial date and final date (wrong, have to do using timeframe)
        ArrayList<String> reservationsMade = new ArrayList<>();
        try {
            //Open reading files
            BufferedReader resReader = new BufferedReader(new FileReader(reservationFile));

            String readingLine = null;
            do {
                readingLine = resReader.readLine();
                if (readingLine == null) {
                    break;
                }

                //Split line and check if the room has been reserved
                String splitLine[] = readingLine.split(" - ");

                //Check if reservation was made by username
                if (splitLine[3].equals(username)) {
                    reservationsMade.add(splitLine[0] + " - " + splitLine[1] + " - " + splitLine[2]);
                }
            } while (readingLine != null);

            //Close reading files
            resReader.close();
        } catch (Exception e) {
            System.out.println("Something went wrong -> " + e.getMessage());
        }

        //Check if no reservations were made
        if (reservationsMade.size() == 0) {
            return "No reservations were made with this account.";
        }

        //Convert into string for user
        String answer = "";
        for (String reservation: reservationsMade) {
            answer += reservation + " ||| ";
        }

        return answer;
    }

    //Check rooms available between date given
    public String checkRoomsAtDate(String initialDateReserved, String finalDateReserved) throws RemoteException {
        //Get rooms currently coinciding with initial date and final date (wrong, have to do using timeframe)
        ArrayList<String> roomsToRemove = new ArrayList<>();

        //Compare set reservation day values
        int initialReservationDay = Integer.parseInt(initialDateReserved.split("/")[0]);
        int initialReservationMonth = Integer.parseInt(initialDateReserved.split("/")[1]);
        int finalReservationDay = Integer.parseInt(finalDateReserved.split("/")[0]);
        int finalReservationMonth = Integer.parseInt(finalDateReserved.split("/")[1]);
        try {
            //Open reading files
            BufferedReader resReader = new BufferedReader(new FileReader(reservationFile));

            String readingLine = null;
            do {
                //Get line from file
                readingLine = resReader.readLine();
                if (readingLine == null) {
                    break;
                }

                try {
                    //Split line and check if the room has been reserved
                    String splitLine[] = readingLine.split(" - ");

                    //Compare current reservation being seen values
                    int currentInitialReservationDay = Integer.parseInt(splitLine[1].split("/")[0]);
                    int currentInitialReservationMonth = Integer.parseInt(splitLine[1].split("/")[1]);
                    int currentFinalReservationDay = Integer.parseInt(splitLine[2].split("/")[0]);
                    int currentFinalReservationMonth = Integer.parseInt(splitLine[2].split("/")[1]);

                    //Months are the same, so we check all days
                    if (currentInitialReservationMonth == initialReservationMonth && currentFinalReservationMonth == finalReservationMonth) {

                        //Reservation date is between and above the current date
                        if ((initialReservationDay <= currentFinalReservationDay + 1) && (finalReservationDay >= currentFinalReservationDay + 1)) {
                            roomsToRemove.add(splitLine[0]);
                            continue;
                        }

                        //Reservation date is below and between the current date
                        if ((initialReservationDay <= currentInitialReservationDay) && (finalReservationDay >= currentInitialReservationDay)) {
                            roomsToRemove.add(splitLine[0]);
                            continue;
                        }

                        //Reservation date is between the current date
                        if ((initialReservationDay >= currentInitialReservationDay) && (finalReservationDay <= currentFinalReservationDay + 1)) {
                            roomsToRemove.add(splitLine[0]);
                            continue;
                        }

                        //Reservation date is below and above the current date (surrounds)
                        if ((initialReservationDay <= currentInitialReservationDay) && (finalReservationDay >= currentFinalReservationDay + 1)) {
                            roomsToRemove.add(splitLine[0]);
                        }
                    } else if (currentInitialReservationMonth == initialReservationMonth && currentFinalReservationMonth != finalReservationMonth) {

                        //Initial reservation day is behind, or between, current reservation
                        if (initialReservationDay <= currentFinalReservationDay + 1) {
                            roomsToRemove.add(splitLine[0]);
                            continue;
                        }
                    } else if (currentInitialReservationMonth != initialReservationMonth && currentFinalReservationMonth == finalReservationMonth) {

                        //Final reservation day is in front of, or between, current reservation
                        if (finalReservationDay >= currentInitialReservationDay) {
                            roomsToRemove.add(splitLine[0]);
                            continue;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Something went wrong -> " + e.getMessage());
                }
            } while (readingLine != null);

            //Close reading file
            resReader.close();
        } catch (Exception e) {
            System.out.println("Something went wrong -> " + e.getMessage());
        }

        //Remove rooms found reserved from list of rooms available
        ArrayList<String> roomsAvailable = new ArrayList<>(roomList);
        for (String removal: roomsToRemove) {
            int roomPos = 0;

            //Go through list of rooms available
            for (String room: roomsAvailable) {
                if (removal.equals(room)) {
                    break;
                }

                roomPos++;
            }

            roomsAvailable.remove(roomPos);
        }

        //Convert into string for user
        String answer = "";
        for (String room: roomsAvailable) {
            answer += room + " - ";
        }

        return answer;
    }

    //Write reservation to reservation file with room and dates
    public boolean reserveRoom(String roomReserved, String initialDateReserved, String finalDateReserved, String username) throws RemoteException {
        boolean answer = false;

        try {
            //Setup writer for reservation file
            BufferedWriter resWriter = new BufferedWriter(new FileWriter(reservationFile, true));

            //Write room to reservation file
            resWriter.append(roomReserved + " - " + initialDateReserved + " - " + finalDateReserved + " - " + username + "\n");

            //Set that the room was written and close writer
            resWriter.close();
            answer = true;
        } catch (Exception e) {
            System.out.println("Error writing reservation for room " + roomReserved + " to file.");
        }

        return answer;
    }

    //Look through file for reservation with these parameters and remove it
    public boolean removeReservation(String room, String initialDateReserved, String finalDateReserved, String username) throws RemoteException {
        //Get all data from reservation file that isnt the reservation to be removed
        ArrayList<String> reservations = new ArrayList<>();

        boolean foundAnswer = false;
        try {
            //Open reading files
            BufferedReader resReader = new BufferedReader(new FileReader(reservationFile));

            String readingLine = null;
            do {
                readingLine = resReader.readLine();
                if (readingLine == null) {
                    break;
                }

                //Split line and check if the room has been reserved
                String splitLine[] = readingLine.split(" - ");

                //Found reservation made by user
                if (username.equals(splitLine[3])) {

                    //Check the associated room with the date (remove reservation)
                    if (splitLine[0].equals(room) && splitLine[1].equals(initialDateReserved) && splitLine[2].equals(finalDateReserved)) {
                        foundAnswer = true;
                        continue;
                    }
                }

                //Add to list
                reservations.add(readingLine);
            } while (readingLine != null);

            //Close reading file
            resReader.close();

            //If we didnt find the reservation, then there is no point in rewriting file
            if (foundAnswer == false) {
                return foundAnswer;
            }

            //Open writing file to reservation file (will reset file) and write all reservations that we got
            BufferedWriter resWriter = new BufferedWriter(new FileWriter(reservationFile));

            //Write to reservation file all reservations except one removed
            for (String reservation: reservations) {
                resWriter.append(reservation + "\n");
            }

            //Close writer
            resWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong -> " + e.getMessage());
        }

        //Return either true or false, depending if we found reservation
        return foundAnswer;
    }

    //Check if account exists and log in user if it does
    //Look through account file for account with username
    public int loginAccount(String username, String password) throws RemoteException {
        int answer = -1;
        try {
            //Open reading files
            BufferedReader accReader = new BufferedReader(new FileReader(accountFile));

            String readingLine = null;
            do {
                readingLine = accReader.readLine();
                if (readingLine == null) {
                    break;
                }

                //Split line and check if the room has been reserved
                String splitLine[] = readingLine.split(" - ");
                String usernameFound = splitLine[0];

                //Remove the associated reservation and send a message saying reservation was removed
                if (usernameFound.equals(username)) {
                    //Decrypt associated password
                    String passwordDecrypted = decrypt(splitLine[1]);

                    //Check if password is the same
                    if (passwordDecrypted.equals(password)) {
                        answer = 0; //Everything was correct
                        break;
                    }

                    answer = -2; //Password was wrong
                    break;
                }
            } while (readingLine != null);

            //Close reading file
            accReader.close();
        } catch (Exception e) {
            System.out.println("Something went wrong -> " + e.getMessage());
        }

        return answer;  //Return answer in integer for multiple errors
    }

    //Might return int instead to have multiple errors
    //Check if username and password are up to standard
    //Get username and check if it is in accounts
    //If not then put into account file with username and password
    public boolean setupAccount(String username, String password) throws RemoteException {
        //Go through file and look for username
        boolean makeAccount = true;
        try {
            //Open reading files
            BufferedReader accReader = new BufferedReader(new FileReader(accountFile));

            String readingLine = null;
            do {
                readingLine = accReader.readLine();
                if (readingLine == null) {
                    break;
                }

                //Split line via ' - '
                String splitLine[] = readingLine.split(" - ");

                //Check if username already exists
                if (splitLine[0].equals(username)) {
                    makeAccount = false;
                    break;
                }
            } while (readingLine != null);

            //Close reading files
            accReader.close();
        } catch (Exception e) {
            System.out.println("Something went wrong -> " + e.getMessage());
        }

        //Check if we found username already
        if (makeAccount == false) {
            return false;   //Username already exists so cant create
        }

        //Encrypt password in text file using ceasers cypher
        String encryptedPassword = encrypt(password);

        //Write room to account file and then flush write line
        try {
            //Open writer for account file
            BufferedWriter accWriter = new BufferedWriter(new FileWriter(accountFile, true));

            //Write username and password
            accWriter.append(username + " - " + encryptedPassword + "\n");

            //Close writer
            accWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing account " + username + " to file.");
        }

        //Correctly inserted account into file
        return true;
    }

    //Encript message using "Cifra de césar"
    //In this case we will take the size of the password and scale each letter up using that size
    public String encrypt(String message) {
        String messageEncrypted = "";

        //Go through message, scaling each letter up with scale
        int scale = message.length();
        for (int pos = 0; pos < message.length(); pos++) {
            char letter = message.charAt(pos);
            char newLetter = (char)(scale + letter);

            //Check if it is number or letter
            if (Character.isDigit(letter)) {
                //Check if it has gone above number
                if (newLetter > 57) {
                    int difference = newLetter - 57;

                    newLetter = (char)('0' - 1 + difference);
                }
            } else {
                //Check if letter is upperscore or underscore
                if (Character.isUpperCase(letter)) {
                    //Check if it is actually letter (uppercase)
                    if (newLetter > 90) {
                        int difference = newLetter - 90;

                        newLetter = (char)('A' - 1 + difference);
                    }
                } else {
                    //Check if it is actually letter (lowercase)
                    if (newLetter > 122) {
                        int difference = newLetter - 122;

                        newLetter = (char)('a' - 1 + difference);
                    }
                }
            }

            //Add letter onto message encrypted
            messageEncrypted += newLetter;
        }

        //Return encrypted message
        return messageEncrypted;
    }

    //Decrypt message using reverse of "Cifra de césar" implemented previously
    public String decrypt(String message) {
        String messageDecrypted = "";

        //Go through message, scaling each letter up with scale
        int scale = message.length();
        for (int pos = 0; pos < message.length(); pos++) {
            char letter = message.charAt(pos);
            char newLetter = (char)(letter - scale);

            //Check if it is number or letter
            if (Character.isDigit(letter)) {
                //Check if it has gone above number
                if (newLetter < 48) {
                    int difference = 48 - newLetter;

                    newLetter = (char)('9' + 1 - difference);
                }
            } else {
                //Check if letter is upperscore or underscore
                if (Character.isUpperCase(letter)) {
                    //Check if it is actually letter (uppercase)
                    if (newLetter < 65) {
                        int difference = 65 - newLetter;

                        newLetter = (char)('Z' + 1 - difference);
                    }
                } else {
                    //Check if it is actually letter (lowercase)
                    if (newLetter < 97) {
                        int difference = 97 - newLetter;

                        newLetter = (char)('z' + 1 - difference);
                    }
                }
            }

            //Add letter onto message decrypted
            messageDecrypted += newLetter;
        }

        //Return decrypted message
        return messageDecrypted;
    }
}