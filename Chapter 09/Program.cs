using System;
using System.Net;
using System.Net.Sockets;

namespace CSharpSocket
{
    class CSharpClient
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Client Started");
            IPEndPoint serverAddress = new IPEndPoint(IPAddress.Parse("127.0.0.1"), 5000);
            Socket clientSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            clientSocket.Connect(serverAddress);

            Console.Write("Enter message: ");
            String message = Console.ReadLine();

            byte[] messageBuffer;
            messageBuffer = System.Text.Encoding.UTF8.GetBytes(message + "\n");
            clientSocket.Send(messageBuffer);

            byte[] receiveBuffer = new byte[32];
            clientSocket.Receive(receiveBuffer);
            String recievedMessage = System.Text.Encoding.ASCII.GetString(receiveBuffer);
            Console.WriteLine("Client received: [" + recievedMessage + "]");

            clientSocket.Close();
            Console.WriteLine("Client Terminated");
        }
    }
}
