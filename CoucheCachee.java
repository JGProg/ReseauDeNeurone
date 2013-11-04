package pack.epsi;

/**
 * User: jessygiacomoni
 * Date: 04/11/2013
 * Time: 11:08
 */
public class CoucheCachee
{
	private double _potentielDelaForme = 0;
	private double _signal = 0;

	private double[] _neuroneEntree;
	private double[] _poidsEntree;
	double _erreur  = 0;

   public CoucheCachee(double[] neuroneEntree, double[] poidsEntree)
   {
		_neuroneEntree = neuroneEntree;
		_poidsEntree = poidsEntree;

	   // On calul le potentiel
	   propagationAvant();

	   // On calcul le signal en s'aidant du potentiel précédemment calculé
	   calculDuSignal();
   }

	//Accessors
	public double getPotentiel()
	{
		return _potentielDelaForme;
	}

	public double getSignal()
	{
		return _signal;
	}

	public void UpdatePoidsEntrée(double [] newValuePoids)
	{
		_poidsEntree = newValuePoids;

		propagationAvant();
		calculDuSignal();
	}

	//Calcul propagation avant. Attibut _potentiel attribué
	private void propagationAvant()
	{
		_potentielDelaForme = 0;
		for(int i = 0; i< _neuroneEntree.length;i++)
		{
			_potentielDelaForme += _neuroneEntree[i] * _poidsEntree[i];
		}

	}

	// Attibut _signal attribué
	private void calculDuSignal()
	{
		if(_potentielDelaForme != 0)
		{
		_signal = 1/ (1 + Math.exp(-_potentielDelaForme));
		}
		else
		{
			_signal = 0;
		}
	}

	public double calculErreur(double[] ErreurDeSortie, sortie[] sorties)
	{
		double derive = _signal * (1 - _signal);

		for(int i = 0; i < ErreurDeSortie.length ; i++)
		{
			_erreur +=  sorties[i].getCoucheSortie() * ErreurDeSortie[i];
		}
		_erreur = derive * _erreur;
		return _erreur;
	}

	public double[] correctionPoidsSynaptiqueSortie(double[] potentielDeSorties, sortie[] sorties, double eta)
	{
		double[] nouveauPoids  = new double[potentielDeSorties.length];
		for( int i = 0; i< potentielDeSorties.length; i++ )
		{
			nouveauPoids[i] = eta * potentielDeSorties[i] + (sorties[i].getCoucheSortie() * _signal);
		}
		return nouveauPoids;
	}

	public double[] correctionPoidsSynaptiqueEntree(double[] potentielDEntree, double[] entreeDesire, double eta)
	{
		double[] nouveauPoids  = new double[potentielDEntree.length];
		for( int i = 0; i< potentielDEntree.length; i++ )
		{
			nouveauPoids[i] = eta * potentielDEntree[i] + (entreeDesire[i] * _erreur);
		}
		return nouveauPoids;
	}
}
