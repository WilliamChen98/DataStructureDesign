import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GUI extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = -7010580109914133479L;
    private Graph graph;
    private InfoCatcher info;

    private Container container;
    private picturePanel picturePanel;

    private JButton loadMap;
    private JButton addNode;
    private JButton deleteNode;
    private JButton saveMap;
    private JButton changeInformation;
    private JButton addEdge;

    public GUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.info = new InfoCatcher();
        this.graph = new Graph(20);
        this.info.readLineFromFile("src/Info.txt");
        this.graph.readMatrixFromFile("src/Matrix.txt");
        this.container = this.getContentPane();

        this.loadMap = new JButton("栽入地图");
        this.addNode = new JButton("添加节点");
        this.deleteNode = new JButton("删除节点");
        this.saveMap = new JButton("保存地图");
        this.changeInformation = new JButton("修改公司信息");
        this.addEdge = new JButton("添加通路");

        this.picturePanel = new picturePanel(this.graph, this.info);
        /* Initialize picturePanel,which is to display the graph */

        this.loadMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawMap();
            }

        });
        this.addNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPoint();
            }
        });
        this.deleteNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePoint();
            }
        });
        this.saveMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                info = picturePanel.getInfoCatcher();
                info.writeLineToFile("src/Info.txt");
            }
        });
        this.changeInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picturePanel.catchTextFromTextFieldToInfoCatcher();
            }
        });
        this.addEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEdge();
                repaint();
            }
        });
        GridBagLayout gridbag = new GridBagLayout();
        container.setLayout(gridbag);
        GridBagConstraints constraint = null;

        constraint = new GridBagConstraints();
        constraint.gridy = 0;
        constraint.gridx = 0;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.anchor = GridBagConstraints.FIRST_LINE_START;
        constraint.insets = new Insets(0, 0, 0, 0);
        container.add(loadMap, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 0;
        constraint.gridx = 2;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.BOTH;
        container.add(addNode, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 0;
        constraint.gridx = 4;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.BOTH;
        container.add(deleteNode, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 0;
        constraint.gridx = 6;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.BOTH;
        container.add(changeInformation, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 0;
        constraint.gridx = 8;
        constraint.gridwidth = GridBagConstraints.REMAINDER;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.anchor = GridBagConstraints.FIRST_LINE_END;
        container.add(saveMap, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 1;
        constraint.gridx = 0;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.anchor = GridBagConstraints.LINE_START;
        container.add(addEdge, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 2;
        constraint.gridx = 0;
        constraint.gridwidth = 20;
        constraint.gridheight = 20;
        constraint.weightx = 1.0;
        constraint.weighty = 1.0;
        constraint.fill = GridBagConstraints.BOTH;
        container.add(picturePanel, constraint);

        container.setBounds(0, 0, 1600, 900);

        this.pack();
        this.setVisible(true);

    }

    private void drawMap() {
        this.picturePanel.setPaintMode(0);
        this.picturePanel.paintComponent();
    }

    private void drawPoint() {
        this.picturePanel.setPaintMode(1);
        this.picturePanel.paintComponent();
    }

    private void deletePoint() {
        this.picturePanel.setPaintMode(2);
        this.picturePanel.paintComponent();
    }

    private void addEdge() {
        this.picturePanel.setPaintMode(3);
        this.picturePanel.paintComponent();
    }
}
