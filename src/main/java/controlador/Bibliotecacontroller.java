package controlador;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Libro;
import modelo.LibroDAO;

@WebServlet(urlPatterns= {"","/insertar"})
public class Bibliotecacontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Bibliotecacontroller() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//System.out.println("Servlet BibliotecaController");
		if(request.getServletPath().equals("")) {
			try {
				LibroDAO libroDAO = new LibroDAO();
				ArrayList<Libro> libros;
				libros = libroDAO.getLibros();
				request.setAttribute("libros", libros);
			}catch(RuntimeException  e) {
				request.setAttribute("error", e.getMessage());
			}
		}
		RequestDispatcher despachador = null;
		//despachador = request.getServletContext().getRequestDispatcher("/index.jsp");
		//despachador.forward(request,response);
		if(request.getServletPath().equals("")) {
			despachador=request.getServletContext().getRequestDispatcher("/index.jsp");
		}else if(request.getServletPath().equals("/insertar")){
			try {
				LibroDAO libroDAO = new LibroDAO();
				Libro libro = new Libro(Integer.parseInt(request.getParameter("isbn")),
						request.getParameter("titulo"),request.getParameter("autor"));
				libroDAO.insertar(libro);
				request.setAttribute("info","Libro "+ libro.toString()+" añadido");
			}catch(NumberFormatException e) {
				request.setAttribute("ERROR", e.getMessage());
			}catch(RuntimeException e) {
				request.setAttribute("error", e.getMessage());
			}
			despachador=request.getServletContext().getRequestDispatcher("/");
		}
		despachador.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
