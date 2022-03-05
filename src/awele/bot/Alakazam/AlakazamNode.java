package awele.bot.Alakazam;

import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Alexandre Blansché
 * Noeud d'un arbre MinMax
 */
public abstract class AlakazamNode
{
    /** Numéro de joueur de l'IA */
    private static int player;

    /** Profondeur maximale */
    private static int maxDepth;

    private int DernierCoupJoue;

    /** L'évaluation du noeud */
    private double evaluation;

    /** Évaluation des coups selon MinMax */
    private double [] decision;

    /**
     * Constructeur...
     * @param board L'état de la grille de jeu
     * @param depth La profondeur du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta Le seuil pour la coupe beta
     */
    public AlakazamNode (Board board, int depth, double alpha, double beta, int dernierCoupJoue)
    {
        /* On crée un tableau des évaluations des coups à jouer pour chaque situation possible */
        this.decision = new double [Board.NB_HOLES];
        /* Initialisation de l'évaluation courante */
        this.evaluation = this.worst ();
        /* On parcourt toutes les coups possibles */
        for (int i = 0; i < Board.NB_HOLES; i++)
            /* Si le coup est jouable */
            if (board.getPlayerHoles () [i] != 0 && !((board.getNbSeeds() > 50)&&(dernierCoupJoue==i)))
            {
                /* Sélection du coup à jouer */
                double [] decision = new double [Board.NB_HOLES];
                decision [i] = 1;
                /* On copie la grille de jeu et on joue le coup sur la copie */
                Board copy = (Board) board.clone ();
                try
                {
                    int score = copy.playMoveSimulationScore (copy.getCurrentPlayer (), decision);
                    copy = copy.playMoveSimulationBoard (copy.getCurrentPlayer (), decision);
                    /* Si la nouvelle situation de jeu est un coup qui met fin à la partie,
                       on évalue la situation actuelle */
                    if ((score < 0) ||
                            (copy.getScore (Board.otherPlayer (copy.getCurrentPlayer ())) >= 25) ||
                            (copy.getNbSeeds () <= 6))
                        this.decision [i] = this.diffScore (copy);
                        /* Sinon, on explore les coups suivants */
                    else
                    {
                        /* Si la profondeur maximale n'est pas atteinte */
                        if (depth < AlakazamNode.maxDepth)
                        {
                            /* On construit le noeud suivant */
                            AlakazamNode child = this.getNextNode (copy, depth + 1, alpha, beta, i);
                            /* On récupère l'évaluation du noeud fils */
                            this.decision [i] = child.getEvaluation ();
                        }
                        /* Sinon (si la profondeur maximale est atteinte), on évalue la situation actuelle */
                        else {
                            this.decision[i] = this.BourrinosShiny(i, copy);
                        }
                    }
                    /* L'évaluation courante du noeud est mise à jour, selon le type de noeud (MinNode ou MaxNode) */
                    this.evaluation = this.minmax (this.decision [i], this.evaluation);
                    /* Coupe alpha-beta */
                    if (depth > 0)
                    {
                        alpha = this.alpha (this.evaluation, alpha);
                        beta = this.beta (this.evaluation, beta);
                    }
                }
                catch (InvalidBotException e)
                {
                    this.decision [i] = 0;
                }
            }
    }

    /** Pire score pour un joueur */
    protected abstract double worst ();

    /**
     * Initialisation
     */
    protected static void initialize (Board board, int maxDepth)
    {
        AlakazamNode.maxDepth = maxDepth;
        AlakazamNode.player = board.getCurrentPlayer ();
    }

    private int diffScore (Board board)
    {
        return board.getScore (AlakazamNode.player) - board.getScore (Board.otherPlayer (AlakazamNode.player));
    }

    private double BourrinosShiny(int i, Board board){
            try {
                double IValue = this.decision[i];
                this.decision[i] = Integer.MAX_VALUE;
                int test = board.playMoveSimulationScore(board.getCurrentPlayer(), decision);
                if (board.getCurrentPlayer()==AlakazamNode.player) {
                    return test + IValue + i + diffScore(board);
                }
                else return diffScore(board) - test + IValue;
            } catch (InvalidBotException e) {
                e.printStackTrace();
            }
            return diffScore(board)+i;
        }

        private double Stratege(int i, Board board){
            int [] trouJoueur = board.getPlayerHoles();
            /** En milieu de partie, strategie du Krou **/
            if (board.getNbSeeds() > 22) {
                /** Si un Krou est valide est permet de scorer, on joue ce trou peu importe la situation des autres **/
                try {
                    double IValue = decision[i];
                    decision[i] += 100;
                    if (trouJoueur[i] + i >= 19 && board.playMoveSimulationScore(board.getCurrentPlayer(),decision)>2)
                    {
                        return 1000+i;
                    }
                    return BourrinosShiny(i, board);
                } catch (InvalidBotException e) {
                    return i;
                }
            }
            /** En fin de partie, on ne cherche qu'a joué des coups qui nous permettent de scorer **/
            else return BourrinosShiny(i,board);
            }

    /**
     * Mise à jour de alpha
     * @param evaluation L'évaluation courante du noeud
     * @param alpha L'ancienne valeur d'alpha
     * @return
     */
    protected abstract double alpha (double evaluation, double alpha);

    /**
     * Mise à jour de beta
     * @param evaluation L'évaluation courante du noeud
     * @param beta L'ancienne valeur de beta
     * @return
     */
    protected abstract double beta (double evaluation, double beta);

    /**
     * Retourne le min ou la max entre deux valeurs, selon le type de noeud (MinNode ou MaxNode)
     * @param eval1 Un double
     * @param eval2 Un autre double
     * @return Le min ou la max entre deux valeurs, selon le type de noeud
     */
    protected abstract double minmax (double eval1, double eval2);

    /**
     * Indique s'il faut faire une coupe alpha-beta, selon le type de noeud (MinNode ou MaxNode)
     * @param eval L'évaluation courante du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta Le seuil pour la coupe beta
     * @return Un booléen qui indique s'il faut faire une coupe alpha-beta
     */
    protected abstract boolean alphabeta (double eval, double alpha, double beta);

    /**
     * Retourne un noeud (MinNode ou MaxNode) du niveau suivant
     * @param board L'état de la grille de jeu
     * @param depth La profondeur du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta Le seuil pour la coupe beta
     * @return Un noeud (MinNode ou MaxNode) du niveau suivant
     */
    protected abstract AlakazamNode getNextNode (Board board, int depth, double alpha, double beta, int DernierCoupJoue);

    /**
     * L'évaluation du noeud
     * @return L'évaluation du noeud
     */
    double getEvaluation ()
    {
        return this.evaluation;
    }

    /**
     * L'évaluation de chaque coup possible pour le noeud
     * @return
     */
    double [] getDecision ()
    {
        return this.decision;
    }
}
