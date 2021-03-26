import javax.swing.event.ListDataEvent;
import java.util.*;

public class PRVP {
	
	public static void main(String[] args) {
		ManipuladorArquivo manarq = new ManipuladorArquivo();
<<<<<<< Updated upstream
		manarq.carregarArquivo("C:\\Users\\marti\\OneDrive\\Desktop\\C-pvrp\\pr01");
=======
		manarq.carregarArquivo("C:\\Users\\marti\\OneDrive\\Desktop\\C-pvrp\\pr02");
>>>>>>> Stashed changes
		//m: número de veiculos. n: número de clientes. t: número de dias
		//D: maximum duration of a route. Q: maximum load of a vehicle
		List<Veiculo> veiculos = monteCarlo(manarq.getClientes(), manarq.getVeiculos(), manarq.getM(), manarq.getN(), manarq.getT());
		int x = checarSolucao(veiculos, manarq.clientes, manarq.t);
		for(int i=0;i<manarq.getT();i++) {
			for(int j=0; j<veiculos.size();j++) {
				veiculos.get(j).getRotaDias().get(i).add(0, 0);
				veiculos.get(j).getRotaDias().get(i).add(0);
				System.out.print((i+1)+" ");
				System.out.print((j+1)+" ");
				System.out.print(getLocacaoAtingida(veiculos, manarq.clientes, i, j) + " ");
				System.out.print(getDuracaoServico(veiculos, manarq.clientes, i, j) + " ");
				System.out.print(veiculos.get(j).getRotaDias().get(i));
				System.out.println();
			}
			System.out.println();
		}
		if (x == 0){
			System.out.println("É solução!!!");
		}else if(x == -1){
			System.out.println("Não é solução :(");
			System.out.println("Nem todos os clientes foram visitados!");
		}else if(x == -2){
			System.out.println("Não é solução :(");
			System.out.println("Carga máxima ultrapassada!");
		}else{
			System.out.println("Não é solução :(");
			System.out.println("Duração máxima ultrapassada!");
		}
	}

