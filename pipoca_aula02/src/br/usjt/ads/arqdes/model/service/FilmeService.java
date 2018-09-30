package br.usjt.ads.arqdes.model.service;

import java.io.IOException;
import java.util.ArrayList;

import br.usjt.ads.arqdes.model.dao.FilmeDAO;
import br.usjt.ads.arqdes.model.entity.Filme;

public class FilmeService
{
	private FilmeDAO filmeDAO;
	
	public FilmeService()
	{
		filmeDAO = new FilmeDAO();
	}
	
	public Filme buscarFilme(int iId) throws IOException
	{
		return filmeDAO.buscarFilme(iId);
	}
	
	public void excluirFilme(int iId) throws IOException
	{
		filmeDAO.excluirFilme(iId);
	}
	
	public Filme inserirFilme(Filme filme) throws IOException
	{
		int iId = 0;

		iId = filmeDAO.inserirFilme(filme);
		filme.setId(id);

		return filme;
	}
	
	public Filme alterarFilme(Filme filme) throws IOException
	{
		int iId = 0;

		iId = filmeDAO.alterarFilme(filme);
		filme.setId(id);

		return filme;
	}

	public ArrayList<Filme> listarFilmes(String chave) throws IOException
	{
		return filmeDAO.listarFilmes(chave);
	}

	public ArrayList<Filme> listarFilmes() throws IOException
	{
		return filmeDAO.listarFilmes();
	}
	
	public ArrayList<Filme> listarFilmesData() throws IOException
	{
		return filmeDAO.listarFilmesData();
	}
}
