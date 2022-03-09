package awele.bot.AlakazamShiny;

import awele.bot.CompetitorBot;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Abraham Giuliani
 * Bot qui prend ses décisions selon MinMax et Stratege
 */
public class AlakazamShinyBot extends CompetitorBot
{
    /** Profondeur maximale */
    private static final int MAX_DEPTH = 4 ;

    /**
     * @throws InvalidBotException
     */
    public AlakazamShinyBot () throws InvalidBotException
    {
        this.setBotName ("AlakazamShiny");
        this.addAuthor ("Abraham Giuliani");
    }

    /**
     * Rien à faire
     */
    @Override
    public void initialize ()
    {
    }

    /**
     * Pas d'apprentissage
     */
    @Override
    public void learn ()
    {
    }

    /**
     * Sélection du coup selon l'algorithme MinMax
     */
    @Override
    public double [] getDecision (Board board)
    {
        AlakazamShinyNode.initialize (board, AlakazamShinyBot.MAX_DEPTH);
        return new MaxNode(board).getDecision ();
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
}
