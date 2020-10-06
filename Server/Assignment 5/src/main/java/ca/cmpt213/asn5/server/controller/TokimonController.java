package ca.cmpt213.asn5.server.controller;

import ca.cmpt213.asn5.server.model.Tokimon;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The RestController which responsible for sending and receiving information from
 * the client sides.
 */

@RestController
public class TokimonController {
    private final AtomicLong counter = new AtomicLong(0);
    private List<Tokimon>tokimons = new ArrayList<>();

    @GetMapping("/api/tokimon/all")
    public ResponseEntity<List<Tokimon>> getTokimons(){
        if (tokimons.size() > 0) {
            return new ResponseEntity<>(tokimons, HttpStatus.OK);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Not Found", "There's no Tokimon");
        return new ResponseEntity<>(tokimons, HttpStatus.NOT_FOUND);
    }

    //1st method
    @GetMapping("/api/tokimon/{id}")
    public ResponseEntity<Tokimon> getTokimon(@PathVariable long id){
        for (int i = 0; i < tokimons.size(); i++) {
            if (tokimons.get(i).getId() == id) {
                return new ResponseEntity<>(tokimons.get(i), HttpStatus.OK);
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Not Found", "Such Tokimon doesn't exist in the system");
        return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
    }

    @PostMapping("api/tokimon/add")
    public ResponseEntity<Tokimon> addTokimon(@RequestBody Tokimon newTokimon){
        newTokimon.setId(counter.incrementAndGet());
        tokimons.add(newTokimon);
        writeToJson();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Created", "The Tokimon you just entered has been added to the list");
        return new ResponseEntity<>(tokimons.get(tokimons.size() - 1), headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/tokimon/{id}")
    public ResponseEntity deleteTokimon(@PathVariable long id){
        boolean removed = false;
        for (int i = 0; i < tokimons.size(); i++){
            if (tokimons.get(i).getId() == id){
                tokimons.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) {
            writeToJson();
            HttpHeaders headers = new HttpHeaders();
            headers.add("No Content", "Tokimon (id = " + id + ") has been deleted from the list");
            return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Not Found", "Tokimon (id = " + id + ") is not in the list so we couldn't remove it");
        return  new ResponseEntity(headers, HttpStatus.NOT_FOUND);
    }

    @PostConstruct
    public void init(){
        //For testing purposes
       /* Tokimon toki1 = new Tokimon("toki1", 1.0,1.0, "Fire", 1.0, "Red", counter.incrementAndGet());
        Tokimon toki2 = new Tokimon("toki2", 1.0,1.0, "Fire", 1.0, "Red", counter.incrementAndGet());
        Tokimon toki3 = new Tokimon("toki3", 1.0,1.0, "Fire", 1.0, "Red", counter.incrementAndGet());
        tokimons.add(toki1);
        tokimons.add(toki2);
        tokimons.add(toki3);
        writeToJson();*/

        readFromJson();
    }

    private void writeToJson(){
        try{
            Gson gsonbuilder = new GsonBuilder().create();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("data\\tokimon.json"), tokimons);
        }
        catch (IOException exp){
            exp.printStackTrace();
        }
    }

    private void readFromJson(){
        try {
            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new FileReader("data\\tokimon.json"));
            tokimons = gson.fromJson(reader, new TypeToken<List<Tokimon>>(){}.getType());
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
