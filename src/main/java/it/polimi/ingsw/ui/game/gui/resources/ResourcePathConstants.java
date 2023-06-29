package it.polimi.ingsw.ui.game.gui.resources;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCardIdentifier;
import it.polimi.ingsw.model.game.goal.Token;

import java.util.List;

/**
 * Class containing constants and methods to interrelate an image to its url
 */
public class ResourcePathConstants {

    public static class Tiles {

        // books
        public static final String book1 = "/img/tiles/book1.1.png";
        public static final String book2 = "/img/tiles/book1.2.png";
        public static final String book3 = "/img/tiles/book1.3.png";
        public static final List<String> books = List.of(book1, book2, book3);

        // cats
        public static final String cat1 = "/img/tiles/cat1.1.png";
        public static final String cat2 = "/img/tiles/cat1.2.png";
        public static final String cat3 = "/img/tiles/cat1.3.png";
        public static final List<String> cats = List.of(cat1, cat2, cat3);

        // frames
        public static final String frame1 = "/img/tiles/frame1.1.png";
        public static final String frame2 = "/img/tiles/frame1.2.png";
        public static final String frame3 = "/img/tiles/frame1.3.png";
        public static final List<String> frames = List.of(frame1, frame2, frame3);

        // games
        public static final String game1 = "/img/tiles/game1.1.png";
        public static final String game2 = "/img/tiles/game1.2.png";
        public static final String game3 = "/img/tiles/game1.3.png";
        public static final List<String> games = List.of(game1, game2, game3);

        // plants
        public static final String plant1 = "/img/tiles/plant1.1.png";
        public static final String plant2 = "/img/tiles/plant1.2.png";
        public static final String plant3 = "/img/tiles/plant1.3.png";
        public static final List<String> plants = List.of(plant1, plant2, plant3);

        // trophies
        public static final String trophy1 = "/img/tiles/trophy1.1.png";
        public static final String trophy2 = "/img/tiles/trophy1.2.png";
        public static final String trophy3 = "/img/tiles/trophy1.3.png";
        public static final List<String> trophies = List.of(trophy1, trophy2, trophy3);


        public static String mapTileToImagePath(Tile tile) {
            assert tile != null;
            return switch (tile) {
                case BOOK -> book1;
                case CAT -> cat1;
                case GAME -> game1;
                case TROPHY -> trophy1;
                case PLANT -> plant1;
                case FRAME -> frame1;
            };
        }


        public static String hashedMapTileToImagePath(Tile tile, int x, int y) {
            assert tile != null;
            int hashedIndex = (7 * (x + 1) + 11 * (y + 1)) % 3;

            return switch (tile) {
                case BOOK -> books.get(hashedIndex); //randomizer(books);
                case CAT -> cats.get(hashedIndex);//randomizer(cats);
                case GAME -> games.get(hashedIndex);//randomizer(games);
                case TROPHY -> trophies.get(hashedIndex);//randomizer(trophies);
                case PLANT -> plants.get(hashedIndex);//randomizer(plants);
                case FRAME -> frames.get(hashedIndex);//randomizer(frames);
            };
        }
    }


    public static class Tokens {
        public static final String token_2_points = "/img/tokens/scoring_2.jpg";
        public static final String token_4_points = "/img/tokens/scoring_4.jpg";
        public static final String token_6_points = "/img/tokens/scoring_6.jpg";
        public static final String token_8_points = "/img/tokens/scoring_8.jpg";
        public static final String token_full_bookshelf = "/img/tokens/first_full_bookshelf_token.jpg";


        public static String mapTokenToImagePath(Token token) {
            assert token != null;

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
        public static final String common_diagonal = "/img/common_goal_cards/diagonal.jpg";
        public static final String common_eight_tiles = "/img/common_goal_cards/eight_tiles.jpg";
        public static final String common_four_corners = "/img/common_goal_cards/four_corners.jpg";
        public static final String common_four_group_four = "/img/common_goal_cards/four_group_four.jpg";
        public static final String common_four_max3diff_lines = "/img/common_goal_cards/four_max3diff_lines.jpg";
        public static final String common_six_pairs = "/img/common_goal_cards/six_pairs.jpg";
        public static final String common_stairs = "/img/common_goal_cards/stairs.jpg";
        public static final String common_three_max3diff_columns = "/img/common_goal_cards/three_max3diff_columns.jpg";
        public static final String common_two_diff_columns = "/img/common_goal_cards/two_diff_columns.jpg";
        public static final String common_two_diff_lines = "/img/common_goal_cards/two_diff_lines.jpg";
        public static final String common_two_squares = "/img/common_goal_cards/two_squares.jpg";
        public static final String common_x_tiles = "/img/common_goal_cards/x_tiles.jpg";


        public static String mapCommonsToImagePath(CommonGoalCardIdentifier id) {
            assert id != null;

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

    public static class Personals {
        public static final String PGC1 = "/img/personal_goal_cards/personal_goal_card_1.png";
        public static final String PGC2 = "/img/personal_goal_cards/personal_goal_card_2.png";
        public static final String PGC3 = "/img/personal_goal_cards/personal_goal_card_3.png";
        public static final String PGC4 = "/img/personal_goal_cards/personal_goal_card_4.png";
        public static final String PGC5 = "/img/personal_goal_cards/personal_goal_card_5.png";
        public static final String PGC6 = "/img/personal_goal_cards/personal_goal_card_6.png";
        public static final String PGC7 = "/img/personal_goal_cards/personal_goal_card_7.png";
        public static final String PGC8 = "/img/personal_goal_cards/personal_goal_card_8.png";
        public static final String PGC9 = "/img/personal_goal_cards/personal_goal_card_9.png";
        public static final String PGC10 = "/img/personal_goal_cards/personal_goal_card_10.png";
        public static final String PGC11 = "/img/personal_goal_cards/personal_goal_card_11.png";
        public static final String PGC12 = "/img/personal_goal_cards/personal_goal_card_12.png";

        public static String mapPersonalsToImagePath(PersonalGoalCardIdentifier id) {
            assert id != null;

            return switch (id) {
                case PGC_1 -> PGC1;
                case PGC_2 -> PGC2;
                case PGC_3 -> PGC3;
                case PGC_4 -> PGC4;
                case PGC_5 -> PGC5;
                case PGC_6 -> PGC6;
                case PGC_7 -> PGC7;
                case PGC_8 -> PGC8;
                case PGC_9 -> PGC9;
                case PGC_10 -> PGC10;
                case PGC_11 -> PGC11;
                case PGC_12 -> PGC12;
            };
        }
    }
}
