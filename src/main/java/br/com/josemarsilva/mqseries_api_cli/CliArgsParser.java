package br.com.josemarsilva.mqseries_api_cli;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
//import org.apache.log4j.Logger;


/*
 * CliArgsParser class is responsible for extract, compile and check consistency
 * for command line arguments passed as parameters in command line
 * 
 * @author josemarsilva@yahoo.com.br
 * @date   2020-02-25
 * 
 */

public class CliArgsParser {

	// Logger ...
//	final static Logger logger = Logger.getLogger(CliArgsParser.class);


	// Constants ...
	public final static String APP_NAME = new String("mqseries-api-cli");
	public final static String APP_VERSION = new String("v.2020.02.26.1200");
	public final static String APP_USAGE = new String(APP_NAME + " [<args-options-list>] - "+ APP_VERSION);

	// Constants defaults ...
	final public static String WMQ_APPLICATIONNAME = new String("MqSeries API CLI");

	// Constants Error Messages ...
	public final static String MSG_ERROR_ACTION_INVALID = new String("Error: action '%s' is invalid ! Try 'get' or 'put'");
	
	// Properties ...
    private String action = new String("");
    private String host = new String("");
    private Integer port = new Integer(0);
    private String channel = new String("");
    private String qmgr = new String("");
    private String appUser = new String("");
    private String appPassword = new String("");
    private String queueName = new String("");
    private String message = new String("");
    private String messageFile = new String("");


    /*
     * CliArgsParser(args) - Constructor ...
     */
	public CliArgsParser( String[] args ) {


		// Options creating ...
//		logger.info("Command line Options");
		Options options = new Options();
		
		
		// Options configuring  ...
        Option helpOption = Option.builder("h") 
        		.longOpt("help") 
        		.required(false) 
        		.desc("shows usage help message. See more https://github.com/josemarsilva/mqseries-api-cli") 
        		.build(); 
        Option actionOption = Option.builder("A")
        		.longOpt("action") 
        		.required(true) 
        		.desc("Action launched. List of values: ( 'put', 'get' ). Ex: -A put")
        		.hasArg()
        		.build();
        Option hostOption = Option.builder("H")
        		.longOpt("host") 
        		.required(true) 
        		.desc("Host name or IP address. Ex: -H 127.0.0.1")
        		.hasArg()
        		.build();
        Option portOption = Option.builder("P")
        		.longOpt("port") 
        		.required(true)
        		.desc("Listener port number for your queue manager. Ex: -P 1414")
        		.hasArg()
        		.build();
        Option channelOption = Option.builder("C")
        		.longOpt("channel") 
        		.required(true) 
        		.desc("Channel name. Ex: -C DEV.APP.SVRCONN")
        		.hasArg()
        		.build();
        Option qmgrOption = Option.builder("Q")
        		.longOpt("qmgr") 
        		.required(true) 
        		.desc("Queue manager name. Ex: -Q QM1")
        		.hasArg()
        		.build();
        Option appUserOption = Option.builder("u")
        		.longOpt("app-user") 
        		.required(false) 
        		.desc("Application User to connect to MQ. Default: ''")
        		.hasArg()
        		.build();
        Option appPasswordOption = Option.builder("p")
        		.longOpt("app-password") 
        		.required(true) 
        		.desc("Application Password to connect to MQ. Ex: -p passw0rd")
        		.hasArg()
        		.build();
        Option queueNameOption = Option.builder("q")
        		.longOpt("queue-name") 
        		.required(true) 
        		.desc("Queue name mqseries-api-cli uses to put or get messages to and from. Ex: -q DEV.QUEUE.1")
        		.hasArg()
        		.build();
        Option messageOption = Option.builder("m")
        		.longOpt("message") 
        		.required(false) 
        		.desc("Message to put. Ex: -m 01020304050607080910")
        		.hasArg()
        		.build();
        Option messageFileOption = Option.builder("f")
        		.longOpt("message-file") 
        		.required(false) 
        		.desc("Message file to put to or get from. Ex: -f msg.txt")
        		.hasArg()
        		.build();
        
		// Options adding configuration ...
        options.addOption(helpOption);
        options.addOption(actionOption);
        options.addOption(hostOption);
        options.addOption(portOption);
        options.addOption(channelOption);
        options.addOption(qmgrOption);
        options.addOption(appUserOption);
        options.addOption(appPasswordOption);
        options.addOption(queueNameOption);
        options.addOption(messageOption);
        options.addOption(messageFileOption);
        
        
        // CommandLineParser ...
        CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmdLine = parser.parse(options, args);
			
	        if (cmdLine.hasOption("help")) { 
	            HelpFormatter formatter = new HelpFormatter();
	            formatter.printHelp(APP_USAGE, options);
	            System.exit(0);
	        } else {
	        	
	        	// Set properties from Options ...
	        	this.setAction( cmdLine.getOptionValue("action", "") );
	        	this.setHost( cmdLine.getOptionValue("host", "") );
	        	this.setPort( Integer.parseInt( cmdLine.getOptionValue("port") ) );
	        	this.setChannel( cmdLine.getOptionValue("channel", "") );
	        	this.setQmgr( cmdLine.getOptionValue("qmgr", "") );
	        	this.setAppUser( cmdLine.getOptionValue("app-user", "") );
	        	this.setAppPassword( cmdLine.getOptionValue("app-password", "") );
	        	this.setQueueName( cmdLine.getOptionValue("queue-name", "") );
	        	this.setMessage( cmdLine.getOptionValue("message", "") );
	        	this.setMessage( cmdLine.getOptionValue("message-file", "") );
	        	
	        	// Logger
	        	
	        	// Check arguments Options ...
	        	try {
	        		checkArgumentOptions();
	        	} catch (Exception e) {
	    			System.err.println(e.getMessage());
	    			System.exit(-1);
	        	}
	        	
	        	System.out.println(APP_NAME + " - "+ APP_VERSION);
	        	
	        }
			
		} catch (ParseException e) {
			System.err.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter(); 
            formatter.printHelp(APP_USAGE, options); 
			System.exit(-1);
		} 
        
	}

	//
	private void checkArgumentOptions() throws Exception {
		
		// Check argument: action ...
		if (!this.getAction().equals("get") && !this.getAction().equals("put")) {
			throw new Exception(MSG_ERROR_ACTION_INVALID.replaceFirst("%s", this.getAction()));
		}
				
	}


	// Generate Getters and Setters - Begin ...

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getQmgr() {
		return qmgr;
	}

	public void setQmgr(String qmgr) {
		this.qmgr = qmgr;
	}

	public String getAppUser() {
		return appUser;
	}

	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}

	public String getAppPassword() {
		return appPassword;
	}

	public void setAppPassword(String appPassword) {
		this.appPassword = appPassword;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageFile() {
		return messageFile;
	}

	public void setMessageFile(String messageFile) {
		this.messageFile = messageFile;
	}

	
	// Generate Getters and Setters - End.


}
