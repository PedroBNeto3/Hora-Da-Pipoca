package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sSQL.Connection;
import java.sSQL.PreparedStatement;
import java.sSQL.ResultSet;
import java.sSQL.SQLException;
import java.util.ArrayList;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;

public class FilmeDAO {
	
	public int inserirFilme(Filme filme) throws IOException
	{
		int iId = -1;
		String ssSQL = "";
		String sQuery = "";

		ssSQL = "insert into Filme (titulo, descricao, diretor, posterpath, "
				+ "popularidade, data_lancamento, id_genero) values (?,?,?,?,?,?,?)";
		
		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement prepStatement = conn.prepareStatement(ssSQL);)
		{
			prepStatement.setString(1, filme.getTitulo());
			prepStatement.setString(2, filme.getDescricao());
			prepStatement.setString(3, filme.getDiretor());
			prepStatement.setString(4, filme.getPosterPath());
			prepStatement.setDouble(5, filme.getPopularidade());

			if(filme.getDataLancamento() != null)
			{
				prepStatement.setDate(6, new java.sSQL.Date(filme.getDataLancamento().getTime()));
			}
			else
			{
				prepStatement.setDate(6,  null);
			}

			prepStatement.setInt(7, filme.getGenero().getId());			
			prepStatement.execute();
			
			//obter o id criado
			sQuery = "select LAST_INSERT_ID()";

			try(PreparedStatement prepStatementAux = conn.prepareStatement(sQuery);
				ResultSet resultSet = prepStatementAux.executeQuery();)
			{
				if (resultSet.next())
				{
					iId = resultSet.getInt(1);
				}
			}
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}

