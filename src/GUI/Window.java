package GUI;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
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
    private final JTextArea searchCommentGradeArea = new JTextArea(2,30);
    private final JScrollPane spSearchComment = new JScrollPane(searchCommentGradeArea);


    // Buttons
    private final JButton allShoes = new JButton("View all shoes");
    private final JButton allCategories = new JButton("View all shoes with categories");
    private final JButton addToCart = new JButton("Add to cart");
    private final JButton sendGrade = new JButton("Send grade");
    private final JButton searchAveGrade = new JButton("Check grade");

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
    }

    private void setUpEastSide(){
        spWindow.setPreferredSize(new Dimension(400,700));
        spWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        east.add(spWindow);
    }

    private void setUpNorthBar(){
        name.setBackground(new Color(76, 103, 246));
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
        northWest.setBorder(new EtchedBorder());
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
        topPanel.setBorder(new EtchedBorder());

        JLabel grade = new JLabel("Grade product", SwingConstants.CENTER);
        grade.setFont(new Font(Font.SERIF,Font.BOLD,16));

        JPanel high1 = new JPanel();
        JLabel articleNr = new JLabel("Article nr");
        articleNr.setFont(font);
        high1.add(articleNr);
        articleNrFieldGrade.setFont(font);
        high1.add(articleNrFieldGrade);

        topPanel.add(grade);
        topPanel.add(high1);

        JPanel high2 = new JPanel();
        JLabel comment = new JLabel("Comment");
        comment.setFont(font);
        high2.add(comment);
        spComment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        high2.add(spComment);
        topPanel.add(high2);

        JPanel high3 = new JPanel();
        high3.add(sendGrade);
        topPanel.add(high3);

        southWest.add(topPanel);

        JPanel lowPanel = new JPanel(new GridLayout(4,1));
        lowPanel.setBorder(new EtchedBorder());

        JLabel checkAveGrade = new JLabel("Check average grade", SwingConstants.CENTER);
        checkAveGrade.setFont(new Font(Font.SERIF,Font.BOLD,16));
        lowPanel.add(checkAveGrade);
        southWest.add(lowPanel);

        JPanel low1 = new JPanel();
        JLabel articleNr2 = new JLabel("Article nr");
        articleNr2.setFont(font);
        low1.add(articleNr2);
        checkArticleNrField.setFont(font);
        low1.add(checkArticleNrField);
        lowPanel.add(low1);

        JPanel low2 = new JPanel();
        JLabel aveGrade = new JLabel("Average grade: ");
        JLabel aveGradeScore = new JLabel("Show score here");
        low2.add(aveGrade);
        low2.add(aveGradeScore);
        low2.add(searchAveGrade);
        lowPanel.add(low2);

        JPanel low3 = new JPanel();
        JLabel searchComment = new JLabel("Comment");
        searchComment.setFont(font);
        low3.add(searchComment);
        spSearchComment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        low3.add(spSearchComment);

        lowPanel.add(low3);

    }

    public JTextArea getInfoWindow() {
        return infoWindow;
    }

    public JTextArea getCommentGradeArea() {
        return commentGradeArea;
    }

    public JTextArea getSearchCommentGradeArea() {
        return searchCommentGradeArea;
    }

    public JButton getAllShoes() {
        return allShoes;
    }

    public JButton getAllCategories() {
        return allCategories;
    }

    public JButton getAddToCart() {
        return addToCart;
    }

    public JButton getSendGrade() {
        return sendGrade;
    }

    public JButton getSearchAveGrade() {
        return searchAveGrade;
    }

    public JTextField getArticleNrFieldCartAdd() {
        return articleNrFieldCartAdd;
    }

    public JTextField getOrderNrFieldCartAdd() {
        return orderNrFieldCartAdd;
    }

    public JTextField getArticleNrFieldGrade() {
        return articleNrFieldGrade;
    }

    public JTextField getCheckArticleNrField() {
        return checkArticleNrField;
    }

    public JLabel getNameLabel() {
        return name;
    }
}
