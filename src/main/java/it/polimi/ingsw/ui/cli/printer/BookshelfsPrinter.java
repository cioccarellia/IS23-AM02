package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.cli.Console;

import java.util.ArrayList;


public class BookshelfsPrinter {

    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();


    public static void print(ArrayList<Bookshelf> bookshelvesList, int dimm) {
        int verticalGuideNumber = 0;

            Console.out("    0  1  2  3  4");
            for (int i = 0; i < rows; i++) {

                Console.out(verticalGuideNumber);
                Console.out("  ");
                int k=0;

                for (int j = 0; j < cols*dimm; j++) {

                    if (bookshelvesList.get(dimm).getShelfMatrix()[i][j] != null) {
                        if(j==5) { k++; }
                        if(j>4)
                        {
                            String tileText = TilePrinter.print(bookshelvesList.get(k).getShelfMatrix()[i][j-5]);
                        }
                        if(j==10) { k++; }
                        if(j>9)
                        {
                            String tileText = TilePrinter.print(bookshelvesList.get(k).getShelfMatrix()[i][j-10]);
                        }
                        if(j==15) { k++; }
                        if(j>14)
                        {
                            String tileText = TilePrinter.print(bookshelvesList.get(k).getShelfMatrix()[i][j-15]);
                        }
                        String tileText = TilePrinter.print(bookshelvesList.get(dimm).getShelfMatrix()[i][j]);

                        Console.out(" " + tileText + " ");
                    } else {
                        Console.out("   ");
                    }
                    if(j==4) { Console.out("   "+ verticalGuideNumber); }
                    if(j==9) { Console.out("   "+ verticalGuideNumber); }
                    if(j==14) { Console.out("   "+ verticalGuideNumber); }
                }

                Console.out("\n");
                verticalGuideNumber++;
            }
    }

}
