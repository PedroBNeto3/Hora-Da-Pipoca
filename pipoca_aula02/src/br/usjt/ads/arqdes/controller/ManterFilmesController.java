package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;

/**
 * Servlet implementation class ManterFilmesController
 */
@WebServlet("/manterfilmes.do")
public class ManterFilmesController extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		String acao = request.getParameter("acao");
		RequestDispatcher dispatcher;
		FilmeService filmeService;
		GeneroService generoService;
		Filme filme;
		HttpSession session;

		String titulo = request.getParameter("titulo");
		String descricao = request.getParameter("descricao");
		String diretor = request.getParameter("diretor");
		String posterPath = request.getParameter("posterPath");
		String popularidade = request.getParameter("popularidade") == null
				|| request.getParameter("popularidade").length() == 0 ? "0" : request.getParameter("popularidade");
		String dataLancamento = request.getParameter("dataLancamento") == null
				|| request.getParameter("dataLancamento").length() == 0 ? "" : request.getParameter("dataLancamento");
		String idGenero = request.getParameter("genero.id");
		String chave = request.getParameter("data[search]");
		int[][] ratingRanges = new int[][] { { 81, 100 }, { 61, 80 }, { 41, 60 }, { 21, 40 }, { 0, 20 } };

		switch (acao)
		{
			case "novo":
			{
				generoService = new GeneroService();
				ArrayList<Genero> arrGeneros = null;
				arrGeneros = generoService.listarGeneros();

				session = request.getSession();
				session.setAttribute("generos", generos);
				dispatcher = request.getRequestDispatcher("CriarFilme.jsp");
				dispatcher.forward(request, response);

				break;
			}
			case "visualizar":
			{
				filmeService = new FilmeService();
				filme = filmeService.buscarFilme(Integer.parseInt(request.getParameter("id")));

				request.setAttribute("filme", filme);
				dispatcher = request.getRequestDispatcher("VisualizarFilme.jsp");
				dispatcher.forward(request, response);

				break;
			}
			case "criar":
			{
				filmeService = new FilmeService();
				filme = new Filme();
				filme.setTitulo(titulo);
				filme.setDescricao(descricao);
				filme.setDiretor(diretor);

				generoService = new GeneroService();
				Genero genero = new Genero();
				genero.setId(Integer.parseInt(idGenero));
				genero.setNome(generoService.buscarGenero(genero.getId()).getNome());

				filme.setGenero(genero);

				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				try
				{
					filme.setDataLancamento(formatter.parse(dataLancamento));
				}
				catch (ParseException err)
				{
					err.printStackTrace();
					filme.setDataLancamento(null);
				}

				filme.setPopularidade(Double.parseDouble(popularidade));
				filme.setPosterPath(posterPath);

				filme = filmeService.inserirFilme(filme);

				request.setAttribute("filme", filme);

				dispatcher = request.getRequestDispatcher("VisualizarFilme.jsp");
				dispatcher.forward(request, response);

				break;
			}
			case "editar":
			{
				ArrayList<Genero> arrGeneros = null;

				filmeService = new FilmeService();
				generoService = new GeneroService();
				eGeneros = generoService.listarGeneros();

				filme = filmeService.buscarFilme(Integer.parseInt(request.getParameter("id")));
				
				session = request.getSession();
				session.setAttribute("filme", filme);
				session.setAttribute("generos", arrGeneros);
				dispatcher = request.getRequestDispatcher("AlterarFilme.jsp");
				dispatcher.forward(request, response);

				break;
			}
			case "alterar":
			{
				int iId = 0;

				iId = Integer.parseInt(request.getParameter("id"));

				filmeService = new FilmeService();
				filme = new Filme();
				filme.setId(iId);
				filme.setTitulo(titulo);
				filme.setDescricao(descricao);
				filme.setDiretor(diretor);

				generoService = new GeneroService();
				genero = new Genero();
				genero.setId(Integer.parseInt(idGenero));
				genero.setNome(generoService.buscarGenero(genero.getId()).getNome());

				filme.setGenero(genero);

				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				try
				{
					filme.setDataLancamento(formatter.parse(dataLancamento));
				}
				catch (ParseException err)
				{
					e.printStackTrace();
					filme.setDataLancamento(null);
				}

				filme.setPopularidade(Double.parseDouble(popularidade));
				filme.setPosterPath(posterPath);

				filme = filmeService.alterarFilme(filme);

				request.setAttribute("filme", filme);

				dispatcher = request.getRequestDispatcher("VisualizarFilme.jsp");
				dispatcher.forward(request, response);

				break;
			}
			case "excluir":
			{
				filmeService = new FilmeService();
				filmeService.excluirFilme(Integer.parseInt(request.getParameter("id")));

				session = request.getSession();
				dispatcher = request.getRequestDispatcher("manterfilmes.do?acao=listar");
				dispatcher.forward(request, response);

				break;
			}
			case "reiniciar":
			{
				session = request.getSession();
				session.setAttribute("lista", null);
				dispatcher = request.getRequestDispatcher("ListarFilmes.jsp");
				dispatcher.forward(request, response);

				break;
			}
			case "listar_genero":
			{
				ArrayList<Genero> arrGeneros = null;
				ArrayList<Filme> arrFilmes = null;

				arrGeneros = generoService.buscarGenerosFilmes();
				arrFilmes = filmeService.listarFilmes();

				session = request.getSession();
				filmeService = new FilmeService();
				generoService = new GeneroService();
				session.setAttribute("arrGeneros", arrGeneros);
				session.setAttribute("arrFilmes", arrFilmes);
				dispatcher = request.getRequestDispatcher("ListarFilmesGenero.jsp");
				dispatcher.forward(request, response);

				break;
			}
			case "listar_popularidade":
			{
				ArrayList<Filme> arrFilmes = null;

				arrFilmes = filmeService.listarFilmes();

				session = request.getSession();
				filmeService = new FilmeService();
				session.setAttribute("arrFilmes", arrFilmes);
				session.setAttribute("ratings", getRatings(arrFilmes, ratingRanges));
				session.setAttribute("ranges", ratingRanges);
				dispatcher = request.getRequestDispatcher("ListarFilmesPopularidade.jsp");
				dispatcher.forward(request, response);

				break;
			}
			case "listar_lancamento":
			{
				ArrayList<Filme> arrFilmes = null;
				ArrayList<ArrayList<Filme>> listas = null;

				filmeService = new FilmeService();
				arrFilmes = filmeService.listarFilmesData();
				listas = getLancamentos(arrFilmes);

				session = request.getSession();
				session.setAttribute("listas", listas);
				dispatcher = request.getRequestDispatcher("ListarFilmesLancamento.jsp");
				dispatcher.forward(request, response);

				break;
			}
			case "listar":
			{
				session = request.getSession();
				filmeService = new FilmeService();
				ArrayList<Filme> arrFilmes;

				if ( chave != null && chave.length() > 0 )
				{
					arrFilmes = filmeService.listarFilmes(chave);
				}
				else
				{
					arrFilmes = filmeService.listarFilmes();
				}

				session.setAttribute("lista", arrFilmes);
				dispatcher = request.getRequestDispatcher("ListarFilmes.jsp");
				dispatcher.forward(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	protected int[] getRatings(ArrayList<Filme> arrFilmes, int[][] r)
	{
		int[] res = new int[] { 0, 0, 0, 0, 0 };
		int k = 0;

		for ( int[] i : r )
		{
			if ( containsRatingInBetween(arrFilmes, i[0], i[1]) )
			{
				res[k] = 1;
			}

			k++;
		}

		return res;
	}

	protected boolean containsRatingInBetween(ArrayList<Filme> arrFilmes, double dMenor, double dMaior)
	{

		for (Filme filmeAux : arrFilmes)
		{
			if (filmeAux.getPopularidade() >= dMenor && filmeAux.getPopularidade() <= dMaior)
			{
				return true;
			}
		}

		return false;
	}

	protected ArrayList<ArrayList<Filme>> getLancamentos(ArrayList<Filme> arrFilmes)
	{
		ArrayList<ArrayList<Filme>> arrListas = null;
		ArrayList<Integer> anos = null;
		int mesAtual = 0;
		int mesFilme = 0;
		int anoFilme = 0;
		int anoAtual = 0;
		int contador = 0;
		Calendar cal = null;
		
		cal = Calendar.getInstance();
		mesAtual = cal.get(Calendar.MONTH);
		anoAtual = cal.get(Calendar.YEAR);

		arrListas = new ArrayList<ArrayList<Filme>>();
		anos = new ArrayList<Integer>();
		arrListas.add(new ArrayList<Filme>());		

		for ( Filme filmeAux : arrFilmes )
		{
			cal.setTime(filmeAux.getDataLancamento());
			mesFilme = cal.get(Calendar.MONTH);
			anoFilme = cal.get(Calendar.YEAR);

			if ( mesAtual == mesFilme && anoFilme == anoAtual )
			{
				arrListas.get(contador).add(filmeAux);
			}
		}

		contador++;

		for ( Filme filmeAux : arrFilmes )
		{
			cal.setTime(filmeAux.getDataLancamento());
			anoFilme = cal.get(Calendar.YEAR);

			if ( !anos.contains(anoFilme) )
			{
				anos.add(anoFilme);
				arrListas.add(new ArrayList<Filme>());
				contador++;
			}

			arrListas.get(contador - 1).add(filmeAux);
		}

		return arrListas;
	}
}
