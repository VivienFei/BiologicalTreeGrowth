package edu.neu.csye6200.bg;

import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

/**
 * @author fjj1213
 */
public class BGCanvas extends JPanel {

    private static final long serialVersionUID = 1L;
    private Logger log = Logger.getLogger(BGCanvas.class.getName());
    private int lineSize = 20;
    private Color col = Color.red;
    private long counter = 0L;
    private BGGenerationSet bgGenerationSet;

    double maxRows;
    double maxColoumn;
    double cellSize;

    private Draw draw = null;

    public Draw getDraw() {
        return draw;
    }

    public void setDraw(Draw draw) {
        this.draw = draw;
    }

    private boolean isSleep = true;
    private boolean isPause = false;

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }

    public boolean isSleep() {
        return isSleep;
    }

    public void setSleep(boolean isSleep) {
        this.isSleep = isSleep;
    }

    private static final int X_SPACE = 20;
    private static final int Y_SPACE = 400;
    private Graphics2D g2d = null;

    public BGGenerationSet getBgGenerationSet() {

        return bgGenerationSet;
    }

    public void setBgGenerationSet(BGGenerationSet bgGenerationSet) {
        this.bgGenerationSet = bgGenerationSet;
    }

    /**
     * CellAutCanvas constructor
     */
    public BGCanvas() {
        col = Color.CYAN;
    }

    /**
     * The UI thread calls this method when the screen changes, or in response
     * to a user initiated call to repaint();
     */
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Draw the CA graphics panel
     *
     * @param g
     *
     */
    public class Draw implements Runnable {

        public Thread t;

        public void start() {
            if (t == null) {
                t = new Thread(this);
            }
            t.start();
        }

        synchronized void resume() {
            isPause = false;
            notify();
        }

        @Override
        public void run() {
            g2d = (Graphics2D) getGraphics();
            //draw a colorful background canvas for the tree growth
            AffineTransform tra = new AffineTransform();
            Dimension size = getSize();
            size.setSize(1500, 1500);
            g2d.scale(3.0, 3.0);
            g2d.setTransform(tra);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, size.width * 2, size.height * 2);
            cellSize = 10;
            maxRows = size.height / cellSize;
            maxColoumn = size.width / cellSize;
            //this nested for loops draws cells on fame.
            for (int x = 0; x <= maxRows - 1; x++) {
                for (int y = 0; y <= maxColoumn - 1; y++) {
                    int redVal = validColor(x * 5);
                    int greenVal = validColor(255 - x * 5);
                    Color col = new Color(redVal, redVal, greenVal);

                    paintCell(g2d, x * cellSize, y * cellSize, cellSize - 1, col);
                }

            }

            int i = 10;
            ArrayList<BGGeneration> genList = bgGenerationSet.getGenList();

            for (BGGeneration bgGen : genList) {
                ArrayList<BGStem> stemList = bgGen.getStemList();
                i += 140;
                for (BGStem stem : stemList) {
                    Position start = stem.getStartPos();
                    Position end = stem.getTipPosition();

                    int startx = (int) start.getX() + X_SPACE + i;
                    int starty = (int) start.getY() + Y_SPACE;
                    int endx = (int) end.getX() + X_SPACE + i;
                    int endy = (int) end.getY() + Y_SPACE;

                    try {
                        if (isSleep) {
                            paintLine(g2d, startx, starty, endx, endy, new Color(255 - stem.age * 10, 255 - stem.age * 20, 255 - stem.age * 10));
                            Thread.sleep(200);

                            synchronized (this) {
                                while (isPause) {

                                    wait();

                                }
                            }

                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    public void drawBG(Graphics g, boolean flag) {
        isSleep = flag;
        draw = new Draw();
        draw.start();

    }

    /**
     * A convenience routine to set the color and draw a line
     *
     * @param g2d the 2D Graphics context
     * @param startx the line start position on the x-Axis
     * @param starty the line start position on the y-Axis
     * @param endx the line end position on the x-Axis
     * @param endy the line end position on the y-Axis
     * @param color the line color
     */
    private void paintLine(Graphics2D g2d, int startx, int starty, int endx, int endy, Color color) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(startx, starty, endx, endy);
    }
//paint cell

    private void paintCell(Graphics2D g2d, double x, double y, double size, Color color) {

        g2d.setColor(color);
        g2d.fillRect((int) x, (int) y, (int) size, (int) size);

    }
//decide whether the color is valid

    private int validColor(int colorVal) {
        if (colorVal > 255) {
            colorVal = 255;
        }
        if (colorVal < 0) {
            colorVal = 0;
        }
        return colorVal;
    }
}
