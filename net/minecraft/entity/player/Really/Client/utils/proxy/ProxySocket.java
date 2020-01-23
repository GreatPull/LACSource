package net.minecraft.entity.player.Really.Client.utils.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.Proxy.Type;

public class ProxySocket {
   public static Socket connectOverProxy(String proxyAdress, int proxyPort, String destAddress, int destPort) throws Exception {
      Proxy proxy = new Proxy(Type.SOCKS, new InetSocketAddress(proxyAdress, proxyPort));
      Socket returnment = new Socket(proxy);
      returnment.setTcpNoDelay(true);
      returnment.connect(new InetSocketAddress(destAddress, destPort));
      return returnment;
   }
}
