package com.example.spring.yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.junit.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.DumperOptions.ScalarStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

/**
 * Date:     2019年01月03日 15:42 <br/>
 *
 * @author lcc
 * @see
 * @since
 */
public class YamlDemo {

  @Test
  public void name() throws IOException {
    DumperOptions dumperOptions = new DumperOptions();
    dumperOptions.setPrettyFlow(true);
    dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
    Representer representer = new Representer();
    Yaml yaml = new Yaml(representer, dumperOptions);
    representer.addClassTag(User.class, Tag.MAP);
    User user = new User();
    user.setUsername("123");
    user.setAddress("tet");
    UserDetail userDetail = new UserDetail();
    userDetail.setAge("123");
    user.getUserDetail().put("123", userDetail);
    Map<String, User> map = new HashMap<>();
    map.put("abc", user);
    yaml.dump(map, new FileWriter("test.yaml"));
  }

  @Data
  public class User {

    private String username;
    private String address;
    private Map<String,UserDetail> userDetail = new HashMap<>();
  }

  @Data
  public class UserDetail {
    private String age;
  }
}
