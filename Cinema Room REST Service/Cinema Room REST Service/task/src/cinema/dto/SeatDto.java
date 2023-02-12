package cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SeatDto {

    @JsonProperty("row")
    private int row;

    @JsonProperty("column")
    private int column;

    @JsonProperty("price")
    private int price;

    public SeatDto(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
