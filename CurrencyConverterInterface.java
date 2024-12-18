import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CurrencyConverterInterface extends Remote {
    double convertCurrency(double amount, String fromCurrency, String toCurrency) throws RemoteException;
}
