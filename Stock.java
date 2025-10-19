public class Stock{
    private String symbol;
    private String companyName;
    private int quantity;
    private double priceperShare;
    public Stock(String symbol, String companyName, int quantity, double priceperShare){
          this.symbol = symbol ;
          this.companyName = companyName;
          this.quantity = quantity;
          this.priceperShare = priceperShare;
    }
    public double get_value(){
        return quantity*priceperShare;
    }
    public void updatePrice(double newprice ){
        this.priceperShare = newprice;
    }
    public String get_symbol(){
        return symbol;
    }
    public int get_quantity(){
        return quantity;
    }
    public void addquantity(int qty){
        this.quantity +=qty;
    }
    public void remove_quantity(int qty){
        if(qty <= quantity) this.quantity -= qty;
        else System.out.println("not enough stock to remove !");
    }
}
