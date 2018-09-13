package br.usjt.ads.arqdes.model.service;

import java.io.IOException;

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

	public Filme inserirFilme(Filme filme) throws IOException
	{
		int iId = filmeDAO.inserirFilme(filme);
		filme.setId(iId);
		
		return filme;
	}

	public void removerFilme(int iId) throws IOException
	{
		filmeDAO.removerFilme(iId);
	}
}
