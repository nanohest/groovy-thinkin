import java.util.concurrent.atomic.AtomicInteger

public class RethinkConnection {
   def token = new AtomicInteger()
   def RethinkSocket socket
   def response

   def open ( hostname = 'localhost' , port = 28015 ) {
      newToken()
      socket   = new RethinkSocket()
      response = socket.open( hostname , port )
   }

   def leftShift ( String parameter ) {
      response = socket.writeString parameter, token.longValue() 
   }

   def stopOperation () {
      
   }

   def close () {
      response = socket.close()
   }

   def newToken () {
      token.incrementAndGet()
      response = "Updated token. New value: " + token.intValue()
   }

   def getResponse() {
      return response
   }

}
