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
		
		sSQL = "insert into Filme (titulo, descricao, diretor, posterpath, popularidade, data_lancamento, id_genero) values (?,?,?,?,?,?,?)";

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

			String query = "select LAST_INSERT_ID()";
			
			try ( PreparedStatement pstAux = conn.prepareStatement(query);
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

	public Filme buscarFilme(int id) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Filme> listarFilmes(String chave) throws IOException
	{
		ArrayList<Filme> arrFilmes = null;
		String sSQL = "";
		Filme filme = null;
		Genero genero = null;
		
		sSQL = "select f.id, f.titulo, f.descricao, f.diretor, f.posterpath, "
				+ "f. popularidade, f.data_lancamento, f.id_genero, g.nome " + "from filme f, genero g "
				+ "where f.id_genero = g.id and upper(f.titulo) like ?";
		
		arrFilmes = new ArrayList<>();
		
		try ( Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(sSQL); )
		{
			pst.setString(1, "%" + chave.toUpperCase() + "%");

			try ( ResultSet resultSet = pst.executeQuery(); )
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
					genero.setId(resultSet.getInt("f.id_genero"));
					genero.setNome(resultSet.getString("g.nome"));
					filme.setGenero(genero);
					
					arrFilmes.add(filme);
				}
			}
		}
		catch ( SQLException err )
		{
			err.printStackTrace();
			throw new IOException(err);
		}

		return arrFilmes;
	}

	public ArrayList<Filme> listarFilmes() throws IOException
	{
		String sSQL = "";
		ArrayList<Filme> arrFilmes = null;
		Filme filme = null;
		Genero genero = null;
		
		sSQL = "select f.id, f.titulo, f.descricao, f.diretor, f.posterpath, "
				+ "f. popularidade, f.data_lancamento, f.id_genero, g.nome " + "from filme f, genero g "
				+ "where f.id_genero = g.id";
		
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
				genero.setId(resultSet.getInt("f.id_genero"));
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
}
