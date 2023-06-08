import java.io.*;

public class Computador {

	char 	ativo;
	String 	marca;
	String 	codComp;
	String 	modelo;
	String 	processador;
	int 	quantMemoria;
	int 	tamanhoTela;
	int 	quantEstoque;
	float 	preco;
	int 	quantVendida;
	String 	dtUltimaVenda;

	static String[] marcas =  {"Dell", "Lenovo", "HP", "Positivo", "Asus", "Apple", "IBM"};
	static String[] processadores = {"Intel Core i3", "Intel Core i5", "Intel Core i7", "Intel Core i9", "AMD Ryzen", "AMD Athlon"};
	static int[] tamanhosTelas = {10, 12, 15, 20, 25, 28};
	

	public long localizarComputador(String codigoComputador) {
		// metodo para localizar um registro no arquivo em disco
		long posicaoCursorArquivo = 0;
		try { 
			RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");
			while (true) {
				posicaoCursorArquivo  = arqComp.getFilePointer();	// posicao do inicio do registro no arquivo
				ativo		 	= arqComp.readChar();
				marca   		= arqComp.readUTF();
				codComp   		= arqComp.readUTF();
				modelo      	= arqComp.readUTF();
				processador 	= arqComp.readUTF();
				quantMemoria 	= arqComp.readInt();
				tamanhoTela		= arqComp.readInt();
				quantEstoque	= arqComp.readInt();
				preco			= arqComp.readFloat();
				quantVendida	= arqComp.readInt();
				dtUltimaVenda	= arqComp.readUTF();

				if ( codigoComputador.equalsIgnoreCase(codComp) && ativo == 'S') {
					arqComp.close();
					return posicaoCursorArquivo;
				}
			}
		}catch (EOFException e) {
			return -1; // registro nao foi encontrado
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
			return -1;
		}
	}

