package io.swagger.api;

import io.swagger.model.User;
import io.swagger.model.VulnerableUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.*;

@Controller
@RequestMapping("/management")
public class VulnerableController {

    @Autowired
    private UserRepository userRepository;

    // Simulated SQL Injection Vulnerability
    @GetMapping("/users-info")
    public String searchUsersPage(Model model) {
        // Populate model with initial data
        model.addAttribute("searchTypes", Arrays.asList("username", "email", "fullName", "gender", "birthdate"));
        model.addAttribute("users", new ArrayList<>()); // Initial empty list of users
        return "vulnerabilities/users-info";
    }

    @PostMapping("/users-info")
    public String searchUsers(@RequestParam String searchType, @RequestParam String searchTerm, Model model) {

        // Construct the SQL query with potential SQL injection vulnerability
        String query = "SELECT * FROM users WHERE " + searchType + " = '" + searchTerm + "'";

        // Execute the potentially vulnerable query
        List<VulnerableUser> users = executeQuery(query);

        // Add the retrieved users to the model
        model.addAttribute("searchTypes", Arrays.asList("username", "email", "fullName", "gender", "birthdate"));
        model.addAttribute("users", users);

        // Return the template name for rendering
        return "vulnerabilities/users-info";
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
    @GetMapping("/userprofile")
    public String xss(@RequestParam String username, Model model) {
        boolean userExists = userRepository.existsByUsername(username);

        User user;
        if (userExists) {
            user = userRepository.findByUsername(username).get(0); // Presumendo che `findByUsername` ritorni una lista
        } else {
            user = new User();
            user.setUsername(username);
        }

        model.addAttribute("userExists", userExists);
        model.addAttribute("user", user);

        return "vulnerabilities/old-profile";
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
    @GetMapping("/command-exec")
    public ResponseEntity<String> executeCommand(@RequestParam String command, @RequestParam(required = false) String pin) {

        // Check if the command is safe or dangerous
        boolean isSafeCommand = safeCommands.containsKey(command);
        boolean isDangerousCommand = dangerousCommands.containsKey(command);

        // If it's a dangerous command and a pin is required
        if (isDangerousCommand && pin == null) {
            return ResponseEntity.ok("Error: A PIN is required to execute dangerous commands.");
        }

        // If it's a dangerous command and the pin is incorrect
        if (isDangerousCommand) {
            return ResponseEntity.ok("Error: Incorrect PIN. Command execution not authorized.");
        }

        // If it's a dangerous command and the pin is correct, or if it's a safe command
        String simulatedOutput;
        if (isSafeCommand) {
            simulatedOutput = safeCommands.get(command);
        } else {
            simulatedOutput = "Error: Command not recognized.";
        }

        // Return the simulated output as plain text
        return ResponseEntity.ok(simulatedOutput);
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
    @GetMapping("/file")
    public ResponseEntity<String> directoryTraversal(@RequestParam String filePath) {
        // Retrieve the fake file content
        String fakeFileContent = fakeFileContents.getOrDefault(filePath, "File not found: " + filePath);

        // Return the content as plain text
        return ResponseEntity.ok(fakeFileContent);
    }



    @GetMapping("/user-management")
    public ResponseEntity<String> userManagement() {
        return deprecatedResourcePage();
    }

    @GetMapping("/user-profile")
    public ResponseEntity<String> userProfile() {
        return deprecatedResourcePage();
    }

    @GetMapping("/server-commands")
    public ResponseEntity<String> serverCommands() {
        return deprecatedResourcePage();
    }

    @GetMapping("/file-access")
    public ResponseEntity<String> fileAccess() {
        return deprecatedResourcePage();
    }

    @GetMapping("/admin-dashboard")
    public ResponseEntity<String> adminDashboard() {
        return deprecatedResourcePage();
    }

    @GetMapping("/user-logs")
    public ResponseEntity<String> userLogs() {
        return deprecatedResourcePage();
    }

    private ResponseEntity<String> deprecatedResourcePage() {
        String htmlContent = "<html><body><h1>Resource Not Managed</h1><p>This resource is no longer managed.</p></body></html>";
        return ResponseEntity.ok(htmlContent);
    }
}

