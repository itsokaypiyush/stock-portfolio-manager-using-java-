import java.util.*;
 class Stock{
    private String symbol;
    private String companyName;
    private int quantity;
    private double priceperShare;
    private int stock_id;
    private Vector<Double> stock_updates=new Vector<>(0);
    public Stock(String symbol, String companyName, int quantity, double priceperShare,int stock_id){
          this.symbol = symbol ;
          this.companyName = companyName;
          this.quantity = quantity;
          this.priceperShare = priceperShare;
          this.stock_id=stock_id;
    }
    public double get_value(){
        return quantity*priceperShare;
    }
    public void updatePrice(double newprice ){
    if(stock_updates.isEmpty()){
            stock_updates.add(this.priceperShare);
        }
        this.priceperShare = newprice;
        stock_updates.add(newprice);
    }
    public Iterator<Double> get_Iterator(){
        return stock_updates.iterator();
    }
    public String get_symbol(){
        return symbol;
    }
    public int get_quantity(){
        return quantity;
    }
    public double get_price_per_share(){
        return this.priceperShare;
    }
    public String get_company_name(){
        return this.companyName;
    }
    public int get_stock_id(){
        return this.stock_id;
    }
    public void addquantity(int qty){
        if(qty<0){
            System.out.println("cant add negative quantity");
            return;
        }else if(qty==0){
            System.out.println("no quantity to add");
            return;
        }
        this.quantity +=qty;
        System.out.println("successfully added!");
    }
    public void remove_quantity(int qty){
        if(qty <= quantity) this.quantity -= qty;
        else System.out.println("not enough stock to remove !");
    }
    public void show(){
        System.out.println("------------");
        System.out.println("Stock id:"+this.stock_id);
        System.out.println("Symbol:"+this.symbol);
        System.out.println("Company Name:"+this.companyName);
        System.out.println("Quantity of stocks buyed: "+this.quantity);
        System.out.println("Price of stock per share:"+this.priceperShare);
    }
}
class portfolio{
    private double amount=0;
    private Vector<Stock> holdings=new Vector<>(0);
    private String password;
    private boolean verifyPassword(String passKey, Scanner sc){
    if(this.password.equals(passKey)) return true;

    while(true){
        System.out.println("Incorrect password:");
        System.out.println("1. Quit");
        System.out.println("2. Enter password again");
        int choice = sc.nextInt();
        sc.nextLine(); 
        if(choice == 1) return false;
        if(choice == 2){
            System.out.print("Enter password: ");
            passKey = sc.nextLine();
            if(this.password.equals(passKey)) return true;
        }
    }
}

    public portfolio(String passKey,double amount){
        this.password=passKey;
        this.amount=amount;
    }
    public void display_all_stocks(){
        if(holdings.isEmpty()){
            System.out.println("you have no stocks!");
            return;
        }else{
            for(Stock s:holdings){
                s.show();
            }
        }

    }
    public void display_stocks_of_certain_company(String company){
        int count=0;
        for(Stock s:holdings){
            if(s.get_company_name().equals(company)){
                s.show();
                count++;
            }
        }
        if(count==0){
            System.out.println("you dont have any stocks of this company");
        }

    }
    public void display_all_stocks_with_highest_price_per_share(){
        double highest_price=-Double.MAX_VALUE;
        for(Stock s: holdings){
            if(highest_price<s.get_price_per_share()){
                highest_price=s.get_price_per_share();
            }
        }
        for( Stock s: holdings){
            if(s.get_price_per_share()==highest_price){
                s.show();
            }
        }
    }
    public void display_all_stocks_with_lowest_price_per_share(){
        double lowest_price=Double.MAX_VALUE;
        for(Stock s: holdings){
            if(lowest_price>s.get_price_per_share()){
                lowest_price=s.get_price_per_share();
            }
        }
        for( Stock s: holdings){
            if(s.get_price_per_share()==lowest_price){
                s.show();
            }
        }
    }
    public void display_stocks_currently_in_loss(){
        for(Stock s: holdings){
            int count=0;
            double update=-Double.MAX_VALUE;
            Iterator<Double> traverse=s.get_Iterator();
            while(traverse.hasNext()){
                double x=traverse.next();
                if(update<x){
                    update=x;
                    count=0;
                }else{
                    count++;
                }
            }
            if(count>0){
                s.show();
                System.out.println("--stock is in loss from last "+count+" updates--");
            }
        }
    }
    public void display_stocks_currently_in_profit(){
        for(Stock s: holdings){
            int count=0;
            double update=-Double.MAX_VALUE;
            Iterator<Double> traverse=s.get_Iterator();
            while(traverse.hasNext()){
                double x=traverse.next();
                if(update<x){
                    update=x;
                    count++;
                }else{
                    count=0;
                }
            }
            if(count>0){
                s.show();
                System.out.println("--stock is in profit from last "+count+" updates--");
            }
        }
    }
    public double get_amount(){
        return amount;
    }
    public void display_amount(){
        System.out.println("Amount:"+amount);
    }
    public void add_amount(double add){
        if(add>0){
            amount+=add;
            return;
        }else{
            System.out.println("enter positive amount");
        }
    }
    public void remove_amount(double remove,String passKey,Scanner sc){
        if(verifyPassword(passKey, sc)==false)return;
        if(remove>amount){
            System.out.println("you dont have much amount!");
        }else{
            amount-=remove;
            System.out.println("amount removed successfully!");
        }
    }
    public void buy_stock(String symbol, String companyName, int quantity, double priceperShare,int stock_id,String passKey,Scanner sc){
        if(verifyPassword(passKey, sc)==false)return;
        if(priceperShare*quantity>amount){
            System.out.println("can't buy this stock as there is not enough money available");
            return ;
        }
        amount-=priceperShare*quantity;
        holdings.add(new Stock(symbol, companyName, quantity, priceperShare, stock_id));
        System.out.println("stock brought successfully");
        return;
    }
    public void update_stock(int stockid,double new_price){
        for(Stock s:holdings){
            if(s.get_stock_id()==stockid){
                s.updatePrice(new_price);
            }
        }
    }
    public void sell_all_stocks(String passKey,Scanner sc){
        if(verifyPassword(passKey, sc)==false)return;
        for(Stock s:holdings){
            amount+=s.get_value();
        }
        holdings.removeAllElements();
        System.out.println("new Amount"+amount);
        System.out.println("all stocks sold");
        return;
    }
    public void sell_stocks_of_certain_company(String company,String passKey,Scanner sc){
        if(verifyPassword(passKey, sc)==false)return;
        Iterator<Stock> traverese=holdings.iterator();
        while(traverese.hasNext()){
            Stock s=traverese.next();
            if(s.get_company_name().equals(company)){
                amount+=s.get_value();
                traverese.remove();
            }
        }

    }
    public void sell_certain_stock(int stockid,String passKey,Scanner sc){
        if(verifyPassword(passKey, sc)==false)return;
                            Iterator<Stock> traverese=holdings.iterator();
        while(traverese.hasNext()){
            Stock s=traverese.next();
            if(s.get_stock_id()==stockid){
                amount+=s.get_value();
                traverese.remove();
                break;
            }
        }
    }

}
public class project{
        public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Portfolio Manager!");

        
        System.out.print("Set your portfolio password: ");
        String password = sc.nextLine();

