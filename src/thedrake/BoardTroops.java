package thedrake;

import java.util.*;

import static thedrake.TroopFace.AVERS;

public class BoardTroops {
	private final PlayingSide playingSide;
	private final Map<BoardPos, TroopTile> troopMap;
	private final TilePos leaderPosition;
	private final int guards;
	
	public BoardTroops(PlayingSide playingSide) {
		this.troopMap = Collections.emptyMap();
		leaderPosition = TilePos.OFF_BOARD;
		guards = 0;
		this.playingSide = playingSide;
	}
	
	public BoardTroops(
			PlayingSide playingSide,
			Map<BoardPos, TroopTile> troopMap,
			TilePos leaderPosition, 
			int guards) {
		this.playingSide = playingSide;
		this.troopMap = troopMap;
		this.leaderPosition = leaderPosition;
		this.guards = guards;
	}

	public Optional<TroopTile> at(TilePos pos) {
		if ( troopMap.containsKey(pos) )
			return Optional.of(troopMap.get(pos));
		else
			return Optional.empty();
	}
	
	public PlayingSide playingSide() {
		return playingSide;
	}
	
	public TilePos leaderPosition() {
		return leaderPosition;
	}

	public int guards() {
		return guards;
	}
	
	public boolean isLeaderPlaced() {
		return this.leaderPosition != TilePos.OFF_BOARD;
	}
	
	public boolean isPlacingGuards() {
		if (guards < 2 && isLeaderPlaced())
			return true;
		return false;
	}	
	
	public Set<BoardPos> troopPositions() {
		Set<BoardPos> set = new HashSet<BoardPos>();

		for (Map.Entry<BoardPos, TroopTile> entry : troopMap.entrySet())
			set.add(entry.getKey());

		return set;
	}

	public BoardTroops placeTroop(Troop troop, BoardPos target) {
		if (this.troopPositions().contains(target)){
			throw new IllegalArgumentException();
		}

		Map<BoardPos, TroopTile> createdMap = new HashMap<>(troopMap);
		createdMap.put(target, new TroopTile(troop, this.playingSide, AVERS));
		if(! this.isLeaderPlaced()) {
			return new BoardTroops(this.playingSide, createdMap, target, this.guards);
		}
		if( this.isPlacingGuards() ) {
			return new BoardTroops(this.playingSide, createdMap, this.leaderPosition, this.guards + 1);
		}

		return new BoardTroops(this.playingSide, createdMap, this.leaderPosition, this.guards);
	}

	public BoardTroops troopStep(BoardPos origin, BoardPos target) {
		if (!isLeaderPlaced())
			throw new IllegalStateException();
		if (isPlacingGuards())
			throw new IllegalStateException();
		if (at(origin).isEmpty())
			throw new IllegalArgumentException();
		if (this.at(target).isPresent())
			throw new IllegalArgumentException();

		Map<BoardPos, TroopTile> updatedTroopMap = new HashMap<>(troopMap);
		BoardTroops newBoardTroops = this.leaderPosition().equals(origin) ?
				new BoardTroops(this.playingSide(), updatedTroopMap, TilePos.OFF_BOARD, this.guards()) :
				new BoardTroops(this.playingSide(), updatedTroopMap, this.leaderPosition(), this.guards());

//		if (this.leaderPosition().equals(origin)) {
//			newBoardTroops = new BoardTroops(this.playingSide(), updatedTroopMap, TilePos.OFF_BOARD, this.guards());
//		} else {
//			newBoardTroops = new BoardTroops(this.playingSide(), updatedTroopMap, this.leaderPosition(), this.guards());
//		}

		newBoardTroops = newBoardTroops.placeTroop(newBoardTroops.troopMap.get(origin).troop(), target);
		if (newBoardTroops.troopMap.get(origin).face() == newBoardTroops.troopMap.get(target).face())
			newBoardTroops = newBoardTroops.troopFlip(target);
		newBoardTroops = newBoardTroops.removeTroop(origin);
		return newBoardTroops;
	}

	public BoardTroops troopFlip(BoardPos origin) {
		if(!isLeaderPlaced()) {
			throw new IllegalStateException(
					"Cannot move troops before the leader is placed.");
		}

		if(isPlacingGuards()) {
			throw new IllegalStateException(
					"Cannot move troops before guards are placed.");
		}

		if(!at(origin).isPresent())
			throw new IllegalArgumentException();

		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		TroopTile tile = newTroops.remove(origin);
		newTroops.put(origin, tile.flipped());

		return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
	}

	public BoardTroops removeTroop(BoardPos target) {
		if(!this.isLeaderPlaced() )
			throw new IllegalStateException();
		if(this.isPlacingGuards() )
			throw new IllegalStateException();
		if (!this.troopPositions().contains(target))
			throw new IllegalArgumentException();
		Map<BoardPos, TroopTile> createdMap = new HashMap<>(troopMap);
		createdMap.remove(target);
		if(target.equals(this.leaderPosition))
			return new BoardTroops(this.playingSide, createdMap, TilePos.OFF_BOARD, this.guards);
		return new BoardTroops(this.playingSide, createdMap, this.leaderPosition, this.guards);
	}
}
