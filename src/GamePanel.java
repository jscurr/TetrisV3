import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Action;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GamePanel extends JPanel {

    private final int WIDTH = 600;
    private final int HEIGHT = 1000;
    private final int COLUMNS;
    private final int ROWS;
    private final int CELL_SIZE = 60;
    private int delay;
    private boolean[][] background;
    private Color[][] colorArray;
    private Tetromino tetromino;
    private boolean[][] structure;
    private int hPosition;
    private int vPosition;
    private int level;
    private int points;
    private JLabel levelLabel;
    private JLabel pointsLabel;


    public GamePanel() {

        pointsLabel = new JLabel("Points");
        levelLabel = new JLabel("Level" + level);
        setLayout(new BorderLayout());
        add(levelLabel,BorderLayout.NORTH);
        add(pointsLabel,BorderLayout.SOUTH);
        

        points = 0;
        level = 1;
        COLUMNS = WIDTH / CELL_SIZE;
        ROWS = HEIGHT / CELL_SIZE;
        delay = 500;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        this.background = new boolean[ROWS][COLUMNS];
        this.colorArray = new Color[ROWS][COLUMNS];
        addBindings();
    }

    public void spawnTetromino(Shape shape, Hue hue) {
        tetromino = new Tetromino(shape, hue);
        this.structure = tetromino.getStructure();
        this.hPosition = tetromino.getHPosition();
        this.vPosition = tetromino.getVPosition();
    }

    // Drawing Methods
    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(getGraphics());
        setBackground(Color.GRAY);
        drawBackground(pen);
        drawTetromino(tetromino, pen);// fix this
    }

    private void drawBackground(Graphics pen) {
        pen.setColor(Color.BLUE);
        for (int r = 0; r < background.length; r++) {
            for (int c = 0; c < background[0].length; c++) {
                if (background[r][c]) {
                    pen.setColor(colorArray[r][c]);
                    pen.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    pen.setColor(Color.BLUE);
                    pen.drawRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else {
                    pen.setColor(Color.GRAY);
                    pen.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    pen.setColor(Color.BLUE);
                    pen.drawRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    private void drawTetromino(Tetromino tetromino, Graphics pen) {
        int hPixelPosition = hPosition * CELL_SIZE;
        int vPixelPosition = vPosition * CELL_SIZE;
        for (int r = 0; r < structure.length; r++) {
            for (int c = 0; c < structure[0].length; c++) {
                if (structure[r][c]) {
                    pen.setColor(tetromino.getHue().getColor());
                    pen.fillRect(c * CELL_SIZE + hPixelPosition, r * CELL_SIZE + vPixelPosition, CELL_SIZE, CELL_SIZE);
                    pen.setColor(Color.BLUE);
                    pen.drawRect(c * CELL_SIZE + hPixelPosition, r * CELL_SIZE + vPixelPosition, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    // methods to check movement
    private boolean canMoveDown() {
        for (int r = 0; r < structure.length; r++) {
            for (int c = 0; c < structure[0].length; c++) {
                // checks the tetromino structure against the bottom of the game area
                if (vPosition + structure.length == ROWS) {
                    return false;
                }
                // checks the tetromino structure against the background
                if (structure[r][c] && background[r + vPosition + 1][c + hPosition]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canMoveRight() {
        for (int r = 0; r < structure.length; r++) {
            for (int c = 0; c < structure[0].length; c++) {
                // checks the tetromino structure against the right edge of the game area
                if (hPosition + structure[0].length == COLUMNS) {
                    return false;
                }
                // checks the tetromino structure against the background
                if (structure[r][c] && background[r + vPosition][c + hPosition + 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canMoveLeft() {
        for (int r = 0; r < structure.length; r++) {
            for (int c = 0; c < structure[0].length; c++) {
                // checks the tetromino structure against the left of the game area
                if (hPosition == 0) {
                    return false;
                }
                // checks the tetromino structure against the background
                if (structure[r][c] && background[r + vPosition][c + hPosition - 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canRotateCockwise() {
        boolean[][] rotatedStructure = new boolean[structure[0].length][structure.length];
        // rotates the structure
        for (int r = 0; r < structure.length; r++) {
            for (int c = 0; c < structure[0].length; c++) {
                rotatedStructure[c][structure.length - 1 - r] = structure[r][c];
            }
        }
        // checks rotated structure against the right wall of the game area
        if (rotatedStructure[0].length + hPosition > COLUMNS) {
            return false;
        }
        // checks rotated structure against the background
        for (int r = 0; r < rotatedStructure.length; r++) {
            for (int c = 0; c < rotatedStructure[0].length; c++) {
                if (rotatedStructure[r][c] && background[r + vPosition][c + hPosition]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canRotateCounterCockwise() {
        int rowLength = this.structure.length;
        int colLength = this.structure[0].length;
        boolean[][] rotatedStructure = new boolean[colLength][rowLength];
        for (int r = 0; r < rowLength; r++) {
            for (int c = 0; c < colLength; c++) {
                rotatedStructure[colLength - 1 - c][r] = this.structure[r][c];
            }
        }
        // checks rotated structure against the right wall of the game area
        if (rotatedStructure[0].length + hPosition > COLUMNS) {
            return false;
        }
        // checks rotated structure against the background
        for (int r = 0; r < rotatedStructure.length; r++) {
            for (int c = 0; c < rotatedStructure[0].length; c++) {
                if (rotatedStructure[r][c] && background[r + vPosition][c + hPosition]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isDropping() {
        if (!canMoveDown()) {
            addToColorArray();
            addToBackground();
            return false;
        } else {
            tetromino.moveDown();
            updateTetrominoState();
        }
        repaint();
        return true;
    }

    // methods to update state
    private void addToBackground() {
        for (int r = 0; r < structure.length; r++) {
            for (int c = 0; c < structure[0].length; c++) {
                // writing information from structure to background
                if (structure[r][c]) {
                    background[r + vPosition][c + hPosition] = true;
                }
            }
        }
        clearLines();
        repaint();
    }

    private void addToColorArray() {
        for (int r = 0; r < structure.length; r++) {
            for (int c = 0; c < structure[0].length; c++) {
                // writing information from structure to colorArray
                if (structure[r][c]) {
                    colorArray[r + vPosition][c + hPosition] = tetromino.getHue().getColor();
                }
            }
        }
    }

    private void updateTetrominoState() {
        this.structure = tetromino.getStructure();
        this.vPosition = tetromino.getVPosition();
        this.hPosition = tetromino.getHPosition();
    }

    public int getDelay() {
        return this.delay;
    }

    public Shape getRandomShape() {
        Random random = new Random();
        Shape shape = Shape.values()[random.nextInt(Shape.values().length)];
        return shape;
    }

    public Hue getRandomColor() {
        Random random = new Random();
        Hue hue = Hue.values()[random.nextInt(Hue.values().length)];
        return hue;
    }

    // TODO make isGameOver method
    // TODO make points and levels and numberof lines cleared

    private void clearLines() {
        ArrayList<Integer> linesToBeCleared = findFullLines();
        boolean[][] backgroundCopy = copyBackground();
        for (int i = 0; i < linesToBeCleared.size(); i++) {
           for(int r = 1; r <= linesToBeCleared.get(i); r++){
             background[r] = backgroundCopy[r-1];
           }
           backgroundCopy = copyBackground();
        }
        repaint();   
    }

    private boolean[][] copyBackground(){
        boolean[][] backgroundCopy = new boolean[background.length][background[0].length];
        for (int r = 0; r < background.length; r++) {
            backgroundCopy[r] = background[r].clone();
        }
        return backgroundCopy;
    }

    private ArrayList<Integer> findFullLines() {
        ArrayList<Integer> linesToBeClearedList = new ArrayList<>();
        for (int r = 0; r < background.length; r++) {
            if (isLineFull(r)) {
                linesToBeClearedList.add(r);
            }
        }
        return linesToBeClearedList;
    }

    private boolean isLineFull(int r) {
        boolean isfull = true;
        for (int c = 0; c < background[0].length; c++) {
            isfull = isfull && background[r][c];
        }
        return isfull;
    }

    // TODO make a addPoints method and update the gui to display the points
    // TODO make a increment speed method
    // TODO make a drop action

    protected void addBindings() {
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "Left");
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "Down");
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "Right");
        this.getInputMap().put(KeyStroke.getKeyStroke('f'), "RotateClockwise");
        this.getInputMap().put(KeyStroke.getKeyStroke('a'), "RotateCounterClockwise");
        this.getInputMap().put(KeyStroke.getKeyStroke(' '), "HardDrop");

        Action rightAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canMoveRight()) {
                    tetromino.moveRight();
                    updateTetrominoState();
                    repaint();
                }
            }
        };

        Action leftAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canMoveLeft()) {
                    tetromino.moveLeft();
                    updateTetrominoState();
                    repaint();
                }
            }
        };

        Action downAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canMoveDown()) {
                    tetromino.moveDown();
                    updateTetrominoState();
                    repaint();
                }
            }
        };

        Action rotateClockwiseAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canRotateCockwise()) {
                    tetromino.rotateClockwise();
                    updateTetrominoState();
                    repaint();
                }
            }
        };

        Action rotateCounterClockwiseAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canRotateCounterCockwise()) {
                    tetromino.rotateCounterClockwise();
                    updateTetrominoState();
                    repaint();
                }
            }
        };

        Action HardDropAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (canMoveDown()) {
                    tetromino.moveDown();
                    updateTetrominoState();
                    repaint();
                }
            }
        };

        getActionMap().put("Right", rightAction);
        getActionMap().put("Left", leftAction);
        getActionMap().put("Down", downAction);
        getActionMap().put("RotateClockwise", rotateClockwiseAction);
        getActionMap().put("RotateCounterClockwise", rotateCounterClockwiseAction);
        getActionMap().put("HardDrop", HardDropAction);

    }

}
