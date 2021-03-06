package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;

public class FilmeDAO
{
	public int inserirFilme(Filme filme) throws IOException
	{
		int iId = -1;
		String sSQL = "";
		String sQuery = "";
		
		sSQL = " INSERT INTO Filme (titulo, descricao, diretor, posterpath, popularidade, data_lancamento, id_genero) VALUES (?,?,?,?,?,?,?)";

		try ( Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(sSQL); )
		{
			pst.setString(1, filme.getTitulo());
			pst.setString(2, filme.getDescricao());
			pst.setString(3, filme.getDiretor());
			pst.setString(4, filme.getPosterPath());
			pst.setDouble(5, filme.getPopularidade());
			
			if ( filme.getDataLancamento() != null )
			{
				pst.setDate(6, new Date(filme.getDataLancamento().getTime()));
			}
			else
			{
				pst.setDate(6, null);
			}
			
			pst.setInt(7, filme.getGenero().getId());
			pst.execute();

			sQuery = " SELECT LAST_INSERT_ID() ";
			
			try ( PreparedStatement pstAux = conn.prepareStatement(sQuery);
					ResultSet resultSet = pstAux.executeQuery(); )
			{
				if ( resultSet.next() )
				{
					iId = resultSet.getInt(1);
				}
			}
		}
		catch ( SQLException err )
		{
			err.printStackTrace();
			throw new IOException(err);
		}
		
		return iId;
	}
	
	public void atualizarFilme(Filme filme) throws IOException
	{
		String sSQL = "";
		
		sSQL = " UPDATE Filme SET titulo = ?, descricao = ?, diretor = ?, posterpath = ?, popularidade = ?, data_lancamento = ?, id_genero = ? WHERE id = ?";
		
		try ( Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(sSQL); )
		{
			pst.setString(1, filme.getTitulo());
			pst.setString(2, filme.getDescricao());
			pst.setString(3, filme.getDiretor());
			pst.setString(4, filme.getPosterPath());
			pst.setDouble(5, filme.getPopularidade());
			pst.setDate(6, new Date(filme.getDataLancamento().getTime()));
			pst.setInt(7, filme.getGenero().getId());
			pst.setInt(8, filme.getId());
			pst.execute();
		}
		catch ( SQLException err )
		{
			err.printStackTrace();
			throw new IOException(err);
		}
	}

	public Filme buscarFilme(int iId) throws IOException
	{
		String sSQL = "";
		Filme filme = null;
		GeneroDAO generoDAO = null;
		
		sSQL = " SELECT * FROM Filme WHERE id = ? ";
		
		try ( Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(sSQL); )
		{
			pst.setInt(1, iId);
			
			try ( ResultSet resultSet = pst.executeQuery(); )
			{
				if ( resultSet.next() )
				{
					generoDAO = new GeneroDAO();

					filme = new Filme();
					filme.setId(iId);
					filme.setTitulo(resultSet.getString("titulo"));
					filme.setDescricao(resultSet.getString("descricao"));
					filme.setDiretor(resultSet.getString("diretor"));
					filme.setPosterPath(resultSet.getString("posterpath"));
					filme.setPopularidade(resultSet.getDouble("popularidade"));
					filme.setDataLancamento(resultSet.getDate("data_lancamento"));
					filme.setGenero(generoDAO.buscarGenero(resultSet.getInt("id_genero")));
				}
			}
		}
		catch ( SQLException err )
		{
			err.printStackTrace();
			throw new IOException(err);
		}
		
		return filme;
	}

	public ArrayList<Filme> listarFilmes() throws IOException
	{
		String sSQL = "";
		ArrayList<Filme> arrFilmes = null;
		Filme filme = null;
		Genero genero = null;
		
		sSQL = " SELECT * FROM Filme f, Genero g WHERE f.id_genero = g.id ";
		
		arrFilmes = new ArrayList<>();
		
		try ( Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(sSQL);
				ResultSet resultSet = pst.executeQuery(); )
		{
			while ( resultSet.next() )
			{
				filme = new Filme();
				filme.setId(resultSet.getInt("f.id"));
				filme.setTitulo(resultSet.getString("f.titulo"));
				filme.setDescricao(resultSet.getString("f.descricao"));
				filme.setDiretor(resultSet.getString("f.diretor"));
				filme.setPosterPath(resultSet.getString("f.posterpath"));
				filme.setDataLancamento(resultSet.getDate("f.data_lancamento"));

				genero = new Genero();
				genero.setId(resultSet.getInt("g.id"));
				genero.setNome(resultSet.getString("g.nome"));
				filme.setGenero(genero);
				
				arrFilmes.add(filme);
			}
		}
		catch ( SQLException err )
		{
			err.printStackTrace();
			throw new IOException(err);
		}
		
		return arrFilmes;
	}
	
	public void removerFilme(int iId) throws IOException
	{
		String sSQL = "";
		
		sSQL = " DELETE FROM Filme WHERE id = ? ";
		
		try ( Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(sSQL) )
		{
			pst.setInt(1, iId);
			pst.execute();
		}
		catch ( SQLException err )
		{
			err.printStackTrace();
			throw new IOException(err);
		}
	}
}
