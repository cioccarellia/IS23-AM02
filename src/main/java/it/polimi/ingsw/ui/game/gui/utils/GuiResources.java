package it.polimi.ingsw.ui.game.gui.utils;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.game.goal.Token;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier.*;
import static it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer.*;
import static it.polimi.ingsw.model.game.goal.Token.*;

/**
 * The GuiResources class provides access to various graphical resources used in the GUI.
 * It includes Strings for tiles, tokens, personal goal cards, common goal cards, and more.
 */
public class GuiResources {

    private GuiResources() {

    }


    //Tile:
    public static final String book1 = "file:img/tiles/book1.1.png";
    public static final String book2 = "file:img/tiles/book1.2.png";
    public static final String book3 = "file:img/tiles/book1.3.png";
    public static final String cat1 = "file:img/tiles/cat1.1.png";
    public static final String cat2 = "file:img/tiles/cat1.2.png";
    public static final String cat3 = "file:img/tiles/cat1.3.png";
    public static final String frame1 = "file:img/tiles/frame1.1.png";
    public static final String frame2 = "file:img/tiles/frame1.2.png";
    public static final String frame3 = "file:img/tiles/frame1.3.png";
    public static final String game1 = "file:img/tiles/game1.1.png";
    public static final String game2 = "file:img/tiles/game1.2.png";
    public static final String game3 = "file:img/tiles/game1.3.png";
    public static final String plant1 = "file:img/tiles/plant1.1.png";
    public static final String plant2 = "file:img/tiles/plant1.2.png";
    public static final String plant3 = "file:img/tiles/plant1.3.png";
    public static final String trophy1 = "file:img/tiles/trophy1.1.png";
    public static final String trophy2 = "file:img/tiles/trophy1.2.png";
    public static final String trophy3 = "file:img/tiles/trophy1.3.png";

    // Tokens
    public static final String token_2_points = "file:img/tokens/scoring_2.jpg";
    public static final String token_4_points = "file:img/tokens/scoring_4.jpg";
    public static final String token_6_points = "file:img/tokens/scoring_6.jpg";
    public static final String token_8_points = "file:img/tokens/scoring_8.jpg";
    public static final String token_full_bookshelf = "file:img/tokens/first_full_bookshelf_token.jpg";

    // Personal goal cards
    public static final String personal_GC1 = "file:img/personal_goal_cards/personal_goal_card_1.png";
    public static final String personal_GC2 = "file:img/personal_goal_cards/personal_goal_card_2.png";
    public static final String personal_GC3 = "file:img/personal_goal_cards/personal_goal_card_3.png";
    public static final String personal_GC4 = "file:img/personal_goal_cards/personal_goal_card_4.png";
    public static final String personal_GC5 = "file:img/personal_goal_cards/personal_goal_card_5.png";
    public static final String personal_GC6 = "file:img/personal_goal_cards/personal_goal_card_6.png";
    public static final String personal_GC7 = "file:img/personal_goal_cards/personal_goal_card_7.png";
    public static final String personal_GC8 = "file:img/personal_goal_cards/personal_goal_card_8.png";
    public static final String personal_GC9 = "file:img/personal_gol_cards/personal_goal_card_9.png";
    public static final String personal_GC10 = "file:img/personal_goal_cards/personal_goal_card_10.png";
    public static final String personal_GC11 = "file:img/personal_goal_cards/personal_goal_card_11.png";
    public static final String personal_GC12 = "file:img/personal_goal_cards/personal_goal_card_12.png";

    // Common goal cards
    public static final String common_diagonal = "file:img/common_goal_cards/diagonal.jpg";
    public static final String common_eight_tiles = "file:img/common_goal_cards/eight_tiles.jpg";
    public static final String common_four_corners = "file:img/common_goal_cards/four_corners.jpg";
    public static final String common_four_group_four = "file:img/common_goal_cards/four_group_four.jpg";
    public static final String common_four_max3diff_lines = "file:img/common_goal_cards/four_max3diff_line.jpg";
    public static final String common_six_pairs = "file:img/common_goal_cards/six_pairs/six_pairs.jpg";
    public static final String common_stairs = "file:img/common_goal_cards/stairs.jpg";
    public static final String common_three_max3diff_columns = "file:img/common_goal_cards/three_max3diff_columns.jpg";
    public static final String common_two_diff_columns = "file:img/common_goal_cards/two_diff_columns.jpg";
    public static final String common_two_diff_lines = "file:img/common_goal_cards/two_diff_lines.jpg";
    public static final String common_two_squares = "file:img/common_goal_cards/two_squares.jpg";
    public static final String common_x_tiles = "file:img/common_goal_cards/x_tiles.jpg";

    // Boards tutte
    public static final String bookshelf_String = "file:img/boards/bookshelf.png";
    public static final String bookshelf_orth_String = "file:img/boards/bookshelf_orth.png";
    public static final String board_String = "file:img/boards/living_room.png";

    // misc wood background
    public static final String wood_background = "file:img/misc/wood_background.jpg";

