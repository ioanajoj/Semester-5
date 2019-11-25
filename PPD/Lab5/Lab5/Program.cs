using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Lab5
{

    public class StateObject
    {
        public string host = "google.com";
        public string requestPath;
        public int httpPort = 80;
        public Socket workSocket = null;
        public const int BUFFER_SIZE = 1024;
        public byte[] buffer = new byte[BUFFER_SIZE];
        public StringBuilder sb = new StringBuilder();
    }

    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("\nBegin Event based Reuqst");
            SocketPain.Start();

            Console.WriteLine("\nBegin Request with tasks:");
            RequestWithTasks.Request();

            Console.WriteLine("\nBegin Request with Async/Await mechanism");
            RequestWithAsyncAwait.Request();

            // Keep console open
            Console.ReadLine();
        }
    }
}
