package awele.bot.MegaAlakazam;

import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Abraham Giuliani
 * Bot qui prend ses décisions selon MinMax et Stratege
 */
public class MegaAlakazamBot extends DemoBot
{
    /** Profondeur maximale */
    private static final int MAX_DEPTH = 100;

    private static final int BUDGET = 45000;

    /**
     * @throws InvalidBotException
     */
    public MegaAlakazamBot () throws InvalidBotException
    {
        this.setBotName ("MegaAlakazam");
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
        MegaAlakazamNode.initialize (board, MegaAlakazamBot.MAX_DEPTH);
        return new MaxNode(board, MegaAlakazamBot.BUDGET).getDecision ();
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
}
