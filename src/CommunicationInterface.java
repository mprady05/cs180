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
