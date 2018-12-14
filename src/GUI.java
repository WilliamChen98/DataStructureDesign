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
    private picturePanel picture;

    private JButton loadMap;
    private JButton addNode;
    private JButton deleteNode;
    private JButton saveMap;
    private JButton changeInformation;
    private JButton addEdge;
    private JButton delEdge;
    private JButton shortestPath;
    private JButton minSpanTree;

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
        this.start = 0;
        this.end = 0;

        this.loadMap = new JButton("载入地图");
        this.addNode = new JButton("添加顶点");
        this.deleteNode = new JButton("删除顶点");
        this.saveMap = new JButton("保存地图");
        this.changeInformation = new JButton("修改信息");
        this.addEdge = new JButton("添加通路");
        this.delEdge = new JButton("删除通路");
        this.shortestPath = new JButton("最短路径");
        this.minSpanTree = new JButton("最小生成树");

        this.companyName = new JTextField("defaultName");
        this.companyAddress = new JTextField("defaultAddr");
        this.companyTel = new JTextField("0000-0000");
        this.companyIsCapital = new JTextField("false");
        JLabel informationTitle = new JLabel("公司信息一览");
        informationTitle.setFont(new Font("楷体", Font.BOLD, 28));
        JLabel companyNameLabel = new JLabel("公司名称：");
        JLabel companyAddrLabel = new JLabel("公司地址：");
        JLabel companyTelLabel = new JLabel("公司电话：");
        JLabel companyIsCapitalLabel = new JLabel("是否为总部：");

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

        this.startCompany = new JTextField("马甸");
        this.endCompany = new JTextField("defaultName2");
        this.weight = new JTextField("-1");
        JLabel edgeTitle = new JLabel("边相关设置");
        edgeTitle.setFont(new Font("楷体", Font.BOLD, 28));
        JLabel startCompanyLabel = new JLabel("起点公司：");
        JLabel endCompanyLabel = new JLabel("终点公司：");
        JLabel weightLabel = new JLabel("公司距离：");

        this.edgePanel = new JPanel();
        layout = new GroupLayout(edgePanel);
        this.edgePanel.setLayout(layout);
        GroupLayout.SequentialGroup hGroupForEdge = layout.createSequentialGroup();
        hGroupForEdge.addGap(5);
        hGroupForEdge.addGroup(layout.createParallelGroup().addComponent(startCompanyLabel)
                .addComponent(endCompanyLabel).addComponent(weightLabel));
        hGroupForEdge.addGap(5);
        hGroupForEdge.addGroup(layout.createParallelGroup().addComponent(edgeTitle).addComponent(startCompany)
                .addComponent(endCompany).addComponent(weight));
        hGroupForEdge.addGap(5);
        layout.setHorizontalGroup(hGroupForEdge);
        GroupLayout.SequentialGroup vGroupForEdge = layout.createSequentialGroup();
        vGroupForEdge.addGap(10);
        vGroupForEdge.addGroup(layout.createParallelGroup().addComponent(edgeTitle));
        vGroupForEdge.addGap(10);
        vGroupForEdge.addGroup(layout.createParallelGroup().addComponent(startCompanyLabel).addComponent(startCompany));
        vGroupForEdge.addGroup(layout.createParallelGroup().addComponent(endCompanyLabel).addComponent(endCompany));
        vGroupForEdge.addGroup(layout.createParallelGroup().addComponent(weightLabel).addComponent(weight));
        layout.setVerticalGroup(vGroupForEdge);

        this.picture = new picturePanel(this.graph, this.info);
        /* Initialize picturePanel,which is to display the graph */

        this.loadMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.setPaintMode(picturePanel.DRAWMAP);
                picture.paintComponent();
            }

        });
        this.addNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.setPaintMode(picturePanel.DRAWPOINT);
                picture.paintComponent();
            }
        });
        this.deleteNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.setPaintMode(picturePanel.DELETEPOINT);
                picture.paintComponent();
            }
        });
        this.saveMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph = picture.getGraph();
                graph.writeMatrixToFile("src/Matrix.txt");
                info = picture.getInfo();
                info.writeLineToFile("src/Info.txt");
            }
        });
        this.changeInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catchTextFromTextFieldToInfoCatcher(picture.getIndex());
                info = picture.getInfo();
                info.writeLineToFile("src/Info.txt");
                picture.setPaintMode(picturePanel.DRAWMAP);
                picture.paintComponent();
            }
        });
        this.addEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catchStartAndEndIndex();
                picture.setStartAndEndIndex(start, end);
                picture.setEdgeWeight(Integer.parseInt(weight.getText()));
                picture.setPaintMode(picturePanel.ADDEDGE);
                picture.paintComponent();
            }
        });
        this.delEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catchStartAndEndIndex();
                picture.setStartAndEndIndex(start, end);
                picture.setPaintMode(picturePanel.DELETEEDGE);
                picture.paintComponent();
            }

        });
        this.shortestPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catchStartAndEndIndex();
                picture.setStartAndEndIndex(start, end);
                picture.setPaintMode(picturePanel.SHORTESTPATH);
                picture.paintComponent();
            }
        });
        this.minSpanTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.setPaintMode(picturePanel.MST);
                picture.paintComponent();
            }
        });
        this.picture.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                for (int i = 0; i < 20; i++) {
                    if (event.getX() >= info.getX(i) - 30 && event.getX() <= info.getX(i) + 30
                            && event.getY() >= info.getY(i) - 30 && event.getY() <= info.getY(i) + 30) {
                        setTextFromInfoCatcherToTextFeild(i);
                        break;
                    }
                }
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
        container.add(delEdge, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 1;
        constraint.gridx = 4;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.PAGE_START;
        container.add(shortestPath, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 1;
        constraint.gridx = 6;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.weightx = 0.01;
        constraint.weighty = 0.01;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.anchor = GridBagConstraints.PAGE_START;
        container.add(minSpanTree, constraint);

        constraint = new GridBagConstraints();
        constraint.gridy = 2;
        constraint.gridx = 0;
        constraint.gridwidth = 8;
        constraint.gridheight = 10;
        constraint.weightx = 1.0;
        constraint.weighty = 1.0;
        constraint.fill = GridBagConstraints.BOTH;
        container.add(picture, constraint);

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

        this.pack();
        this.setVisible(true);

    }

    private void setTextFromInfoCatcherToTextFeild(int num) {
        this.companyName.setText(this.info.getName(num));
        this.companyAddress.setText(this.info.getAddr(num));
        this.companyTel.setText(this.info.getTel(num));
        this.companyIsCapital.setText("" + this.info.getIsCapital(num));
    }

    private void catchTextFromTextFieldToInfoCatcher(int num) {
        this.info.setName(this.companyName.getText(), num);
        this.info.setAddr(this.companyAddress.getText(), num);
        this.info.setTel(this.companyTel.getText(), num);
        this.info.setIsCapital(Boolean.parseBoolean(this.companyIsCapital.getText()), num);
    }

    private void catchStartAndEndIndex() {
        for (int i = 0; i < 20; i++) {
            if (this.startCompany.getText().equals(info.getName(i))) {
                start = i;
            }
            if (this.endCompany.getText().equals(info.getName(i))) {
                end = i;
            }
        }
    }
}
