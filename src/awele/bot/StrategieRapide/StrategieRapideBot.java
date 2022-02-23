package awele.bot.StrategieRapide;

import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

public class StrategieRapideBot extends DemoBot
{
    /**
     * @throws InvalidBotException
     */
    private boolean premierTour;
    private int dernierCoupJoue;

    public StrategieRapideBot() throws InvalidBotException
    {
        this.setBotName ("Le stratege rapide");
        this.addAuthor ("Abraham");
    }

    /**
     * Rien à faire
     */
    @Override
    public void initialize ()
    {
        premierTour = true;
    }

    /**
     * Retourne une valeur décroissante avec l'index du trou
     */
    @Override
    public double [] getDecision (Board board)
    {
        double [] decision = new double [Board.NB_HOLES];
        int [] trouJoueur = board.getPlayerHoles();
        int [] trouAdversaire = board.getOpponentHoles();
        for (int i = 0; i < decision.length; i++) {
            decision[i] = i;
            if (!premierTour) {
                if (board.getNbSeeds() > 40) {
                    if (i == dernierCoupJoue) {
                        decision[i] = 0;
                    }
                } else if (board.getNbSeeds() > 20) {
                    if (trouJoueur[i] + i >= 19 && trouAdversaire[(trouJoueur[i] + i) % 6] < 3) {
                        decision[i] = Integer.MAX_VALUE;
                    } else {
                        for (int j = i + 1; j < decision.length; j++) {
                            if (trouJoueur[i] + i > j && trouJoueur[j] > 4) {
                                decision[i] += 1;
                            }
                        }
                    }
                } else {
                    try {
                        double IValue = decision[i];
                        decision[i] = Integer.MAX_VALUE;
                        int test = board.playMoveSimulationScore(board.getCurrentPlayer(), decision);
                        decision[i] = test + IValue;
                    } catch (InvalidBotException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                premierTour = false;
            }

        }
        double max = decision[0];
        int index = 0;
        for (int i = 0; i < decision.length; i++) {
            if (decision[i] > max) {
                max = decision[i];
                index = i;
            }
        }
        dernierCoupJoue = index;
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
