/**
 * defining what Position is
 */
public class Position {
    public int row;
    public int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    /**
     * 
     * @return random position on the boardgame
     */
    public static Position random() {
        int row = (int) (Math.random() * (Constants.ROWS_COUNT - 1) + 1);
        int col = (int) (Math.random() * (Constants.COLS_COUNT - 1) + 1);
        return new Position(row, col);
    }

    /**
     * @return true if position is same as given
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof Position)) {
            return false;
        }

        Position other = (Position) o;
        return this.row == other.row && this.col == other.col;
    }
}
