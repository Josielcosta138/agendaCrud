package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update" }) // Essa linha recebe as
																						// REQUISIÇÕES por /nome
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();

	}

	// Método principal do SERVELet
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) { // /main acessou a Agenda
			contatos(request, response);

		} else if (action.equals("/insert")) { // /insert Inseriu um novo Usuário
			novoContato(request, response);

		} else if (action.equals("/select")) { // /select Selecinou o usário pela lista da agenda do banco de dados
			listarContato(request, response);

		} else if (action.equals("/update")) { // /update atualizou um Usuário pelo Editar e encaminhou os dados do
												// formulario para camada Controler
			editarContato(request, response);
		} else {
			response.sendRedirect("index.html"); // index. Iniciou o agenda
		}
	}

	// listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// criando um objeto que ira receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);

		/*
		 * teste de recebimento da lista for (int i = 0; i < lista.size(); i++) {
		 * System.out.println(lista.get(i).getIdcon());
		 * System.out.println(lista.get(i).getNome());
		 * System.out.println(lista.get(i).getFone());
		 * System.out.println(lista.get(i).getEmail()); }
		 */
	}

	// Novo contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// setar as variaveis JavaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// invocar o método inserirContato passando o objeto Contato
		dao.inserirContato(contato);

		// redirecionar para o documento agenda.jsp
		response.sendRedirect("main");

		// teste de recebimento dos dados do formulário
		/**
		 * System.out.println(request.getParameter("nome")); //capturar os dados do
		 * formulario System.out.println(request.getParameter("fone"));
		 * System.out.println(request.getParameter("email"));
		 **/
	}

	// editar contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recebe o id do contato que será editado
		String idcon = request.getParameter("idcon");
		// System.out.println(idcon); teste

		// Setar a váriavel JavaBeans
		contato.setIdcon(idcon);
		// Executar o método selecionarContato (DAO)
		dao.selecionarContado(contato);
		// Setar os atributos do formulário com o conteudi JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// Encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
		// teste de recebimento
		/*
		 * System.out.println(contato.getIdcon());
		 * System.out.println(contato.getNome()); System.out.println(contato.getFone());
		 * System.out.println(contato.getEmail());
		 */
	}

	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// setar as variaveis JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		//executar método alterarContato
		dao.alterarContato(contato);
		
		// redirecionar para o documento agenda.jsp
				response.sendRedirect("main");


	}

}
