package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-11 <br>
 * Time: 16:50 <br>
 * Project: ShoeWorld <br>
 */
public class Window extends JPanel {

    // Panels
    private final JPanel east = new JPanel();
    private final JPanel northWest = new JPanel(new GridLayout(3,1));
    private final JPanel southWest = new JPanel(new GridLayout(2,1));

    // Textareas
    private final JTextArea infoWindow = new JTextArea();
    private final JScrollPane sp = new JScrollPane(infoWindow);

    // Buttons
    private final JButton allShoes = new JButton("View all shoes");
    private final JButton allCategories = new JButton("View with categories");
    private final JButton addToCart = new JButton("Add to cart");

    // Textfields
    private final JTextField articleNrField = new JTextField(15);
    private final JTextField orderNrField = new JTextField(15);

    public Window(){
        this.setLayout(new BorderLayout());
        addPanels();
        setUpEastSide();
        setUpSouthBar();
        setUpNorthWestSide();
    }

    private void addPanels(){
        JPanel centerPanel = new JPanel(new GridLayout(1,2));
        this.add(centerPanel,BorderLayout.CENTER);

        JPanel west = new JPanel(new GridLayout(2,1));
        centerPanel.add(west);
        centerPanel.add(east);

        west.add(northWest);
        west.add(southWest);

        east.setBackground(Color.BLUE);
        southWest.setBackground(Color.RED);
    }

    private void setUpEastSide(){
        sp.setPreferredSize(new Dimension(400,700));
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        east.add(sp);
    }

    private void setUpNorthWestSide(){
        JLabel placeOrder = new JLabel("Place order", SwingConstants.CENTER);
        placeOrder.setFont(new Font(Font.SERIF,Font.BOLD,50));
        northWest.add(placeOrder);

        JPanel middle = new JPanel(new GridLayout(2,1));

        JPanel panel1 = new JPanel();
        JLabel articleNr = new JLabel("Article nr");
        articleNr.setFont(new Font(Font.SERIF,Font.BOLD,20));
        articleNrField.setFont(new Font(Font.SERIF,Font.BOLD,20));
        panel1.add(articleNr);
        panel1.add(articleNrField);
        middle.add(panel1);

        JPanel panel2 = new JPanel();
        JLabel orderNr = new JLabel("Order nr");
        orderNr.setFont(new Font(Font.SERIF,Font.BOLD,20));
        orderNrField.setFont(new Font(Font.SERIF,Font.BOLD,20));
        panel2.add(orderNr);
        panel2.add(orderNrField);
        middle.add(panel2);

        northWest.add(middle);

        JPanel panel3 = new JPanel();
        addToCart.setFont(new Font(Font.SERIF,Font.BOLD,40));
        panel3.add(addToCart);

        northWest.add(panel3);
    }

    private void setUpSouthWestSide(){
        // fortsätt här och gör liknande som ovan
    }

    private void setUpSouthBar(){
        JPanel panel = new JPanel();
        panel.add(allShoes);
        panel.add(allCategories);
        this.add(panel, BorderLayout.SOUTH);
    }

}
