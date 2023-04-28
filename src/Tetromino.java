public class Tetromino {
   
    private boolean[][] structure;
    private int hPosition;
    private int vPosition;
    private Hue hue;

    public Tetromino(Shape shape, Hue hue){
        structure = shape.getStructure();
        hPosition = 4;
        vPosition = 0;
        this.hue = hue;
    }

    public void moveDown(){this.vPosition++;}
    public void moveUp(){this.vPosition--;}
    public void moveLeft(){this.hPosition--;}
    public void moveRight(){this.hPosition++;}
    public boolean[][] getStructure(){return this.structure;}
    public int getHPosition(){return this.hPosition;}
    public int getVPosition(){return this.vPosition;}
    public Hue getHue(){return this.hue;}
    
    public void rotateClockwise(){
        int rowLength = this.structure.length;
        int colLength = this.structure[0].length;
        boolean[][] rotatedStructure = new boolean[colLength][rowLength];
        for(int r = 0; r < rowLength; r++){
            for(int c = 0; c < colLength; c++){
                rotatedStructure[c][rowLength - 1 - r] = this.structure[r][c];
            }
        }
        this.structure = rotatedStructure;
    }

    //TODO update the hPosition during rotation so that it is not always locked from the top left position
    public void rotateCounterClockwise(){
        int rowLength = this.structure.length;
        int colLength = this.structure[0].length;
        boolean[][] rotatedStructure = new boolean[colLength][rowLength];
        for(int r = 0; r < rowLength; r++){
            for(int c = 0; c < colLength; c++){
                rotatedStructure[colLength - 1 - c][r] = this.structure[r][c];
            }
        }
        this.structure = rotatedStructure;
    }
    //TODO Upload to github
}
