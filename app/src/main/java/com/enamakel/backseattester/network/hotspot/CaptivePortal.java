package com.enamakel.backseattester.network.hotspot;


import com.enamakel.backseattester.util.Superuser;


public class CaptivePortal {
    public static void start(String host_address) {
        return;
//        Superuser.execute(new String[]{
//                "iptables -A FORWARD -p udp --dport 53 -j ACCEPT",
//                "iptables -A FORWARD -p udp --sport 53 -j ACCEPT",
//                "iptables -t nat -A PREROUTING -p tcp --dport 80 -j DNAT --to-destination " + host_address,
//                "iptables -P FORWARD DROP"
//        });
    }


    public static void stop() {
        Superuser.execute(new String[] {
                "iptables -F",
                "iptables -F FORWARD",
                "iptables -X",
                "iptables -t nat -F"
        });
    }
}
