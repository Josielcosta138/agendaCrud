package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//import com.mysql.cj.xdevapi.Result

public class DAO {
	// Modulo de conexão 

	// Parâmetros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "janice270390joel1993";

	// metodo de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/** CRUD CREAT **/
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome,fone,email) values (?,?,?)";
		try {
			// abrir conexão com Banco
			Connection con = conectar();
			// Preparar a query para execução no banco de Dados
			PreparedStatement pst = con.prepareStatement(create);
			// Substituir os parametros (?) pelo conteudo das variaveis
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// Executar a Query
			pst.executeUpdate();
			// Encerrar a conexão com Banco
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/** CRUD READ **/
	public ArrayList<JavaBeans> listarContatos() {
		// Criando um objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList();
		String read = "select * from contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read); // prepara a execução
			ResultSet rs = pst.executeQuery();
			// laço abaixo sera executado enquanto tiver contatos
			while (rs.next()) {
				// variaveis de apoio que recebem os dados do banco
				String idcon = rs.getString(1); // recebe o primeiro campo do banco de dados
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// populando o ArrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// testando conexao
	public void testeConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	/** CRUD UPDATE  **/
	//Selecionar o contato
	public void selecionarContado(JavaBeans contato) {
		String read2 = "select * from contatos  where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery(); 
			while (rs.next()) {
				// setando as váriaveis do JavaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// editar contato 
	public void alterarContato(JavaBeans contato) {
		String creat = "update contatos set nome=?,fone=?,email=? where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(creat);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			
			//Executa a Query e atualiza e salva o contato
			pst.executeUpdate();
			// Finaliza a Conexão
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	 /* CRUD DELETE */ 
	public void deletarContato(JavaBeans contato) {
		String delete = "delete from contatos where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			
			pst.executeUpdate();
			
			con.close();
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
