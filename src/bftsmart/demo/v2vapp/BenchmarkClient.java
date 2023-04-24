package bftsmart.demo.v2vapp;


import bftsmart.tom.ServiceProxy;

import java.io.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author robin
 */
public class BenchmarkClient {
    private static int initialClientId;
    private static byte[] data;
    private static byte[] serializedReadRequest;
    private static byte[] serializedWriteRequest;

    private static int requestSize = 0;
    static byte[] padding;

    private static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 4) {
            System.out.println("USAGE: sharbft.benchmark.ThroughputLatencyClient <initial client id> " +
                    "<num clients> <number of operations per client> <request size>");// <isWrite?> <measurement leader?>
            System.exit(-1);
        }
        random.setSeed(1);
        initialClientId = Integer.parseInt(args[0]);
        int numClients = Integer.parseInt(args[1]);
        int numOperationsPerClient = Integer.parseInt(args[2]);
        requestSize = Integer.parseInt(args[3]);
        boolean isWrite = true;//Boolean.parseBoolean(args[4]);
        boolean measurementLeader = true;//Boolean.parseBoolean(args[5]);
        CountDownLatch latch = new CountDownLatch(numClients);
        Client[] clients = new Client[numClients];
        data = new byte[requestSize];

        for (int i = 0; i < requestSize; i++) {
            data[i] = (byte) i;
        }

        padding = data;

        /**
        ByteBuffer writeBuffer = ByteBuffer.allocate(1 + Integer.BYTES + requestSize);
        writeBuffer.put((byte) Operation.PUT.ordinal());
        writeBuffer.putInt(requestSize);
        writeBuffer.put(data);
        serializedWriteRequest = writeBuffer.array();

        ByteBuffer readBuffer = ByteBuffer.allocate(1);
        readBuffer.put((byte) Operation.GET.ordinal());
        serializedReadRequest = readBuffer.array();
         **/

        for (int i = 0; i < numClients; i++) {
            clients[i] = new Client(initialClientId + i,
                    numOperationsPerClient, isWrite, measurementLeader, latch);
            clients[i].start();
            Thread.sleep(10);
        }
        new Thread(() -> {
            try {
                latch.await();
                System.out.println("Executing experiment");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static class Client extends Thread {
        private final int clientId;
        private final int numOperations;
        private final boolean isWrite;
        private final ServiceProxy proxy;
        private final CountDownLatch latch;
        private final boolean measurementLeader;

        public Client(int clientId, int numOperations, boolean isWrite, boolean measurementLeader, CountDownLatch latch) {
            this.clientId = clientId;
            this.numOperations = numOperations;
            this.isWrite = isWrite;
            this.measurementLeader = measurementLeader;
            this.proxy = new ServiceProxy(clientId);
            this.latch = latch;
            this.proxy.setInvokeTimeout(100); // in seconds
        }

        @Override
        public void run() {
            try {
                latch.countDown();
                if (initialClientId == clientId) {
                    buyOrder(String.valueOf(clientId), 20, 0.5, 1, 1);
                }
                long start = System.nanoTime();
                for (int i = 0; i < numOperations; i++) {
                    long t1, t2, latency;
                    byte[] response;
                    t1 = System.nanoTime();
                    boolean o = random.nextBoolean();
                    if (o) {
                        int units = 20 + random.nextInt(15);
                        double originalPrice = 0.1 + (0.7 - 0.1) * random.nextDouble();
                        int xpos = random.nextInt(5);
                        int ypos = random.nextInt(5);
                        buyOrder(String.valueOf(clientId),units, originalPrice, xpos, ypos);
                    } else {
                        int units = 20 + random.nextInt(20);
                        double originalPrice = 0.1 + (0.7 - 0.1) * random.nextDouble();
                        double unitPrice = 0.1 + (0.7 - 0.1) * random.nextDouble();
                        int xpos = random.nextInt(5);
                        int ypos = random.nextInt(5);
                        sellOrder(String.valueOf(clientId),units, unitPrice, originalPrice, xpos, ypos);
                    }
                    t2 = System.nanoTime();
                    latency = t2 - t1;
                    if (initialClientId == clientId && measurementLeader) {
                        System.out.println("Num:"+i+", Latency[ns/ops]: " + latency);
                    }
                }
                long end = System.nanoTime();
                System.out.println("Average latency[ms]: " + ((float)(end-start)/(numOperations+1))/1_000_000);
            } finally {
                proxy.close();
            }
        }

        public String register(String clientID) {
            try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                 ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

                objOut.writeObject(V2VRequestType.REGISTER);
                objOut.writeObject(clientID);

                objOut.flush();
                byteOut.flush();

                byte[] reply = proxy.invokeOrdered(byteOut.toByteArray());
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

                byte[] reply = proxy.invokeOrdered(byteOut.toByteArray());
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

                byte[] reply = proxy.invokeOrdered(byteOut.toByteArray());
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
                objOut.writeObject(data);


                objOut.flush();
                byteOut.flush();

                byte[] reply = proxy.invokeOrdered(byteOut.toByteArray());
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
                objOut.writeObject(data);


                objOut.flush();
                byteOut.flush();

                byte[] reply = proxy.invokeOrdered(byteOut.toByteArray());
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
    }
}
