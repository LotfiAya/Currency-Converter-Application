import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverterServer extends UnicastRemoteObject implements CurrencyConverterInterface {

    private static final long serialVersionUID = 1L;
    private final Map<String, Double> exchangeRates;

    protected CurrencyConverterServer() throws RemoteException {
        super();
        exchangeRates = new HashMap<>();
        // Initialize exchange rates (example rates, update as needed)
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("MAD", 9.5);
        // Add more currencies and rates as needed
    }

    @Override
    public double convertCurrency(double amount, String fromCurrency, String toCurrency) throws RemoteException {
        if (exchangeRates.containsKey(fromCurrency) && exchangeRates.containsKey(toCurrency)) {
            double fromRate = exchangeRates.get(fromCurrency);
            double toRate = exchangeRates.get(toCurrency);
            return amount * (toRate / fromRate);
        } else {
            throw new RemoteException("Unsupported currency");
        }
    }

    public static void main(String[] args) {
        try {
            // Start the RMI registry on port 1099
            LocateRegistry.createRegistry(1099);
            CurrencyConverterServer server = new CurrencyConverterServer();
            Naming.rebind("rmi://localhost:1099/CurrencyConverter", server);
            System.out.println("Currency Converter Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
