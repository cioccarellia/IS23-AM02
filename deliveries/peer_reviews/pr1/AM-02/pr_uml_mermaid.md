classDiagram
direction BT
class App {

+ App()
+ main(String[]) void
  }
  class Board {
+ Board()
+ countEmptyCells(GameMode) int
+ countFreeEdges(Coordinates) int
+ fill(List~Tile~, GameMode) void
+ getTileAt(Coordinates) Optional~Tile~
+ hasAtLeastOneFreeEdge(Coordinates) boolean

- mapFromGameMode(GameMode) CellPattern

+ removeTileAt(Coordinates) void
  Cell[][] cellMatrix
  Tile[][] tileMatrix
  }
  class BoardConstants {
+ BoardConstants()
+ algorithmicPattern() CellPattern[][]
  }
  class BoardUtils {
+ BoardUtils()
+ hasFreeEdge(Board, Coordinates, Edge) boolean
  }
  class BookShelfConstants {
+ BookShelfConstants()
  }
  class Bookshelf {
+ Bookshelf()
+ canFit(int, int) boolean
+ insert(int, List~Tile~) void
  boolean full
  Tile[][] shelfMatrix
  }
  class Cell {
+ Cell(CellPattern, Tile?)
+ Cell(CellPattern, boolean)

- Tile content
- CellPattern pattern

+ clear() void
  ~ createDeadCell() Cell
  Optional~Tile~ content
  boolean dead
  boolean empty
  CellPattern pattern
  }
  class CellPattern {
  <<enumeration>>

- CellPattern(int)
- int playerCount

+ valueOf(String) CellPattern
+ values() CellPattern[]
  int playerCount
  }
  class CommonGoalCard {
+ CommonGoalCard(CommonGoalCardIdentifier, Predicate~Tile[][]~)

- CommonGoalCardIdentifier id

+ matches(Tile[][]) boolean
  CommonGoalCardIdentifier id
  }
  class CommonGoalCardExtractor {
+ CommonGoalCardExtractor()
+ domain() Set~CommonGoalCard~
+ extract() CommonGoalCard
  }
  class CommonGoalCardFunctionContainer {
+ CommonGoalCardFunctionContainer()
+ commonGoalCardMap() Map~CommonGoalCardIdentifier, CommonGoalCard~

- f1(Tile[][]) Boolean
- f10(Tile[][]) Boolean
- f11(Tile[][]) Boolean
- f12(Tile[][]) Boolean
- f2(Tile[][]) Boolean
- f3(Tile[][]) Boolean
- f4(Tile[][]) Boolean
- f5(Tile[][]) Boolean
- f6(Tile[][]) Boolean
- f7(Tile[][]) Boolean
- f8(Tile[][]) Boolean
- f9(Tile[][]) Boolean
  }
  class CommonGoalCardIdentifier {
  <<enumeration>>

+ CommonGoalCardIdentifier()
+ valueOf(String) CommonGoalCardIdentifier
+ values() CommonGoalCardIdentifier[]
  }
  class CommonGoalCardStatus {
+ CommonGoalCardStatus(CommonGoalCard, GameMode)

- CommonGoalCard commonGoalCard

+ acquireAndRemoveTopToken() Optional~Token~
  CommonGoalCard commonGoalCard
  boolean empty
  }
  class Coordinates {
+ Coordinates(int, int)
+ x() int
+ y() int
  }
  class ElementExtractor~E~ {
+ ElementExtractor()
+ extract() E
  }
  class Game {
+ Game(GameMode)

- PlayerNumber currentPlayer
- PlayerNumber startingPlayer

+ addPlayer(String) void
+ onGameEnded() void
+ onGameStarted() void
+ onLastTurn() void
+ onPLayerTileSelection(String, Set~Tile~) void
+ onPlayerBookshelfTileInsertion(String, int, List~Tile~) void
+ onPlayerQuit() void
+ onPlayerTokenUpdate(String) void
+ onTurnChange() void
  List~CommonGoalCardStatus~ commonGoalCards
  PlayerSession currentPlayer
  GameMode gameMode
  GameStatus gameStatus
  List~PlayerSession~ playerSessions
  PlayerNumber startingPlayer
  }
  class GameMode {
  <<enumeration>>
+ GameMode()
+ valueOf(String) GameMode
+ values() GameMode[]
  }
  class GameStatus {
  <<enumeration>>
+ GameStatus()
+ valueOf(String) GameStatus
+ values() GameStatus[]
  }
  class Group {
+ Group(Tile, int)
+ size() int
+ type() Tile
  }
  class GroupFinder {
+ GroupFinder(Tile[][])
+ computeGroupPartition() List~Group~
+ computeGroupPartitionMap() Map~Tile, List~Group~~

- pathfind(int, int, Tile) int
  }
  class LauncherParameters {

+ LauncherParameters()
  }
  class ListUtils {
+ ListUtils()
+ extractAndRemoveRandomElement(List~E~) E
+ extractRandomElement(Collection~E~) E
  }
  class PersonalGoalCard {
+ PersonalGoalCard(Tile[][])
  Tile[][] shelfPointMatrix
  }
  class PersonalGoalCardExtractor {
+ PersonalGoalCardExtractor()
+ domain() Set~PersonalGoalCard~
+ extract() PersonalGoalCard
  }
  class PersonalGoalCardMatrixContainer {
+ PersonalGoalCardMatrixContainer()
  }
  class PlayerCurrentAction {
  <<enumeration>>
+ PlayerCurrentAction()
+ valueOf(String) PlayerCurrentAction
+ values() PlayerCurrentAction[]
  }
  class PlayerNumber {
  <<enumeration>>
+ PlayerNumber()
+ fromInt(int) PlayerNumber
+ valueOf(String) PlayerNumber
+ values() PlayerNumber[]
  }
  class PlayerSession {
+ PlayerSession(String, PlayerNumber, PersonalGoalCard)

- List~Token~ acquiredTokens
- Bookshelf bookshelf
- PersonalGoalCard personalGoalCard
- PlayerNumber playerNumber
- String username

+ addAcquiredToken(Token) void
+ calculateCurrentPoint() int
  List~Token~ acquiredTokens
  Bookshelf bookshelf
  PersonalGoalCard personalGoalCard
  PlayerNumber playerNumber
  String username
  }
  class PlayerStatus {
  <<enumeration>>
+ PlayerStatus()
+ valueOf(String) PlayerStatus
+ values() PlayerStatus[]
  }
  class PlayerTileSelection {
+ PlayerTileSelection()

- Set~Tile~ selectedTiles
  Set~Tile~ selectedTiles
  }
  class Tile {
  <<enumeration>>

+ Tile()
+ valueOf(String) Tile
+ values() Tile[]
  }
  class TileExtractor {
+ TileExtractor()
+ extract(int) List~Tile~
+ extract() Tile
+ leftoverCapacity() int
  }
  class Token {
  <<enumeration>>

- Token(int)
- int points

+ valueOf(String) Token
+ values() Token[]
  int points
  }
  class TurnHelper {
+ TurnHelper()
+ getNextNumber(PlayerNumber, GameMode) PlayerNumber
  }

