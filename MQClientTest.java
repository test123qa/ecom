package com.api.framework;

import java.io.IOException;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.headers.MQHeaderIterator;
import com.ibm.mq.headers.pcf.CMQC;

/**
 * Java class to connect to MQ. Post and Retreive messages.
 *
 */
public class MQClientTest {
	// message to put on MQ.
	// Create a default local queue.
	MQQueue defaultLocalQueue;
	MQQueueManager qManager;

	/**
	 * Initialize the MQ
	 *
	 */
	public void init() {

		MQEnvironment.hostname = ConnectSettings.mosaic.hostName;
		MQEnvironment.channel = ConnectSettings.mosaic.channel;
		MQEnvironment.port = ConnectSettings.mosaic.port;
		MQEnvironment.userID = ConnectSettings.mosaic.userid;
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_CLIENT);
		try {
			// initialize MQ manager.
			qManager = new MQQueueManager(ConnectSettings.mosaic.qMngrStr);
		} catch (MQException e) {
			e.printStackTrace();
		}
	}

	public void initLocal() {

		MQEnvironment.hostname = ConnectSettings.mosaicl.hostName;
		MQEnvironment.channel = ConnectSettings.mosaicl.channel;
		MQEnvironment.port = ConnectSettings.mosaicl.port;
		MQEnvironment.userID = ConnectSettings.mosaicl.userid;
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_CLIENT);
		try {
			// initialize MQ manager.
			qManager = new MQQueueManager(ConnectSettings.mosaicl.qMngrStr);
		} catch (MQException e) {
			e.printStackTrace();
		}
	}

	public void initLime() {

		MQEnvironment.hostname = ConnectSettings.lime.hostName;
		MQEnvironment.channel = ConnectSettings.lime.channel;
		MQEnvironment.port = ConnectSettings.lime.port;
		MQEnvironment.userID = ConnectSettings.lime.userid;
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_CLIENT);
		try {
			// initialize MQ manager.
			qManager = new MQQueueManager(ConnectSettings.lime.qMngrStr);
		} catch (MQException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to put message to MQ.
	 *
	 */
	public void putAndGetMessage(String msg, String Queuename) {

		int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;
		try {
			defaultLocalQueue = qManager.accessQueue(Queuename, openOptions, "", null, null);
			MQMessage putMessage = new MQMessage();
			putMessage.correlationId = "7".getBytes();
			putMessage.format = MQC.MQFMT_STRING;
			putMessage.feedback = MQC.MQFB_NONE;
			putMessage.messageType = MQC.MQMT_DATAGRAM;
			System.out.println("######INPUT#######");
			System.out.println(msg.toString());
			putMessage.writeString(msg.toString());
			MQPutMessageOptions pmo = new MQPutMessageOptions();
			defaultLocalQueue.put(putMessage, pmo);
			MQMessage getMessages = new MQMessage();
			getMessages.messageId = putMessage.messageId;
		} catch (MQException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMessage(String Queuename) throws MQException {

		int openOptions = MQC.MQOO_FAIL_IF_QUIESCING | MQC.MQOO_INPUT_SHARED | MQC.MQOO_BROWSE;
		String msgText = null;
		MQQueue queue = qManager.accessQueue(Queuename, openOptions);

		MQMessage theMessage = new MQMessage();
		// MQHeader header=new MQheader
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST;
		gmo.matchOptions = MQC.MQMO_NONE;
		gmo.waitInterval = 5000;

		boolean thereAreMessages = true;
		while (thereAreMessages) {
			try {
				// read the message
				queue.get(theMessage, gmo);
				MQHeaderIterator it = new MQHeaderIterator(theMessage);
				while (it.hasNext()) {
					com.ibm.mq.headers.MQHeader header = it.nextHeader();
				}
				msgText = theMessage.readString(theMessage.getMessageLength());
				System.out.println("######OUTPUT#######");
				System.out.println("" + msgText);
				gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;

			} catch (MQException e) {

				if (e.reasonCode == e.MQRC_NO_MSG_AVAILABLE) {
				}

				thereAreMessages = false;
			} catch (IOException e) {
			}
		}
		return msgText;

	}

	public void clearMessage(String Queuename) throws MQException {

		int openOptions = MQC.MQOO_FAIL_IF_QUIESCING | MQC.MQOO_INPUT_SHARED | MQC.MQOO_BROWSE;

		MQQueue queue = qManager.accessQueue(Queuename, openOptions);

		MQMessage theMessage = new MQMessage();
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST;
		gmo.matchOptions = MQC.MQMO_NONE;
		gmo.waitInterval = 5000;

		boolean thereAreMessages = true;
		while (thereAreMessages) {
			try {
				queue.get(theMessage, gmo);
				theMessage.clearMessage();
				gmo.options = CMQC.MQGMO_MSG_UNDER_CURSOR;
				queue.get(theMessage, gmo);
				gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;

			} catch (MQException e) {

				if (e.reasonCode == e.MQRC_NO_MSG_AVAILABLE) {
				}

				thereAreMessages = false;
			} catch (IOException e) {
			}
		}

	}

}
