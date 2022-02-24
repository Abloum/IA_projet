package awele.bot.bourrinos;

import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

public class BourrinosShiny extends DemoBot
{
    /**
     * @throws InvalidBotException
     */
    public BourrinosShiny () throws InvalidBotException
    {
        this.setBotName ("Bourrinos shiny");
        this.addAuthor ("Abraham GIULIANI");
    }

    /**
     * Rien à faire
     */
    @Override
    public void initialize ()
    {
    }

    /**
     * Retourne une valeur décroissante avec l'index du trou
     */
    @Override
    public double [] getDecision (Board board)
    {
        double [] decision = new double [Board.NB_HOLES];
        for (int i = 0; i < decision.length; i++) {
            decision[i] = i;
            try {
                double IValue = decision[i];
                decision[i] = Integer.MAX_VALUE;
                int test = board.playMoveSimulationScore(board.getCurrentPlayer(), decision);
                decision[i] = test*2 + IValue;
            } catch (InvalidBotException e) {
                e.printStackTrace();
            }
        }
        return decision;
    }

    /**
     * Pas d'apprentissage
     */
    @Override
    public void learn ()
    {
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
}
