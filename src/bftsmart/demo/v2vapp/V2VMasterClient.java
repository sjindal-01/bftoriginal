package bftsmart.demo.v2vapp;

import bftsmart.tom.ServiceProxy;

import java.io.*;

public class V2VMasterClient {

    ServiceProxy serviceProxy;

    public V2VMasterClient(int clientId) {serviceProxy = new ServiceProxy(clientId);}

    public String register(String clientID) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

            objOut.writeObject(V2VRequestType.REGISTER);
            objOut.writeObject(clientID);

            objOut.flush();
            byteOut.flush();

            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
            if (reply.length == 0)
                return null;
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                 ObjectInput objIn = new ObjectInputStream(byteIn)) {
                return (String)objIn.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception getting value from map: " + e.getMessage());
        }
        return null;
    }

    public String addFunds(String clientID, double amount) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

            objOut.writeObject(V2VRequestType.DEPOSIT);
            objOut.writeObject(clientID);
            objOut.writeObject(amount);


            objOut.flush();
            byteOut.flush();

            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
            if (reply.length == 0)
                return null;
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                 ObjectInput objIn = new ObjectInputStream(byteIn)) {
                return (String)objIn.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception depositing");
        }
        return null;
    }

    public String removeFunds(String clientID, double amount) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

            objOut.writeObject(V2VRequestType.WITHDRAW);
            objOut.writeObject(clientID);
            objOut.writeObject(amount);


            objOut.flush();
            byteOut.flush();

            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
            if (reply.length == 0)
                return null;
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                 ObjectInput objIn = new ObjectInputStream(byteIn)) {
                return (String)objIn.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception withdrawing");
        }
        return null;
    }

    public String removeOrder(String clientID, int units, double unitprice, double originalPrice, int xpos, int ypos) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

            objOut.writeObject(V2VRequestType.SELLORDER);
            objOut.writeObject(clientID);
            objOut.writeObject(units);
            objOut.writeObject(unitprice);
            objOut.writeObject(xpos);
            objOut.writeObject(ypos);
            objOut.writeObject(originalPrice);


            objOut.flush();
            byteOut.flush();

            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
            if (reply.length == 0)
                return null;
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                 ObjectInput objIn = new ObjectInputStream(byteIn)) {
                return (String)objIn.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception getting value from map: " + e.getMessage());
        }
        return null;
    }
    public void close() {
        serviceProxy.close();
    }
}