	public void gravarComputador() {	
		// metodo para incluir um novo registro no final do arquivo em disco
		try {
			RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");	
			arqComp.seek(arqComp.length());  // posiciona o ponteiro no final do arquivo (EOF)
			arqComp.writeChar(ativo);
			arqComp.writeUTF(marca);
			arqComp.writeUTF(codComp);
			arqComp.writeUTF(modelo);
			arqComp.writeUTF(processador);
			arqComp.writeInt(quantMemoria);
			arqComp.writeInt(tamanhoTela);	
			arqComp.writeInt(quantEstoque);	
			arqComp.writeFloat(preco);	
			arqComp.writeInt(quantVendida);	
			arqComp.writeUTF(dtUltimaVenda);	
			arqComp.close();
			System.out.println("Dados gravados com sucesso !\n");
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	public void desativarComputador(long posicao)	{ 
		// metodo para alterar o valor do campo ATIVO para N, tornando assim o registro excluido
		try {
			RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");			
			arqComp.seek(posicao);
			arqComp.writeChar('N');   // desativar o registro antigo
			arqComp.close();
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	// ***********************   INCLUSAO   *****************************
	public void incluir() {
		String marcaComputador;
		char confirmacao;
		int maiorCodigoNumerico = 0;
		String codigo = "";

		do {
			Main.leia.nextLine();
			System.out.println("\n ***************  INCLUSAO DE COMPUTADORES  ***************** ");

			do {
				System.out.print("Digite a Marca do computador (FIM para encerrar): ");
				marcaComputador = Main.leia.nextLine();
				if(marcaComputador.equalsIgnoreCase("FIM")) break;
				if(!consistirMarca(marcaComputador)) System.out.println("Marca inválida! Opções disponíveis: [Dell, Lenovo, HP, Positivo, Asus, Apple, IBM]");
			} while(!consistirMarca(marcaComputador));
			if(marcaComputador.equalsIgnoreCase("FIM")) break;



			try {
				RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");
				while (true) {
					ativo = arqComp.readChar();
					marca = arqComp.readUTF();
					codComp = arqComp.readUTF();
					modelo = arqComp.readUTF();
					processador = arqComp.readUTF();
					quantMemoria = arqComp.readInt();
					tamanhoTela = arqComp.readInt();
					quantEstoque = arqComp.readInt();
					preco = arqComp.readFloat();
					quantVendida = arqComp.readInt();
					dtUltimaVenda = arqComp.readUTF();

					if (marcaComputador.substring(0, 2).equals(marca.substring(0, 2))
							&& Integer.parseInt(codComp.substring(2)) > maiorCodigoNumerico && ativo == 'S') {
						maiorCodigoNumerico = Integer.parseInt(codComp.substring(2));
					}
				}
			} catch (EOFException e) {
				String maiorCodigoString = String.valueOf(maiorCodigoNumerico + 1);
				while(maiorCodigoString.length() < 4) {
					maiorCodigoString = "0" + maiorCodigoString;
				}
				codigo = marcaComputador.substring(0, 2).toUpperCase() + maiorCodigoString;
				System.out.println("codigo gerado: " + codigo);
				maiorCodigoNumerico = 0;
			} catch (IOException e) {
				System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
				System.exit(0);
			}

			if (marcaComputador.equals("FIM")) {
				break;
			}

			ativo = 'S';
			marca = marcaComputador;
			codComp = codigo;
			System.out.print("Digite o modelo do computador..................: ");
			modelo = Main.leia.nextLine();

			do {
				System.out.print("Digite o processador do computador.............: ");
				processador = Main.leia.nextLine();
				if(!consistirProcessador(processador)) {
					System.out.println("Processador inválido! Opções disponíveis: Intel Core i3, Intel Core i5, Intel Core i7, Intel Core i9, AMD Ryzen, AMD Athlon");
				}
			} while(!consistirProcessador(processador));

			System.out.print("Digite a quantidade de memória do computador...: ");
			quantMemoria = Main.leia.nextInt();

			do {
				System.out.print("Digite o tamanho da tela.......................: ");
				tamanhoTela = Main.leia.nextInt();
				if(!consitirTamanhoTela(tamanhoTela)) System.out.println("Tamanho invalido. Opções disponíveis: 10, 12, 15, 20, 25, 28");
			} while(!consitirTamanhoTela(tamanhoTela));

			System.out.print("Digite a quantidade em estoque.................: ");
			quantEstoque = Main.leia.nextInt();
			System.out.print("Digite o valor do computador...................: ");
			preco = Main.leia.nextFloat();
			quantVendida = 0;
			dtUltimaVenda = "";

			do {
				System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S' || confirmacao == 's') {
					gravarComputador();
				}
			}while (confirmacao != 'S' && confirmacao != 's' && confirmacao != 'N');

		}while ( ! codComp.equals("FIM"));	    
	}


	//************************  ALTERACAO  *****************************
	public void alterar() {
		String codigoComputador;
		char confirmacao;
		long posicaoRegistro = 0;
		byte opcao;

		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  ALTERACAO DE COMPUTADORES  ***************** ");
				System.out.print("Digite o código do Computador que deseja alterar( FIM para encerrar ): ");
				codigoComputador = Main.leia.nextLine();
				if (codigoComputador.equalsIgnoreCase("FIM")) {
					break;
				}

				posicaoRegistro = localizarComputador(codigoComputador);
				if (posicaoRegistro == -1) {
					System.out.println("Matricula nao cadastrada no arquivo, digite outro valor\n");
				}
			}while (posicaoRegistro == -1);

			if (codigoComputador.equals("FIM")) {
				break;
			}

			ativo = 'S';

			do {
				System.out.println("[ 1 ] Marca do Computador........: " + marca);
				System.out.println("[ 2 ] Modelo do computador ......: " + modelo);
				System.out.println("[ 3 ] Processador................: " + processador);
				System.out.println("[ 4 ] Quantidade de memoria......: " + quantMemoria);
				System.out.println("[ 5 ] Tamanho da tela............: " + tamanhoTela);
				System.out.println("[ 5 ] Quantidade em estoque......: " + quantEstoque);
				System.out.println("[ 6 ] Preco do computador........: " + preco);

				do{
					System.out.println("Digite o numero do campo que deseja alterar (0 para finalizar as alteracoes): ");
					opcao = Main.leia.nextByte();
				}while (opcao < 0 || opcao > 4);

				switch (opcao) {
				case 1:
					Main.leia.nextLine();
					do {
						System.out.print("Digite a NOVA MARCA do Computador..................: ");
						marca = Main.leia.nextLine();
						if(!consistirMarca(marca)) System.out.println("Marca inválida! Opções disponíveis: [Dell, Lenovo, HP, Positivo, Asus, Apple, IBM]");
					} while(!consistirMarca(marca));
					break;
				case 2: 
					Main.leia.nextLine();
					System.out.print("Digite o NOVO MODELO do computador.................: ");
					modelo = Main.leia.nextLine();
					break;
				case 3:
					do {
						System.out.print  ("Digite o NOVO PROCESSADOR do computador............: ");
						processador = Main.leia.nextLine();
						if(!consistirProcessador(processador)) {
							System.out.println("Processador inválido! Opções disponíveis: Intel Core i3, Intel Core i5, Intel Core i7, Intel Core i9, AMD Ryzen, AMD Athlon");
						}
					} while(!consistirProcessador(processador));
					break;
				case 4: 
					System.out.print  ("Digite a NOVA quantidade de memoria do Computador..: ");
					quantMemoria = Main.leia.nextInt();
					break;
				case 5:
					do {
						System.out.print  ("Digite o NOVO tamanho de tela......................: ");
						tamanhoTela = Main.leia.nextInt();
						if(!consitirTamanhoTela(tamanhoTela)) System.out.println("Tamanho invalido. Opções disponíveis: 10, 12, 15, 20, 25, 28");
					} while(!consitirTamanhoTela(tamanhoTela));
					break;
				case 6: 
					System.out.print  ("Digite o NOVO preco do Computador..................: ");
					preco = Main.leia.nextFloat();
					break;
				}
				quantVendida = 0;
				dtUltimaVenda = "";
				System.out.println();
			}while (opcao != 0);  		

			do {
				System.out.print("\nConfirma a alteracao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarComputador(posicaoRegistro);
					gravarComputador();
					System.out.println("Dados gravados com sucesso !\n");
				}
			}while (confirmacao != 'S' && confirmacao != 'N');

		}while ( ! codComp.equals("FIM"));
	}


	//************************  EXCLUSAO  *****************************
	public void excluir() {
		String codigoComputador;
		char confirmacao;
		long posicaoRegistro = 0;

		do {
			do {
				Main.leia.nextLine();
				System.out.println(" ***************  EXCLUSAO DE COMPUTADORES  ***************** ");
				System.out.print("Digite a Matricula do Computador que deseja excluir ( FIM para encerrar ): ");
				codigoComputador = Main.leia.nextLine();
				if (codigoComputador.equals("FIM")) {
					break;
				}

				posicaoRegistro = localizarComputador(codigoComputador);
				if (posicaoRegistro >= 0) {
					System.out.println("Matricula nao cadastrada no arquivo, digite outro valor\n");
				}
			}while (posicaoRegistro >= 0);

			if (codigoComputador.equals("FIM")) {
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;
			}

			System.out.println("Marca do Computador........: " + marca);
			System.out.println("Modelo do computador ......: " + modelo);
			System.out.println("Processador................: " + processador);
			System.out.println("Quantidade de memoria......: " + quantMemoria);
			System.out.println("Tamanho da tela............: " + tamanhoTela);
			System.out.println("Quantidade em estoque......: " + quantEstoque);
			System.out.println("Preco do computador........: " + preco);
			System.out.println();

			do {
				System.out.print("\nConfirma a exclusao deste PC? (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarComputador(posicaoRegistro);
				}
			}while (confirmacao != 'S' && confirmacao != 'N');

		}while ( ! codComp.equals("FIM"));
	}
	
	//************************  REGISTRAR VENDAS  *********************
	
	public void registrarVenda() {
		//Não estava parando na parte da matricula, tive que adicionar esse nextLine() para corrigir esse bug
		Main.leia.nextLine();
		
		String codigo;
		long posicao = 0;
		String dataVenda = "---";
		int vendido;
		
		
		do {
			System.out.print("Digite a Matricula do Computador( FIM para encerrar): ");
			codigo = Main.leia.nextLine();
			if (codigo.equals("FIM")) {
				break;
			}
			posicao = localizarComputador(codigo);
			
			if (posicao == -1) {
				System.out.println("Matricula Não Cadastrada, digite Um Valor válido! \n");
			}
		} while(posicao == -1);
		
		System.out.print("Qual a quantidade vendida? ");
		vendido = Main.leia.nextInt();
		
		Main.leia.nextLine();
		
		System.out.print("Qual a data da Venda? ");
		dataVenda = Main.leia.nextLine();
		
		desativarComputador(posicao);
		
		//ainda precisamos validar essas quantidades e datas
		quantVendida += vendido;
		quantEstoque -= quantVendida;
		dtUltimaVenda = (String) dataVenda;
		
		gravarComputador();
		
		if(quantEstoque <=0) {
			desativarComputador(localizarComputador(codigo));
		}
	}

	
	//************************  CONSULTA  *****************************
	public void consultar() 	{
		RandomAccessFile arqComp;
		byte opcao;
		String codigoComputador;
		long posicaoRegistro;

		do {
			do {
				System.out.println(" ***************  CONSULTA DE COMPUTADORES  ***************** ");
				System.out.println(" [1] CONSULTAR APENAS 1 COMPUTADOR ");
				System.out.println(" [2] LISTA DE TODOS OS COMPUTADORES ");
				System.out.println(" [3] LISTA DE TODOS OS COMPUTADORES VENDIDOS");
				System.out.println(" [0] SAIR");
				System.out.print("\nDigite a opcao desejada: ");
				opcao = Main.leia.nextByte();
				if (opcao < 0 || opcao > 2) {
					System.out.println("opcao Invalida, digite novamente.\n");
				}
			}while (opcao < 0 || opcao > 2);

			switch (opcao) {
			case 0:
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;

			case 1:  // consulta de uma unica matricula
				Main.leia.nextLine();  // limpa buffer de memoria
				System.out.print("Digite a Matricula do Computador: ");
				codigoComputador = Main.leia.nextLine();

				posicaoRegistro = localizarComputador(codigoComputador);
				if (posicaoRegistro == -1) {
					System.out.println("Matricula nao cadastrada no arquivo \n");
				} else {
					imprimirCabecalho();
					imprimirComputador();
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
				}

				break;

			case 2:  // imprime todos os alunos
				try { 
					arqComp = new RandomAccessFile("COMP.DAT" , "rw");
					imprimirCabecalho();
					while (true) {
						ativo		 	= arqComp.readChar();
						marca   		= arqComp.readUTF();
						codComp   		= arqComp.readUTF();
						modelo      	= arqComp.readUTF();
						processador 	= arqComp.readUTF();
						quantMemoria 	= arqComp.readInt();
						tamanhoTela		= arqComp.readInt();
						quantEstoque	= arqComp.readInt();
						preco			= arqComp.readFloat();
						quantVendida	= arqComp.readInt();
						dtUltimaVenda	= arqComp.readUTF();
						if ( ativo == 'S') {
							imprimirComputador();
						}
					}
					//    arqAluno.close();
				} catch (EOFException e) {
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codigoComputador = Main.leia.nextLine();
				} catch (IOException e) { 
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
				break;

			case 3:  
				// imprime alunos do sexo desejado
//				do {
//					System.out.print("Digite o Sexo desejado (M/F): ");
//					sexoAux = Main.leia.next().charAt(0);
//					if (sexoAux != 'F' && sexoAux != 'M') {
//						System.out.println("Sexo Invalido, digite M ou F");
//					}
//				}while (sexoAux != 'F' && sexoAux != 'M');

				try { 
					arqComp = new RandomAccessFile("COMP.DAT", "rw");
					imprimirCabecalho();
					while (true) {
						ativo		 	= arqComp.readChar();
						marca   		= arqComp.readUTF();
						codComp   		= arqComp.readUTF();
						modelo      	= arqComp.readUTF();
						processador 	= arqComp.readUTF();
						quantMemoria 	= arqComp.readInt();
						tamanhoTela		= arqComp.readInt();
						quantEstoque	= arqComp.readInt();
						preco			= arqComp.readFloat();
						quantVendida	= arqComp.readInt();
						dtUltimaVenda	= arqComp.readUTF();
						if ( quantVendida > 0 && ativo == 'S') {
							imprimirComputador();
						}
					}
				} catch (EOFException e) {
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codigoComputador = Main.leia.nextLine();
				} catch (IOException e) { 
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}

			}	

		} while ( opcao != 0 );
	}

	public void imprimirCabecalho () {
		System.out.println("---MARCA---  ---CODIGO---  --MODELO--  -PROCESSADOR-  -QTD MEMORIA- -TAMANHO TELA-  --QTD ESTOQUE-- --PRECO-- -QTD VENDIDA- -ULTIMA VENDA-");
	}

	public void imprimirComputador () {
		System.out.println(	formatarString(marca, 14 ) + "  " +
				formatarString(codComp , 11) + "  " + 
				formatarString(modelo , 12) + "  " + 
				formatarString(processador , 8) + "  " +
				formatarString( String.valueOf(quantMemoria) , 13 ) + "  " +
				formatarString( String.valueOf(tamanhoTela) , 13 ) + "  " +
				formatarString( String.valueOf(quantEstoque) , 13 ) + "  " +
				formatarString( String.valueOf(preco) , 13 ) + "  " +
				formatarString( String.valueOf(quantVendida) , 13 ) + "  " +
				formatarString(dtUltimaVenda, 13 )   ); 
	}

	public static String formatarString (String texto, int tamanho) {	
		// retorna uma string com o numero de caracteres passado como parametro em TAMANHO
		if (texto.length() > tamanho) {
			texto = texto.substring(0,tamanho);
		}else{
			while (texto.length() < tamanho) {
				texto = texto + " ";
			}
		}
		return texto;
	}

	public static boolean consistirMarca(String marcaDigitada) {
		for(int i = 0; i < marcas.length; i++) {
			if(marcaDigitada.equalsIgnoreCase(marcas[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean consistirProcessador(String processadorDigitado) {
		for(int i = 0; i < processadores.length; i++) {
			if(processadorDigitado.equalsIgnoreCase(processadores[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean consitirTamanhoTela(int tamanhoTelaDigitado) {
		for(int i = 0; i < tamanhosTelas.length; i++) {
			if(tamanhoTelaDigitado == tamanhosTelas[i]) {
				return true;
			}
		}
		return false;
	}
}
