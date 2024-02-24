import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ControlPoint extends JPanel {
    public boolean alive = true;
    boolean dragging = false;
    public ControlPoint(int x, int y) {
        setSize(10, 10);
        setLocation(x,y);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                alive = false;
                setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                dragging = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(dragging == false) {
                    return;
                }
                setLocation(getX() + e.getX(), getY() + e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.fillOval(0, 0, 10, 10);
    }
}
