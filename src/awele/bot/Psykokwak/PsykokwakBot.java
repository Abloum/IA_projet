package awele.bot.Psykokwak;

import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Abraham Giuliani
 * Bot qui prend ses décisions selon MinMax et Bourrinos
 */
public class PsykokwakBot extends CompetitorBot
{
    /** Profondeur maximale */
    private static final int MAX_DEPTH = 3;

    /**
     * @throws InvalidBotException
     */
    public PsykokwakBot () throws InvalidBotException
    {
        this.setBotName ("Psykokwak");
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
        PsykokwakNode.initialize (board, PsykokwakBot.MAX_DEPTH);
        return new MaxNode (board).getDecision ();
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
}
