package bftsmart.demo.v2vapp;

public class OrderMatch {
    int buyOrder;
    int sellOrder;
    double consumerCost;
    double providerProfit;

    public OrderMatch(int buyOrder, int sellOrder, double consumerCost, double providerProfit) {
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
        this.consumerCost = consumerCost;
        this.providerProfit = providerProfit;
    }
}
