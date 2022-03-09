package awele.bot.MegaAlakazam;

import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Abraham Giuliani
 * Bot qui prend ses décisions selon MinMax et Stratege
 */
public class MegaAlakazamBot extends CompetitorBot
{

    private static final int BUDGET = 45000;

    /**
     * @throws InvalidBotException
     */
    public MegaAlakazamBot () throws InvalidBotException
    {
        this.setBotName ("MegaAlakazam");
        this.addAuthor ("Abraham Giuliani Camille Masson");
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
        MegaAlakazamNode.initialize (board);
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
