package br.com.josemarsilva.mqseries_api_cli;

import org.apache.log4j.Logger;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;


public class MqseriesApi {

	// Logger ...
	final static Logger logger = Logger.getLogger(MqseriesApi.class);

	/*
	 * Constants ...
	 */
	

	/*
	 * Properties ...
	 */
	CliArgsParser cliArgsParser = null;

	
	/*
	 * Constructor 
	 */
	public MqseriesApi(CliArgsParser cliArgsParser) throws Exception {
		super();
		
		// Setter cliArgsParser ...
		logger.info("MqseriesApi - Constructor()");
		this.cliArgsParser = cliArgsParser;
		
	}
	
	
	/*
	 * execute() 
	 */
	public void execute() throws Exception {
		
		logger.info("MqseriesApi.execute()");

		// Variables
		JMSContext context = null;
		Destination destination = null;
		JMSProducer producer = null;
		JMSConsumer consumer = null;

		
		// Create a connection factory
		JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
		JmsConnectionFactory cf = ff.createConnectionFactory();

		// Set the properties
		cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, cliArgsParser.getHost());
		cf.setIntProperty(WMQConstants.WMQ_PORT, cliArgsParser.getPort());
		cf.setStringProperty(WMQConstants.WMQ_CHANNEL, cliArgsParser.getChannel());
		cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, cliArgsParser.getQmgr());
		cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, cliArgsParser.WMQ_APPLICATIONNAME);
		cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		cf.setStringProperty(WMQConstants.USERID, cliArgsParser.getAppUser());
		cf.setStringProperty(WMQConstants.PASSWORD, cliArgsParser.getAppPassword());
		
		// Create JMS objects
		context = cf.createContext();
		destination = context.createQueue("queue:///" + cliArgsParser.getQueueName());
		TextMessage message = context.createTextMessage(cliArgsParser.getMessage());

		// PUT or GET ?
		if (cliArgsParser.getAction().toLowerCase().contentEquals("put")) {
			
			// PUT ...
			producer = context.createProducer();
			producer.send(destination, message);
			System.out.println("Sent message:\n" + message);
			
		} else if (cliArgsParser.getAction().toLowerCase().contentEquals("get")) {

			// GET ...
			consumer = context.createConsumer(destination); // autoclosable
			String receivedMessage = consumer.receiveBody(String.class, 15000); // in ms or 15 seconds
			System.out.println("\nReceived message:\n" + receivedMessage);

		}

	}

	
}
