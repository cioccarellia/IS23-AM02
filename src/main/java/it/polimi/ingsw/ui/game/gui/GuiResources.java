package it.polimi.ingsw.ui.game.gui;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.game.goal.Token;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier.*;
import static it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer.*;
import static it.polimi.ingsw.model.game.goal.Token.*;
import static it.polimi.ingsw.ui.game.gui.ImageExtractor.extract;

public class GuiResources {


    private GuiResources() {

    }

    // Tiles
    public static final Image book1 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/book1.1.png")));
    public static final Image book2 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/book1.2.png")));
    public static final Image book3 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/book1.3.png")));
    public static final Image cat1 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/cat1.1.png")));
    public static final Image cat2 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/cat1.2.png")));
    public static final Image cat3 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/cat1.3.png")));
    public static final Image frame1 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/frame1.1.png")));
    public static final Image frame2 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/frame1.2.png")));
    public static final Image frame3 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/frame1.3.png")));
    public static final Image game1 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/game1.1.png")));
    public static final Image game2 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/game1.2.png")));
    public static final Image game3 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/game1.3.png")));
    public static final Image plant1 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/plant1.1.png")));
    public static final Image plant2 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/plant1.2.png")));
    public static final Image plant3 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/plant1.3.png")));
    public static final Image trophy1 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/trophy1.1.png")));
    public static final Image trophy2 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/trophy1.2.png")));
    public static final Image trophy3 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tiles/trophy1.3.png")));
    private static final Map<Tile, Image> tileImageMap = Map.of(BOOK, extract(BOOK), CAT, extract(CAT), FRAME, extract(FRAME), GAME, extract(GAME), PLANT, extract(PLANT), TROPHY, extract(TROPHY));

    // Tokens
    public static final Image token_2_points = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tokens/scoring_2.jpg")));
    public static final Image token_4_points = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tokens/scoring_4.jpg")));
    public static final Image token_6_points = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tokens/scoring_6.jpg")));
    public static final Image token_8_points = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tokens/scoring_8.jpg")));
    public static final Image token_full_bookshelf = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tokens/first_full_bookshelf_token.jpg")));
    private static final Map<Token, Image> tokenMap = Map.of(COMMON_GOAL_TOKEN_2_POINTS, token_2_points,
            COMMON_GOAL_TOKEN_4_POINTS, token_4_points, COMMON_GOAL_TOKEN_6_POINTS, token_6_points, COMMON_GOAL_TOKEN_8_POINTS, token_8_points, FULL_SHELF_TOKEN, token_full_bookshelf);


    // Personal goal cards
    public static final Image personal_GC1 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_1.png")));
    public static final Image personal_GC2 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_2.png")));
    public static final Image personal_GC3 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_3.png")));
    public static final Image personal_GC4 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_4.png")));
    public static final Image personal_GC5 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_5.png")));
    public static final Image personal_GC6 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_6.png")));
    public static final Image personal_GC7 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_7.png")));
    public static final Image personal_GC8 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_8.png")));
    public static final Image personal_GC9 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_9.png")));
    public static final Image personal_GC10 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_10.png")));
    public static final Image personal_GC11 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_11.png")));
    public static final Image personal_GC12 = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/personal_goal_cards/personal_goal_card_12.png")));
    private static final Map<PersonalGoalCard, Image> personalGCMap = Map.ofEntries(
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

    // Common goal cards
    public static final Image common_diagonal = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/diagonal.jpg")));
    public static final Image common_eight_tiles = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/eight_tiles.jpg")));
    public static final Image common_four_corners = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/four_corners.jpg")));
    public static final Image common_four_group_four = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/four_group_four.jpg")));
    public static final Image common_four_max3diff_lines = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/four_max3diff_line.jpg")));
    public static final Image common_six_pairs = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/six_pairs/six_pairs.jpg")));
    public static final Image common_stairs = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/stairs.jpg")));
    public static final Image common_three_max3diff_columns = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/three_max3diff_columns.jpg")));
    public static final Image common_two_diff_columns = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/two_diff_columns.jpg")));
    public static final Image common_two_diff_lines = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/two_diff_lines.jpg")));
    public static final Image common_two_squares = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/two_squares.jpg")));
    public static final Image common_x_tiles = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/common_goal_cards/x_tiles.jpg")));
    private static final Map<CommonGoalCardIdentifier, Image> commonGCMap = Map.ofEntries(
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

    // Boards tutte
    public static final Image bookshelf_image = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/boards/bookshelf.png")));
    public static final Image bookshelf_orth_image = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/boards/bookshelf_orth.png")));
    public static final Image board_image = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/boards/living_room.png")));

    // misc wood background
    public static final Image wood_background = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/misc/wood_background.jpg")));

    // publisher material title 618
    public static final Image title = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/publisher_material/title_2000x618px.png")));


    //Get resources
    public static Image getTile(Tile tile) {
        return tileImageMap.get(tile);
    }

    public static Image getToken(Token token) {
        return tokenMap.get(token);
    }

    public static Image getCommonGC(CommonGoalCardIdentifier card) {
        return commonGCMap.get(card);
    }

    public static Image getPersonalGC(PersonalGoalCard card) {
        return personalGCMap.get(card);
    }

    public static Tile getTileType(Image image) {
        if (image.equals(book1) || image.equals(book2) || image.equals(book3)) {
            return BOOK;
        } else if (image.equals(cat1) || image.equals(cat2) || image.equals(cat3)) {
            return CAT;
        } else if (image.equals(frame1) || image.equals(frame2) || image.equals(frame3)) {
            return FRAME;
        } else if (image.equals(game1) || image.equals(game2) || image.equals(game3)) {
            return GAME;
        } else if (image.equals(plant1) || image.equals(plant2) || image.equals(plant3)) {
            return PLANT;
        } else if (image.equals(trophy1) || image.equals(trophy2) || image.equals(trophy3)) {
            return TROPHY;
        }
        return null;
    }

    public static final List<Image> tilesDomain = Arrays.asList(
            book1, book2, book3, cat1, cat2, cat3, frame1, frame2, frame3, game1, game2, game3, plant1, plant2, plant3, trophy1, trophy2, trophy3
    );


}
