package fr.lteconsulting.pomexplorer.commands;

import fr.lteconsulting.pomexplorer.Client;
import fr.lteconsulting.pomexplorer.Log;
import fr.lteconsulting.pomexplorer.PomAnalyzer;
import fr.lteconsulting.pomexplorer.Session;

public class AnalyzeCommand
{
	@Help( "analyse all the pom files in a directory, recursively" )
	public void directory( CommandOptions options, Client client, Session session, Log log, String directory )
	{
		log.html( "Analyzing directoy '" + directory + "'...<br/>" );
		log.html( "<i>possible options: quiet, nofetch, offline</i>" );

		PomAnalyzer analyzer = new PomAnalyzer();
		analyzer.analyze( directory, !options.hasFlag( "quiet" ), !options.hasFlag( "nofetch" ), !options.hasFlag( "offline" ), session, log );

		log.html( "Analyzis completed.<br/>" );
	}
}
