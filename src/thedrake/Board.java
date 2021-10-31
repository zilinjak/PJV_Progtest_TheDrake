package thedrake;

import java.util.ArrayList;
import java.util.List;

public class Board {
//	List<List<BoardTile>> board = new ArrayList<>();
	BoardTile [][] board;
	int dimension;

	// Konstruktor. Vytvoří čtvercovou hrací desku zadaného rozměru, kde všechny dlaždice jsou prázdné, tedy BoardTile.EMPTY
	public Board(int dimension) {
		this.board = new BoardTile[dimension][dimension];
		this.dimension = dimension;
		for (int i = 0 ; i < dimension ; i++){
			for (int j = 0 ; j < dimension ; j++) {
				board[i][j] = BoardTile.EMPTY;
			}
		}
	}

	public Board(int dimension, BoardTile [][] board) {
		this.dimension = dimension;
		this.board = board;
	}

	// Rozměr hrací desky
	public int dimension() {
		return this.dimension;
	}

	public BoardTile at(TilePos pos) {
		return board[pos.i()][pos.j()];
	}

	// Vytváří novou hrací desku s novými dlaždicemi. Všechny ostatní dlaždice zůstávají stejné
	public Board withTiles(TileAt ... ats) {
		BoardTile[][] newBoard = new BoardTile[this.dimension][this.dimension];
		for (int i = 0 ; i < this.dimension ; i++){
			newBoard[i] = this.board[i].clone();
		}
		for (TileAt it : ats){
			newBoard[it.pos.i()][it.pos.j()] = it.tile;
		}
		return new Board(dimension, newBoard);
	}

	// Vytvoří instanci PositionFactory pro výrobu pozic na tomto hracím plánu
	public PositionFactory positionFactory() {
		return new PositionFactory(this.dimension);
	}
	
	public static class TileAt {
		public final BoardPos pos;
		public final BoardTile tile;
		
		public TileAt(BoardPos pos, BoardTile tile) {
			this.pos = pos;
			this.tile = tile;
		}
	}
}

