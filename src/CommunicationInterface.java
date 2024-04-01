/**
 * CS18000 -- Project 5 -- Phase 1
 * Defines communication between clients and a server.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public interface CommunicationInterface {
    /**
     * Sends a request to the server.
     * @param request The request object to be sent to the server. This could be a command or data package.
     */
    void sendRequest(Object request);

    /**
     * Receives a response from the server.
     * @return An Object that represents the response from the server. This could be data or confirmation of receipt.
     */
    Object receiveResponse();
}
