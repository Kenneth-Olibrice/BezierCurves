import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static java.awt.event.MouseEvent.BUTTON2;
import static java.awt.event.MouseEvent.BUTTON3;

public class Window extends JFrame {
    // Top level containers
    private JPanel m_configPanel;
    private JPanel m_drawingPanel;
    private ArrayList<ControlPoint> m_controlPoints = new ArrayList<>();
    public static final double[][] pascalsTriangle = {
            {1},
            {1, 1},
            {1, 2, 1},
            {1, 3, 3, 1}
    };
    public void initialize() {
        setTitle("Bezier Curves");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Config panel properties
        m_configPanel = new JPanel();
        m_configPanel.setBorder(BorderFactory.createEtchedBorder());

        // Drawing panel properties
        m_drawingPanel = new JPanel();
        m_drawingPanel.setLayout(null); // No layout
        m_drawingPanel.setBorder(BorderFactory.createEtchedBorder());

        m_drawingPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Right click
                if(e.getButton() == BUTTON3) {
                    addControlPoint(new ControlPoint(e.getX(), e.getY()));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        add(m_drawingPanel, BorderLayout.CENTER);
        add(m_configPanel, BorderLayout.NORTH);



        setVisible(true);
    }

    public void loop() {
        for(int i = 0; i < m_controlPoints.size(); i++) {
            if(m_controlPoints.get(i).alive == false) {
                m_controlPoints.remove(i);
            }
        }
        drawBezierCurve(0.1, m_controlPoints.size());
        repaint();
    }

    public void addControlPoint(ControlPoint p) {
        m_controlPoints.add(p);
        m_drawingPanel.add(p);
    }

    public void drawLinearCurve() {
        double step = 0.01;
        ArrayList<Point> points = new ArrayList<>();

        for(double t = 0; t < 1; t+= step) {
            double xDraw = (1 - t) * m_controlPoints.get(0).getX() + t * m_controlPoints.get(1).getX();
            double yDraw = (1 - t) * m_controlPoints.get(0).getY() + t * m_controlPoints.get(1).getY();
            points.add(new Point((int) xDraw, (int) yDraw));
        }

        for(int i = 0; i < points.size() - 1; i++) {
            m_drawingPanel.getGraphics().drawLine((int) points.get(i).getX(), (int) points.get(i).getY(), (int) points.get(i + 1).getX(), (int) points.get(i + 1).getY());
        }
    }

    public void drawQuadraticCurve() {
        double step = 0.05;
        ArrayList<Point> points = new ArrayList<>();

        for(double t = 0; t < 1; t+= step) {
            double xDraw = binomialCoefficient(3, 1) * Math.pow((1 - t), 2) * m_controlPoints.get(0).getX() + binomialCoefficient (3, 2) * (1-t) * t * m_controlPoints.get(1).getX() + binomialCoefficient(3, 3) * Math.pow(t, 2) * m_controlPoints.get(2).getX();
            double yDraw = binomialCoefficient(3, 1) * Math.pow((1 - t), 2) * m_controlPoints.get(0).getY() + binomialCoefficient (3, 2) * (1-t) * t * m_controlPoints.get(1).getY() + binomialCoefficient(3, 3) * Math.pow(t, 2) * m_controlPoints.get(2).getY();
            points.add(new Point((int) xDraw, (int) yDraw));
        }

        for(int i = 0; i < points.size() - 1; i++) {
            m_drawingPanel.getGraphics().drawLine((int) points.get(i).getX(), (int) points.get(i).getY(), (int) points.get(i + 1).getX(), (int) points.get(i + 1).getY());
        }
    }

    public void drawBezierCurve(double stepValue, int numPoints) {
        ArrayList<Point> points = new ArrayList<>();
        int degree = numPoints - 1;
        for(double t = 0; t <= 1; t += stepValue) {
            double xValue = 0;
            double yValue = 0;
            for (int term = 1; term <= numPoints; term++) {
                xValue += binomialCoefficient(degree, term) * Math.pow(1 - t, degree - term + 1)
                        * Math.pow(t, term - 1) * m_controlPoints.get(term - 1).getX();
                yValue += binomialCoefficient(degree, term) * Math.pow(1 - t, degree - term + 1)
                        * Math.pow(t, term - 1) * m_controlPoints.get(term - 1).getY();
            }
            points.add(new Point((int) xValue, (int) yValue));
        }

        for(int i = 0; i < points.size() - 1; i++) {
            m_drawingPanel.getGraphics().drawLine((int) points.get(i).getX(), (int) points.get(i).getY(), (int) points.get(i + 1).getX(), (int) points.get(i + 1).getY());
        }
    }

    public double binomialCoefficient(int degree, int term) {
        return pascalsTriangle[degree][term - 1];
    }

}
