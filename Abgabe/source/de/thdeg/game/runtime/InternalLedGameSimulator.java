// Attention: Do not change anything in this file
// This file is part of the visualization environment that is going to change during the project
// If you change something here, your final project will not work!

package de.thdeg.game.runtime;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class InternalLedGameSimulator extends JFrame {

    private static boolean dirty = true;

    class FunctionalAction extends AbstractAction {

        ActionListener myaction;

        public FunctionalAction(ActionListener customaction) {
            this.myaction = customaction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            myaction.actionPerformed(e);
        }

        public ActionListener getMyaction() {
            return myaction;
        }

        public void setMyaction(ActionListener myaction) {
            this.myaction = myaction;
        }

    }

    private static final int ledWidth = 48;
    private static final int ledHeight = 24;
    private static final int ledRect = 20;
    private static final int ledPadding = 1;
    private static final int ledOctaPadding = 10;
    private static final Color ledBackgroundColor = Color.LIGHT_GRAY;
    private static final int GAME_REPAINT_DELAY = 20;

    private static short[] ledImage;
    private int[][] position;
    private static volatile int direction;
    private static final Object directionLock = new Object();
    private static InternalLedGameSimulator instance = null;

    private final JLabel canvasDisplay;
    private final Graphics2D canvas;

    InternalLedGameSimulator() {
        setSize(ledWidth * ledRect + (ledWidth - 1) / 8 * ledOctaPadding, ledHeight * ledRect + (ledHeight * 2) / 8 * ledOctaPadding);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);

        BufferedImage bi = new BufferedImage(
                ledWidth * ledRect + (ledWidth - 1) / 8 * ledOctaPadding,
                ledHeight * ledRect + (ledHeight - 1) / 8 * ledOctaPadding,
                BufferedImage.TYPE_INT_RGB);
        RenderingHints antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvasDisplay = new JLabel(new ImageIcon(bi));

        InputMap inputMap = canvasDisplay.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = canvasDisplay.getActionMap();
        setupKeys(inputMap, actionMap);

        this.add(canvasDisplay);

        canvas = bi.createGraphics();
        canvas.setRenderingHints(antialiasing);
        setup();

        SwingUtilities.invokeLater(()-> { repaintLoop(); });
    }

    void repaintLoop() {
        try {
            Thread.sleep( GAME_REPAINT_DELAY );
        } catch (InterruptedException e) {
            return;
        }
        this.draw();
        SwingUtilities.invokeLater(()-> {
            repaintLoop();
        });

    }

    private void setupKeys(InputMap inputMap, ActionMap actionMap) {
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
        inputMap.put(KeyStroke.getKeyStroke("UP"), "UP");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "DOWN");

        actionMap.put("LEFT", new FunctionalAction(e -> {
            synchronized (directionLock) { direction = 2; }
        }));
        actionMap.put("RIGHT", new FunctionalAction(e -> {
            synchronized (directionLock) { direction = 3; }
        }));
        actionMap.put("UP", new FunctionalAction(e -> {
            synchronized (directionLock) { direction = 0; }
        }));
        actionMap.put("DOWN", new FunctionalAction(e -> {
            synchronized (directionLock) { direction = 1; }
        }));
    }

    static int getKeyboard() {
        int returnDirection = 0;

        synchronized (directionLock) {
            returnDirection = direction;
            direction = -1;
        }
        return returnDirection;
    }

    void setup() {

        ledImage = new short[ledWidth * ledHeight * 3];
        position = new int[ledWidth * ledHeight][2];
        direction = -1;

        canvas.setColor(ledBackgroundColor);
        canvas.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Prefill position cache...
        for (int h = 0; h < ledHeight; h++) {
            for (int w = 0; w < ledWidth; w++) {
                position[w + h * ledWidth][0] = ((ledRect * w + w / 8 * ledOctaPadding));
                position[w + h * ledWidth][1] = ((ledRect * h + h / 8 * ledOctaPadding));
            }
        }
    }

    /**
     * Submits the draw job.
     * Works by copying data into an internal buffer.
     * Periodically the draw function is called
     */
    static void showImage(short[] image) {
        System.arraycopy(image, 0, ledImage, 0, Integer.min(image.length, ledImage.length));
        synchronized (directionLock) {
            dirty = true;
        }

    }

    /**
     * Draws leds on canvas
     */
    void draw() {

        synchronized (directionLock) {
            if (dirty == false) return;
            dirty = false;
        }

        Color color = null;
        for (int i = 0; i < ledWidth * ledHeight; i++) {

            // Optimize object creation and draw performance
            if (
                  (color == null) || (
                    (ledImage[(i-1) * 3 + 0] != ledImage[i * 3 + 0]) ||
                    (ledImage[(i-1) * 3 + 1] != ledImage[i * 3 + 1]) ||
                    (ledImage[(i-1) * 3 + 2] != ledImage[i * 3 + 2])
                  )
            )
                color = new Color(ledImage[i * 3 + 0], ledImage[i * 3 + 1], ledImage[i * 3 + 2]);
            ellipse(position[i][0], position[i][1], ledRect, ledRect, color);
        }
        // Ist thread save
        this.canvasDisplay.repaint();

    }

    /**
     * Draws the LED
     */
    private void ellipse(int posx, int posy, int sizex, int sizey, Color color) {
        // fill the BG
        canvas.setColor(ledBackgroundColor);
        canvas.fillRect(posx, posy, sizex, sizey);

        // draw the circle
        canvas.setColor(ledBackgroundColor.darker());
        canvas.drawOval(posx, posy, ledRect, ledRect);
        canvas.setColor(color);
        canvas.fillOval(posx + ledPadding, posy + ledPadding, ledRect - ledPadding, ledRect - ledPadding);
    }

    // Just for testing the main setup
    static public void main(String[] passedArgs) {
        Runnable swingStarter = new Runnable() {
            @Override
            public void run() {
                new InternalLedGameSimulator();
            }
        };

        SwingUtilities.invokeLater(swingStarter);
    }
}
