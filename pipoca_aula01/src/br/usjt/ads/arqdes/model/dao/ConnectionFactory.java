package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory
{
	static
	{
		try
		{
			// 8.0.12
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 5.1.38
			//Class.forName("com.mysql.jdbc.Driver");
		}
		catch ( ClassNotFoundException err )
		{
			err.printStackTrace();
			// porque este erro nao pode ser prevenido via programa usa-se RuntimeException,
			// que é unchecked.
			throw new RuntimeException(err);
		}
	}

	public static Connection getConnection() throws IOException
	{
		try
		{
			return DriverManager.getConnection("jdbc:mysql://localhost/pipocadb?user=Alunos&password=alunos"
					+ "&useSSL=false&allowMultiQueries=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		}
		catch ( SQLException err )
		{
			err.printStackTrace();
			throw new IOException(err);
		}
	}

}
