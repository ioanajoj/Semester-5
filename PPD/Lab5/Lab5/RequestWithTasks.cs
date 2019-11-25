using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Lab5
{
    class RequestWithTasks
    {
        public static void Request()
        {
            StateObject stateObject = new StateObject();
            stateObject.requestPath = stateObject.host.Contains("/") ? stateObject.host.Substring(stateObject.host.IndexOf("/")) : "/";

            IPHostEntry ipHost = Dns.GetHostEntry(stateObject.host);
            IPAddress ipAddress = ipHost.AddressList[0];
            IPEndPoint iPEndPoint = new IPEndPoint(ipAddress, stateObject.httpPort);
            Console.WriteLine("Host Entry {0}", ipAddress);

            stateObject.workSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            var connect = Connect(stateObject, iPEndPoint);
            var send = connect.ContinueWith((x) =>
            {
                return Send(stateObject);
            }).Unwrap();
            var receive = send.ContinueWith((x) =>
            {
                return Receive(stateObject);
            }).Unwrap();
            receive.ContinueWith((read) =>
            {
                return Task.Factory.StartNew(() =>
                {
                    stateObject.sb.Append(Encoding.ASCII.GetString(stateObject.buffer, 0, read.Result));
                    string strContent;
                    strContent = stateObject.sb.ToString();
                    string regex = "Content-Length: [0-9]+";
                    string strFound = System.Text.RegularExpressions.Regex.Match(strContent, regex).ToString();
                    Console.WriteLine("Task based found content length: " + strFound);
                });
            });
            receive.Wait();
        }

        static Task<string> Connect(StateObject stateObject, IPEndPoint IPEndPoint)
        {
            return Task<String>.Factory.StartNew(() =>
            {
                stateObject.workSocket.Connect(IPEndPoint);
                return "Connected";
            });
        }

        static Task<string> Send(StateObject stateObject)
        {
            return Task<String>.Factory.StartNew(() =>
            {
                var byteData = Encoding.ASCII.GetBytes(HttpHelper.getRequestString(stateObject.host, stateObject.requestPath));
                stateObject.workSocket.Send(byteData, 0, byteData.Length, 0);
                return "Sent request";
            });
        }

        static Task<int> Receive(StateObject stateObject)
        {
            return Task<int>.Factory.StartNew(() =>
            {
                return stateObject.workSocket.Receive(stateObject.buffer, 0, StateObject.BUFFER_SIZE, 0);
            });
        }        

    }
}
