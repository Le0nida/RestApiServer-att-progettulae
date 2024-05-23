package io.swagger.api;

import io.swagger.model.VulnerableUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vulnerable")
public class VulnerableController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Simulated SQL Injection Vulnerability
    @GetMapping("/api/user-info")
    public List<VulnerableUser> searchUsers(@RequestParam String searchType, @RequestParam String searchTerm) {
        String query = "SELECT * FROM users WHERE " + searchType + " = '" + searchTerm + "'";
        return executeQuery(query);
    }

    private List<VulnerableUser> executeQuery(String query) {
        List<VulnerableUser> users = new ArrayList<>();
        try {
            // Connessione al database (in un ambiente di produzione, utilizzare un pool di connessioni)
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");

            // Esecuzione della query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Elaborazione dei risultati
            while (resultSet.next()) {
                VulnerableUser user = new VulnerableUser();
                //user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                //user.setFullName(resultSet.getString("full_name"));
                user.setGender(resultSet.getString("gender"));
                //user.setBirthdate(resultSet.getString("birthdate"));
                users.add(user);
            }

            // Chiusura delle risorse
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Simulated XSS Vulnerability
    @GetMapping("/old-profile")
    public String xss(@RequestParam String input) {

        return "<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "    <title>Old VulnerableUser Profile</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }\n" +
                "        .container { width: 80%; margin: auto; }\n" +
                "        header, footer { background-color: #f4f4f4; padding: 10px 0; text-align: center; }\n" +
                "        nav { background: #333; color: #fff; padding: 10px 0; text-align: center; }\n" +
                "        nav a { color: #fff; margin: 0 10px; text-decoration: none; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <header>\n" +
                "        <h1>Company Name</h1>\n" +
                "    </header>\n" +
                "    <nav>\n" +
                "        <a href='/home'>Home</a>\n" +
                "        <a href='/old-profile'>Profile</a>\n" +
                "        <a href='/contact'>Contact</a>\n" +
                "    </nav>\n" +
                "    <div class='container'>\n" +
                "        <h2>Old VulnerableUser Profile</h2>\n" +
                "        <p>Welcome, <span id='username'>" + input + "</span>!</p>\n" +
                "        <p>Your role: <span id='userRole'>user</span></p>\n" +
                "        <p>Your email: <span id='userEmail'>" + input + "@example.com</span></p>\n" +
                "        <script>\n" +
                "            // Potentially vulnerable script\n" +
                "            console.log('VulnerableUser input: " + input + "');\n" +
                "        </script>\n" +
                "    </div>\n" +
                "    <footer>\n" +
                "        <p>Contact us at <a href='mailto:support@example.com'>support@example.com</a></p>\n" +
                "    </footer>\n" +
                "</body>\n" +
                "</html>";
    }

    // Define safe commands and their simulated outputs
    private static final Map<String, String> safeCommands = new HashMap<>();
    static {
        safeCommands.put("ls", "file1.txt\nfile2.txt\ndir1\ndir2");
        safeCommands.put("ls -l", "total 4\n-rw-r--r-- 1 user user 0 May 22 12:34 file1.txt\n-rw-r--r-- 1 user user 0 May 22 12:34 file2.txt\n");
        safeCommands.put("ls -a", ".\n..\nfile1.txt\nfile2.txt\ndir1\ndir2\n.hiddenfile");
        safeCommands.put("pwd", "/home/user");
        safeCommands.put("whoami", "admin");
        safeCommands.put("id", "uid=1000(admin) gid=1000(admin) groups=1000(admin),27(sudo)");
        safeCommands.put("date", "Wed May 22 12:34:56 UTC 2024");
        safeCommands.put("uptime", "12:34:56 up 10 days,  3:42,  2 users,  load average: 0.00, 0.01, 0.05");
        safeCommands.put("df -h", "Filesystem      Size  Used Avail Use% Mounted on\n/dev/sda1       50G   25G   25G  50% /");
        safeCommands.put("free -m", "              total        used        free      shared  buff/cache   available\nMem:           2000         600         500         100         900        1200\nSwap:          1000         200         800");
        safeCommands.put("ps aux", "USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND\nroot         1  0.0  0.1  18560  1056 ?        Ss   12:34   0:01 /sbin/init\n");
        safeCommands.put("netstat -tuln", "Active Internet connections (only servers)\nProto Recv-Q Send-Q Local Address           Foreign Address         State\ntcp        0      0 0.0.0.0:80              0.0.0.0:*               LISTEN\n");
        safeCommands.put("ifconfig", "eth0      Link encap:Ethernet  HWaddr 00:0c:29:4f:8e:35  \n          inet addr:192.168.0.100  Bcast:192.168.0.255  Mask:255.255.255.0\n          inet6 addr: fe80::20c:29ff:fe4f:8e35/64 Scope:Link\n          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1\n          RX packets:308 errors:0 dropped:0 overruns:0 frame:0\n          TX packets:268 errors:0 dropped:0 overruns:0 carrier:0\n          collisions:0 txqueuelen:1000 \n          RX bytes:34292 (34.2 KB)  TX bytes:29268 (29.2 KB)");
        safeCommands.put("ping -c 1 google.com", "PING google.com (172.217.164.110) 56(84) bytes of data.\n64 bytes from 172.217.164.110: icmp_seq=1 ttl=54 time=15.6 ms\n\n--- google.com ping statistics ---\n1 packets transmitted, 1 received, 0% packet loss, time 0ms\nrtt min/avg/max/mdev = 15.602/15.602/15.602/0.000 ms");
        // Add more safe commands as needed
    }

    // Define dangerous commands
    private static final Map<String, String> dangerousCommands = new HashMap<>();
    static {
        dangerousCommands.put("sudo", "Error: Permission denied.");
        dangerousCommands.put("rm -rf /", "Error: Permission denied.");
        dangerousCommands.put("shutdown -h now", "Error: Permission denied.");
        dangerousCommands.put("reboot", "Error: Permission denied.");
        dangerousCommands.put("dd if=/dev/zero of=/dev/sda", "Error: Permission denied.");
        dangerousCommands.put("mkfs.ext4 /dev/sda1", "Error: Permission denied.");
        dangerousCommands.put("iptables -F", "Error: Permission denied.");
        // Add more dangerous commands as needed
    }

    // Simulated Remote Code Execution (RCE) Vulnerability
    @GetMapping("/execute-command")
    public ResponseEntity<String> executeCommand(@RequestParam String command) {
        // Log the attempted RCE
        System.out.println("Attempted RCE with command: " + command);

        String simulatedOutput;
        if (safeCommands.containsKey(command)) {
            simulatedOutput = safeCommands.get(command);
        } else simulatedOutput = dangerousCommands.getOrDefault(command, "Error: Command not recognized.");

        // Return a more complex and realistic HTML page
        return ResponseEntity.ok("<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "    <title>Admin Command Execution</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }\n" +
                "        .container { width: 80%; margin: auto; }\n" +
                "        header, footer { background-color: #f4f4f4; padding: 10px 0; text-align: center; }\n" +
                "        nav { background: #333; color: #fff; padding: 10px 0; text-align: center; }\n" +
                "        nav a { color: #fff; margin: 0 10px; text-decoration: none; }\n" +
                "        pre { background-color: #eee; padding: 10px; border: 1px solid #ccc; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <header>\n" +
                "        <h1>Admin Panel</h1>\n" +
                "    </header>\n" +
                "    <nav>\n" +
                "        <a href='/home'>Home</a>\n" +
                "        <a href='/old-admin/execute-command'>Execute Command</a>\n" +
                "        <a href='/contact'>Contact</a>\n" +
                "    </nav>\n" +
                "    <div class='container'>\n" +
                "        <h2>Command Execution</h2>\n" +
                "        <p>Command: <strong>" + command + "</strong></p>\n" +
                "        <p>Output:</p>\n" +
                "        <pre>" + simulatedOutput + "</pre>\n" +
                "        <footer>\n" +
                "            <p>Contact us at <a href='mailto:support@example.com'>support@example.com</a></p>\n" +
                "        </footer>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");
    }

    private static final Map<String, String> fakeFileContents = new HashMap<>();
    static {
        fakeFileContents.put("/etc/passwd", "root:x:0:0:root:/root:/bin/bash\nuser:x:1000:1000:user:/home/user:/bin/bash\n");
        fakeFileContents.put("/etc/shadow", "root:$6$randomsalt$encryptedpassword:18000:0:99999:7:::\nuser:$6$randomsalt$encryptedpassword:18000:0:99999:7:::\n");
        fakeFileContents.put("/var/www/html/index.php", "<?php\n// Sample PHP file\necho 'Hello, world!';\n?>");
        fakeFileContents.put("/var/log/auth.log", "May 22 12:34:56 localhost sshd[12345]: Accepted password for user from 192.168.0.100 port 22 ssh2");
        fakeFileContents.put("/home/user/.ssh/id_rsa", "-----BEGIN RSA PRIVATE KEY-----\nMIIEpAIBAAKCAQEArandomfakekeydata\n-----END RSA PRIVATE KEY-----");
        fakeFileContents.put("/home/user/Documents/secret.txt", "VGhpcyBpcyBhIHNhbXBsZSBzZWNyZXQgZG9jdW1lbnQuIFBsZWFzZSBjb250YWN0IHlvdXIgc3lzdGVtIGFkbWluaXN0cmF0b3IgZm9yIG1vcmUgaW5mb3JtYXRpb24u");
        // Add more fake file paths and contents as needed
    }

    // Simulated Directory Traversal Vulnerability
    @GetMapping("/dir-traversal")
    public ResponseEntity<String> directoryTraversal(@RequestParam String filePath) {
        // Log the attempted Directory Traversal
        System.out.println("Attempted Directory Traversal with filePath: " + filePath);

        String fakeFileContent = fakeFileContents.getOrDefault(filePath, "Fake file content for " + filePath);

        // Return a more complex and realistic HTML page
        return ResponseEntity.ok("<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "    <title>File Viewer</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }\n" +
                "        .container { width: 80%; margin: auto; }\n" +
                "        header, footer { background-color: #f4f4f4; padding: 10px 0; text-align: center; }\n" +
                "        nav { background: #333; color: #fff; padding: 10px 0; text-align: center; }\n" +
                "        nav a { color: #fff; margin: 0 10px; text-decoration: none; }\n" +
                "        pre { background-color: #eee; padding: 10px; border: 1px solid #ccc; white-space: pre-wrap; word-wrap: break-word; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <header>\n" +
                "        <h1>File Viewer</h1>\n" +
                "    </header>\n" +
                "    <nav>\n" +
                "        <a href='/home'>Home</a>\n" +
                "        <a href='/old-admin/dir-traversal'>File Viewer</a>\n" +
                "        <a href='/contact'>Contact</a>\n" +
                "    </nav>\n" +
                "    <div class='container'>\n" +
                "        <h2>Viewing File: " + filePath + "</h2>\n" +
                "        <pre>" + fakeFileContent + "</pre>\n" +
                "        <footer>\n" +
                "            <p>Contact us at <a href='mailto:support@example.com'>support@example.com</a></p>\n" +
                "        </footer>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");
    }

    private static final Map<String, String> dangerousActions = new HashMap<>();
    static {
        dangerousActions.put("deleteUser", "Simulated: VulnerableUser account deleted.");
        dangerousActions.put("shutdownSystem", "Simulated: System shutdown.");
        dangerousActions.put("reformatDisk", "Simulated: Disk reformatted.");
        dangerousActions.put("disableNetwork", "Simulated: Network disabled.");
    }

    @PostMapping("/deserialize")
    public ResponseEntity<String> insecureDeserialization(@RequestBody byte[] serializedObject) {
        // Log the attempted Deserialization
        System.out.println("Attempted Insecure Deserialization");

        String simulatedOutput;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedObject);
             ObjectInputStream ois = new ObjectInputStream(bis)) {

            // Simulate deserialization
            Object deserializedObject = ois.readObject();
            System.out.println("Deserialized object: " + deserializedObject);

            // Check if the deserialized object has a dangerous action
            if (deserializedObject instanceof Map) {
                Map<String, String> deserializedMap = (Map<String, String>) deserializedObject;
                String action = deserializedMap.get("action");

                if (dangerousActions.containsKey(action)) {
                    simulatedOutput = dangerousActions.get(action);
                } else {
                    simulatedOutput = "Deserialized object: " + deserializedMap.toString();
                }
            } else {
                simulatedOutput = "Deserialized object of type: " + deserializedObject.getClass().getName();
            }
        } catch (Exception e) {
            simulatedOutput = "Error during deserialization: " + e.getMessage();
        }

        // Return a more complex and realistic HTML page
        return ResponseEntity.ok("<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "    <title>Deserialization Result</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }\n" +
                "        .container { width: 80%; margin: auto; }\n" +
                "        header, footer { background-color: #f4f4f4; padding: 10px 0; text-align: center; }\n" +
                "        nav { background: #333; color: #fff; padding: 10px 0; text-align: center; }\n" +
                "        nav a { color: #fff; margin: 0 10px; text-decoration: none; }\n" +
                "        pre { background-color: #eee; padding: 10px; border: 1px solid #ccc; white-space: pre-wrap; word-wrap: break-word; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <header>\n" +
                "        <h1>Deserialization Result</h1>\n" +
                "    </header>\n" +
                "    <nav>\n" +
                "        <a href='/home'>Home</a>\n" +
                "        <a href='/old-admin/deserialize'>Deserialize</a>\n" +
                "        <a href='/contact'>Contact</a>\n" +
                "    </nav>\n" +
                "    <div class='container'>\n" +
                "        <h2>Deserialized Object:</h2>\n" +
                "        <pre>" + simulatedOutput + "</pre>\n" +
                "        <footer>\n" +
                "            <p>Contact us at <a href='mailto:support@example.com'>support@example.com</a></p>\n" +
                "        </footer>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");
    }
}

