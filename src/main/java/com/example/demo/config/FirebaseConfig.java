//package com.example.demo.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Configuration
//public class FirebaseConfig {
//
//    @Value("${firebase.config.path}")
//    private String firebaseConfigPath;
//
//    @PostConstruct
//    public void initialize() throws IOException {
//        FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        FirebaseApp.initializeApp(options);
//    }
//}
package com.example.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() throws IOException {
        InputStream serviceAccount;

        // Kiểm tra xem tệp có phải là đường dẫn tới file trên hệ thống hay không
        if (firebaseConfigPath.startsWith("classpath:")) {
            serviceAccount = getClass().getClassLoader().getResourceAsStream(firebaseConfigPath.replace("classpath:", ""));
        } else {
            // Nếu đường dẫn không phải classpath, kiểm tra xem file có tồn tại trên hệ thống không
            if (Files.exists(Paths.get(firebaseConfigPath))) {
                serviceAccount = Files.newInputStream(Paths.get(firebaseConfigPath));
            } else {
                throw new IOException("Firebase config file not found at path: " + firebaseConfigPath);
            }
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}


