package com.qa.James.JMS

import entities.MessageContent
import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Session
import javax.jms.DeliveryMode

/**
 * @author jforster
 */
class Sender {
    val boardName = "WOTS.OUT"
    
    def sendMessage(toSend:MessageContent) {
      try {
        val connFactory:ActiveMQConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost")
        
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