package it.polimi.ingsw.model.board;

public record Coordinates(int x, int y) {
    public Coordinates(int x,int y){
        this.x=x;
        this.y=y;
    }

}
