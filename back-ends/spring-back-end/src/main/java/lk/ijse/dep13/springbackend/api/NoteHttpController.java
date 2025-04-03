package lk.ijse.dep13.springbackend.api;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NoteHttpController {
    @PostMapping
    public String createNote(){
        return "Create a new note";
    }

    @GetMapping
    public String getAllNotes(){
        return "Get all notes";
    }

    @GetMapping("/{id:^\\d+$}")
    public String getNote(@PathVariable Integer id){
        return "Get note " + id;
    }

    @PatchMapping("/{id:^\\d+$}")
    public String updateNote(@PathVariable Integer id){
        return "Update note " + id;
    }

    @DeleteMapping("/{id:^\\d+$}")
    public String deleteNote(@PathVariable Integer id){
        return "Delete note " + id;
    }
}