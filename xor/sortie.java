package pack.epsi.xor;

/**
 * User: jessygiacomoni
 * Date: 04/11/2013
 * Time: 11:31
 */
public class sortie
{
	private CoucheCachee[] _coucheCachee;
	private double[] _potentielDeNeuroneDeSortie;

	private double _signal;
	private double _potentiel;
	private double _sortieDesiree;
	private double _error;
	private double _erreurCoucheSortie;

	public sortie(CoucheCachee[] couchecachee, double [] potentielDeNeuroneDeSortie , double sortieDesiree)
	{
		_coucheCachee = couchecachee;
		_potentielDeNeuroneDeSortie = potentielDeNeuroneDeSortie;
		_sortieDesiree = sortieDesiree;
		calculSignalDeNeurones();
	}

	//Accessors
	public double getPotentiel()
	{
		return _potentiel;
	}

	public double getSignal()
	{
		return _signal;
	}

	public double getSortieDesiree()
	{
		return _sortieDesiree;
	}

	public double getErreur()
	{
		return _error;
	}

	public void updatePoids(double[] newValuePoids,CoucheCachee[] couchecachee)
	{
		_potentielDeNeuroneDeSortie = newValuePoids;
		_coucheCachee = couchecachee;
		calculSignalDeNeurones();
		calculDeLErreur();
	}


	//Calcul des methodes
	public void calculSignalDeNeurones()
	{
		_potentiel = 0;
		 for( int i =0; i < _coucheCachee.length ; i++)
		 {
			_potentiel += _coucheCachee[i].getSignal() * _potentielDeNeuroneDeSortie[i];
		 }

			_signal = 1 / ( 1 + Math.exp( - _potentiel ));

	}

	public double calculDeLErreur()
	{
		_error = _sortieDesiree - _signal;
		return _error;
	}

	public double getCoucheSortie()
	{
		return _erreurCoucheSortie;
	}
	public double calculSignalErreur()
	{
		double derive = _signal * (1 - _signal);
		_erreurCoucheSortie =_error * derive;
		return _erreurCoucheSortie;
	}
}
