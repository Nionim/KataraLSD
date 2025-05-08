package delta.cion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Pinger {

    private static final String URL = "https://presence.roproxy.com/v1/presence/users";
    private static final String JSON_PAYLOAD = "{\"userIds\":[%s]}";

    public static void ping() {
        if (Util.isSleepTime()) return;
        for (String s : Util.getIds()) {
            long i = Long.parseLong(Util.getID(s));
            String n = Util.getName(s);
            pinger(n, i);
        }
    }

    public static void PING() {
        for (String s : Util.getIds()) {
            long i = Long.parseLong(Util.getID(s));
            String n = Util.getName(s);
            pinger(n, i);
        }
    }

    public static String parseData(JSONObject o) {
        List<String> l = new ArrayList<>();

        l.add(o.optString("userId", "N/A"));
        l.add(o.optString("lastOnline", "N/A"));
        l.add(o.optString("invisibleModeExpiry", "N/A"));
        l.add(o.optString("lastLocation", "N/A"));
        l.add(o.optString("gameId", "Not in a game"));
        l.add(o.optString("userPresenceType", "-1"));

        return ConnectInfo(l);
    }

    public static String ConnectInfo(List<String> l) {
        return String.format("""
                User-ID: %s
                Last-Online: %s
                Invisible-Mode: %s
                Last-Location: %s
                Game-ID: %s
                User-Activity: %s
                """,
                l.get(0),
                l.get(1),
                l.get(2),
                l.get(3),
                l.get(4),
                parseActivity(l.get(5), l.get(4))
        );
    }

    private static String parseActivity(String s, String ss) {
        int i = Integer.parseInt(s);
        switch (i) {
            case 0 -> {return "Offline";}
            case 1-> {return String.format("Playing a game (%s)",ss);}
            case 2-> {return "In Roblox Studio";}
            case 3-> {return "On Roblox Website";}
            default-> {return "Unknown Status";}
        }
    }

    public static void pinger(String n, long i) {
        String rs = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = String.format(JSON_PAYLOAD, i).getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();

            if (status == 200) {
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                }

                rs = response.toString();
            } else {
                System.err.println("Ошибка запроса. Код состояния: " + status);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }



        JSONObject root = new JSONObject(rs);
        JSONArray u = root.optJSONArray("userPresences");
        JSONObject DATA = u.getJSONObject(0);

        String finallyData = parseData(DATA);
        String m = String.format("User-Name: %s\n%s", n, finallyData);
        Sender.sendTelegram(m);
    }
}
