package it.polimi.ingsw.ui.game.guiv2.resources;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.game.goal.Token;

/**
 * Contains constants and methods for managing and loading images/resources
 */
public class ResourcePathConstants {

    public static class Tiles {
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


        public static String mapTileToImagePath(Tile tile) {
            return switch (tile) {
                case BOOK -> book1;
                case CAT -> cat1;
                case GAME -> game1;
                case TROPHY -> trophy1;
                case PLANT -> plant1;
                case FRAME -> frame1;
            };
        }
    }


    public static class Tokens {
        public static final String token_2_points = "file:img/tokens/scoring_2.jpg";
        public static final String token_4_points = "file:img/tokens/scoring_4.jpg";
        public static final String token_6_points = "file:img/tokens/scoring_6.jpg";
        public static final String token_8_points = "file:img/tokens/scoring_8.jpg";
        public static final String token_full_bookshelf = "file:img/tokens/first_full_bookshelf_token.jpg";


        public static String mapTokenToImagePath(Token token) {
             return switch (token) {
                 case FULL_SHELF_TOKEN -> token_full_bookshelf;
                 case COMMON_GOAL_TOKEN_2_POINTS -> token_2_points;
                 case COMMON_GOAL_TOKEN_4_POINTS -> token_4_points;
                 case COMMON_GOAL_TOKEN_6_POINTS -> token_6_points;
                 case COMMON_GOAL_TOKEN_8_POINTS -> token_8_points;
             };
        }
    }


    public static class Commons {
        public static final String common_six_pairs = "file:img/common_goal_cards/six_pairs/six_pairs.jpg";
        public static final String common_diagonal = "file:img/common_goal_cards/diagonal.jpg";
        public static final String common_eight_tiles = "file:img/common_goal_cards/eight_tiles.jpg";
        public static final String common_four_corners = "file:img/common_goal_cards/four_corners.jpg";
        public static final String common_four_group_four = "file:img/common_goal_cards/four_group_four.jpg";
        public static final String common_four_max3diff_lines = "file:img/common_goal_cards/four_max3diff_line.jpg";
        public static final String common_stairs = "file:img/common_goal_cards/stairs.jpg";
        public static final String common_three_max3diff_columns = "file:img/common_goal_cards/three_max3diff_columns.jpg";
        public static final String common_two_diff_columns = "file:img/common_goal_cards/two_diff_columns.jpg";
        public static final String common_two_diff_lines = "file:img/common_goal_cards/two_diff_lines.jpg";
        public static final String common_two_squares = "file:img/common_goal_cards/two_squares.jpg";
        public static final String common_x_tiles = "file:img/common_goal_cards/x_tiles.jpg";

        public static String mapCommonsToImagePath(CommonGoalCardIdentifier id) {
            return switch (id) {
                case SIX_PAIRS -> common_six_pairs;
                case DIAGONAL -> common_diagonal;
                case FOUR_GROUP_FOUR -> common_four_group_four;
                case FOUR_MAX3DIFF_LINES -> common_four_max3diff_lines;
                case FOUR_CORNERS -> common_four_corners;
                case TWO_DIFF_COLUMNS -> common_two_diff_columns;
                case TWO_SQUARES -> common_two_squares;
                case TWO_DIFF_LINES -> common_two_diff_lines;
                case THREE_MAX3DIFF_COLUMNS -> common_three_max3diff_columns;
                case X_TILES -> common_x_tiles;
                case EIGHT_TILES -> common_eight_tiles;
                case STAIRS -> common_stairs;
            };
        }
    }

    public static class Personal {
        public static final String PGC1 = "file:img/personal_goal_cards/personal_goal_card_1.png";
        public static final String PGC2 = "file:img/personal_goal_cards/personal_goal_card_2.png";
        public static final String PGC3 = "file:img/personal_goal_cards/personal_goal_card_3.png";
        public static final String PGC4 = "file:img/personal_goal_cards/personal_goal_card_4.png";
        public static final String PGC5 = "file:img/personal_goal_cards/personal_goal_card_5.png";
        public static final String PGC6 = "file:img/personal_goal_cards/personal_goal_card_6.png";
        public static final String PGC7 = "file:img/personal_goal_cards/personal_goal_card_7.png";
        public static final String PGC8 = "file:img/personal_goal_cards/personal_goal_card_8.png";
        public static final String PGC9 = "file:img/personal_gol_cards/personal_goal_card_9.png";
        public static final String PGC10 = "file:img/personal_goal_cards/personal_goal_card_10.png";
        public static final String PGC11 = "file:img/personal_goal_cards/personal_goal_card_11.png";
        public static final String PGC12 = "file:img/personal_goal_cards/personal_goal_card_12.png";
    }

}
