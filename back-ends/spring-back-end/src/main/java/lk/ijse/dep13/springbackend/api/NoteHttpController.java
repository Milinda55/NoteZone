package lk.ijse.dep13.springbackend.api;


import lk.ijse.dep13.springbackend.entity.Note;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/notes")
public class NoteHttpController {

    private final Connection connection;

    public NoteHttpController(Connection connection) {
        this.connection = connection;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public Note createNote(@SessionAttribute("user") String email, @RequestBody Note note) throws SQLException {
        if (email == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                "This operation only supports for authenticated users");
        try(var stm = connection
                .prepareStatement("INSERT INTO note (text, \"user\", color) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)){
            stm.setString(1, note.getText());
            stm.setString(2, email);
            stm.setString(3, note.getColor());
            stm.executeUpdate();
            ResultSet rst = stm.getGeneratedKeys();
            rst.next();
            int id = rst.getInt(1);
            note.setId(id);
            return note;
        }
    }



    @GetMapping
    public String getAllNotes(){
        return "Get all notes";
    }

    @GetMapping("/{id:^\\d+$}")
    public String getNote(@PathVariable Integer id){
        return "Get note " + id;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id:^\\d+$}")
    public String updateNote(@PathVariable Integer id){
        return "Update note " + id;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id:^\\d+$}")
    public String deleteNote(@PathVariable Integer id){
        return "Delete note " + id;
    }
}