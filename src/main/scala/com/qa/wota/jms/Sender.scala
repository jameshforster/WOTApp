package com.qa.wota.jms

import entities.MessageContent
import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Session
import javax.jms.DeliveryMode

/**
 * @author jforster
 * Class to initialise the connection to the Backend through JMS and send a formatted message
 */
class Sender {
    val boardName = "WOTS.OUT"
    
    /**
     * Method to send a constructed message to the ActiveMQ Broker
     */
    def sendMessage(toSend:MessageContent) {
      try {
        val connFactory:ActiveMQConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:8081")
        
        val connection = connFactory.createConnection()
        connection.start()
        
        val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
        
        val destination = session.createTopic(boardName)
        
        val producer = session.createProducer(destination)
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)
        
        val message = session.createObjectMessage(toSend)
        
        producer.send(message)
        
        session.close()
        connection.close()
      }
      catch{
        case jmse:Exception => jmse.printStackTrace()
      }
    }
    
}