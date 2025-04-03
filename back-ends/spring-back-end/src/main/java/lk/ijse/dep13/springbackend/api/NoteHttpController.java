package lk.ijse.dep13.springbackend.api;


import lk.ijse.dep13.springbackend.entity.Note;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

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
    public List<Note> getAllNotes(@SessionAttribute("user") String email) {
        if (email == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                "This operation only supports for authenticated users");
        try(var stm = connection
                .prepareStatement("SELECT * FROM note WHERE \"user\"=?")){
            LinkedList<Note> noteList = new LinkedList<>();
            stm.setString(1, email);
            ResultSet rst = stm.executeQuery();

            while (rst.next()) {
                int id = rst.getInt("id");
                String text = rst.getString("text");
                String color = rst.getString("color");
                noteList.add(new Note(id, text, color));
            }

            return noteList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id:^\\d+$}")
    public Note getNote(@PathVariable Integer id, @SessionAttribute("user") String email) {
        if (email == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                "This operation only supports for authenticated users");
        try (var stm = connection
                .prepareStatement("SELECT * FROM note WHERE id=? AND \"user\"=?")) {
            stm.setInt(1, id);
            stm.setString(1, email);
            ResultSet rst = stm.executeQuery();
            if (!rst.next()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            String text = rst.getString("text");
            String color = rst.getString("color");
            return new Note(id, text, color);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id:^\\d+$}")
    public String updateNote(@PathVariable Integer id){
        return "Update note " + id;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id:^\\d+$}")
    public void deleteNote(@PathVariable Integer id, @SessionAttribute("user") String email) throws SQLException {
        if (email == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                "This operation only supports for authenticated users");
        try(var stm = connection.prepareStatement("DELETE FROM note WHERE id=? AND \"user\"=?")){
            stm.setInt(1, id);
            stm.setString(2, email);
            stm.executeUpdate();
        }
    }
}