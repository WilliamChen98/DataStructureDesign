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
    private int start;
    private int end;

    private Container container;
    private picturePanel picturePanel;

    private JButton loadMap;
    private JButton addNode;
    private JButton deleteNode;
    private JButton saveMap;
    private JButton changeInformation;
    private JButton addEdge;
    private JButton shortestPath;

    private JTextField companyName;
    private JTextField companyAddress;
    private JTextField companyTel;
    private JTextField companyIsCapital;
    private JTextField startCompany;
    private JTextField endCompany;
    private JTextField weight;

    private JPanel informationPanel;
    private JPanel edgePanel;

    public GUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.info = new InfoCatcher();
        this.graph = new Graph(20);
        this.info.readLineFromFile("src/Info.txt");
        this.graph.readMatrixFromFile("src/Matrix.txt");
        this.container = this.getContentPane();
        this.start= 0;
        this.end = 0;

        this.loadMap = new JButton("�����ͼ");
        this.addNode = new JButton("���ӽڵ�");
        this.deleteNode = new JButton("ɾ���ڵ�");
        this.saveMap = new JButton("�����ͼ");
        this.changeInformation = new JButton("�޸Ĺ�˾��Ϣ");
        this.addEdge = new JButton("����ͨ·");
        this.shortestPath = new JButton("���·��");

        this.companyName = new JTextField("defaultName");
        this.companyAddress = new JTextField("defaultAddr");
        this.companyTel = new JTextField("0000-0000");
        this.companyIsCapital = new JTextField("false");
        JLabel informationTitle = new JLabel("��˾��Ϣһ��");
        informationTitle.setFont(new Font("����", Font.BOLD, 28));
        JLabel companyNameLabel = new JLabel("��˾���ƣ�");
        JLabel companyAddrLabel = new JLabel("��˾��ַ��");
        JLabel companyTelLabel = new JLabel("��˾�绰��");
        JLabel companyIsCapitalLabel = new JLabel("�Ƿ�Ϊ�ܲ���");

        this.informationPanel = new JPanel();
        /*
         * Initialize informationPanel, which contains the information of the selected
         * company. The informationPanel is using GroupLayout.
         */
        GroupLayout layout = new GroupLayout(informationPanel);
        this.informationPanel.setLayout(layout);
        GroupLayout.SequentialGroup hGroupForInformation = layout.createSequentialGroup();
        hGroupForInformation.addGap(5);
        hGroupForInformation.addGroup(layout.createParallelGroup().addComponent(companyNameLabel)
                .addComponent(companyAddrLabel).addComponent(companyTelLabel).addComponent(companyIsCapitalLabel));
        hGroupForInformation.addGap(5);
        hGroupForInformation
                .addGroup(layout.createParallelGroup().addComponent(informationTitle).addComponent(companyName)
                        .addComponent(companyAddress).addComponent(companyTel).addComponent(companyIsCapital));
        hGroupForInformation.addGap(5);
        layout.setHorizontalGroup(hGroupForInformation);
        GroupLayout.SequentialGroup vGroupForInformation = layout.createSequentialGroup();
        vGroupForInformation.addGap(10);
        vGroupForInformation.addGroup(layout.createParallelGroup().addComponent(informationTitle));
        vGroupForInformation.addGap(10);
        vGroupForInformation
                .addGroup(layout.createParallelGroup().addComponent(companyNameLabel).addComponent(companyName));
        vGroupForInformation
                .addGroup(layout.createParallelGroup().addComponent(companyAddrLabel).addComponent(companyAddress));
        vGroupForInformation
                .addGroup(layout.createParallelGroup().addComponent(companyTelLabel).addComponent(companyTel));
        vGroupForInformation.addGroup(
                layout.createParallelGroup().addComponent(companyIsCapitalLabel).addComponent(companyIsCapital));
        layout.setVerticalGroup(vGroupForInformation);

        this.startCompany = new JTextField("defaultName1");
        this.endCompany = new JTextField("defaultName2");
        this.weight = new JTextField("-1");
        JLabel startCompanyLabel = new JLabel("��㹫˾��");
        JLabel endCompanyLabel = new JLabel("�յ㹫˾��");
        JLabel weightLabel = new JLabel("��˾���룺");

        this.edgePanel = new JPanel();
        layout = new GroupLayout(edgePanel);
        this.edgePanel.setLayout(layout);
        GroupLayout.SequentialGroup hGroupForEdge = layout.createSequentialGroup();
        hGroupForEdge.addGap(5);
        hGroupForEdge.addGroup(layout.createParallelGroup().addComponent(startCompanyLabel)
                .addComponent(endCompanyLabel).addComponent(weightLabel));
        hGroupForEdge.addGap(5);
        hGroupForEdge.addGroup(
                layout.createParallelGroup().addComponent(startCompany).addComponent(endCompany).addComponent(weight));
        hGroupForEdge.addGap(5);
        layout.setHorizontalGroup(hGroupForEdge);
        GroupLayout.SequentialGroup vGroupForEdge = layout.createSequentialGroup();
        vGroupForEdge.addGap(10);
        vGroupForEdge.addGroup(layout.createParallelGroup().addComponent(startCompanyLabel).addComponent(startCompany));
        vGroupForEdge.addGroup(layout.createParallelGroup().addComponent(endCompanyLabel).addComponent(endCompany));
        vGroupForEdge.addGroup(layout.createParallelGroup().addComponent(weightLabel).addComponent(weight));
        layout.setVerticalGroup(vGroupForEdge);

        this.picturePanel = new picturePanel(this.graph, this.info);
        /* Initialize picturePanel,which is to display the graph */

        this.loadMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picturePanel.setPaintMode(picturePanel.DRAWMAP);
                picturePanel.paintComponent();
            }

        });
        this.addNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picturePanel.setPaintMode(picturePanel.DRAWPOINT);
                picturePanel.paintComponent();
            }
        });
        this.deleteNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picturePanel.setPaintMode(picturePanel.DELETEPOINT);
                picturePanel.paintComponent();
            }
        });
        this.saveMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph = picturePanel.getGraph();
                graph.writeMatrixToFile("src/Matrix.txt");
                info = picturePanel.getInfo();
                info.writeLineToFile("src/Info.txt");
            }
        });
        this.changeInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picturePanel.setPaintMode(picturePanel.DRAWMAP);
                catchTextFromTextFieldToInfoCatcher(picturePanel.getIndex());
                info = picturePanel.getInfo();
                info.writeLineToFile("src/Info.txt");
            }
        });
        this.addEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catchStartAndEndIndex();
                picturePanel.setStartAndEndIndex(start, end);
                picturePanel.setEdgeWeight(Integer.parseInt(weight.getText()));
                picturePanel.setPaintMode(3);
                picturePanel.paintComponent();
            }
        });
        this.shortestPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catchStartAndEndIndex();
                picturePanel.setStartAndEndIndex(start, end);
                picturePanel.setPaintMode(4);
                picturePanel.paintComponent();
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
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.PAGE_START;
        constraint.insets = new Insets(0, 0, 0, 0);
        container.add(loadMap, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 0;
        constraint.gridx = 2;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.PAGE_START;
        container.add(addNode, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 0;
        constraint.gridx = 4;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.PAGE_START;
        container.add(deleteNode, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 0;
        constraint.gridx = 6;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.PAGE_START;
        container.add(changeInformation, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 0;
        constraint.gridx = 8;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.PAGE_START;
        container.add(saveMap, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 1;
        constraint.gridx = 0;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.PAGE_START;
        container.add(addEdge, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 1;
        constraint.gridx = 2;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.PAGE_START;
        container.add(shortestPath, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 2;
        constraint.gridx = 0;
        constraint.gridwidth = 8;
        constraint.gridheight = 10;
        constraint.weightx = 1.0;
        constraint.weighty = 1.0;
        constraint.fill = GridBagConstraints.BOTH;
        container.add(picturePanel, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 3;
        constraint.gridx = 8;
        constraint.gridwidth = 2;
        constraint.gridheight = 3;
        constraint.weightx = 0.01;
        constraint.weighty = 1;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.CENTER;
        container.add(this.edgePanel, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 2;
        constraint.gridx = 8;
        constraint.gridwidth = 2;
        constraint.gridheight = 5;
        constraint.weightx = 0.01;
        constraint.weighty = 1;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.PAGE_START;
        container.add(this.informationPanel, constraint);

        this.pack();
        this.setVisible(true);

    }

    private void catchTextFromTextFieldToInfoCatcher(int num) {
        this.info.name[num] = this.companyName.getText();
        this.info.addr[num] = this.companyAddress.getText();
        this.info.tel[num] = this.companyTel.getText();
        this.info.isCapital[num] = Boolean.parseBoolean(this.companyIsCapital.getText());
    }
    
    private void catchStartAndEndIndex() {
        for(int i = 0;i < 20;i++) {
            if(this.startCompany.getText().equals(info.getName(i))) {
                start = i;
            }
            if(this.endCompany.getText().equals(info.getName(i))) {
                end = i;
            }
        }
    }
    
}