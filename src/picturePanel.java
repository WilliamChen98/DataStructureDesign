import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

public class picturePanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 7637966191176096259L;
    private Graph graph;
    private InfoCatcher info;
    private int paintMode;
    private int index;
    private Point2D start;
    private Point2D end;
    private int edgeWeight;
    public final int DRAWMAP = 0;
    public final int DRAWPOINT = 1;
    public final int DELETEPOINT = 2;
    public final int ADDEDGE = 3;

    private JPanel informationPanel;
    private JPanel edgePanel;

    private JTextField companyName;
    private JTextField companyAddress;
    private JTextField companyTel;
    private JTextField companyIsCapital;
    private JTextField startCompany;
    private JTextField endCompany;
    private JTextField weight;

    public picturePanel(Graph g, InfoCatcher i) {
        super();
        this.graph = g;
        this.info = i;
        this.paintMode = 0;
        this.setVisible(true);

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

        this.startCompany = new JTextField("defaultName1");
        this.endCompany = new JTextField("defaultName2");
        this.weight = new JTextField("-1");
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

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                for (int i = 0; i < 20; i++) {
                    if (event.getX() >= info.x[i] - 15 && event.getX() <= info.x[i] + 15
                            && event.getY() >= info.y[i] - 15 && event.getY() <= info.y[i] + 15) {
                        index = i;
                    }
                }
            }
        });

        add(this.informationPanel);
        add(this.edgePanel);
    }

    public void setPaintMode(int mode) {
        this.paintMode = mode;
    }

    public void setInfoCatcher(InfoCatcher infoToBeSet) {
        this.info = infoToBeSet;
    }

    public void setTextFields(int num) {
        this.companyName.setText(this.info.getName(num));
        this.companyAddress.setText(this.info.getAddr(num));
        this.companyTel.setText(this.info.getTel(num));
        this.companyIsCapital.setText("" + this.info.getIsCapital(num));
    }

    public InfoCatcher getInfoCatcher() {
        return this.info;
    }

    public int getIndex() {
        return this.index;
    }

    public void catchTextFromTextFieldToInfoCatcher() {
        this.info.name[index] = this.companyName.getText();
        this.info.addr[index] = this.companyAddress.getText();
        this.info.tel[index] = this.companyTel.getText();
        this.info.isCapital[index] = Boolean.parseBoolean(this.companyIsCapital.getText());
        this.edgeWeight = Integer.parseInt(this.weight.getText());
    }

    public void paintComponent() {
        Graphics g = getGraphics();
        super.paintComponent(g);
        switch (this.paintMode) {
        case DRAWMAP:
            this.paintMap(g);
            break;
        case DRAWPOINT:
            this.paintPoint();
            break;
        case DELETEPOINT:
            this.deletePoint();
            break;
        case ADDEDGE:
            this.addEdge();
            break;
        }

    }

    private void paintMap(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D circle = new Ellipse2D.Double();
        Point2D center = new Point2D.Double();
        Point2D corner = new Point2D.Double();
        for (int i = 0; i < 20; i++) {
            if (info.x[i] != -100) {
                center.setLocation(info.x[i], info.y[i]);
                corner.setLocation(info.x[i] + 30, info.y[i] + 30);
                circle.setFrameFromCenter(center, corner);
                g2.draw(circle);
            }
        }
        for (int i = 0; i < 20; i++) {
            for (Edge edge = graph.FirstEdge(i); graph.isEdge(edge); edge = graph.NextEdge(edge)) {
                Point2D from = new Point2D.Double(info.x[edge.from], info.y[edge.from]);
                Point2D to = new Point2D.Double(info.x[edge.to], info.y[edge.to]);
                if (from.getX() >= 0 && to.getX() >= 0) {
                    g2.draw(new Line2D.Double(from, to));
                    g2.drawString("" + edge.weight, (info.x[edge.from] + info.x[edge.to]) / 2 - 15,
                            (info.y[edge.from] + info.y[edge.to]) / 2 - 15);
                }

            }
        }
        add(this.informationPanel);
        add(this.edgePanel);
    }

    private void paintPoint() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                // TODO Auto-generated method stub
                int i = 0;
                for (i = 0; i < 20; i++) {
                    if (info.x[i] == -100) {
                        break;
                    }
                }
                if (i < 20) {
                    info.x[i] = event.getX();
                    info.y[i] = event.getY();
                    index = i;
                }
                paintMode = 0;
                paintComponent();
            }
        });
    }

    private void deletePoint() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                // TODO Auto-generated method stub
                int i = 0;
                for (i = 0; i < 20; i++) {
                    if (event.getX() >= info.x[i] - 15 && event.getX() <= info.x[i] + 15
                            && event.getY() >= info.y[i] - 15 && event.getY() <= info.y[i] + 15) {
                        info.name[i] = "defaultName";
                        info.addr[i] = "defaultAddr";
                        info.tel[i] = "00000000";
                        info.x[i] = -100;
                        info.y[i] = -100;
                        info.isCapital[i] = false;
                        index = i;
                        for (int j = 0; j < 20; j++) {
                            graph.delEdge(i, j);
                        }
                        break;
                    }
                }
                paintMode = 0;
                paintComponent();
            }
        });
    }

    private void addEdge() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                start = new Point2D.Double(event.getX(), event.getY());
            }

            public void mouseReleased(MouseEvent event) {
                int startPosition = 0;
                int endPosition = 0;
                end = new Point2D.Double(event.getX(), event.getY());
                for (int i = 0; i < 20; i++) {
                    if (start.getX() >= info.x[i] - 15 && start.getX() <= info.x[i] + 15) {
                        startPosition = i;
                    }
                    if (end.getY() >= info.y[i] - 15 && end.getY() <= info.y[i] + 15) {
                        endPosition = i;
                    }
                }
                graph.setEdge(startPosition, endPosition, edgeWeight);
            }
        });
    }
}