	public static List<Veiculo> monteCarlo(List<Cliente> clientes, List<Veiculo> veiculos, int m, int n, int t){
		Random rand = new Random();
		int randomNum, randomNum2;
		List<List<Integer>> maxLoud = new ArrayList<List<Integer>>();
		List<List<Double>> maxDuration = new ArrayList<List<Double>>();
		Set<Integer> c = new TreeSet<Integer>();
		List<List<Cliente>> clienteDia = new ArrayList<>();
		for(int i=0;i</*m**/t;i++) {
			clienteDia.add(new ArrayList<>());
			clienteDia.set(i, new ArrayList<>());
		}
		for(int i=0;i<m*t;i++) {
			List<Integer> aux = new ArrayList<Integer>();
			List<Double> aux2 = new ArrayList<>();
			for(int j=0;j<t;j++) {
				aux.add(0);
				aux2.add(0.0);
			}
			maxLoud.add(aux);
			maxDuration.add(aux2);
		}
		while(c.size() != n) {
			randomNum = rand.nextInt(n);
			if (c.contains(randomNum)) {
				randomNum = 1 + rand.nextInt(n - 1);
			} else {
				c.add(clientes.get(randomNum).numeroCliente);
				if (clientes.get(randomNum).numberCombinations == 0) {
					continue;
				}
				randomNum2 = rand.nextInt(clientes.get(randomNum).numberCombinations);
				String combinacao = Integer.toBinaryString(clientes.get(randomNum).possibleVisitCombinations.get(randomNum2));
				if (combinacao.length() < t) {
					int x = combinacao.length();
					for (int j = 0; j < t - x; j++) {
						combinacao = "0" + combinacao;
					}
				}
<<<<<<< Updated upstream
				for (int l = t - 1; l >= 0; l--) {
					if (combinacao.charAt(l) == '1') {
						clienteDia.get(l).add(clientes.get(randomNum));
					}
				}
			}
		}
		List<Integer> aux = new ArrayList<>();
		for(int i=0;i<t;i++){
			List<Cliente> anterior = new ArrayList<>();
			for(int j=0;j<m;j++){
				randomNum = rand.nextInt(clienteDia.get(i).size());
				while (true){
					if(aux.contains(randomNum)){
						randomNum = rand.nextInt(clienteDia.get(i).size());
					}else{
						break;
					}
				}
				aux.add(randomNum);
				veiculos.get(j).rotaDias.get(i).add(clientes.get(randomNum).numeroCliente);
				anterior.add(clientes.get(randomNum));
				clienteDia.get(i).remove(clientes.get(randomNum));
			}
			int k=0;
			while(clienteDia.get(i).size()>0) {
				Cliente newCliente = gerarCliente(clienteDia.get(i), anterior.get(k));
				int auxiliar = maxLoud.get(k).get(i) + newCliente.demand;
				double auxiliar2 = maxDuration.get(k).get(i) + distance(anterior.get(k), newCliente);
				if((auxiliar <= veiculos.get(k).maxLoud.get(i) && auxiliar2 <= veiculos.get(k).maxDuration.get(i)) || (veiculos.get(k).maxLoud.get(i) == 0 && auxiliar2 <= veiculos.get(k).maxDuration.get(i))) {
					maxLoud.get(k).set(i, maxLoud.get(k).get(i) + newCliente.demand);
					maxDuration.get(k).set(i, maxDuration.get(k).get(i) + auxiliar2);
					veiculos.get(k).rotaDias.get(i).add(newCliente.numeroCliente);
					clienteDia.get(i).remove(newCliente);
					anterior.set(k, newCliente);
				}else{
					if(k==m-1){
						if(!((auxiliar <= veiculos.get(k-1).maxLoud.get(i) && auxiliar2 <= veiculos.get(k-1).maxDuration.get(i)) || (veiculos.get(k-1).maxLoud.get(i) == 0 && auxiliar2 <= veiculos.get(k-1).maxDuration.get(i))) ){
							maxLoud.get(k).set(i, maxLoud.get(k).get(i) + newCliente.demand);
							maxDuration.get(k).set(i, maxDuration.get(k).get(i) + auxiliar2);
							veiculos.get(k).rotaDias.get(i).add(newCliente.numeroCliente);
							clienteDia.get(i).remove(newCliente);
							anterior.set(k, newCliente);
						}
						k=0;
					}else{
						k++;
=======
				for(int l=t-1; l>=0; l--) {
					if(combinacao.charAt(l)== '1') {
						randomNum2 = rand.nextInt(m);
						while(true){
							if(opt == 1){
								int auxiliar = maxLoud.get(randomNum2).get(l) + clientes.get(randomNum).demand;
								int auxiliar2 = maxDuration.get(randomNum2).get(l) + clientes.get(randomNum).getServiceDuration();
								if(randomNum2 == m-1 ||(auxiliar <= veiculos.get(randomNum2).maxLoud.get(l) && auxiliar2 <= veiculos.get(randomNum2).maxDuration.get(l)) || (veiculos.get(randomNum2).maxLoud.get(l) == 0 && auxiliar2 <= veiculos.get(randomNum2).maxDuration.get(l))) {
									maxLoud.get(randomNum2).set(l, maxLoud.get(randomNum2).get(l) + clientes.get(randomNum).demand);
									maxDuration.get(randomNum2).set(l, maxDuration.get(randomNum2).get(l) + clientes.get(randomNum).serviceDuration);
									veiculos.get(randomNum2).rotaDias.get(l).add(clientes.get(randomNum).numeroCliente);
									break;
								}else {
									randomNum2 = rand.nextInt(m);
									//randomNum2++;

									if(randomNum2 >= m*n)
										break;
								}
							}else{
								int auxiliar = maxLoud.get(randomNum2).get(l) + clientes.get(randomNum).demand;
								if((auxiliar <= veiculos.get(randomNum2).maxLoud.get(l)) || (veiculos.get(randomNum2).maxLoud.get(l) == 0 )) {
									maxLoud.get(randomNum2).set(l, maxLoud.get(randomNum2).get(l) + clientes.get(randomNum).demand);
									veiculos.get(randomNum2).rotaDias.get(l).add(clientes.get(randomNum).numeroCliente);
									break;
								}else {
									//randomNum2 = rand.nextInt(m);
									randomNum2++;
									if(randomNum2 >= m)
										break;
								}
							}
						}		
>>>>>>> Stashed changes
					}
				}
			}
		}
		return veiculos;
	}


	public static Cliente gerarCliente(List<Cliente> clientes, Cliente anterior){
		quickSortCliente(clientes, anterior, 0, clientes.size());
		Random rand = new Random();
		int numeroAleatorio;
		if(clientes.size()>=5){
			numeroAleatorio = rand.nextInt(5);
		}else{
			numeroAleatorio = rand.nextInt(clientes.size());
		}
		return clientes.get(0);
	}

	public static List<Cliente> quickSortCliente(List<Cliente> a, Cliente anterior, int ini, int fim){
		if(ini<fim){
			int pp = particao(a, anterior,ini, fim);
			quickSortCliente(a, anterior,ini, pp);
			quickSortCliente(a, anterior,pp+1, fim);
		}
		return a;
	}

	public static int particao(List<Cliente> a, Cliente anterior, int ini, int fim){
		Cliente pivo = a.get(fim-1);
		int inicio = ini;
		int finish = fim;
		for(int i=inicio;i<finish;i++){
			if(distance(a.get(i), anterior) > distance(pivo, anterior)){
				fim++;
			}else{
				fim++;
				ini++;
				Cliente aux = a.get(i);
				a.set(i, a.get(ini-1));
				a.set(ini-1, aux);
			}
		}
		return ini-1;
	}

	public static int checarSolucao(List<Veiculo> veiculos, List<Cliente> clientes, int t) {
		for (Cliente cliente : clientes) {
			String combinacao = Integer.toBinaryString(cliente.getFrequencyOfVisit());
			int frequencia = 0;
			if (combinacao.length() < t) {
				int x = combinacao.length();
				for (int j = 0; j < t - x; j++) {
					combinacao = "0" + combinacao;
				}
				for (int l = t - 1; l >= 0; l--) {
					for (Veiculo veiculo : veiculos) {
						if (veiculo.rotaDias.get(l).contains(cliente.numeroCliente)) {
							frequencia++;
						}
					}
				}
			}
			if (frequencia != cliente.frequencyOfVisit && cliente.frequencyOfVisit !=0) {
				return -1;
			}
		}
		for(int i=0;i<t;i++){
			for(int j=0;j<veiculos.size();j++){
				if(veiculos.get(i).maxLoud.get(i) > 0 && veiculos.get(j).maxLoud.get(i) < getLocacaoAtingida(veiculos, clientes, i, j)){
					return -2;
				}
				if(veiculos.get(j).maxDuration.get(i) < getDuracaoServico(veiculos, clientes, i, j)){
					return -3;
				}
			}
		}
		return 0;
	}

	public static double getLocacaoAtingida(List<Veiculo> veiculos, List<Cliente> clientes,int i, int j) {
		int x = 0;
		for(int k = 0; k<veiculos.get(j).getRotaDias().get(i).size(); k++) {
			x += clientes.get(veiculos.get(j).getRotaDias().get(i).get(k)).getDemand();
		}
		return x;
	}

	public static double getDuracaoServico(List<Veiculo> veiculos, List<Cliente> clientes,int i, int j) {
		int x = 0;
		for(int k = 0; k<veiculos.get(j).getRotaDias().get(i).size(); k++) {
			x += clientes.get(veiculos.get(j).getRotaDias().get(i).get(k)).getServiceDuration();
		}
		return x;
	}

	public static Double distance(Cliente a, Cliente b){
		return Math.sqrt(Math.pow(a.getxCoordinate() - b.getxCoordinate(), 2) + Math.pow(a.getyCoordinate() - b.getyCoordinate() , 2));
	}
}