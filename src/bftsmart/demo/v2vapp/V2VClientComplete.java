package bftsmart.demo.v2vapp;

import bftsmart.tom.ServiceProxy;

import java.io.*;

public class V2VClientComplete {

    ServiceProxy serviceProxy;
    Vehicle EV;

    public V2VClientComplete(int clientId) {serviceProxy = new ServiceProxy(clientId);}

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

    public String deposit(String clientID, double amount) {
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

    public String withdraw(String clientID, double amount) {
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

    public String buyOrder(String clientID, int units, double originalPrice, int xpos, int ypos) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

            objOut.writeObject(V2VRequestType.BUYORDER);
            objOut.writeObject(clientID);
            objOut.writeObject(units);
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

    public String linkEV(String id, Location location, double efficiency) {
        this.EV = new Vehicle(id, location, efficiency);
        return "LINKED EV";
    }

    public String sellOrder(String clientID, int units, double unitprice, double originalPrice, int xpos, int ypos) {
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
