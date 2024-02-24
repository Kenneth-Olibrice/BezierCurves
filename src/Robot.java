import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Robot extends JPanel {
    private boolean dragging = false;
    private boolean driving = false;
    public Robot(int width, int height) {
        setSize(width, height);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(driving == false) {
                    dragging = true;
                }
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
