import akka.actor.{Props, Actor, ActorSystem}
import com.github.sstone.amqp.Amqp.{Delivery, Ack, QueueParameters, Publish}
import com.github.sstone.amqp.{Amqp, RabbitMQConnection}
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.AMQP.BasicProperties
import scala.collection.JavaConverters._

object RabbitMQAkka extends App {
  implicit val system = ActorSystem("mySystem")

  // create an AMQP connection


  //  // create an AMQP connection
  val conn2 = new RabbitMQConnection(host = "localhost", port = 25672, name = "ConnectionRC")

  // create an actor that will receive AMQP deliveries
  val listener2 = system.actorOf(Props(new Actor {
    def receive = {
      case Delivery(consumerTag, envelope, properties, body) => {
        println("got a message: " + new String(body))
        println(consumerTag)
        println(envelope.getRoutingKey)
        println(properties.getHeaders.get("sendFrom:"))
        println(properties)

        sender ! Ack(envelope.getDeliveryTag)
      }
    }
  }))

  // create a consumer that will route incoming AMQP messages to our listener
  val queueParams = QueueParameters("my_queue", passive = false, durable = true, exclusive = false, autodelete = false)
  val consumer = conn2.createConsumer(Amqp.StandardExchanges.amqDirect, queueParams, "my_queue", listener2, None)
  //
  //  // wait till everyone is actually connected to the broker
  Amqp.waitForConnection(system, consumer).await()


  val conn = new RabbitMQConnection(host = "localhost", port = 35672, name = "ConnectionPB")

  // create a "channel owner" on this connection
  val producer = conn.createChannelOwner()

  // wait till everyone is actually connected to the broker
  Amqp.waitForConnection(system, producer).await()

  // send a message
  val properties = new com.rabbitmq.client.AMQP.BasicProperties
  properties.setHeaders(Map("sendFrom:" -> "europe").asJava.asInstanceOf[java.util.Map[String, Object]])

  for (i <- 0 until 5)
    producer ! Publish("f", "", "yo!!".getBytes, properties = Some(properties), mandatory = true, immediate = false)

}