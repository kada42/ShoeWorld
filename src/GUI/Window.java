package GUI;

import javax.swing.*;
import java.awt.*;

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
    private final JScrollPane spWindow = new JScrollPane(infoWindow);
    private final JTextArea commentGradeArea = new JTextArea(2,30);
    private final JScrollPane spComment = new JScrollPane(commentGradeArea);


    // Buttons
    private final JButton allShoes = new JButton("View all shoes");
    private final JButton allCategories = new JButton("View all shoes with categories");
    private final JButton addToCart = new JButton("Add to cart");
    private final JButton sendGrade = new JButton("Send grade");

    // Textfields
    private final JTextField articleNrFieldCartAdd = new JTextField(10);
    private final JTextField orderNrFieldCartAdd = new JTextField(10);
    private final JTextField articleNrFieldGrade = new JTextField(10);
    private final JTextField checkArticleNrField = new JTextField(10);

    // Labels
    private final JLabel name = new JLabel("Hej name...");

    // Font
    Font font = new Font(Font.SERIF,Font.BOLD,20);

    public Window(){
        this.setLayout(new BorderLayout());
        addPanels();
        setUpNorthBar();
        setUpSouthBar();
        setUpEastSide();
        setUpNorthWestSide();
        setUpSouthWestSide();
    }

    private void addPanels(){
        JPanel centerPanel = new JPanel(new GridLayout(1,2));
        this.add(centerPanel,BorderLayout.CENTER);

        JPanel west = new JPanel(new GridLayout(2,1));
        centerPanel.add(west);
        centerPanel.add(east);

        west.add(northWest);
        west.add(southWest);

        east.setBackground(Color.GREEN);
        southWest.setBackground(Color.RED);
    }

    private void setUpEastSide(){
        spWindow.setPreferredSize(new Dimension(400,700));
        spWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        east.add(spWindow);
    }

    private void setUpNorthBar(){
        name.setBackground(new Color(51, 82, 245));
        name.setOpaque(true);
        name.setFont(new Font(Font.DIALOG,Font.ITALIC,16));
        this.add(name, BorderLayout.NORTH);
    }

    private void setUpSouthBar(){
        JPanel panel = new JPanel();
        panel.add(allShoes);
        panel.add(allCategories);
        this.add(panel, BorderLayout.SOUTH);
    }

    private void setUpNorthWestSide(){
        JLabel placeOrder = new JLabel("Place order", SwingConstants.CENTER);
        placeOrder.setFont(new Font(Font.SERIF,Font.BOLD,50));
        northWest.add(placeOrder);

        JPanel middle = new JPanel(new GridLayout(2,1));

        JPanel panel1 = new JPanel();
        JLabel articleNr = new JLabel("Article nr");
        articleNr.setFont(font);
        articleNrFieldCartAdd.setFont(font);
        panel1.add(articleNr);
        panel1.add(articleNrFieldCartAdd);
        middle.add(panel1);

        JPanel panel2 = new JPanel();
        JLabel orderNr = new JLabel(" Order nr");
        orderNr.setFont(font);
        orderNrFieldCartAdd.setFont(font);
        panel2.add(orderNr);
        panel2.add(orderNrFieldCartAdd);
        middle.add(panel2);

        northWest.add(middle);

        JPanel panel3 = new JPanel();
        addToCart.setFont(new Font(Font.SERIF,Font.BOLD,40));
        panel3.add(addToCart);

        northWest.add(panel3);
    }

    private void setUpSouthWestSide(){
        JPanel topPanel = new JPanel(new GridLayout(4,1));

        JLabel grade = new JLabel("Grade product", SwingConstants.CENTER);
        grade.setFont(new Font(Font.SERIF,Font.BOLD,16));

        JPanel panel1 = new JPanel();
        JLabel articleNr = new JLabel("Article nr");
        articleNr.setFont(font);
        panel1.add(articleNr);
        articleNrFieldGrade.setFont(font);
        panel1.add(articleNrFieldGrade);

        topPanel.add(grade);
        topPanel.add(panel1);

        JPanel panel2 = new JPanel();
        JLabel comment = new JLabel("Comment");
        comment.setFont(font);
        panel2.add(comment);
        spComment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel2.add(spComment);
        topPanel.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.add(sendGrade);
        topPanel.add(panel3);

        southWest.add(topPanel);

        JPanel lowPanel = new JPanel(new GridLayout(4,1));

        // Fortsätt här med att lägga till saker för den andra halvan av den nedersta. (röda)

    }

}
