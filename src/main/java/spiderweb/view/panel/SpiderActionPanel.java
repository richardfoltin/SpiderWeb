/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.RIGHT_ALIGNMENT;
import javax.swing.JScrollPane;

import spiderweb.view.button.SpiderBigButton;
import spiderweb.view.button.SpiderButton;
import spiderweb.view.constant.SpiderColor;
import spiderweb.view.constant.SpiderFont;
import static spiderweb.view.panel.SpiderPanel.SIDE_MARGIN;
import static spiderweb.view.MainWindow.WINDOW_WIDTH;
import spiderweb.view.button.SpiderSmallButton;
import spiderweb.view.table.SpiderTable;

/**
 *
 * @author pokemonterkep
 */
public abstract class SpiderActionPanel extends SpiderPanel {

    public static final int FOOTER_PADDING = 20;
    public static final int FOOTER_HEIGHT = 80;
    
    protected JPanel footer;
    protected JLabel header;
    
    protected SpiderButton backButton;
    protected SpiderButton actionButton;
    
    public SpiderActionPanel(String title, String actionButtonText) {
        super();
        
        header = new JLabel(title.toUpperCase());
        header.setOpaque(true);
        header.setBackground(SpiderColor.HIGHTLIGHT_LOW);
        header.setForeground(SpiderColor.BLACK);
        header.setFont(SpiderFont.HEADER2);
        header.setVerticalAlignment(JLabel.CENTER);
        header.setVerticalTextPosition(JLabel.CENTER);
        header.setBorder(new EmptyBorder(5, SIDE_MARGIN, 5, SIDE_MARGIN));
        add(header, BorderLayout.PAGE_START);

        footer = new JPanel();
        footer.setLayout(new BorderLayout());
        footer.setPreferredSize(new Dimension(WINDOW_WIDTH, FOOTER_HEIGHT));
        add(footer, BorderLayout.PAGE_END);
        footer.setBorder(new EmptyBorder(FOOTER_PADDING,SIDE_MARGIN,FOOTER_PADDING,SIDE_MARGIN));

        backButton = new SpiderSmallButton("Back");
        backButton.setAlignmentX(LEFT_ALIGNMENT);
        backButton.addActionListener(backAction());
        footer.add(backButton, BorderLayout.LINE_START);

        actionButton = new SpiderSmallButton(actionButtonText);
        actionButton.setAlignmentX(RIGHT_ALIGNMENT);
        actionButton.addActionListener(actionAction());
        footer.add(actionButton, BorderLayout.LINE_END);
    }
  
    protected JScrollPane addScrollPane(SpiderTable table) {
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }    
    
    protected abstract ActionListener backAction();
    
    protected abstract ActionListener actionAction();
}
