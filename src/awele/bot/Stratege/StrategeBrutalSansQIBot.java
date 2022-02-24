package awele.bot.Stratege;

import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

public class StrategeBrutalSansQIBot extends CompetitorBot
{
    /**
     * @throws InvalidBotException
     */
    private boolean premierTour;
    private int dernierCoupJoue;

    public StrategeBrutalSansQIBot() throws InvalidBotException
    {
        this.setBotName ("Stratege brutal mais toujours sans QI");
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
     * Applique des stratégies connues
     */
    @Override
    public double [] getDecision (Board board)
    {
        double [] decision = new double [Board.NB_HOLES];
        int [] trouJoueur = board.getPlayerHoles();
        int [] trouAdversaire = board.getOpponentHoles();
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
            if (!premierTour) {
                /** En début de partie, on ne joue pas 2x le même coup **/
                if (board.getNbSeeds() > 50) {
                    if (i == dernierCoupJoue) {
                        decision[i] = 0;
                    }
                }
                /** En milieu de partie, strategie du Krou **/
                else if (board.getNbSeeds() > 22) {
                    /** Si un Krou est valide est permet de scorer, on joue ce trou peu importe la situation des autres **/
                    try {
                        double IValue = decision[i];
                        decision[i] += 100;
                        if (trouJoueur[i] + i >= 19 && board.playMoveSimulationScore(board.getCurrentPlayer(),decision)<=1)
                        {
                            decision[i] = IValue;
                        }
                    } catch (InvalidBotException e) {
                        e.printStackTrace();
                    }
                    /** Si un Krou n'est pas disponible, on met une legere priorité au trou pouvant initier un Krou **/
                }
                /** En fin de partie, on ne cherche qu'a joué des coups qui nous permettent de scorer **/
            }
            else {
                premierTour = false;
            }

        }
        /** retenir le dernier coup joué si l'on est en debut de partie **/
        if (board.getNbSeeds() > 40) {
            double max = decision[0];
            int index = 0;
            for (int i = 0; i < decision.length; i++) {
                if (decision[i] > max) {
                    max = decision[i];
                    index = i;
                }
            }
            dernierCoupJoue = index;
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

    @Override
    public void finish ()
    {
    }
}
