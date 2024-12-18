import java.rmi.Naming;
import java.rmi.RemoteException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConverterClient {

    private CurrencyConverterInterface currencyConverter;

    public CurrencyConverterClient() {
        try {
            currencyConverter = (CurrencyConverterInterface) Naming.lookup("rmi://localhost:1099/CurrencyConverter");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the currency converter server.");
            System.exit(1);
        }

        JFrame frame = new JFrame("Currency Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JTextField amountField = new JTextField();
        JComboBox<String> fromCurrencyBox = new JComboBox<>(new String[]{"USD", "EUR", "MAD"});
        JComboBox<String> toCurrencyBox = new JComboBox<>(new String[]{"USD", "EUR", "MAD"});
        JLabel resultLabel = new JLabel("Converted Amount: ");

        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("From Currency:"));
        panel.add(fromCurrencyBox);
        panel.add(new JLabel("To Currency:"));
        panel.add(toCurrencyBox);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    String fromCurrency = (String) fromCurrencyBox.getSelectedItem();
                    String toCurrency = (String) toCurrencyBox.getSelectedItem();
                    double convertedAmount = currencyConverter.convertCurrency(amount, fromCurrency, toCurrency);
                    resultLabel.setText("Converted Amount: " + String.format("%.2f", convertedAmount));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number for the amount.");
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error in conversion. Please try again.");
                }
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(convertButton, BorderLayout.SOUTH);
        frame.add(resultLabel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CurrencyConverterClient();
            }
        });
    }
}
