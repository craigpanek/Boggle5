/**
 * This class represents a computer player who is able to automatically
 * find all the words that exist for a given bogglegame.
 * @author Craig Panek
 * Date: 6-19-2014
 */

import java.io.FileNotFoundException;

public class ComputerPlayer {
    
	private BoggleGame game;
    private static final int maxLevelToSearch = 9;
    
    public ComputerPlayer(BoggleBoard board) throws FileNotFoundException {
    	this.game = new BoggleGame(board);
    }
    
    /**
     * Searches for all the words on the game.
     * @return a string with all the words found, separated by newlines.
     */
	public String findWords() {
	    game.startWord();
	    for(int row = 0; row < game.getNumRows(); row++) {
	        for(int col = 0; col < game.getNumCols(); col++) {
	            // preselect before call to findwords(int, int, int)
	            game.selectCube(row, col);
	            findWords(row, col, 1);
	        }
	    }
	    return game.getWordList();
	}
	
    /**
     * Searches, recursively, for all the words on the game that start at (row, column).
     * @param row row at which all words start
     * @param column column at which all words start
     * @param depth maximum depth of recursion (longest words to search for).
     */
	private void findWords(int row, int column, int depth) {
	    game.stepBack();
	    game.selectCube(row, column); // this call should always return true
	    game.isWord(); // isWord() adds current 'word' to wordList if it's valid
	    if(depth >= maxLevelToSearch) {
	        game.stepBack();
	        return;
	    }
	    depth++;
	    for(int dRow = -1; dRow <= 1; dRow++) {
	        for(int dCol = -1; dCol <= 1; dCol++) {
	                if(game.selectCube(row + dRow, column + dCol))
	                    findWords(row + dRow, column + dCol, depth);
	        }
	    }
	    game.stepBack();
    }
}