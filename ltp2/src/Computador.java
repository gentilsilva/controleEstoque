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
	double vlrTotalVendido;
	int qtdTotalVendida;


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
			System.out.println("Dados gravados com sucesso !");
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
				if(!consistirMarca(marcaComputador)) System.out.println("Marca invalida! Opcoes disponiveis: [Dell, Lenovo, HP, Positivo, Asus, Apple, IBM]");
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

					if (marcaComputador.substring(0, 2).equalsIgnoreCase(marca.substring(0, 2))
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
			
				do {
					System.out.print("Digite o modelo do computador..................: ");
					modelo = Main.leia.nextLine();
					if(modelo.equals("")) {
						System.out.println("Digitacao obrigatoria para modelo de computador.");
					}
				} while(modelo.equals(""));
				
				do {
					System.out.print("Digite o processador do computador.............: ");
					processador = Main.leia.nextLine();
					if(!consistirProcessador(processador)) {
						System.out.println("Processador invalido! Opcoes disponiveis: Intel Core i3, Intel Core i5, Intel Core i7, Intel Core i9, AMD Ryzen, AMD Athlon");
					}
				} while(!consistirProcessador(processador));


				do {
					System.out.print("Digite a quantidade de memoria do computador...: ");
					quantMemoria = Main.leia.nextInt();
					if(quantMemoria < 1 || quantMemoria > 16) {
						System.out.println("Quantidade de memoria do computador deve ser entre 1 e 16 GB");
					}
				} while (quantMemoria < 1 || quantMemoria > 16);

				do {
					System.out.print("Digite o tamanho da tela.......................: ");
					tamanhoTela = Main.leia.nextInt();
					if(!consitirTamanhoTela(tamanhoTela)) {
						System.out.println("Tamanho invalido. Opcoes disponiveis: 10, 12, 15, 20, 25, 28");
					}
				} while (!consitirTamanhoTela(tamanhoTela));
					
				do {
					System.out.print("Digite a quantidade em estoque.................: ");
					quantEstoque = Main.leia.nextInt();
					if(quantEstoque < 0) {
						System.out.println("Quantidade em estoque deve ser maior ou igual a zero!");
					}
				} while (quantEstoque < 0);
					
				do {
					System.out.print("Digite o valor do computador...................: ");
					preco = Main.leia.nextFloat();
					if(preco < 1000 || preco > 20000) {
						System.out.println("O valor do computador deve ser entre 1000 e 20000 reais");
					}
				} while (preco < 1000 || preco > 20000);

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
				System.out.print("Digite o codigo do Computador que deseja alterar( FIM para encerrar ): ");
				codigoComputador = Main.leia.nextLine();
				if (codigoComputador.equalsIgnoreCase("FIM")) {
					break;
				}

				posicaoRegistro = localizarComputador(codigoComputador);
				if (posicaoRegistro == -1) {
					System.out.println("C√≥digo de computador n√£o cadastrado no arquivo, digite outro valor\n");
				}
			}while (posicaoRegistro == -1);

			if (codigoComputador.equalsIgnoreCase("FIM")) {
				break;
			}

			ativo = 'S';
			do {
				System.out.println("[ 1 ] Modelo do computador ......: " + modelo);
				System.out.println("[ 2 ] Processador................: " + processador);
				System.out.println("[ 3 ] Quantidade de memoria......: " + quantMemoria);
				System.out.println("[ 4 ] Tamanho da tela............: " + tamanhoTela);
				System.out.println("[ 5 ] Quantidade em estoque......: " + quantEstoque);
				System.out.println("[ 6 ] Preco do computador........: " + preco);

				do{
					System.out.println("Digite o numero do campo que deseja alterar (0 para finalizar as alteracoes): ");
					opcao = Main.leia.nextByte();
				}while (opcao < 0 || opcao > 6);
				
			switch (opcao) {
			case 1:
				do {
					Main.leia.nextLine();
					System.out.print("Digite o NOVO MODELO do computador.................: ");
					modelo = Main.leia.nextLine();
					if(modelo.equals("")) {
						System.out.println("Digite um valor N„o Nulo!");
					}
				} while(modelo.equals(""));
				break;
			case 2:
				Main.leia.nextLine();
				do {
					System.out.print  ("Digite o NOVO PROCESSADOR do computador............: ");
					processador = Main.leia.nextLine();
					if(!consistirProcessador(processador)) {
						System.out.println("Processador invalido! Opcoes disponiveis: Intel Core i3, Intel Core i5, Intel Core i7, Intel Core i9, AMD Ryzen, AMD Athlon");
						break;
					}
				} while(!consistirProcessador(processador));
				
				break;
			case 3:
				do {
					System.out.print  ("Digite a NOVA quantidade de memoria do Computador..: ");
					quantMemoria = Main.leia.nextInt();
					if(quantMemoria < 1 || quantMemoria > 16) {
						System.out.println("Quantidade de memoria entre 1 e 16 GB");
					}
				} while(quantMemoria < 1 || quantMemoria > 16);
				
				break;
			case 4:
				Main.leia.nextLine();
				do {
					System.out.print  ("Digite o NOVO tamanho de tela......................: ");
					tamanhoTela = Main.leia.nextInt();
					if(!consitirTamanhoTela(tamanhoTela)) {
						System.out.println("Tamanho invalido. opcoes disponiveis: 10, 12, 15, 20, 25, 28");
					}
				} while(!consitirTamanhoTela(tamanhoTela));
				
				break;
			case 5: 
				do {
					System.out.print  ("Digite A nova Quantidade em Estoque do Computador..: ");
					quantEstoque= Main.leia.nextInt();
					if(quantEstoque < 0) {
						System.out.println("Digite uma quantidade maior que 0!");
					}
				} while(quantEstoque < 0);
				break;
			case 6:
				do {
					System.out.print  ("Digite O novo preÁo do Computador..: ");
					preco= Main.leia.nextInt();
					if(preco < 1000 || preco > 20000) {
						System.out.println("Digite uma quantidade maior que 0!");
					}
				} while(preco < 1000 || preco > 20000);
			}
				quantVendida = 0;
				dtUltimaVenda = "";
				System.out.println();
			}while (opcao != 0);  		

			do {
				System.out.print("\nConfirma a alteracao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S' || confirmacao == 's') {
					desativarComputador(posicaoRegistro);
					gravarComputador();
				}
			}while (confirmacao != 'S' && confirmacao != 's' && confirmacao != 'N');

		}while ( ! codComp.equalsIgnoreCase("FIM"));
	}


	//************************  EXCLUSAO  *****************************
	public void excluir() {
		String codigoComputador;
		char confirmacao;
		long posicaoRegistro = 0;

		do {
			do {
				Main.leia.nextLine();
				System.out.println(" ***************  EXCLUSAO DE COMPUTADOR  ***************** ");
				System.out.print("Digite o codigo do Computador que deseja excluir ( FIM para encerrar ): ");
				codigoComputador = Main.leia.nextLine();
				if (codigoComputador.equalsIgnoreCase("FIM")) {
					break;
				}

				posicaoRegistro = localizarComputador(codigoComputador);
				if (posicaoRegistro == -1 ) {
					System.out.println("Codigo de computador nao cadastrado no arquivo, digite outro valor\n");
				}
			}while (posicaoRegistro == -1);

			if (codigoComputador.equalsIgnoreCase("FIM")) {
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
				if (confirmacao == 'S' || confirmacao == 's') {
					desativarComputador(posicaoRegistro);
				}
			}while (confirmacao != 'S' && confirmacao != 's' && confirmacao != 'N');

		}while ( ! codComp.equalsIgnoreCase("FIM"));
	}
	
	//************************  REGISTRAR VENDAS  *********************
	
	public void registrarVenda() {
		//nao estava parando na parte do codigo, tive que adicionar esse nextLine() para corrigir esse bug
		Main.leia.nextLine();

		String codigoComputador;
		long posicaoRegistro = 0;
		String dataVenda = "";
		int qtdVendida;
		char confirmacao;
		
		do {
			System.out.print("Digite o codigo do Computador( FIM para encerrar): ");
			codigoComputador = Main.leia.nextLine();
			if (codigoComputador.equalsIgnoreCase("FIM")) {
				break;
			}
			posicaoRegistro = localizarComputador(codigoComputador);
			
			if (posicaoRegistro == -1) {
				System.out.println("Codigo do computador nao cadastrado no arquivo, digite Um Valor valido! \n");
			}
		} while(posicaoRegistro == -1);

		System.out.println("Marca do Computador........: " + marca);
		System.out.println("Modelo do computador ......: " + modelo);
		System.out.println("Processador................: " + processador);
		System.out.println("Quantidade de memoria......: " + quantMemoria);
		System.out.println("Tamanho da tela............: " + tamanhoTela);
		System.out.println("Quantidade em estoque......: " + quantEstoque);
		System.out.println("Preco do computador........: " + preco);
		System.out.println();
		
		
		do{
			System.out.print("Qual a quantidade vendida? ");
			qtdVendida = Main.leia.nextInt();
			if(qtdVendida > quantEstoque) {
				System.out.println("Quantidade vendida deve ser menor ou igual a quantidade em estoque");
			}
		}while(qtdVendida > quantEstoque);
		
		Main.leia.nextLine();
		
		System.out.print("Qual a data da Venda? ");
		dataVenda = Main.leia.nextLine();
		
		do {
			System.out.print("\nConfirma a venda realizada? (S/N) ? ");
			confirmacao = Main.leia.next().charAt(0);
			if (confirmacao == 'S' || confirmacao == 's') {
				quantVendida += qtdVendida;
				quantEstoque -= qtdVendida;
				dtUltimaVenda = dataVenda;
				desativarComputador(posicaoRegistro);
				gravarComputador();
			}
		}while (confirmacao != 'S' && confirmacao != 's' && confirmacao != 'N');
	}
	
	
	
	//************************  CONSULTA  *****************************
	public void consultar() 	{
		RandomAccessFile arqComp;
		byte opcao;
		String codigoComputador;
		long posicaoRegistro;

		do {
			vlrTotalVendido = 0;
			qtdTotalVendida = 0;
			do {
				System.out.println(" ***************  CONSULTA DE COMPUTADORES  ***************** ");
				System.out.println(" [1] LISTAR TODOS OS COMPUTADORES ");
				System.out.println(" [2] LISTAR COMPUTADOR POR CODIGO");
				System.out.println(" [3] LISTAR TODOS OS COMPUTADORES VENDIDOS");
				System.out.println(" [4] LISTAR COMPUTADORES POR MES E ANO DA ULTIMA VENDA");
				System.out.println(" [5] LISTAR NOTEBOOKS POR FAIXA DE PRE«O INFORMANDO MINIMO E MAXIMO");
				System.out.println(" [0] SAIR");
				System.out.print("\nDigite a opcao desejada: ");
				opcao = Main.leia.nextByte();
				if (opcao < 0 || opcao > 5) {
					System.out.println("opcao Invalida, digite novamente.\n");
				}
			}while (opcao < 0 || opcao > 5);

			switch (opcao) {
				case 0:
					System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
					break;
				case 1:  // consulta todos os computadores
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
								somaTotalVendidoEValor();
							}
						}
						//   arqComp.close();
					} catch (EOFException e) {
						imprimirTotalVendidoEValor();
						System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
						Main.leia.nextLine();
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa sera finalizado");
						System.exit(0);
					}
					break;
				case 2:  // lista um computador pelo codComp
					Main.leia.nextLine();  // limpa buffer de memoria
					System.out.print("Digite o codigo do Computador: ");
					codigoComputador = Main.leia.nextLine();

					posicaoRegistro = localizarComputador(codigoComputador);
					if (posicaoRegistro == -1 || ativo != 'S') {
						System.out.println("codigo nao cadastrado no arquivo \n");
					} else {
						somaTotalVendidoEValor();
						imprimirCabecalho();
						imprimirComputador();
						imprimirTotalVendidoEValor();
						System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					}
					break;
				case 3: // lista computadores j· vendidos
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
								somaTotalVendidoEValor();
							}
						}
					} catch (EOFException e) {
						imprimirTotalVendidoEValor();
						System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa sera finalizado");
						System.exit(0);
					}
					break;
				case 4:
					Main.leia.nextLine();
					System.out.print("Digite o mes e o ano desejado (MM/yyyy): ");
					String dataVenda = Main.leia.nextLine();
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
							if ( !dtUltimaVenda.equals("")) {
								if(dataVenda.equals(dtUltimaVenda.substring(3)) && ativo == 'S') {
									imprimirComputador();
									somaTotalVendidoEValor();
								}
							}
						}
					} catch (EOFException e) {
						imprimirTotalVendidoEValor();
						System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
						Main.leia.nextLine();
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa sera finalizado");
						System.exit(0);
					}
					break;
			}
		} while ( opcao != 0 );
	}

	public void imprimirCabecalho () {
		System.out.println("---MARCA---  ---CODIGO---  --MODELO--  -PROCESSADOR-  -QTD MEMORIA- -TAMANHO TELA-  --QTD ESTOQUE-- --PRECO-- -QTD VENDIDA- -ULTIMA VENDA-");
	}

	public void imprimirComputador () {
		System.out.println(	formatarString(marca, 14 ) + "  " +
				formatarString(codComp , 11) + "  " + 
				formatarString(modelo , 10) + "  " + 
				formatarString(processador ,16) + "  " +
				formatarString( String.valueOf(quantMemoria) , 13 ) + "  " +
				formatarString( String.valueOf(tamanhoTela) , 14 ) + "  " +
				formatarString( String.valueOf(quantEstoque) , 10 ) + "  " +
				formatarString( String.valueOf(preco) , 13 ) + "  " +
				formatarString( String.valueOf(quantVendida) , 7 ) + "  " +
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

	public void somaTotalVendidoEValor() {
		vlrTotalVendido += (quantVendida * preco);
		qtdTotalVendida += quantVendida;
	}

	public void imprimirTotalVendidoEValor() {
		System.out.println(
			formatarString("TOTAIS", 100) + " " +
			formatarString(String.valueOf(vlrTotalVendido), 15) + " " +
			formatarString(String.valueOf(qtdTotalVendida), 13)
		);
	}
}
