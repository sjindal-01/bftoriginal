package bftsmart.demo.v2vapp;


import java.util.Scanner;

public class V2VInteractiveClientComplete {

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage: demo.map.MapInteractiveClient <client id>");
        }

        int clientId = Integer.parseInt(args[0]);
        V2VClient client = new V2VClient(clientId);
        Scanner sc= new Scanner(System.in);

        boolean exit = false;
        String key, value, result;
        while(!exit) {
            System.out.println("Select an option:");
            System.out.println("0 - Terminate this client");
            System.out.println("1 - Register Client");
            System.out.println("2 - Add Funds");
            System.out.println("3 - Withdraw Funds");
            System.out.println("4 - Submit Buy Order");
            System.out.println("5 - Submit Sell Order");
            int cmd = Integer.parseInt(sc.nextLine());
            String clientID = "";
            int units = 0;
            int xpos = 0;
            int ypos = 0;
            double unitprice = 0.0;
            double originalprice = 0.0;
            double money = 0.0;

            switch (cmd) {
                case 0:
                    client.close();
                    exit = true;
                    break;
                case 1:
                    System.out.println("Register new client");
                    System.out.println("Enter New Client ID:");
                    clientID = sc.nextLine();
                    result = client.register(clientID);
                    System.out.println(result);
                    break;
                case 2:
                    System.out.println("Adding funds for client");
                    System.out.println("Enter CLient ID");
                    clientID = sc.nextLine();
                    System.out.println("Amount to add (£)");
                    money = Double.parseDouble(sc.nextLine());
                    System.out.println(client.deposit(clientID, money));
                    break;
                case 3:
                    System.out.println("Withdrawing funds for client");
                    System.out.println("Enter client ID:");
                    clientID = sc.nextLine();
                    System.out.println("Amount to withdraw (£)");
                    money = Double.parseDouble(sc.nextLine());
                    System.out.println(client.withdraw(clientID, money));
                    break;
                case 4:
                    System.out.println("Submitting Buy order");
                    System.out.println("Enter Client ID");
                    clientID = sc.nextLine();
                    System.out.println("Enter Requested Units (in kWh):");
                    units = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter XPosition of EV:");
                    xpos = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter YPosition of EV:");
                    ypos = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter Original price of energy (in £/kWh)");
                    originalprice = Double.parseDouble(sc.nextLine());
                    System.out.println(client.buyOrder(clientID,units, originalprice, xpos, ypos));
                    break;
                case 5:
                    System.out.println("Submitting Sell order");
                    System.out.println("Enter Client ID");
                    clientID = sc.nextLine();
                    System.out.println("Enter Available Units (in kWh):");
                    units = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter Unit price (in £/kWh)");
                    unitprice = Double.parseDouble(sc.nextLine());
                    System.out.println("Enter XPosition of EV:");
                    xpos = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter YPosition of EV:");
                    ypos = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter Original price of energy (in £/kWh)");
                    originalprice = Double.parseDouble(sc.nextLine());
                    System.out.println(client.sellOrder(clientID,units, unitprice, originalprice, xpos, ypos));
                default:
                    break;
            }
        }
    }

}
