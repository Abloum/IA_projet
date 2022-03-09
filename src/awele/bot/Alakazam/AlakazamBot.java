package awele.bot.Alakazam;

import awele.bot.Alakazam.AlakazamNode;
import awele.bot.Alakazam.MaxNode;
import awele.bot.CompetitorBot;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Abraham Giuliani
 * Bot qui prend ses décisions selon MinMax et Stratege
 */
public class AlakazamBot extends CompetitorBot
{
    /** Profondeur maximale */
    private static final int MAX_DEPTH = 3;

    /**
     * @throws InvalidBotException
     */
    public AlakazamBot () throws InvalidBotException
    {
        this.setBotName ("Alakazam");
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
        AlakazamNode.initialize (board, AlakazamBot.MAX_DEPTH);
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
