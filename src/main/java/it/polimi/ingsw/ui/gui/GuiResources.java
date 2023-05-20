package it.polimi.ingsw.ui.gui;

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
import static it.polimi.ingsw.ui.gui.ImageExtractor.extract;
import static it.polimi.ingsw.model.game.goal.Token.*;
import static it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer.*;

public class GuiResources {

    private static Map<Tile, Image> tileImageMap = null;
    private static Map<Token, Image> tokenMap = null;
    private static  Map<CommonGoalCardIdentifier, Image> commonGCMap = null;
    private static Map<PersonalGoalCard, Image> personalGCMap = null;


    public GuiResources() {
        tileImageMap = Map.of(BOOK, extract(BOOK), CAT, extract(CAT), FRAME, extract(FRAME), GAME, extract(GAME), PLANT, extract(PLANT), TROPHY, extract(TROPHY));

        tokenMap = Map.of(COMMON_GOAL_TOKEN_2_POINTS, token_2_points,
            COMMON_GOAL_TOKEN_4_POINTS, token_4_points, COMMON_GOAL_TOKEN_6_POINTS, token_6_points, COMMON_GOAL_TOKEN_8_POINTS, token_8_points, FULL_SHELF_TOKEN, token_full_bookshelf);

        commonGCMap = Map.of(SIX_PAIRS, common_six_pairs, DIAGONAL, common_diagonal, FOUR_GROUP_FOUR, common_four_group_four,
                FOUR_MAX3DIFF_LINES, common_four_max3diff_lines, FOUR_CORNERS, common_four_corners, TWO_DIFF_COLUMNS, common_two_diff_columns, TWO_SQUARES, common_two_squares,
                TWO_DIFF_LINES, common_two_diff_lines, THREE_MAX3DIFF_COLUMNS, common_three_max3diff_columns, X_TILES, common_x_tiles);
        commonGCMap.put(EIGHT_TILES, common_eight_tiles);
        commonGCMap.put(STAIRS, common_stairs);

        personalGCMap = Map.of(p1, personal_GC1, p2, personal_GC2, p3, personal_GC3, p4, personal_GC4, p5, personal_GC5, p6, personal_GC6, p7, personal_GC7, p8, personal_GC8, p9, personal_GC9, p10, personal_GC10);
        personalGCMap.put(p11, personal_GC11);
        personalGCMap.put(p12, personal_GC12);

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

    // Tokens
    public static final Image token_2_points = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tokens/scoring_2.jpg")));
    public static final Image token_4_points = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tokens/scoring_4.jpg")));
    public static final Image token_6_points = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tokens/scoring_6.jpg")));
    public static final Image token_8_points = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tokens/scoring_8.jpg")));
    public static final Image token_full_bookshelf = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("img/tokens/first_full_bookshelf_token.jpg")));


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




    //Get resources
    public static Image getTile(Tile tile) {
        return tileImageMap.get(tile);
    }

    public static Image getToken(Token token) {
        return tokenMap.get(token);
    }

    public static Image getCommonGC(CommonGoalCardIdentifier card){
            return commonGCMap.get(card);
    }

    public static Image getPersonalGC(PersonalGoalCard card){
        return personalGCMap.get(card);
    }



    public static final List<Image> tilesDomain = Arrays.asList(
            book1, book2, book3, cat1, cat2, cat3, frame1, frame2, frame3, game1, game2, game3, plant1, plant2, plant3, trophy1, trophy2, trophy3
    );
}
