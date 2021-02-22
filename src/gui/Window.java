package gui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-11 <br>
 * Time: 16:50 <br>
 * Project: ShoeWorld <br>
 */
public class Window extends JPanel {

    // Panels
    private final JPanel east = new JPanel();
    private final JPanel northWest = new JPanel(new GridLayout(4,1));
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
    private final JButton sendGrade = new JButton("Send review");
    private final JButton searchAveGrade = new JButton("See reviews");
    private final JButton viewCart = new JButton("View cart");
    private final JButton newOrder = new JButton("Start new order");

    // Textfields
    private final JTextField articleNrFieldCartAdd = new JTextField(10);
    private final JTextField orderNrFieldCartAdd = new JTextField(10);
    private final JTextField articleNrFieldGrade = new JTextField(10);
    private final JTextField checkArticleNrField = new JTextField(10);

    // Labels
    private final JLabel name = new JLabel("Hej name...");
    private final JLabel aveGradeScore = new JLabel("");

    // Lists
    private final List<JRadioButton> rateButtons = new ArrayList<>();

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
        east.setBackground(Color.LIGHT_GRAY);

        west.add(northWest);
        west.add(southWest);
    }

    private void setUpEastSide(){
        spWindow.setPreferredSize(new Dimension(500,730));
        spWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        east.add(spWindow);
        infoWindow.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        infoWindow.setEditable(false);
    }

    private void setUpNorthBar(){
        name.setBackground(new Color(76, 103, 246));
        name.setOpaque(true);
        name.setFont(new Font(Font.DIALOG,Font.ITALIC,16));
        this.add(name, BorderLayout.NORTH);
    }

    private void setUpSouthBar(){
        JPanel panel = new JPanel();
        panel.setBackground(new Color(76,103,246));
        panel.add(allShoes);
        panel.add(allCategories);
        this.add(panel, BorderLayout.SOUTH);
    }

    private void setUpNorthWestSide(){
        northWest.setBorder(new EtchedBorder());
        JLabel placeOrder = new JLabel("Place order", SwingConstants.CENTER);
        placeOrder.setFont(new Font(Font.SERIF,Font.BOLD,40));
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
        orderNrFieldCartAdd.setEditable(false);
        panel2.add(orderNr);
        panel2.add(orderNrFieldCartAdd);
        middle.add(panel2);

        northWest.add(middle);

        JPanel low = new JPanel();
        JPanel panel3 = new JPanel();
        addToCart.setFont(new Font(Font.SERIF,Font.BOLD,40));
        panel3.add(addToCart);

        low.add(panel3);

        northWest.add(low);

        JPanel panel4 = new JPanel();
        newOrder.setFont(new Font(Font.SERIF, Font.BOLD,20));
        panel4.add(newOrder);
        viewCart.setFont(new Font(Font.SERIF, Font.BOLD,20));
        panel4.add(viewCart);
        northWest.add(panel4);
    }

    private void setUpSouthWestSide(){
        JPanel topPanel = new JPanel(new GridLayout(4,1));
        topPanel.setBorder(new EtchedBorder());

        JLabel grade = new JLabel("Review product", SwingConstants.CENTER);
        grade.setFont(font);

        JPanel high1 = new JPanel();
        JLabel articleNr = new JLabel("Article nr");
        articleNr.setFont(font);
        high1.add(articleNr);
        articleNrFieldGrade.setFont(font);
        high1.add(articleNrFieldGrade);

        high1.add(sendGrade);

        topPanel.add(grade);
        topPanel.add(high1);

        List<String> grades = List.of("Very satisfied","Satisfied","A little satisfied","Not satisfied");
        ButtonGroup group = new ButtonGroup();
        JPanel high2 = new JPanel();

        for(String s : grades) rateButtons.add(new JRadioButton(s));

        for(JRadioButton rb : rateButtons){
            group.add(rb);
            high2.add(rb);
        }

        topPanel.add(high2);

        JPanel high3 = new JPanel();
        JLabel comment = new JLabel("Comment");
        comment.setFont(font);
        high3.add(comment);
        spComment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        high3.add(spComment);
        topPanel.add(high3);

        southWest.add(topPanel);

        JPanel lowPanel = new JPanel(new GridLayout(3,1));
        lowPanel.setBorder(new EtchedBorder());

        JLabel checkAveGrade = new JLabel("Check reviews", SwingConstants.CENTER);
        checkAveGrade.setFont(font);
        lowPanel.add(checkAveGrade);
        southWest.add(lowPanel);

        JPanel low1 = new JPanel();
        JLabel articleNr2 = new JLabel("Article nr");
        articleNr2.setFont(font);
        low1.add(articleNr2);
        checkArticleNrField.setFont(font);
        low1.add(checkArticleNrField);
        low1.add(searchAveGrade);
        lowPanel.add(low1);

        JPanel low2 = new JPanel();
        JLabel aveGrade = new JLabel("Average grade: ");
        aveGrade.setFont(font);
        aveGradeScore.setFont(font);
        low2.add(aveGrade);
        low2.add(aveGradeScore);
        lowPanel.add(low2);

    }

    public JTextArea getInfoWindow() {
        return infoWindow;
    }

    public JTextArea getCommentGradeArea() {
        return commentGradeArea;
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

    public JLabel getAveGradeScore() {
        return aveGradeScore;
    }

    public JButton getViewCart() {
        return viewCart;
    }

    public JButton getNewOrder() {
        return newOrder;
    }

    public List<JRadioButton> getRateButtons() {
        return rateButtons;
    }
}