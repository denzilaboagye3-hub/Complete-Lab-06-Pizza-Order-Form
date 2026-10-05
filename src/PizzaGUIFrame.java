import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PizzaGUIFrame extends JFrame {

    private JRadioButton thinButton;
    private JRadioButton regularButton;
    private JRadioButton deepDishButton;
    private ButtonGroup crustGroup;

    private JComboBox<String> sizeComboBox;

    private JCheckBox pepperoniBox;
    private JCheckBox sausageBox;
    private JCheckBox baconBox;
    private JCheckBox mushroomBox;
    private JCheckBox onionBox;
    private JCheckBox extraCheeseBox;

    private JTextArea receiptArea;

    private JButton orderButton;
    private JButton clearButton;
    private JButton quitButton;

    public PizzaGUIFrame() {

        setTitle("Pizza Order Form");
        setSize(750, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        createOptionsPanel();
        createReceiptPanel();
        createButtonPanel();
    }

    private void createOptionsPanel() {

        JPanel optionsPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        // CRUST PANEL
        JPanel crustPanel = new JPanel(new GridLayout(3, 1));
        crustPanel.setBorder(new TitledBorder("Crust"));

        thinButton = new JRadioButton("Thin");
        regularButton = new JRadioButton("Regular");
        deepDishButton = new JRadioButton("Deep-dish");

        crustGroup = new ButtonGroup();
        crustGroup.add(thinButton);
        crustGroup.add(regularButton);
        crustGroup.add(deepDishButton);

        crustPanel.add(thinButton);
        crustPanel.add(regularButton);
        crustPanel.add(deepDishButton);

        // SIZE PANEL
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(new TitledBorder("Size"));

        String[] sizes = {
                "Select Size",
                "Small - $8.00",
                "Medium - $12.00",
                "Large - $16.00",
                "Super - $20.00"
        };

        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        // TOPPINGS PANEL
        JPanel toppingsPanel = new JPanel(new GridLayout(3, 2));
        toppingsPanel.setBorder(new TitledBorder("Toppings - $1.00 Each"));

        pepperoniBox = new JCheckBox("Pepperoni");
        sausageBox = new JCheckBox("Sausage");
        baconBox = new JCheckBox("Bacon");
        mushroomBox = new JCheckBox("Mushrooms");
        onionBox = new JCheckBox("Onions");
        extraCheeseBox = new JCheckBox("Extra Cheese");

        toppingsPanel.add(pepperoniBox);
        toppingsPanel.add(sausageBox);
        toppingsPanel.add(baconBox);
        toppingsPanel.add(mushroomBox);
        toppingsPanel.add(onionBox);
        toppingsPanel.add(extraCheeseBox);

        optionsPanel.add(crustPanel);
        optionsPanel.add(sizePanel);
        optionsPanel.add(toppingsPanel);

        add(optionsPanel, BorderLayout.NORTH);
    }

    private void createReceiptPanel() {

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setBorder(new TitledBorder("Order Receipt"));

        add(scrollPane, BorderLayout.CENTER);
    }

    private void createButtonPanel() {

        JPanel buttonPanel = new JPanel();

        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        orderButton.addActionListener(e -> createOrder());
        clearButton.addActionListener(e -> clearForm());
        quitButton.addActionListener(e -> quitProgram());

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createOrder() {

        String crust = "";

        if (thinButton.isSelected()) {
            crust = "Thin";
        } else if (regularButton.isSelected()) {
            crust = "Regular";
        } else if (deepDishButton.isSelected()) {
            crust = "Deep-dish";
        }

        if (crust.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a crust."
            );
            return;
        }

        int sizeIndex = sizeComboBox.getSelectedIndex();

        if (sizeIndex == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a pizza size."
            );
            return;
        }

        String size;
        double basePrice;

        switch (sizeIndex) {
            case 1:
                size = "Small";
                basePrice = 8.00;
                break;
            case 2:
                size = "Medium";
                basePrice = 12.00;
                break;
            case 3:
                size = "Large";
                basePrice = 16.00;
                break;
            case 4:
                size = "Super";
                basePrice = 20.00;
                break;
            default:
                return;
        }

        int toppingCount = 0;
        StringBuilder toppings = new StringBuilder();

        if (pepperoniBox.isSelected()) {
            toppings.append(String.format("%-25s $1.00%n", "Pepperoni"));
            toppingCount++;
        }

        if (sausageBox.isSelected()) {
            toppings.append(String.format("%-25s $1.00%n", "Sausage"));
            toppingCount++;
        }

        if (baconBox.isSelected()) {
            toppings.append(String.format("%-25s $1.00%n", "Bacon"));
            toppingCount++;
        }

        if (mushroomBox.isSelected()) {
            toppings.append(String.format("%-25s $1.00%n", "Mushrooms"));
            toppingCount++;
        }

        if (onionBox.isSelected()) {
            toppings.append(String.format("%-25s $1.00%n", "Onions"));
            toppingCount++;
        }

        if (extraCheeseBox.isSelected()) {
            toppings.append(String.format("%-25s $1.00%n", "Extra Cheese"));
            toppingCount++;
        }

        if (toppingCount == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select at least one topping."
            );
            return;
        }

        double subtotal = basePrice + toppingCount;
        double tax = subtotal * 0.07;
        double total = subtotal + tax;

        receiptArea.setText("");

        receiptArea.append(
                "=========================================\n"
        );

        receiptArea.append(
                String.format("%s Crust - %s%15s$%.2f%n",
                        crust, size, "", basePrice)
        );

        receiptArea.append(toppings.toString());

        receiptArea.append(
                String.format("%-25s $%.2f%n", "Sub-total:", subtotal)
        );

        receiptArea.append(
                String.format("%-25s $%.2f%n", "Tax:", tax)
        );

        receiptArea.append(
                "-----------------------------------------\n"
        );

        receiptArea.append(
                String.format("%-25s $%.2f%n", "Total:", total)
        );

        receiptArea.append(
                "=========================================\n"
        );
    }

    private void clearForm() {

        crustGroup.clearSelection();
        sizeComboBox.setSelectedIndex(0);

        pepperoniBox.setSelected(false);
        sausageBox.setSelected(false);
        baconBox.setSelected(false);
        mushroomBox.setSelected(false);
        onionBox.setSelected(false);
        extraCheeseBox.setSelected(false);

        receiptArea.setText("");
    }

    private void quitProgram() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Confirm Quit",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}