    // publisher material title 618
    public static final String title = "file:img/publisher_material/title_2000x618px.png";


    // maps
    private static final Map<Tile, String> tileStringMap = Map.of(BOOK, book1, CAT, cat1, FRAME, frame1, GAME, game1, PLANT, plant1, TROPHY, trophy1);
    private static final Map<Token, String> tokenStringMap = Map.of(COMMON_GOAL_TOKEN_2_POINTS, token_2_points,
            COMMON_GOAL_TOKEN_4_POINTS, token_4_points, COMMON_GOAL_TOKEN_6_POINTS, token_6_points, COMMON_GOAL_TOKEN_8_POINTS, token_8_points, FULL_SHELF_TOKEN, token_full_bookshelf);
    private static final Map<PersonalGoalCard, String> personalGCStringMap = Map.ofEntries(
            Map.entry(p1, personal_GC1),
            Map.entry(p2, personal_GC2),
            Map.entry(p3, personal_GC3),
            Map.entry(p4, personal_GC4),
            Map.entry(p5, personal_GC5),
            Map.entry(p6, personal_GC6),
            Map.entry(p7, personal_GC7),
            Map.entry(p8, personal_GC8),
            Map.entry(p9, personal_GC9),
            Map.entry(p10, personal_GC10),
            Map.entry(p11, personal_GC11),
            Map.entry(p12, personal_GC12));
    private static final Map<CommonGoalCardIdentifier, String> commonGCStringMap = Map.ofEntries(
            Map.entry(SIX_PAIRS, common_six_pairs),
            Map.entry(DIAGONAL, common_diagonal),
            Map.entry(FOUR_GROUP_FOUR, common_four_group_four),
            Map.entry(FOUR_MAX3DIFF_LINES, common_four_max3diff_lines),
            Map.entry(FOUR_CORNERS, common_four_corners),
            Map.entry(TWO_DIFF_COLUMNS, common_two_diff_columns),
            Map.entry(TWO_SQUARES, common_two_squares),
            Map.entry(TWO_DIFF_LINES, common_two_diff_lines),
            Map.entry(THREE_MAX3DIFF_COLUMNS, common_three_max3diff_columns),
            Map.entry(X_TILES, common_x_tiles),
            Map.entry(EIGHT_TILES, common_eight_tiles),
            Map.entry(STAIRS, common_stairs));


    //Get resources

    /**
     * Retrieves the String associated with a specific tile.
     *
     * @param tile The tile type.
     * @return The String representing the tile.
     */
    public static String getTile(Tile tile) {
        return tileStringMap.get(tile);
    }

    /**
     * Retrieves the String associated with a specific token.
     *
     * @param token The token type.
     * @return The String representing the token.
     */
    public static String getToken(Token token) {
        return tokenStringMap.get(token);
    }

    /**
     * Retrieves the String associated with a specific common goal card.
     *
     * @param card The common goal card identifier.
     * @return The String representing the common goal card
     */
    public static String getCommonGC(CommonGoalCardIdentifier card) {
        return commonGCStringMap.get(card);
    }

    /**
     * Retrieves the String associated with a specific personal goal card.
     *
     * @param card The personal goal card.
     * @return The String representing the personal goal card.
     */
    public static String getPersonalGC(PersonalGoalCard card) {
        return personalGCStringMap.get(card);
    }

    /**
     * Determines the tile type based on the provided String.
     *
     * @param string The String representing a tile.
     * @return The corresponding tile type.
     */
    public static Tile getTileType(String string) {

        return switch (string) {
            case book1, book2, book3 -> BOOK;
            case cat1, cat2, cat3 -> CAT;
            case frame1, frame2, frame3 -> FRAME;
            case game1, game2, game3 -> GAME;
            case plant1, plant2, plant3 -> PLANT;
            case trophy1, trophy2, trophy3 -> TROPHY;
            default -> null;
        };
    }

    public static final List<String> tilesDomain = Arrays.asList(
            book1, book2, book3, cat1, cat2, cat3, frame1, frame2, frame3, game1, game2, game3, plant1, plant2, plant3, trophy1, trophy2, trophy3
    );

    /*public static String extract(@NotNull Tile tileType) {
        if (tilesDomain != null) {
            switch (tileType) {
                case BOOK -> {
                    return tilesDomain.get(new Random().ints(0, 3).findAny().getAsInt());
                }
                case CAT -> {
                    return tilesDomain.get(new Random().ints(3, 6).findAny().getAsInt());
                }
                case FRAME -> {
                    return tilesDomain.get(new Random().ints(6, 9).findAny().getAsInt());
                }
                case GAME -> {
                    return tilesDomain.get(new Random().ints(9, 12).findAny().getAsInt());
                }
                case PLANT -> {
                    return tilesDomain.get(new Random().ints(12, 15).findAny().getAsInt());
                }
                case TROPHY -> {
                    return tilesDomain.get(new Random().ints(15, 18).findAny().getAsInt());
                }
                default -> throw new IllegalStateException("Unexpected value: " + tileType);
            }
        } else
            return null;
    }*/
}
