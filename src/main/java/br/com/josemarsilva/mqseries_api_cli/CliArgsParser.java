package br.com.josemarsilva.mqseries_api_cli;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


/*
 * CliArgsParser class is responsible for extract, compile and check consistency
 * for command line arguments passed as parameters in command line
 * 
 * @author josemarsilva@yahoo.com.br
 * @date   2020-02-25
 * 
 */

public class CliArgsParser {

	// Constants ...
	public final static String APP_NAME = new String("mqseries-api-cli");
	public final static String APP_VERSION = new String("v.2020.02.26.1200");
	public final static String APP_USAGE = new String(APP_NAME + " [<args-options-list>] - "+ APP_VERSION);

	// Constants defaults ...
	final public static String WMQ_APPLICATIONNAME = new String("MqSeries API CLI");

	// Constants Error Messages ...
	public final static String MSG_ERROR_ACTION_INVALID = new String("Error: action '%s' is invalid ! Try 'get' or 'put'");
	public final static String MSG_ERROR_MESSAGE_FILE_REQUIRED = new String("Error: message-body IS NOT PRESENT, so message-file MUST BE PRESENT! Try '-m YOUR-MESSAGE-HERE' or '-f your-message-filename.txt'");
	public final static String MSG_ERROR_MESSAGE_FILE_MUSTBEOMMITED = new String("Error: message-body IS PRESENT, so message-file MUST BE OMMITED! Try to remove '-f %s'");
	public final static String MSG_ERROR_MESSAGE_BODY_MUSTBEOMMITED = new String("Error: message-body IS PRESENT but MUST BE OMMITED! Try to remove '-m %s'");
	
	// Properties ...
    private String action = new String("");
    private String host = new String("");
    private Integer port = new Integer(0);
    private String channel = new String("");
    private String qmgr = new String("");
    private String appUser = new String("");
    private String appPassword = new String("");
    private String queueName = new String("");
    private String messageBody = new String("");
    private String messageFile = new String("");


    /*
     * CliArgsParser(args) - Constructor ...
     */
	public CliArgsParser( String[] args ) {

		// Options creating ...
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
        Option messageBodyOption = Option.builder("m")
        		.longOpt("message-body") 
        		.required(false) 
        		.desc("Message body to put. Ex: -m 01020304050607080910")
        		.hasArg()
        		.build();
        Option messageFileOption = Option.builder("f")
        		.longOpt("message-file") 
        		.required(false) 
        		.desc("Message file to put to or get from body. Ex: -f msg.txt")
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
        options.addOption(messageBodyOption);
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
	        	this.setMessageBody( cmdLine.getOptionValue("message-body", "") );
	        	this.setMessageFile( cmdLine.getOptionValue("message-file", "") );
	        		        	
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
		
		// Check argument: action IN ( 'get', 'put' ) ...
		if (!this.getAction().equals("get") && !this.getAction().equals("put")) {
			throw new Exception(MSG_ERROR_ACTION_INVALID.replaceFirst("%s", this.getAction()));
		}
		// Check argument: PUT message-body IS NULL, message-file MUST NOT BE NULL ...
		if (this.getAction().equals("put") && this.getMessageBody().equals("") && this.getMessageFile().equals("")) {
			throw new Exception(MSG_ERROR_MESSAGE_FILE_REQUIRED);
		}
		// Check argument: PUT message-body IS NOT NULL message-file MUST BE NULL ...
		if (this.getAction().equals("put") && !this.getMessageBody().equals("") && !this.getMessageFile().equals("")) {
			throw new Exception(MSG_ERROR_MESSAGE_FILE_MUSTBEOMMITED.replaceFirst("%s", this.getMessageFile()));
		}
		// Check argument: GET message-body MUST BE NULL ...
		if (this.getAction().equals("get") && !this.getMessageBody().equals("")) {
			throw new Exception(MSG_ERROR_MESSAGE_BODY_MUSTBEOMMITED.replaceAll("%s", this.getMessageBody()));
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

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getMessageFile() {
		return messageFile;
	}

	public void setMessageFile(String messageFile) {
		this.messageFile = messageFile;
	}

	
	// Generate Getters and Setters - End.


}