App ..>  LauncherParameters : «create»
Board ..>  Cell : «create»
Board "1" *--> "matrix *" Cell
Board ..>  Tile : «create»
BoardConstants ..>  CellPattern : «create»
BoardConstants "1" *--> "DFU_BOARD_MATRIX *" CellPattern
BoardUtils ..>  Coordinates : «create»
Bookshelf "1" *--> "bookshelfMatrix *" Tile
Bookshelf ..>  Tile : «create»
Cell "1" *--> "pattern 1" CellPattern
Cell "1" *--> "content 1" Tile
CommonGoalCard "1" *--> "id 1" CommonGoalCardIdentifier
CommonGoalCardExtractor "1" *--> "state *" CommonGoalCard
CommonGoalCardExtractor -->  ElementExtractor~E~
CommonGoalCardFunctionContainer ..>  CommonGoalCard : «create»
CommonGoalCardFunctionContainer "1" *--> "commonGoalCardDomain *" CommonGoalCard
CommonGoalCardFunctionContainer ..>  GroupFinder : «create»
CommonGoalCardStatus "1" *--> "commonGoalCard 1" CommonGoalCard
Game "1" *--> "board 1" Board
Game ..>  Board : «create»
Game ..>  CommonGoalCardExtractor : «create»
Game "1" *--> "commonGoalCardExtractor 1" CommonGoalCardExtractor
Game "1" *--> "commonGoalCardStatuses *" CommonGoalCardStatus
Game ..>  CommonGoalCardStatus : «create»
Game "1" *--> "mode 1" GameMode
Game "1" *--> "status 1" GameStatus
Game ..>  PersonalGoalCardExtractor : «create»
Game "1" *--> "personalGoalCardExtractor 1" PersonalGoalCardExtractor
Game "1" *--> "playersMap *" PlayerNumber
Game ..>  PlayerSession : «create»
Game "1" *--> "playersMap *" PlayerSession
Game ..>  TileExtractor : «create»
Game "1" *--> "tileExtractor 1" TileExtractor
Group "1" *--> "type 1" Tile
GroupFinder ..>  Group : «create»
GroupFinder "1" *--> "matrix *" Tile
PersonalGoalCard "1" *--> "shelfPointsMatrix *" Tile
PersonalGoalCardExtractor -->  ElementExtractor~E~
PersonalGoalCardExtractor "1" *--> "state *" PersonalGoalCard
PersonalGoalCardMatrixContainer ..>  PersonalGoalCard : «create»
PersonalGoalCardMatrixContainer "1" *--> "personalGoalCardDomain *" PersonalGoalCard
PersonalGoalCardMatrixContainer "1" *--> "m1 *" Tile
PlayerSession "1" *--> "bookshelf 1" Bookshelf
PlayerSession "1" *--> "achievedCommonGoalCards *" CommonGoalCardIdentifier
PlayerSession "1" *--> "personalGoalCard 1" PersonalGoalCard
PlayerSession "1" *--> "playerCurrentGamePhase 1" PlayerCurrentAction
PlayerSession "1" *--> "playerNumber 1" PlayerNumber
PlayerSession "1" *--> "playerStatus 1" PlayerStatus
PlayerSession "1" *--> "playerTileSelection 1" PlayerTileSelection
PlayerSession "1" *--> "acquiredTokens *" Token
PlayerTileSelection "1" *--> "selectedTiles *" Tile
TileExtractor -->  ElementExtractor~E~
TileExtractor "1" *--> "status *" Tile 
