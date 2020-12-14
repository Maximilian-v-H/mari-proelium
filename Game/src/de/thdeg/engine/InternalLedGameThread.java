// Attention: Do not change anything in this file
// This file is part of the visualization environment that is going to change during the project
// If you change something here, your final project will not work!

// package de.thdeg.game.runtime;

import javax.swing.*;

public class InternalLedGameThread {

    public static InternalLedGameSimulator sim = null;

    /**
     * TODO: Via Depencency Injection den entsprechenden Simulator / Implementierung laden.
     * Dann wegabstrahieren
     */

    public static int getKeyboard() { return InternalLedGameSimulator.getKeyboard(); }

    public static void startGame() {
        InternalLedGameThread.sim = new InternalLedGameSimulator();
    }

    public static void showImage(short[] image) {
        InternalLedGameSimulator.showImage(image);
    }

    public static void run() {
        main(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            startGame();
        });
    }
}
