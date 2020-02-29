package br.com.josemarsilva.mqseries_api_cli;

public class App 
{

    public static void main( String[] args ) throws Exception
    {
    	String strCommandLineArguments = new String("");
    	for (int i=0;i<args.length;i++) {
    		strCommandLineArguments = new String(strCommandLineArguments.concat(" ").concat(args[i]));
    	}
    	
		// Parser Command Line Arguments ...
		CliArgsParser cliArgsParser = new CliArgsParser(args);
		
		// Create Object Instance ...
		MqseriesApi mqseriesApi = new MqseriesApi(cliArgsParser);

		// Execute Object Instance ...
		mqseriesApi.execute();

    }
}
