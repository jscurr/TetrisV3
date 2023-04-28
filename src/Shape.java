public enum Shape {

    TSHAPE(new boolean[][] { { true, true, true }, { false, true, false } }),
    LSHAPE(new boolean[][] { { true, false, false }, { true, true, true } }),
    STRAIGHT(new boolean[][] { { true, true, true, true } }),
    SQUARE(new boolean[][] { { true, true }, { true, true } }),
    ZSHAPE(new boolean[][] { { true, true, false }, { false, true, true } });

    private boolean[][] structure;

    Shape(boolean[][] structure) {
        this.structure = structure;
    }

    public boolean[][] getStructure() {
        return this.structure;
    }

}
