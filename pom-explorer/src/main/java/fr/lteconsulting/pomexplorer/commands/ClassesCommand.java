package fr.lteconsulting.pomexplorer.commands;

import java.util.List;

import fr.lteconsulting.pomexplorer.Client;
import fr.lteconsulting.pomexplorer.GAV;
import fr.lteconsulting.pomexplorer.GavTools;
import fr.lteconsulting.pomexplorer.Project;
import fr.lteconsulting.pomexplorer.Tools;
import fr.lteconsulting.pomexplorer.WorkingSession;
import fr.lteconsulting.pomexplorer.javac.JavaSourceAnalyzer;

public class ClassesCommand
{
	@Help( "gives the java classes provided by the session's gavs" )
	public String main( WorkingSession session, Client client )
	{
		return providedBy( session, client, null );
	}

	@Help( "gives the java classes provided by the session's gavs, filtered by the given parameter" )
	public String providedBy(WorkingSession session, Client client, FilteredGAVs gavFilter)
	{
		if (gavFilter == null)
			return Tools.warningMessage("You should specify a GAV filter");

		StringBuilder log = new StringBuilder();

		log.append("<br/>GAV list filtered with '" + gavFilter + "' :<br/>");

		for (GAV gav : gavFilter.getGavs(session))
		{
			List<String> classes = GavTools.analyseProvidedClasses( session, gav, log );
			if( classes == null )
			{
				log.append( Tools.warningMessage( "No class provided by gav " + gav ) );
				continue;
			}

			for( String className : classes )
				log.append( className + "<br/>" );
		}

		return log.toString();
	}

	/*
	 * parse all the Java source files in the gav's project directory and extract all referenced fqns.
	 * 
	 * substract the gav's provided classes from this set, to get external references
	 */
	@Help( "gives the fqn list of referenced classes by the session's gavs, filtered by the given parameter" )
	public String referencedBy(WorkingSession session, FilteredGAVs gavFilter)
	{
		StringBuilder log = new StringBuilder();

		JavaSourceAnalyzer analyzer = new JavaSourceAnalyzer();

		for (GAV gav : gavFilter.getGavs(session))
		{
			Project project = session.projects().forGav( gav );
			if( project == null )
				continue;

			analyzer.analyzeProject( project, true, log );
		}

		return log.toString();
	}
}