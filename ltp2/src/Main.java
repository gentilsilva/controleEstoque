import java.util.*;
public class Main {
	static Scanner leia = new Scanner(System.in);
	
	public static void main(String[] args) {	
		Computador computador = new Computador();
    	byte opcao = -1;
    	 
    	do {
			do {
    			System.out.println("\n ***************  CADASTRO E VENDA DE COMPUTADORES  ***************** ");
    			System.out.println(" [1] INCLUIR COMPUTADOR ");
    			System.out.println(" [2] ALTERAR COMPUTADOR ");
    			System.out.println(" [3] EXCLUIR COMPUTADOR ");
    			System.out.println(" [4] REGISTRAR VENDA ");
    			System.out.println(" [5] RELATORIOS");
    			System.out.println(" [0] SAIR");
    			System.out.print("\nDigite a opcao desejada: ");
    			opcao = leia.nextByte();
    			if (opcao < 0 || opcao > 5) {
    				System.out.println("opcao Invalida, digite novamente.\n");
    			}
    		}while (opcao < 0 || opcao > 5);
			
			switch (opcao) {
				case 0:
					System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
					break;
				case 1: 
					computador.incluir(); 
					break;
				case 2:
					computador.alterar();
					break;
				case 3: 
					computador.excluir();
					break;
				case 4: 
					computador.registrarVenda();
					break;
				case 5: 
					computador.consultar();
					break;
			}
    	} while ( opcao != 0 );
    	//leia.close();
	}

}
