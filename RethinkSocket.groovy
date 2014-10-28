import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.SocketChannel
import java.net.InetSocketAddress

public class RethinkSocket {
   def version = 0x5f75e83e   //V0_3
   def aklen   = 0x00000000
   def authkey = ""
   def proto   = 0x7e6970c7   //JSON

   def SocketChannel socketChannel

   def open ( hostname , port ) {
      socketChannel = SocketChannel.open()
      socketChannel.configureBlocking(true)
      socketChannel.connect(new InetSocketAddress(hostname, port))
      writeLEInt version
      writeLEInt aklen
      writeLEInt proto
      def response = new String(_read(5000).array())  // apply error handling instead
      return response
   }

   def close ( ) {
      socketChannel.close( )
   }


   def writeLEInt(int i) {
     def buffer = ByteBuffer.allocate(4)
     buffer.order(ByteOrder.LITTLE_ENDIAN)
     buffer.putInt(i)
     _write(buffer)
   }

   def writeString(String s, long token) {
     writeToken token
     writeLEInt s.length()
     def buffer = ByteBuffer.allocate(s.length())
     buffer.order(ByteOrder.LITTLE_ENDIAN);
     buffer.put(s.getBytes());
     _write(buffer)
     return new String(_read(5000).array())  
   }

   def writeToken(long i) {
     def buffer = ByteBuffer.allocate(8)
     buffer.order(ByteOrder.LITTLE_ENDIAN)
     buffer.putLong(i)
     _write(buffer)
   }

   def _write(ByteBuffer buffer) {
     try {
       buffer.flip()
       while (buffer.hasRemaining()) {
         socketChannel.write(buffer)
       }
     } catch (IOException e) {
         println e.getMessage()
     }
   }

   def ByteBuffer _read(int i) {
     def buffer = ByteBuffer.allocate(i)
     buffer.order(ByteOrder.LITTLE_ENDIAN)
     try {
         int read = socketChannel.read(buffer)
         buffer.flip()
         return buffer
     } catch (IOException e) {
         println e.getMesssage()
     }
   }
}
