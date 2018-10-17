package com.example.java.file;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatchServiceDemo {

    @Test
    public void name() throws IOException, InterruptedException {

        Path path = Paths.get("C:\\Users\\liucongcong\\Desktop\\aaa");
        WatchService watcher = FileSystems.getDefault().newWatchService();
        path.register(watcher,ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        for (;;) {
            // retrieve key
            WatchKey key = watcher.take();

            // process events
            for (WatchEvent<?> event: key.pollEvents()) {
                System.out.println("kind" + event.kind());
            }

            // reset the key
            boolean valid = key.reset();
            if (!valid) {
                // object no longer registered
            }
        }
    }

    @Test
    public void test2() throws IOException {
        Path file = Paths.get("C:\\Users\\liucongcong\\Desktop\\aaa");
        BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
        try {
            ReflectionUtils.doWithFields(attrs.getClass(),field -> {
                if(!field.isAccessible()){
                    field.setAccessible(true);
                }
                if("fileAttrs".equals(field.getName())){
                    System.out.println(field.get(attrs));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
