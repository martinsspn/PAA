import java.util.*;

public class PRVP {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<List<Double>> matriz = new ArrayList<List<Double>>();
		ManipuladorArquivo manarq = new ManipuladorArquivo();
		manarq.carregarArquivo("C:\\Users\\marti\\OneDrive\\Desktop\\C-pvrp\\pr05");
		//m: número de veiculos. n: número de clientes. t: número de dias
		//D: maximum duration of a route. Q: maximum load of a vehicle
		int opt = 1;
		List<Veiculo> veiculos = construtorAleatorio(manarq.getClientes(), manarq.getVeiculos(), manarq.getM(), manarq.getN(), manarq.getT(), opt);
		int x=5;
		for(int i=0;i<100;i++) {
			x = checarSolucao(veiculos, manarq.clientes, manarq.t);
			if(x==0) {
				break;
			}
		}
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
		}else {
			System.out.println("Não é solução :(");
		}
	}
	
	public static List<Veiculo> construtorAleatorio(List<Cliente> clientes, List<Veiculo> veiculos, int m, int n, int t, int opt) {
		Random rand = new Random();
		int randomNum, randomNum2;
		List<List<Integer>> maxLoud = new ArrayList<List<Integer>>();
		List<List<Integer>> maxDuration = new ArrayList<List<Integer>>();
		Set<Integer> c = new TreeSet<Integer>();
		for(int i=0;i<m*t;i++) {
			List<Integer> aux = new ArrayList<Integer>();
			List<Integer> aux2 = new ArrayList<Integer>();
			for(int j=0;j<t;j++) {
				aux.add(0);
				aux2.add(0);
			}
			maxLoud.add(aux);
			maxDuration.add(aux2);
		}
		while(c.size()!= n) {
			randomNum = rand.nextInt(n);
			if(c.contains(randomNum)) {
				randomNum = 1+ rand.nextInt(n-1);
			}else {
				c.add(clientes.get(randomNum).numeroCliente);
				if(clientes.get(randomNum).numberCombinations == 0) {
					continue;
				}
				randomNum2 = rand.nextInt(clientes.get(randomNum).numberCombinations);
				String combinacao = Integer.toBinaryString(clientes.get(randomNum).possibleVisitCombinations.get(randomNum2));
				if(combinacao.length() < t) {
					int x = combinacao.length();
					for(int j=0;j<t-x;j++) {
						combinacao = "0" + combinacao;
					}
				}
				for(int l=t-1; l>=0; l--) {
					if(combinacao.charAt(l)== '1') {
						//randomNum2 = rand.nextInt(m);
						randomNum2 = 0;
						while(true){
							if(opt == 1){
								int auxiliar = maxLoud.get(randomNum2).get(l) + clientes.get(randomNum).demand;
								int auxiliar2 = maxDuration.get(randomNum2).get(l) + clientes.get(randomNum).getServiceDuration();
								if((auxiliar <= veiculos.get(randomNum2).maxLoud.get(l) && auxiliar2 <= veiculos.get(randomNum2).maxDuration.get(l)) || (veiculos.get(randomNum2).maxLoud.get(l) == 0 && auxiliar2 <= veiculos.get(randomNum2).maxDuration.get(l))) {
									maxLoud.get(randomNum2).set(l, maxLoud.get(randomNum2).get(l) + clientes.get(randomNum).demand);
									maxDuration.get(randomNum2).set(l, maxDuration.get(randomNum2).get(l) + clientes.get(randomNum).serviceDuration);
									veiculos.get(randomNum2).rotaDias.get(l).add(clientes.get(randomNum).numeroCliente);
									break;
								}else {
									//randomNum2 = rand.nextInt(m);
									randomNum2++;

									if(randomNum2 >= m)
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
					}
				}
			}
		}
		return veiculos;
	}
	
	public static int checarSolucao(List<Veiculo> veiculos, List<Cliente> clientes, int t) {
		for(int i=0;i<clientes.size();i++) {
			String combinacao = Integer.toBinaryString(clientes.get(i).getFrequencyOfVisit());
			int frequencia = 0;
			if(combinacao.length() < t) {
				int x = combinacao.length();
				for(int j=0;j<t-x;j++) {
					combinacao = "0" + combinacao;
				}
				for(int l=t-1;l>=0;l--) {
					for(int j=0;j<veiculos.size();j++) {
						if(veiculos.get(j).rotaDias.get(l).contains(clientes.get(i).numeroCliente)){
							frequencia++;
						}
					}
				}
			}
			if(frequencia != clientes.get(i).frequencyOfVisit) {
				return -1;
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
	public void gerarMatriz(List<Cliente> clientes){
		for(int k = 0; k<clientes.size(); k++) {
			for(int j = 0; j <clientes.size(); j++) {
				matriz.get(k).get(j).add(distance(cliente.get(k), cliente.get(j)));
			}
		}
		
	}
}



//To do:
/*Implementar as verificações, olhar pq n ta criando 2 dias para cada veiculo
ConstrutivoAleatorio(clientes)
1:Inicializa a soluçãos com todas as rotas dos veículos vazias.
2:for(todo cliente i em Uc)do
3:	Selecione uma agenda de visita r aleatória pertencente a Ri
4:	for(todo dia l em L)do
5:		if(cliente i precisa ser visitado no dia l na agenda r)then
6:			Selecione um veículo aleatório v pertencente ao conjunto de veículos de s
7:			Insira i na rota de v do dia l na última posição.
8:		end if
9:	end for
10:end for
11:Retorne s;
*/
