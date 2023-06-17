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
			}while (confirmacao != 'S' && confirmacao != 's' && confirmacao != 'N' && confirmacao != 'n');

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
					System.out.println("Codigo de computador nao cadastrado no arquivo, digite outro valor\n");
				}
			}while (posicaoRegistro == -1);

			if (codigoComputador.equalsIgnoreCase("FIM")) {
				break;
			}

			ativo = 'S';
			do {
				System.out.println("[ 1 ] Modelo do computador.......: " + modelo);
				System.out.println("[ - ] Codigo do computador.......:" + codComp);
				System.out.println("[ 2 ] Processador................: " + processador);
				System.out.println("[ 3 ] Quantidade de memoria......: " + quantMemoria);
				System.out.println("[ 4 ] Tamanho da tela............: " + tamanhoTela);
				System.out.println("[ 5 ] Quantidade em estoque......: " + quantEstoque);
				System.out.println("[ 6 ] Preco do computador........: " + preco);
				System.out.println("[ - ] Quantidade vendida.........: " + quantVendida);
				System.out.println("[ - ] Data ultima venda..........: " + dtUltimaVenda);
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
						System.out.println("Digitacao obrigatoria para modelo de computador.");
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
					System.out.print  ("Digite a NOVA Quantidade em Estoque do Computador..: ");
					quantEstoque= Main.leia.nextInt();
					if(quantEstoque < 0) {
						System.out.println("Quantidade em estoque deve ser maior ou igual a zero!");
					}
				} while(quantEstoque < 0);
				break;
			case 6:
				do {
					System.out.print  ("Digite O NOVO preço do Computador..: ");
					preco= Main.leia.nextInt();
					if(preco < 1000 || preco > 20000) {
						System.out.println("O valor do computador deve ser entre 1000 e 20000 reais!");
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
			}while (confirmacao != 'S' && confirmacao != 's' && confirmacao != 'N' && confirmacao != 'n');

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
			}while (confirmacao != 'S' && confirmacao != 's' && confirmacao != 'N' && confirmacao != 'n');

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
				System.out.println("Codigo do computador nao cadastrado no arquivo, digite um codigo valido! \n");
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
			if(qtdVendida > quantEstoque || qtdVendida <= 0) {
				System.out.println("Quantidade vendida deve ser menor ou igual a quantidade em estoque e maior que zero");
			}
		}while(qtdVendida > quantEstoque || qtdVendida <= 0);
		
		Main.leia.nextLine();

		do {
			System.out.print("Qual a data da Venda? ");
			dataVenda = Main.leia.nextLine();
		} while(!consistirData(dataVenda));
		

		
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
		}while (confirmacao != 'S' && confirmacao != 's' && confirmacao != 'N' && confirmacao != 'n');
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
				System.out.println(" [5] LISTAR COMPUTADORES POR FAIXA DE PREÇO");
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
				case 1:  // Consultar todos os computadores
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
								qtdTotalVendida += quantVendida;
								vlrTotalVendido += calcularValorTotal(quantVendida, preco);
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
					Main.leia.nextLine();
					break;
				case 2:  // Consultar computador por código de computador
					Main.leia.nextLine();  // limpa buffer de memoria
					System.out.print("Digite o codigo do Computador: ");
					codigoComputador = Main.leia.nextLine();

					posicaoRegistro = localizarComputador(codigoComputador);
					if (posicaoRegistro == -1 || ativo != 'S') {
						System.out.println("Codigo nao cadastrado no arquivo \n");
					} else {
						imprimirCabecalho();
						imprimirComputador();
						imprimirTotalVendidoEValor();
						System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					}
					Main.leia.nextLine();
					break;
				case 3: // Consultar todos os computadores que tiveram vendas
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
								qtdTotalVendida += quantVendida;
								vlrTotalVendido += calcularValorTotal(quantVendida, preco);
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
					Main.leia.nextLine();
					break;
				case 4:		// Consultar computadores por data de venda
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
							if(!dtUltimaVenda.equals("") && dataVenda.equals(dtUltimaVenda.substring(3)) && ativo == 'S') {
								imprimirComputador();
								qtdTotalVendida += quantVendida;
								vlrTotalVendido += calcularValorTotal(quantVendida, preco);
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
					Main.leia.nextLine();
					break;
				case 5:
					System.out.print("Digite o valor minimo: ");
					float valorMinimo = Float.parseFloat(Main.leia.nextLine());
					System.out.print("Digite o valor maximo: ");
					float valorMaximo = Float.parseFloat(Main.leia.nextLine());
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
							if(preco >= valorMinimo && preco <= valorMaximo && ativo == 'S') {
								imprimirComputador();
								qtdTotalVendida += quantVendida;
								vlrTotalVendido += calcularValorTotal(quantVendida, preco);
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
					Main.leia.nextLine();
					break;
			}
		} while ( opcao != 0 );
	}

	public void imprimirCabecalho () {
		System.out.println("---MARCA---  ---CODIGO---  --MODELO--  -PROCESSADOR-  -QTD MEMORIA- -TAMANHO TELA-  --QTD ESTOQUE-- --PRECO-- -QTD VENDIDA- -ULTIMA VENDA- -VLR TOTAL-");
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
				formatarString(dtUltimaVenda, 13) + " " +
				formatarString( String.valueOf(calcularValorTotal(quantVendida, preco)), 5));
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

	public void imprimirTotalVendidoEValor() {
		System.out.println();
		System.out.println(
			formatarString("TOTAIS", 116) + " " +
			formatarString(String.valueOf(qtdTotalVendida), 22) + " " +
			formatarString(String.valueOf(vlrTotalVendido), 13)
		);
	}
	
	public float calcularValorTotal(int qtdVendida, float valor) {
		return qtdVendida * valor;
	}

	public boolean consistirData(String data) {
		int dia;
		int mes;
		int ano;

		if(data.length() != 10) {
			System.out.println("Data invalida, deve possuir 10 caracteres. Ex: (DD/MM/AAAA).");
			return false;
		}

		if(data.charAt(2) != '/' || data.charAt(5) != '/') {
			System.out.println("Formato invalido. Data valida no formato (DD/MM/AAAA).");
			return false;
		}

		try {
			dia = Integer.parseInt(data.substring(0, 2));
			mes = Integer.parseInt(data.substring(3, 5));
			ano = Integer.parseInt(data.substring(6));
		} catch (NumberFormatException exception) {
			System.out.println("Data invalida, data, mes e ano devem ser numericos.");
			return false;
		}

		if(mes < 1 || mes > 12) {
			System.out.println("O mes deve estar entre 1 e 12");
			return false;
		}

		if(mes == 4 || mes == 6 || mes == 9 || mes == 11) {
			if(dia < 1 || dia > 30) {
				System.out.println("Os meses de (Abril, Junho, setembro e Novembro devem ter de 1 a 30 dias.");
				return false;
			}
		} else if(mes == 2) {
			if((ano % 4 == 0 && ano % 100 == 0 && ano % 400 == 0) || (ano % 4 == 0 && ano % 100 != 0)) {
				if(dia < 1 || dia > 29) {
					System.out.println("Em ano bissexto o mes de Fevereiro possui de 1 a 29 dias.");
					return false;
				}
			} else {
				if(dia < 1 || dia > 28) {
					System.out.println("Em ano nao bissexto o mes de Fevereiro possui de 1 a 28 dias.");
					return false;
				}
			}
		} else {
			if(dia < 1 || dia > 31) {
				System.out.println("Os meses de (Janeiro, Marco, Maio, Julho, Agosto, Outubro e Dezembro " +
						"devem ter de 1 a 31 dias.");
				return false;
			}
		}
		return true;
	}
}
