import java.awt.*;
import java.util.*;
import java.util.List;

public class BruteForceDetector {

    public static List<String> detectBruteForce(String[] logs) {

        /* Var */
        Map<String, Integer> ips = new HashMap<>();
        Set<String>failureIps = new HashSet<>();

        for(String log : logs){

            // We split the logs string in 3 parts
            String[] text = log.split("\\s+");

            String ip = text[0];
            String status = text[1];

            if(status.equals("LOGIN_FAIL")){
                ips.put(ip, ips.getOrDefault(ip, 0) + 1);
                if(ips.get(ip) == 3){
                    failureIps.add(ip);
                }
            } else if(status.equals("LOGIN_SUCCESS")){
                ips.put(ip, 0);
            }
        }

        List<String> ipsError = new ArrayList<>(failureIps);

        Collections.sort(ipsError);

        return ipsError;
    }

    public static void main(String[] args) {
        String[] logs = {"9.9.9.9 LOGIN_FAIL user=a", "1.1.1.1 LOGIN_FAIL user=b", "9.9.9.9 LOGIN_FAIL user=a", "1.1.1.1 LOGIN_FAIL user=b", "9.9.9.9 LOGIN_FAIL user=a", "1.1.1.1 LOGIN_FAIL user=b"};
        System.out.println(detectBruteForce(logs));
    }
}


/*
You're analyzing authentication logs. Each log entry is a string like:

192.168.1.1 LOGIN_FAIL user=admin
192.168.1.1 LOGIN_SUCCESS user=admin
10.0.0.5 LOGIN_FAIL user=root

An IP is suspicious if it has 3 or more consecutive failures without a success in between. Return a list of suspicious IPs, sorted alphabetically.

logs = [
    "192.168.1.1 LOGIN_FAIL user=admin",
    "192.168.1.1 LOGIN_FAIL user=admin",
    "192.168.1.1 LOGIN_FAIL user=root",
    "10.0.0.5 LOGIN_FAIL user=test",
    "10.0.0.5 LOGIN_SUCCESS user=test"
]
detect_brute_force(logs)  # ["192.168.1.1"]

The 10.0.0.5 IP had a failure then a success, so its streak reset. The 192.168.1.1 IP hit 3 failures in a row - busted. Only respond with a list of the suspicious IPs.

A success resets that IP's failure count to zero. Empty list returns empty list.

PS. You do not need to validate the IP addresses.
 */
