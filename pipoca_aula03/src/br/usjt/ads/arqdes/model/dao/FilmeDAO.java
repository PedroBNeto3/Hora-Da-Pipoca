package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;

@Repository
public class FilmeDAO
{
	Connection conn;
	
	@Autowired
	public FilmeDAO(DataSource ds) throws IOException
	{
		try
		{
			conn = ds.getConnection();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	
	public int inserirFilme(Filme filme) throws IOException
	{
		int id = -1;
		String sSQL = "";
		String sQuery = "";

		sSQL = "insert into Filme (titulo, descricao, diretor, posterpath, "
				+ "popularidade, data_lancamento, id_genero) values (?,?,?,?,?,?,?)";
		
		try(PreparedStatement prepStatement = conn.prepareStatement(sSQL);)
		{
			
			prepStatement.setString(1, filme.getTitulo());
			prepStatement.setString(2, filme.getDescricao());
			prepStatement.setString(3, filme.getDiretor());
			prepStatement.setString(4, filme.getPosterPath());
			prepStatement.setDouble(5, filme.getPopularidade());

			if(filme.getDataLancamento() != null)
			{
				prepStatement.setDate(6, new java.sql.Date(filme.getDataLancamento().getTime()));
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
					id = resultSet.getInt(1);
				}
			}
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}

		return id;
	}

	public Filme buscarFilme(int id) throws IOException
	{
		Genero genero = null;
		Filme filme = null;
		String sSQL = "";

		filme = new Filme();
		sSQL = "select f.id, f.titulo, f.descricao, f.diretor, f.posterpath, "
				+ "f.popularidade, f.data_lancamento, f.id_genero, g.nome "
				+ "from filme f, genero g "
				+ "where f.id_genero = g.id and f.id = ?";
		
		try(PreparedStatement prepStatement = conn.prepareStatement(sSQL);)
		{
			
			prepStatement.setInt(1, id);
			
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

	public ArrayList<Filme> listarFilmes(String chave) throws IOException
	{
		ArrayList<Filme> arrFilmes = null;
		String sSQL = "";
		Filme filme = null;
		Genero genero = null;

		arrFilmes = new ArrayList<>();
		sSQL = "select f.id, f.titulo, f.descricao, f.diretor, f.posterpath, "
				+ "f.popularidade, f.data_lancamento, f.id_genero, g.nome "
				+ "from filme f, genero g "
				+ "where f.id_genero = g.id and upper(f.titulo) like ?";

		try(PreparedStatement prepStatement = conn.prepareStatement(sSQL);)
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
			e.printStackTrace();
			throw new IOException(e);
		}
				
		return arrFilmes;
	}
	
	public ArrayList<Filme> listarFilmes() throws IOException
	{
		Filme filme = null;
		Genero genero = null;
		ArrayList<Filme> arrFilmes = null;
		String sSQL = "";

		arrFilmes = new ArrayList<>();
		sSQL = "select f.id, f.titulo, f.descricao, f.diretor, f.posterpath, "
				+ "f.popularidade, f.data_lancamento, f.id_genero, g.nome "
				+ "from filme f, genero g "
				+ "where f.id_genero = g.id ORDER BY f.data_lancamento DESC";

		try(PreparedStatement prepStatement = conn.prepareStatement(sSQL);
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
	
	public void excluirFilme(int id) throws IOException
	{
		String sSQL = "";

		sSQL = "delete from filme where id = ?";
		
		try(PreparedStatement prepStatement = conn.prepareStatement(sSQL);)
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
		int id = -1;
		String sSQL = "";

		sSQL = "update filme set titulo = ?, descricao = ?, diretor = ?, posterpath = ?, "
				+ "popularidade = ?, data_lancamento = ?, id_genero = ? where id = ?";
		
		try(PreparedStatement prepStatement = conn.prepareStatement(sSQL);)
		{
			prepStatement.setString(1, filme.getTitulo());
			prepStatement.setString(2, filme.getDescricao());
			prepStatement.setString(3, filme.getDiretor());
			prepStatement.setString(4, filme.getPosterPath());
			prepStatement.setDouble(5, filme.getPopularidade());

			if(filme.getDataLancamento() != null)
			{
				prepStatement.setDate(6, new java.sql.Date(filme.getDataLancamento().getTime()));
			}
			else
			{
				prepStatement.setDate(6,  null);
			}

			prepStatement.setInt(7, filme.getGenero().getId());	
			prepStatement.setInt(8, filme.getId());
			id = filme.getId();
			prepStatement.execute();
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}

		return id;
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

		try(PreparedStatement prepStatement = conn.prepareStatement(sql);
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