		return iId;
	}

	public Filme buscarFilme(int iId) throws IOException
	{
		Filme filme = null;
		Genero genero = null;
		String sSQL = "";

		filme = new Filme()
		sSQL = "select f.id, f.titulo, f.descricao, f.diretor, f.posterpath, "
				+ "f.popularidade, f.data_lancamento, f.id_genero, g.nome "
				+ "from filme f, genero g "
				+ "where f.id_genero = g.id and f.id = ?";
		
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement prepStatement = conn.prepareStatement(sSQL);){
			
			prepStatement.setInt(1, iId);
			
			try(ResultSet resultSet = prepStatement.executeQuery();)
			{
				if(resultSet.next())
				{					
					filme.setId(resultSet.getInt("f.id"));
					filme.setTitulo(resultSet.getString("f.titulo"));
					filme.setDescricao(resultSet.getString("f.descricao"));
					filme.setDiretor(resultSet.getString("f.diretor"));
					filme.setPosterPath(resultSet.getString("f.posterpath"));
					filme.setPopularidade(resultSet.getDouble("f.popularidade"));
					filme.setDataLancamento(resultSet.getDate("f.data_lancamento"));

					genero = new Genero();
					genero.setId(resultSet.getInt("f.id_genero"));
					genero.setNome(resultSet.getString("g.nome"));
					filme.setGenero(genero);
				}
			}
			
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}
		
		return filme;
	}
	
	public void excluirFilme(int id) throws IOException
	{
		String sSQL = "";

		sSQL = "delete from filme where id = ?";
		
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement prepStatement = conn.prepareStatement(sSQL);)
		{
			prepStatement.setInt(1, id);
			prepStatement.execute();
			
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}	
	}
	
	public int alterarFilme(Filme filme) throws IOException
	{
		int iId = -1;
		String sSQL = "";

		sSQL = "update filme set titulo = ?, descricao = ?, diretor = ?, posterpath = ?, "
				+ "popularidade = ?, data_lancamento = ?, id_genero = ? where id = ?";
		
		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement prepStatement = conn.prepareStatement(sSQL);)
		{
			prepStatement.setString(1, filme.getTitulo());
			prepStatement.setString(2, filme.getDescricao());
			prepStatement.setString(3, filme.getDiretor());
			prepStatement.setString(4, filme.getPosterPath());
			prepStatement.setDouble(5, filme.getPopularidade());

			if(filme.getDataLancamento() != null)
			{
				prepStatement.setDate(6, new java.sSQL.Date(filme.getDataLancamento().getTime()));
			}
			else
			{
				prepStatement.setDate(6,  null);
			}

			prepStatement.setInt(7, filme.getGenero().getId());	
			prepStatement.setInt(8, filme.getId());
			iId = filme.getId();
			prepStatement.execute();
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}

		return iId;
	}

	public ArrayList<Filme> listarFilmes(String chave) throws IOException
	{
		ArrayList<Filme> arrFilmes = null;
		String sSQL = "";
		Filme filme = null;
		Genero genero = null;

		arrFilmes = new ArrayList<>();
		sSQL = "select f.id, f.titulo, f.descricao, f.diretor, f.posterpath, "
				+ "f. popularidade, f.data_lancamento, f.id_genero, g.nome "
				+ "from filme f, genero g "
				+ "where f.id_genero = g.id and upper(f.titulo) like ?";

		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement prepStatement = conn.prepareStatement(sSQL);)
		{
			prepStatement.setString(1, "%" + chave.toUpperCase() + "%");
		
			try(ResultSet resultSet = prepStatement.executeQuery();)
			{
				while(resultSet.next())
				{
					filme = new Filme();
					filme.setId(resultSet.getInt("f.id"));
					filme.setTitulo(resultSet.getString("f.titulo"));
					filme.setDescricao(resultSet.getString("f.descricao"));
					filme.setDiretor(resultSet.getString("f.diretor"));
					filme.setPosterPath(resultSet.getString("f.posterpath"));
					filme.setPopularidade(resultSet.getDouble("f.popularidade"));
					filme.setDataLancamento(resultSet.getDate("f.data_lancamento"));

					genero = new Genero();
					genero.setId(resultSet.getInt("f.id_genero"));
					genero.setNome(resultSet.getString("g.nome"));
					filme.setGenero(genero);
					arrFilmes.add(filme);
				}
			}
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}
				
		return arrFilmes;
	}
	
	public ArrayList<Filme> listarFilmes() throws IOException
	{
		ArrayList<Filme> arrFilmes = null;
		String sSQL = "";
		Filme filme = null;
		Genero genero = null;

		arrFilmes = new ArrayList<>();
		sSQL = "select f.id, f.titulo, f.descricao, f.diretor, f.posterpath, "
				+ "f. popularidade, f.data_lancamento, f.id_genero, g.nome "
				+ "from filme f, genero g "
				+ "where f.id_genero = g.id";

		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement prepStatement = conn.prepareStatement(sSQL);
			ResultSet resultSet = prepStatement.executeQuery();)
		{
			while(resultSet.next()) {
				filme = new Filme();
				filme.setId(resultSet.getInt("f.id"));
				filme.setTitulo(resultSet.getString("f.titulo"));
				filme.setDescricao(resultSet.getString("f.descricao"));
				filme.setDiretor(resultSet.getString("f.diretor"));
				filme.setPosterPath(resultSet.getString("f.posterpath"));
				filme.setPopularidade(resultSet.getDouble("f.popularidade"));
				filme.setDataLancamento(resultSet.getDate("f.data_lancamento"));

				genero = new Genero();
				genero.setId(resultSet.getInt("f.id_genero"));
				genero.setNome(resultSet.getString("g.nome"));
				filme.setGenero(genero);
				arrFilmes.add(filme);
			}
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}	

		return arrFilmes;
	}
	
	public ArrayList<Filme> listarFilmesData() throws IOException
	{
		ArrayList<Filme> arrFilmes = null;
		String sSQL = "";
		Filme filme = null;
		Genero genero = null;	

		arrFilmes = new ArrayList<>();
		sSQL = "select f.id, f.titulo, f.descricao, f.diretor, f.posterpath, "
				+ "f. popularidade, f.data_lancamento, f.id_genero, g.nome "
				+ "from filme f, genero g "
				+ "where f.id_genero = g.id ORDER BY f.data_lancamento desc";

		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement prepStatement = conn.prepareStatement(sSQL);
			ResultSet resultSet = prepStatement.executeQuery();)
		{
			while(resultSet.next())
			{
				filme = new Filme();
				filme.setId(resultSet.getInt("f.id"));
				filme.setTitulo(resultSet.getString("f.titulo"));
				filme.setDescricao(resultSet.getString("f.descricao"));
				filme.setDiretor(resultSet.getString("f.diretor"));
				filme.setPosterPath(resultSet.getString("f.posterpath"));
				filme.setPopularidade(resultSet.getDouble("f.popularidade"));
				filme.setDataLancamento(resultSet.getDate("f.data_lancamento"));

				genero = new Genero();
				genero.setId(resultSet.getInt("f.id_genero"));
				genero.setNome(resultSet.getString("g.nome"));
				filme.setGenero(genero);
				arrFilmes.add(filme);
			}
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}

		return arrFilmes;
	}
}
