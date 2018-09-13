package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.usjt.ads.arqdes.model.entity.Filme;

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
			pst.setDate(6, new Date(filme.getDataLancamento().getTime()));
			pst.setInt(7, filme.getGenero().getId());
			pst.execute();

			// obter o id criado
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
	
	public void atualizarFilme(Filme filme) throws IOException
	{
	}
}
