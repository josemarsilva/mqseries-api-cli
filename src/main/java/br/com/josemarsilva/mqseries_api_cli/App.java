package br.com.josemarsilva.mqseries_api_cli;

import org.apache.log4j.Logger;

public class App 
{
	final static Logger logger = Logger.getLogger(App.class);

    public static void main( String[] args ) throws Exception
    {
    	String strCommandLineArguments = new String("");
    	for (int i=0;i<args.length;i++) {
    		strCommandLineArguments = new String(strCommandLineArguments.concat(" ").concat(args[i]));
    	}
    	logger.info("main( String[] args ):" + strCommandLineArguments);
    	
		// Parser Command Line Arguments ...
		CliArgsParser cliArgsParser = new CliArgsParser(args);
		
		// Create Object Instance ...
		MqseriesApi mqseriesApi = new MqseriesApi(cliArgsParser);

		// Execute Object Instance ...
		mqseriesApi.execute();

    }
}