        System.out.print("Enter initial amount: ");
        double initialAmount = sc.nextDouble();
        sc.nextLine(); 

        portfolio myPortfolio = new portfolio(password, initialAmount);

        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Display all stocks");
            System.out.println("2. Display stocks of a company");
            System.out.println("3. Display stocks with highest price per share");
            System.out.println("4. Display stocks with lowest price per share");
            System.out.println("5. Display stocks in profit");
            System.out.println("6. Display stocks in loss");
            System.out.println("7. Display current amount");
            System.out.println("8. Add amount");
            System.out.println("9. Remove amount");
            System.out.println("10. Buy stock");
            System.out.println("11. Sell all stocks");
            System.out.println("12. Sell stocks of a company");
            System.out.println("13. Sell a certain stock");
            System.out.println("14. Update stock price");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    myPortfolio.display_all_stocks();
                    break;
                case 2:
                    System.out.print("Enter company name: ");
                    String company = sc.nextLine();
                    myPortfolio.display_stocks_of_certain_company(company);
                    break;
                case 3:
                    myPortfolio.display_all_stocks_with_highest_price_per_share();
                    break;
                case 4:
                    myPortfolio.display_all_stocks_with_lowest_price_per_share();
                    break;
                case 5:
                    myPortfolio.display_stocks_currently_in_profit();
                    break;
                case 6:
                    myPortfolio.display_stocks_currently_in_loss();
                    break;
                case 7:
                    myPortfolio.display_amount();
                    break;
                case 8:
                    System.out.print("Enter amount to add: ");
                    double addAmt = sc.nextDouble();
                    sc.nextLine();
                    myPortfolio.add_amount(addAmt);
                    break;
                case 9:
                    System.out.print("Enter amount to remove: ");
                    double removeAmt = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter password: ");
                    String passKeyRemove = sc.nextLine();
                    myPortfolio.remove_amount(removeAmt, passKeyRemove, sc);
                    break;
                case 10:
                    System.out.print("Enter stock symbol: ");
                    String symbol = sc.nextLine();
                    System.out.print("Enter company name: ");
                    String companyName = sc.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();
                    System.out.print("Enter price per share: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter stock id: ");
                    int stockId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter password: ");
                    String passKeyBuy = sc.nextLine();
                    myPortfolio.buy_stock(symbol, companyName, quantity, price, stockId, passKeyBuy, sc);
                    break;
                case 11:
                    System.out.print("Enter password: ");
                    String passKeySellAll = sc.nextLine();
                    myPortfolio.sell_all_stocks(passKeySellAll, sc);
                    break;
                case 12:
                    System.out.print("Enter company name: ");
                    String sellCompany = sc.nextLine();
                    System.out.print("Enter password: ");
                    String passKeySellCompany = sc.nextLine();
                    myPortfolio.sell_stocks_of_certain_company(sellCompany, passKeySellCompany, sc);
                    break;
                case 13:
                    System.out.print("Enter stock id: ");
                    int sellStockId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter password: ");
                    String passKeySellStock = sc.nextLine();
                    myPortfolio.sell_certain_stock(sellStockId, passKeySellStock, sc);
                    break;
                case 14:
                    System.out.print("Enter stock id to update: ");
                    int updateId = sc.nextInt();
                    System.out.print("Enter new price: ");
                    double newPrice = sc.nextDouble();
                    sc.nextLine();
                    myPortfolio.update_stock(updateId, newPrice);
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting Portfolio Manager. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }

        sc.close();
    }
}