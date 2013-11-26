package pack.epsi.xor;

public class Main {

    public static void main(String[] args)
	{
		double ErreurMoyenne = 0.200;
		double ErreurGlobale = 9;
		int cptLoop =0;

		//Etape 1
		double [] neuroneEntree = { 0, 1};
		double [] poidsEntreeH1 = { 0.100, 0.150, 0.050 };
		double [] poidsEntreeH2 = { 0.120, 0.180, 0.080 };


		double SortieDesireY1 = 0;
		double [] PotentielDeNeuroneDeSortieY1 = {0.100, 0.140};

		/*double SortieDesireY2 = 0.9;
		double [] PotentielDeNeuroneDeSortieY2 = {0.125, 0.210};

		double SortieDesireY3 = 0.9;
		double [] PotentielDeNeuroneDeSortieY3 = {0.130, 0.070}; */


		double [] poidsConnectionSortie = {PotentielDeNeuroneDeSortieY1[0]};
		double [] poidsConnectionSortie2 = {PotentielDeNeuroneDeSortieY1[1]};

		double eta = 1;


		//Etape 2
		CoucheCachee H1 = new CoucheCachee(neuroneEntree, poidsEntreeH1);
		CoucheCachee H2 = new CoucheCachee(neuroneEntree, poidsEntreeH2);

		// On calul le potentiel
		H1.propagationAvant();
		H2.propagationAvant();

		// On calcul le signal en s'aidant du potentiel précédemment calculé
		H1.calculDuSignal();
		H2.calculDuSignal();

		System.out.println("Le potentiel de H1 est "+ H1.getPotentiel()+" et le signal est : "+ H1.getSignal());
		System.out.println("Le potentiel de H2 est "+ H2.getPotentiel()+" et le signal est : "+ H2.getSignal());



		CoucheCachee[] TabCouche = {H1, H2 };

		sortie Y1 = new sortie(TabCouche, PotentielDeNeuroneDeSortieY1 , SortieDesireY1);
		//sortie Y2 = new sortie(TabCouche, PotentielDeNeuroneDeSortieY2 , SortieDesireY2);
		//sortie Y3 = new sortie(TabCouche, PotentielDeNeuroneDeSortieY3 , SortieDesireY3);

		// Etape 3
		System.out.println("la sortie d'erreur est pour Y1 : "+ Y1.calculDeLErreur());
		//System.out.println("la sortie d'erreur est pour Y2 : "+ Y2.calculDeLErreur());
		//System.out.println("la sortie d'erreur est pour Y3 : "+ Y3.calculDeLErreur());

		do
		{
			ErreurGlobale = 0.5 * (Math.pow(Y1.getErreur(),2.0));
			System.out.println("l'erreur globale est " + ErreurGlobale);

			if(ErreurGlobale <= ErreurMoyenne)
			{
				System.out.println("Terminé");
			}
			else
			{
				// Etape 4
				System.out.println("Le Signal d'erreur de la couche de sortie pour Y1 : "+ Y1.calculSignalErreur());
				//System.out.println("Le Signal d'erreur de la couche de sortie pour Y2 : "+ Y2.calculSignalErreur());
				//System.out.println("Le Signal d'erreur de la couche de sortie pour Y3 : "+ Y3.calculSignalErreur());

				//Etape 5

				sortie[] sorties = {Y1 };
				System.out.println("Calcul d'erreur de la couche cachée est : "+ H1.calculErreur(poidsConnectionSortie, sorties ));
				System.out.println("Calcul d'erreur de la couche cachée est : "+ H2.calculErreur(poidsConnectionSortie2, sorties ));


				//Etape 6
				double[] newValeurPoidsConnectionH1 = H1.correctionPoidsSynaptiqueSortie(poidsConnectionSortie, sorties, eta);
				PotentielDeNeuroneDeSortieY1[0] = newValeurPoidsConnectionH1[0];
				//PotentielDeNeuroneDeSortieY2[0] = newValeurPoidsConnectionH1[1];
				//PotentielDeNeuroneDeSortieY3[0] = newValeurPoidsConnectionH1[2];
				poidsConnectionSortie = newValeurPoidsConnectionH1;


				double[] newValeurPoidsConnectionH2 = H2.correctionPoidsSynaptiqueSortie(poidsConnectionSortie2, sorties, eta);
				PotentielDeNeuroneDeSortieY1[1] = newValeurPoidsConnectionH2[0];
				//PotentielDeNeuroneDeSortieY2[1] = newValeurPoidsConnectionH2[1];
				//PotentielDeNeuroneDeSortieY3[1] = newValeurPoidsConnectionH2[2];
				poidsConnectionSortie2 = newValeurPoidsConnectionH2;

				System.out.println("Nouveau poids de sortie H1" );
				for (int i=0; i<newValeurPoidsConnectionH1.length-1;i++)
				{
					System.out.print(newValeurPoidsConnectionH1[i]+ " , " );
				}
				System.out.println(newValeurPoidsConnectionH1[newValeurPoidsConnectionH1.length-1]);


				System.out.println("Nouveau poids de sortie H2" );
				for (int i=0; i<newValeurPoidsConnectionH2.length-1;i++)
				{
					System.out.print(newValeurPoidsConnectionH2[i] + " , ");
				}
				System.out.println(newValeurPoidsConnectionH2[newValeurPoidsConnectionH2.length-1]);


				//Etape 7
				poidsEntreeH1 = H1.correctionPoidsSynaptiqueEntree(poidsEntreeH1, neuroneEntree,  eta);

				System.out.println("Nouveau poids d'entrée  X1" );
				for (int i=0; i<poidsEntreeH1.length-1;i++)
				{
					System.out.print(poidsEntreeH1[i]+ " , " );
				}
				System.out.println(poidsEntreeH1[poidsEntreeH1.length-1]);

				poidsEntreeH2 = H2.correctionPoidsSynaptiqueEntree(poidsEntreeH2, neuroneEntree,  eta);
				System.out.println("Nouveau poids d'entrée  X2" );
				for (int i=0; i<poidsEntreeH2.length-1;i++)
				{
					System.out.print( poidsEntreeH2[i]+ " , " );
				}
				System.out.println( poidsEntreeH2[poidsEntreeH2.length-1]);


				H1.UpdatePoidsEntrée(poidsEntreeH1);
				H2.UpdatePoidsEntrée(poidsEntreeH2);

				CoucheCachee[] TabCouche2 = {H1, H2 };
				Y1.updatePoids(PotentielDeNeuroneDeSortieY1,TabCouche2);
				//Y2.updatePoids(PotentielDeNeuroneDeSortieY2,TabCouche2);
				//Y3.updatePoids(PotentielDeNeuroneDeSortieY3,TabCouche2);

			}
			cptLoop++;
		} while(ErreurGlobale >= ErreurMoyenne) ;

		System.out.println("Le potentiel de H1 est "+ H1.getPotentiel());
		System.out.println("Le potentiel de H2 est "+ H2.getPotentiel());

		System.out.println("Le potentiel de Y1 est "+ Y1.getPotentiel()+" et le signal est : "+Y1.getSignal());
		//System.out.println("Le potentiel de Y2 est "+ Y2.getPotentiel()+" et le signal est : "+Y2.getSignal());
		//System.out.println("Le potentiel de Y3 est "+ Y3.getPotentiel()+" et le signal est : "+Y3.getSignal());
		System.out.println("Le compteur de loop est de "+cptLoop);
	}
}
