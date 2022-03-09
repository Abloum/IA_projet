package awele.bot.Relicanth;

import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Alexandre Blansché
 * Bot qui prend ses décisions selon le MinMax
 */
public class RelicanthBot extends CompetitorBot
{
    /** Profondeur maximale */
    private static final int MAX_DEPTH = 100;

    private static final int BUDGET = 45000;
	
    /**
     * @throws InvalidBotException
     */
    public RelicanthBot() throws InvalidBotException
    {
        this.setBotName ("Relicanth");
        this.addAuthor ("Masson Camille");
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
        RelicanthNode.initialize (board, RelicanthBot.MAX_DEPTH);
        return new MaxNode(board, RelicanthBot.BUDGET).getDecision ();
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
}
