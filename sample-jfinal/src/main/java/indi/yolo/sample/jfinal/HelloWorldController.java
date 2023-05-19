package indi.yolo.sample.jfinal;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yoloz
 */
@RestController
public class HelloWorldController {

    @GetMapping("/")
    public void helloWorld() {
        Record record =  Db.findFirst("select 1 from dual");
        System.out.println(record.getStr("1"));
    }
}
