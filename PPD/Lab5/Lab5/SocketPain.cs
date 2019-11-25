using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Lab5
{
    enum ConnectionState { BeginConnect };

    class SocketPain
    {
        public static ManualResetEvent connectionDone = new ManualResetEvent(false);
        public static ManualResetEvent receiveDone = new ManualResetEvent(false);

        public static void Received(IAsyncResult ar)
        {
            StateObject stateObject = (StateObject)ar.AsyncState;
            receiveDone.Set();

            int read = stateObject.workSocket.EndReceive(ar);
            stateObject.sb.Append(Encoding.ASCII.GetString(stateObject.buffer, 0, read));

            if (read > 0)
            {
                stateObject.sb.Append(Encoding.ASCII.GetString(stateObject.buffer, 0, read));
                string strContent;
                strContent = stateObject.sb.ToString();
                string regex = "Content-Length: [0-9]+";
                string strFound = System.Text.RegularExpressions.Regex.Match(strContent, regex).ToString();
                Console.WriteLine("Event-based found content length: " + strFound);
                //Console.WriteLine(String.Format("Read {0} byte from socket" + "data = {1} ", strContent.Length, strContent));
            }
            else
            {
                Console.WriteLine("Closing socket..");
                stateObject.workSocket.Close();
            }
        }

        public static void Sent(IAsyncResult ar)
        {
            connectionDone.Set();
            StateObject stateObject = (StateObject)ar.AsyncState;
            stateObject.workSocket.EndSend(ar);
            Console.WriteLine("Socket sent to -> {0} ", stateObject.workSocket.RemoteEndPoint.ToString());
            stateObject.workSocket.BeginReceive(stateObject.buffer, 0, StateObject.BUFFER_SIZE, 0, new AsyncCallback(Received), stateObject);
            Console.WriteLine("Made call to BeginReceive");
        }

        public static void Connected(IAsyncResult ar)
        {
            connectionDone.Set();
            StateObject stateObject = (StateObject)ar.AsyncState;
            stateObject.workSocket.EndConnect(ar);
            Console.WriteLine("Socket connected to -> {0} ", stateObject.workSocket.RemoteEndPoint.ToString());

            var byteData = Encoding.ASCII.GetBytes(HttpHelper.getRequestString(stateObject.host, stateObject.requestPath));
            stateObject.workSocket.BeginSend(byteData, 0, byteData.Length, 0, new AsyncCallback(Sent), stateObject);
            Console.WriteLine("Made call to Send");
        }

        public static void Start()
        {
            StateObject stateObject = new StateObject();
            stateObject.requestPath = stateObject.host.Contains("/") ? stateObject.host.Substring(stateObject.host.IndexOf("/")) : "/";

            IPHostEntry ipHost = Dns.GetHostEntry(stateObject.host);
            IPAddress ipAddress = ipHost.AddressList[0];
            IPEndPoint iPEndPoint = new IPEndPoint(ipAddress, stateObject.httpPort);
            Console.WriteLine("Host Entry {0}", ipAddress);
            
            stateObject.workSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            try
            {
                Console.WriteLine("Establishing Connection to {0}", stateObject.host);
                stateObject.workSocket.BeginConnect(iPEndPoint, new AsyncCallback(Connected), stateObject);

                Console.WriteLine("Waiting for connection signal");
                connectionDone.WaitOne();
                Console.WriteLine("Received signal");

                Console.WriteLine("Waiting for receive signal");
                receiveDone.WaitOne();
                Console.WriteLine("Received signal");
            }
            catch (ArgumentNullException ane)
            {
                Console.WriteLine("ArgumentNullException : {0}", ane.ToString());
            }
            catch (SocketException se)
            {
                Console.WriteLine("SocketException : {0}", se.ToString());
            }
            catch (Exception e)
            {
                Console.WriteLine("Unexpected exception : {0}", e.ToString());
            } 
        }
    }
}
